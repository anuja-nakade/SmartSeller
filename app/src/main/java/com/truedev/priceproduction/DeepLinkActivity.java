package com.truedev.priceproduction;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

public class DeepLinkActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String act = intent.getAction();
        String action = String.valueOf(intent.getClass());
        Set<String> cat = intent.getCategories();



        Uri data = intent.getData();
        Log.d("Data123", action + " : " + data + " : " + act + " : " + action  + " : " + cat);
       /* ObjectGraph objectGraph = MySmartPriceApp.get(this).getBaseObjectGraph();
        objectGraph.inject(this);*/
    }

    public void onStart() {
        super.onStart();
        parseDeepLink(getIntent().getData());
        finish();
    }

    private void parseDeepLink(Uri deepLink) {

        int pathSegmentsSize = deepLink.getPathSegments().size(); // total number of path segments
        if (pathSegmentsSize > 0) {
            String urlBase = deepLink.getPathSegments().get(0);
            BaseLink link = new BaseLink();
            switch(urlBase) {

                case "my_leads":
                    String id;
                    if(pathSegmentsSize > 1) {
                       id = deepLink.getPathSegments().get(1);
                        Intent i = new Intent(this, MainActivity.class);
                        i.putExtra("urlBase",urlBase);
                        i.putExtra("isDeepLink", true);
                        i.putExtra("id",id);
                        startActivity(i);
                    }

                    break;
                default :
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    break;



            }
        }




    }

}
