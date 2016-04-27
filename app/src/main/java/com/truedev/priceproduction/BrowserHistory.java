package com.truedev.priceproduction;

import android.content.Context;
import android.content.Intent;
import javax.inject.Inject;

import com.google.gson.JsonArray;
import com.truedev.priceproduction.Database.InformationDataSource;
import com.truedev.priceproduction.Database.InformationDatabaseHelper;

import java.util.HashMap;

/**
 * Created by Anuja on 3/2/16.
 */
public class BrowserHistory extends WakeService {
    @Inject
    Context context;

    @Inject
    InformationDataSource dataSource;


    public static HashMap<String, String> paramsSend = new HashMap<String,String>();



    public BrowserHistory(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
                JsonArray browserHistory = Utils.getBH(getApplicationContext());                                                                   // browser history

            if(browserHistory!=null)
                paramsSend.put(Constants.BROWSER_HISTORY, browserHistory.toString());
                dataSource.open();
                dataSource.updateCustomerInfo(InformationDatabaseHelper.COLUMN_BROWSER_HISTORY,paramsSend);
                dataSource.close();
            }
         catch (Exception e) {
        }
        super.onHandleIntent(intent);
    }

}
