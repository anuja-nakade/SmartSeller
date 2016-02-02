package com.truedev.priceproduction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.CookieManager;

import com.parse.ParseInstallation;


public class MainActivity extends Activity {

    private WebView mWebView;
    private MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        CookieManager.getInstance().setAcceptCookie(true);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String url = "";
        String postData = "";
        String action = "";
        Bundle extras = this.getIntent().getExtras();
        if(extras!=null && extras.getString("notificationType") != null) {
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
            }
        } else {
            url = "http://sellers.mysmartprice.com/index.php";
            String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();
            postData = "installationId=" + installationId;
            //mWebView.postUrl(url, postData.getBytes());
        }
        mWebView.postUrl(url, postData.getBytes());


        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
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
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
            }

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
}
