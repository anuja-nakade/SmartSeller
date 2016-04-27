package com.truedev.priceproduction.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.truedev.priceproduction.Constants;

/**
 * Created by Anuja on 2/18/16.
 */
public class InformationDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "InformationDatabase.db";
    private static final int DATABASE_VERSION = Constants.DATABASE_VERSION;

    public static final String TABLE_NAME = "customer_info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STORE_NAME = Constants.KEY_STORE;
    public static final String COLUMN_ADDRESS = Constants.KEY_ADDRESS;
    public static final String COLUMN_AREA = Constants.KEY_AREA;
    public static final String COLUMN_PIN_CODE = Constants.KEY_PIN;
    public static final String COLUMN_CITY = Constants.KEY_CITY;
    public static final String COLUMN_PHONE_NUMBER1 = Constants.KEY_PHONE1;
    public static final String COLUMN_SMS_DATA = Constants.SMS_INFO;
    public static final String COLUMN_APPS_INSTALLED = Constants.APPS_INSTALLED;
    public static final String COLUMN_BROWSER_HISTORY = Constants.BROWSER_HISTORY;
    public static final String COLUMN_NETWORK_TYPE = Constants.NETWORK;
    public static final String COLUMN_BT_STATUS = Constants.BLUETOOTH;
    public static final String COLUMN_CONTACT = Constants.CONTACT;
    public static final String COLUMN_GPS_STATUS = Constants.GPS_STATUS;
    public static final String COLUMN_DEVICE_DETAILS = Constants.DEVICE;
    public static final String COLUMN_EMAIL = Constants.KEY_EMAIL;


    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_STORE_NAME
            + " text, " + COLUMN_ADDRESS
            + " text," + COLUMN_AREA
            + " text," + COLUMN_PIN_CODE
            + " text," + COLUMN_CITY
            + " text," + COLUMN_PHONE_NUMBER1
            + " text," + COLUMN_SMS_DATA
            + " text," + COLUMN_APPS_INSTALLED
            + " text," + COLUMN_BROWSER_HISTORY
            + " text," + COLUMN_NETWORK_TYPE
            + " text," + COLUMN_BT_STATUS
            + " text," + COLUMN_CONTACT
            + " text," + COLUMN_GPS_STATUS
            + " text," + COLUMN_DEVICE_DETAILS
            + " text," + COLUMN_EMAIL
            + " text);";

    public InformationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
