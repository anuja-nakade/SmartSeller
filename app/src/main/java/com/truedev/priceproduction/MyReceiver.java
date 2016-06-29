package com.truedev.priceproduction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Yash on 09/09/15.
 */
public class MyReceiver extends ParsePushBroadcastReceiver {
    private static final int LEAD_NOTIFICATION_ID= 123;
    private static final int WON_NOTIFICATION_ID= 124;
    private static final int LOST_NOTIFICATION_ID= 125;
    public static final String ACTION_PUSH_OPEN = "com.msp.sellers.push.intent.OPEN";
    public static final String ACTION_PUSH_DELETE = "com.msp.sellers.push.intent.DELETE";



    @Override
    protected Notification getNotification(Context context, Intent intent) {
        //return super.getNotification(context, intent);


        try {
            JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));

            if (pushData != null && pushData.has("type")) {
                String notificationType = pushData.getString("type");
                if ("lead_notification".equals(notificationType)) {

                    String customerName = pushData.optString("customer_name");
                    String productName = pushData.optString("product_name");
                    String price = pushData.optString("price");
                    String contactNumber = pushData.optString("contact_no");
                    String productId = pushData.optString("product_id");
                    if (!contactNumber.startsWith("0") && !contactNumber.startsWith("+91")) {
                        contactNumber = "+91" + contactNumber;
                    }

                    Intent ordersIntent = new Intent(context, MainActivity.class);
                    ordersIntent.setAction("test");
                    ordersIntent.putExtra("notificationType", notificationType);
                    String action = "myOrders";
                    String url = "http://sellers.mysmartprice.com/my_orders.php";
                    //String url = "http://sellers.ravindra.mysmartprice.com/my_orders.php";
                    ordersIntent.putExtra("url", url);
                    ordersIntent.putExtra("action", action);
                    PendingIntent pOrdersIntent = PendingIntent.getActivity(context, 0, ordersIntent, 0);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + contactNumber));
                    PendingIntent pCallIntent = PendingIntent.getActivity(context, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Intent outOfStockIntent = new Intent(context, MainActivity.class);
                    outOfStockIntent.setAction(productId);
                    outOfStockIntent.putExtra("notificationType", notificationType);
                    action = "outOfStock";
                    url = "http://sellers.mysmartprice.com/mark_out_of_stock.php";
                    //url = "http://sellers.ravindra.mysmartprice.com/mark_out_of_stock.php";
                    outOfStockIntent.putExtra("url", url);
                    outOfStockIntent.putExtra("action", action);
                    outOfStockIntent.putExtra("mspid", productId);
                    outOfStockIntent.putExtra("productName", productName);
                    PendingIntent pOutOfStockIntent = PendingIntent.getActivity(context, 0, outOfStockIntent, 0);

                    //NotificationManager mNotifM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.msp_launch)
                                    .setContentTitle("MySmartPrice Leads")
                                    .setContentText(" You Got a Lead, Hurry Up!!!")
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText(
                                            "New Lead : " + productName + "@ Rs." + price + "\n" + "Customer Name: " + customerName + "\n" + "Contact: " + contactNumber))
                                    .addAction(0, "Call", pCallIntent)
                                    .addAction(0, "out of stock", pOutOfStockIntent)
                                    .setAutoCancel(true)
                                    .setDefaults(-1);

                    mBuilder.setContentIntent(pOrdersIntent);
                    return null;
                    //return mBuilder.build();
                } else if ("cpl_notification".equals(notificationType)) {

                    //leadid, username, title with colour,
                    String leadId = pushData.optString("leadID");
                    String title = pushData.optString("title");
                    String color = pushData.optString("color");
                    String action = null;
                    String url;

                    Intent outOfStockIntent = new Intent(context, MainActivity.class);
                    outOfStockIntent.setAction(leadId);
                    outOfStockIntent.putExtra("notificationType", notificationType);
                    action = "outOfStockLead";
                    url = "http://sellers.mysmartprice.com/cpl/update_bid.php";
                    //url = "http://sellers.ravindra.mysmartprice.com/mark_out_of_stock.php";
                    outOfStockIntent.putExtra("url", url);
                    outOfStockIntent.setAction(action);
                    outOfStockIntent.putExtra("leadid", leadId);
                    PendingIntent pOutOfStockIntent = PendingIntent.getActivity(context, 0, outOfStockIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                    Intent bidIntent = new Intent(ACTION_PUSH_OPEN);
                    bidIntent.putExtra("isDeepLink", true);
                    bidIntent.putExtra("id", leadId);
                    bidIntent.putExtra("notificationType", notificationType);
                    PendingIntent pBidIntent = PendingIntent.getBroadcast(context, 0, bidIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                    Intent deleteIntent = new Intent(ACTION_PUSH_DELETE);
                    deleteIntent.putExtra("notificationType",notificationType);

                    PendingIntent pDeleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.msp_launch)
                                    .setContentTitle("MySmartPrice Leads")
                                    .setContentText(" You Got a Lead, Hurry Up!!!")
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Customer" + " has enquired for " + title.toUpperCase() + " "
                                            + color + " .Kindly provide your best price here\n"))
                                    .addAction(0, "Bid Here", pBidIntent)
                                    .addAction(0, "Out of Stock", pBidIntent)
                                    .setAutoCancel(true)
                                    .setDefaults(-1);

                    mBuilder.setContentIntent(pBidIntent).setDeleteIntent(pDeleteIntent);
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager mNotificationManager;
                    mNotificationManager = (NotificationManager) context.getSystemService(ns);
                    mNotificationManager.notify(LEAD_NOTIFICATION_ID, mBuilder.build());



                    return null;

                    //{username} has enquired for {title and colour}. Kindly provide your best price here

                } else if("cpl_lead_won".equals(notificationType)) {

                    String title = pushData.optString("title");
                    String color = pushData.optString("color");
                    String contactNumber = pushData.optString("contact_no");
                    String leadId = pushData.optString("leadID");
                    if (!contactNumber.startsWith("0") && !contactNumber.startsWith("+91")) {
                        contactNumber = "+91" + contactNumber;
                    }

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + contactNumber));
                    PendingIntent pCallIntent = PendingIntent.getActivity(context, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                    Intent cplWon = new Intent(ACTION_PUSH_OPEN);
                    cplWon.putExtra("notificationType", notificationType);
                    String action = "myProducts";
                    String url = "http://sellers.mysmartprice.com/my_leads.php?filter=won";
                    //url = "http://sellers.ravindra.mysmartprice.com/mark_out_of_stock.php";
                    cplWon.putExtra("url", url);
                    cplWon.putExtra("action", action);
                    cplWon.putExtra("isDeepLink", false);

                    cplWon.setAction(ACTION_PUSH_OPEN);


                    Intent deleteIntent = new Intent(ACTION_PUSH_DELETE);
                    deleteIntent.putExtra("notificationType",notificationType);

                    PendingIntent pDeleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    PendingIntent pBidWonIntent = PendingIntent.getBroadcast(context, 0, cplWon, PendingIntent.FLAG_UPDATE_CURRENT);
                    //PendingIntent pBidWonIntent = PendingIntent.getBroadcast(context, 0, cplWon, PendingIntent.FLAG_UPDATE_CURRENT);

//


                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.msp_launch)
                                    .setContentTitle("Congrats! You have won")
                                    .setContentText("Your bid has been selected for")
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Your bid has been selected for " + title.toUpperCase() + " " + color + " .You can contact customer on " + contactNumber +
                                            " .Click here to see your winning bids.\n"))
                                    .addAction(android.R.drawable.ic_menu_call, "Call", pCallIntent)
                                    .setAutoCancel(true)
                                    .setDefaults(-1);

                    mBuilder.setContentIntent(pBidWonIntent).setDeleteIntent(pDeleteIntent);
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager mNotificationManager;
                    mNotificationManager = (NotificationManager) context.getSystemService(ns);
                    mNotificationManager.notify(WON_NOTIFICATION_ID, mBuilder.build());
                    return null;


                } else if("cpl_lead_lost".equals(notificationType)) {

                    String title = pushData.optString("title");
                    String bidAmt = pushData.optString("bidAmt");
                    String loosingAmt = pushData.optString("losingAmt");
                    String leadId = pushData.optString("leadID");

                    Intent cplLost = new Intent(ACTION_PUSH_OPEN);
                    cplLost.putExtra("notificationType", notificationType);
                    String action = "myProducts";
                    String url = "http://sellers.mysmartprice.com/my_products.php";
                    //url = "http://sellers.ravindra.mysmartprice.com/mark_out_of_stock.php";
                    cplLost.putExtra("url", url);
                    cplLost.putExtra("action", action);
                    cplLost.setAction(ACTION_PUSH_OPEN);
                    cplLost.putExtra("isDeepLink", false);

                    PendingIntent pBidLostIntent = PendingIntent.getBroadcast(context, 0, cplLost, PendingIntent.FLAG_UPDATE_CURRENT);
                    Intent deleteIntent = new Intent(ACTION_PUSH_DELETE);
                    deleteIntent.putExtra("notificationType",notificationType);


                    PendingIntent pDeleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.msp_launch)
                                    .setContentTitle("Just missed")
                                    .setContentText("Your bid did not win ")
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Your bid of Rs. " + bidAmt + " for " + title.toUpperCase()
                                            + " did not win by Rs." + loosingAmt + " .You can change the price for future bids from My Products page.\n"))
                                    .setAutoCancel(true)
                                    .setDefaults(-1);

                    mBuilder.setContentIntent(pBidLostIntent).setDeleteIntent(pDeleteIntent);

                    return mBuilder.build();

                } else if ("cpl_shopclosed".equals(notificationType)) {
                    int leadCount = pushData.optInt("no_of_products");
                    String url = "http://sellers.mysmartprice.com/my_leads.php?filter=shopclosed";
                    Intent cplClosed = new Intent(ACTION_PUSH_OPEN);
                    cplClosed.putExtra("notificationType", notificationType);
                    cplClosed.putExtra("url",url);
                    cplClosed.putExtra("isDeepLink", false);
                    PendingIntent pShopClosedIntent = PendingIntent.getBroadcast(context, 0, cplClosed, PendingIntent.FLAG_UPDATE_CURRENT);

                    Intent deleteIntent = new Intent(ACTION_PUSH_DELETE);
                    deleteIntent.putExtra("notificationType",notificationType);
                    PendingIntent pDeleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.msp_launch)
                                    .setContentTitle("Just missed")
                                    .setContentText("Your bid did not win ")
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText("You have received quotes for "+ leadCount +" products when your shop was closed from 9 PM - 12 PM. Click here to provide your bids. \n"))
                                    .setAutoCancel(true)
                                    .setDefaults(-1);

                    mBuilder.setContentIntent(pShopClosedIntent).setDeleteIntent(pDeleteIntent);

                    return mBuilder.build();

                } else if ("price_expiry".equals(notificationType)) {

                    String title = pushData.optString("title");
                    Integer noOfProducts = pushData.optInt("no_of_products");
                    String alert = pushData.optString("alert", "Price of " + Integer.toString(noOfProducts) + " products will expire " + "today. Please update the price");
                    String tickerText = String.format(Locale.getDefault(), "%s: %s", new Object[]{title, alert});

                    Intent contentIntent = new Intent(context, MainActivity.class);
                    contentIntent.putExtra("notificationType", notificationType);
                    String url = "http://sellers.ravindra.mysmartprice.com/my_products.php";
                    contentIntent.putExtra("url", url);
                    contentIntent.putExtra("action", "my_products");
                    PendingIntent pContentIntent = PendingIntent.getActivity(context, 0, contentIntent, 0);

                    Intent deleteIntent = new Intent("com.parse.push.intent.DELETE");
                    Bundle extras = intent.getExtras();
                    deleteIntent.putExtras(extras);
                    String packageName = context.getPackageName();
                    deleteIntent.setPackage(packageName);
                    PendingIntent pDeleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, 0);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setContentTitle(title)
                                    .setContentText(alert)
                                    .setTicker(tickerText)
                                    .setSmallIcon(this.getSmallIconId(context, intent))
                                    .setLargeIcon(this.getLargeIcon(context, intent))
                                    .setContentIntent(pContentIntent)
                                    .setDeleteIntent(pDeleteIntent)
                                    .setAutoCancel(true)
                                    .setDefaults(-1);

                    if (alert != null && alert.length() > 38) {
                        mBuilder.setStyle((new NotificationCompat.BigTextStyle()).bigText(alert));
                    }
                    return mBuilder.build();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
