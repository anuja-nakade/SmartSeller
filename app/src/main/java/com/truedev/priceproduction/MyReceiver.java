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

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        //return super.getNotification(context, intent);

        try {
            JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));

            if(pushData!=null && pushData.has("type")) {
                String notificationType = pushData.getString("type");
                if("lead_notification".equals(notificationType)) {

                    String customerName = pushData.optString("customer_name");
                    String productName = pushData.optString("product_name");
                    String price = pushData.optString("price");
                    String contactNumber = pushData.optString("contact_no");
                    String productId = pushData.optString("product_id");
                    if(!contactNumber.startsWith("0")&& !contactNumber.startsWith("+91")){
                        contactNumber = "+91"+ contactNumber;
                    }

                    Intent ordersIntent = new Intent(context, MainActivity.class);
                    ordersIntent.setAction("test");
                    ordersIntent.putExtra("notificationType", notificationType);
                    String action = "myOrders";
                    String url = "http://sellers.mysmartprice.com/my_orders.php";
                    ordersIntent.putExtra("url", url);
                    ordersIntent.putExtra("action", action);
                    PendingIntent pOrdersIntent = PendingIntent.getActivity(context, 0, ordersIntent, 0);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + contactNumber));
                    PendingIntent pCallIntent = PendingIntent.getActivity(context, 0, callIntent, 0);

                    Intent outOfStockIntent = new Intent(context, MainActivity.class);
                    outOfStockIntent.setAction(productId);
                    outOfStockIntent.putExtra("notificationType", notificationType);
                    action = "outOfStock";
                    url = "http://sellers.mysmartprice.com/mark_out_of_stock.php";
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
                                            "New Lead : "  + productName + "@ Rs." + price + "\n" + "Customer Name: " + customerName + "\n" + "Contact: " + contactNumber))
                                    .addAction(android.R.drawable.ic_menu_call, "Call", pCallIntent)
                                    .addAction(R.drawable.close1, "out of stock", pOutOfStockIntent)
                                    .setAutoCancel(true)
                                    .setDefaults(-1);;

                    mBuilder.setContentIntent(pOrdersIntent);

                    return mBuilder.build();
                } else if("price_expiry".equals(notificationType)) {

                    String title = pushData.optString("title");
                    Integer noOfProducts = pushData.optInt("no_of_products");
                    String alert = pushData.optString("alert", "Price of " + Integer.toString(noOfProducts) + " products will expire " + "today. Please update the price");
                    String tickerText = String.format(Locale.getDefault(), "%s: %s", new Object[]{title, alert});

                    Intent contentIntent = new Intent(context, MainActivity.class);
                    contentIntent.putExtra("notificationType", notificationType);
                    String url = "http://sellers.mysmartprice.com/my_products.php";
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
