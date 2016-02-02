package com.truedev.priceproduction.gcm;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;
//import com.truedev.priceproduction.Activities.NotificationActivity;
import com.truedev.priceproduction.R;
//import com.truedev.priceproduction.Utils.Constants;


import android.support.v4.app.NotificationCompat;


/**
 * Created by mutha on 30/06/15.
 *
 * This implements the customized push notification
 * it uses some of the parse functionality
 *
 */
public class CustomBroadcastReceiver extends ParsePushBroadcastReceiver {


    public CustomBroadcastReceiver() {
        super();
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }


    /**
     * It creates the customize notification and return to parse for
     * displaying it and for further processing
     * modified by mutha 3-7-15
     * @param context
     * @param intent
     * @return
     */
    @Override
    protected Notification getNotification(Context context, Intent intent) {

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.msp_launch);

        mNotifyBuilder.setContentText("test");
        return mNotifyBuilder.build();
    }

    /*@Override
    protected Notification getNotification(Context context, Intent intent) {
        // JSONObject pushData = this.getPushData(intent);
        JSONObject pushData;
        try {
            pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
            String notification_id=pushData.optString("notification_id","temp_id");

            if (pushData != null && (pushData.has("alert") || pushData.has("title")) && (!pushData.has("contactNumber"))) {
                String title = pushData.optString("title", "Update the price");
                Integer noOfProducts=pushData.optInt("no_of_products", 0);
                String alert = pushData.optString("alert", "Price of " + Integer.toString(noOfProducts) + " products will expire " + "today. Please update the price");
                String tickerText = String.format(Locale.getDefault(), "%s: %s", new Object[]{title, alert});
                Bundle extras = intent.getExtras();
                String packageName = context.getPackageName();
                Intent contentIntent = new Intent(context, MainActivity.class);
                contentIntent.setAction("open_My_products");
                PendingIntent pContentIntent = PendingIntent.getActivity(context, 0, contentIntent, 0);
                Intent deleteIntent = new Intent("com.parse.push.intent.DELETE");
                deleteIntent.putExtras(extras);
                deleteIntent.setPackage(packageName);
                PendingIntent pDeleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, 0);
                NotificationCompat.Builder parseBuilder = new NotificationCompat.Builder(context);
                parseBuilder.setContentTitle(title).setContentText(alert).setTicker(tickerText).setSmallIcon(this.getSmallIconId(context, intent)).setLargeIcon(this.getLargeIcon(context, intent)).setContentIntent(pContentIntent).setDeleteIntent(pDeleteIntent).setAutoCancel(true).setDefaults(-1);
                if (alert != null && alert.length() > 38) {
                    parseBuilder.setStyle((new NotificationCompat.BigTextStyle()).bigText(alert));
                }
                return parseBuilder.build();
            }
            else {

                Intent intentHome = new Intent(context, MainActivity.class);
                intentHome.setAction("open_My_leads");
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intentHome, 0);
                *//**
                 * Parse Data from Json object comes with
                 * the notification
                 *//*

                String customerName = pushData.optString("customer_name","anonymous");
                String productName = pushData.optString("product_name", "newproduct");
                String price = pushData.optString("price","notgettingprice");
                String contactNumber = pushData.optString("contact_no","1234567890");
                String productId = pushData.optString("product_id","");
                if(!contactNumber.startsWith("0")&& !contactNumber.startsWith("+91")){
                    contactNumber = "+91"+ contactNumber;
                }
                Intent intentCall = new Intent(Intent.ACTION_CALL);
                intentCall.setData(Uri.parse("tel:" + contactNumber));
                PendingIntent contentIntentCall = PendingIntent.getActivity(context, 0, intentCall, 0);



                *//**
                 * Action for Out of Stock Button
                 *
                 *//*
                Intent intentOutOfStock = new Intent(context,MainActivity.class);
                intentOutOfStock.setAction(productId);
                PendingIntent contentIntentOutOfStock = PendingIntent.getActivity(context, 0, intentOutOfStock, 0);

                *//**
                 * Building the notification
                 *//*


                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.msp_launch)
                                .setContentTitle("MySmartPrice Leads")
                                .setContentText(" You Got a Lead, Hurry Up!!!")
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                                        "New Lead : "  + productName + "@ Rs." + price + "\n" + "Customer Name: " + customerName + "\n" + "Contact: " + contactNumber))
                                .addAction(android.R.drawable.ic_menu_call, "Call", contentIntentCall)
                                .addAction(R.drawable.close1, "out of stock", contentIntentOutOfStock)
                                .setAutoCancel(true);


                mBuilder.setContentIntent(contentIntent);

                Intent deleteIntent = new Intent("com.parse.push.intent.DELETE");
                Bundle extras = intent.getExtras();
                deleteIntent.putExtras(extras);
                String packageName = context.getPackageName();
                deleteIntent.setPackage(packageName);
                PendingIntent pDeleteIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, 0);
                mBuilder.setDeleteIntent(pDeleteIntent).setAutoCancel(true).setDefaults(-1);

                return mBuilder.build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
