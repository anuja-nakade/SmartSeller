package com.truedev.priceproduction;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anuja on 5/30/16.
 */
public class UploadPushData extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        String responseString;
        String mUrl = (String) params[0];
        String parseID = (String) params[1];
        String action = (String) params[2];
        String notfType = (String) params[3];
        responseString = makePostRequest(mUrl,parseID,action,notfType);
        return responseString;
    }


    private String makePostRequest(String mUrl,String parseID,String action,String notfType){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(mUrl);
        String responseString = "";

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("parseinstallation_id", parseID));
            nameValuePairs.add(new BasicNameValuePair("action", action));
            nameValuePairs.add(new BasicNameValuePair("notfication_type", notfType));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            try {

                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();

                Log.e("Response POST ::: ", "" + response.getStatusLine());
                if (statusCode == 200) { // Ok
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    Log.e("Statuscode","200");
                    String linha="";
                    while ((linha = rd.readLine()) != null) {
                        responseString += linha;
                    }
                    Log.e("Response string",""+ responseString);
                }
            }
            catch (IOException e) {
                //progress.dismiss();
                //onPostExecute("{success:'false'}");
                //Toast.makeText(mContext, "No Internet Connection !!Data will get uploaded once connection is back", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return responseString;
    }
}
