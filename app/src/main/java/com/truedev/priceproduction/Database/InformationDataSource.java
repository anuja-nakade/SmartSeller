package com.truedev.priceproduction.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.truedev.priceproduction.Constants;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Anuja on 2/18/16.
 */
public class InformationDataSource {

    private final Context context;

    private SQLiteDatabase database;
    private final InformationDatabaseHelper dbHelper;

    private String[] allColumns = {
            InformationDatabaseHelper.COLUMN_ID,
            InformationDatabaseHelper.COLUMN_STORE_NAME,
            InformationDatabaseHelper.COLUMN_ADDRESS,
            InformationDatabaseHelper.COLUMN_AREA,
            InformationDatabaseHelper.COLUMN_PIN_CODE,
            InformationDatabaseHelper.COLUMN_CITY,
            InformationDatabaseHelper.COLUMN_PHONE_NUMBER1,
            InformationDatabaseHelper.COLUMN_SMS_DATA,
            InformationDatabaseHelper.COLUMN_APPS_INSTALLED,
            InformationDatabaseHelper.COLUMN_BROWSER_HISTORY,
            InformationDatabaseHelper.COLUMN_NETWORK_TYPE,
            InformationDatabaseHelper.COLUMN_BT_STATUS,
            InformationDatabaseHelper.COLUMN_CONTACT,
            InformationDatabaseHelper.COLUMN_GPS_STATUS,
            InformationDatabaseHelper.COLUMN_DEVICE_DETAILS,
            InformationDatabaseHelper.COLUMN_EMAIL
    };

    public InformationDataSource(Context context) {
        this.context = context;
        dbHelper = new InformationDatabaseHelper(context);
    }

    public void open() throws SQLException {
        Log.i("InfoDataSource", "open before location database" + database);
        database = dbHelper.getWritableDatabase();
        Log.i("InfoDataSource", "open after location database" + database);
    }

    public void close() {
        dbHelper.close();
    }

    public long insertCustomerInfo(HashMap<String , String> data) {
        String tempInfo;
        ContentValues values = new ContentValues();
        for(int index = 0; index < (allColumns.length) ; index++) {
            tempInfo = data.get(allColumns[index]);
            if(tempInfo!=null) {

                values.put(allColumns[index] , tempInfo);
            }
        }
        long insertId = database.insert(InformationDatabaseHelper.TABLE_NAME, null, values);
        return insertId;
    }

    public int updateCustomerInfo(String id, HashMap<String,String> data) {
        ContentValues values = new ContentValues();
        String tempInfo;
        for(int index=0; index<(allColumns.length); index++) {
            tempInfo = data.get(allColumns[index]);
            if(tempInfo != null) {
                values.put(allColumns[index], tempInfo);
            }
        }
        int rowEffected = database.update(InformationDatabaseHelper.TABLE_NAME, values, InformationDatabaseHelper.COLUMN_ID + "= ?", new String[]{id});
        return rowEffected;

    }
    public Cursor getInfoById(String id) {
        Cursor cursor = database.query(InformationDatabaseHelper.TABLE_NAME, allColumns, InformationDatabaseHelper.COLUMN_ID + " = " + id, null,
                null, null, null);
        return cursor;
    }
}
