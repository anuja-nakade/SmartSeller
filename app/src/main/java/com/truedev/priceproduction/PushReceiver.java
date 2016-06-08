package com.truedev.priceproduction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseInstallation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anuja on 5/10/16.
 */
public class PushReceiver extends BroadcastReceiver {
    public static final String ACTION_PUSH_OPEN = "com.msp.sellers.push.intent.OPEN";
    public static final String ACTION_PUSH_RECEIVE = "com.google.android.c2dm.intent.RECEIVE";
    public static final String ACTION_PUSH_DELETE = "com.msp.sellers.push.intent.DELETE";


    @Override
    public void onReceive(Context context, Intent intent) {
        String notfType = null;
        if((ACTION_PUSH_RECEIVE.equals(intent.getAction()))) {
            Bundle bundle = intent.getExtras();
            String data = bundle.getString("data");


            if (data != null) {


                try {
                    JSONObject jsonObj = new JSONObject(data);
                    notfType = jsonObj.getString("type");
                } catch (JSONException e) {
                     e.printStackTrace();
                }
            }
        } else {
            notfType = intent.getStringExtra("notificationType");
        }
        //String type = intent.getStringExtra("type");
        String url = intent.getStringExtra("url");
        //String notfType = intent.getStringExtra("notificationType");
        String parseID = ParseInstallation.getCurrentInstallation().getInstallationId();
        boolean isDeepLink = intent.getBooleanExtra("isDeepLink", false);
        String leadId = null;
        if (isDeepLink) {
            leadId = intent.getStringExtra("id");
        }

        if (ACTION_PUSH_OPEN.equals(intent.getAction())) {
            Log.d("opened", "success");
            Intent mainIntent = new Intent(context, MainActivity.class);
            if (isDeepLink) {
                mainIntent.putExtra("id", leadId);
            }
            mainIntent.putExtra("isDeepLink", isDeepLink);
            mainIntent.putExtra("url", url);
            mainIntent.putExtra("notificationType", notfType);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
            postData(parseID, "OPEN", notfType);

        } else if (ACTION_PUSH_RECEIVE.equals(intent.getAction())) {
            Log.d("push Recieved", "success");
            postData(parseID, "RECEIVE", notfType);

        } else if (ACTION_PUSH_DELETE.equals(intent.getAction())) {
            Log.d("push Deleted", "success");
            postData(parseID, "DELETE", notfType);

        }


    }

    private void postData(String parseID, String action, String notfType) {
        UploadPushData uploadForms = new UploadPushData();
        uploadForms.execute("http://sellers.mysmartprice.com/push_notification_tracking.php", parseID, action, notfType);
      /*  BufferedReader reader=null;
        try
        {
            String data = URLEncoder.encode("parseinstallation_id", "UTF-8")
                    + "=" + URLEncoder.encode(parseID, "UTF-8");

            data += "&" + URLEncoder.encode("action", "UTF-8") + "="
                    + URLEncoder.encode(action, "UTF-8");

            data += "&" + URLEncoder.encode("notfication_type", "UTF-8")
                    + "=" + URLEncoder.encode(notfType, "UTF-8");



            // Defined URL  where to send data
            URL url = new URL("http://sellers.mysmartprice.com/push_notification_tracking.php");

            // Send POST data request
            Log.d("data",parseID + " " + action + " " + notfType);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.connect();
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
                Log.d("Server",line);
            }



        }
        catch(Exception ex)
        {

        }

        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }
*/
    }


}
