package com.truedev.priceproduction;

/**
 * Created by Anuja on 2/18/16.
 */

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.parse.ParseInstallation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private final static String YOUTUBE_VIDEO_ID_PATTERN = "(\\?<=watch|\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|watch\\?v%3D|%2Fvideos%2F|embed%‌​2F|youtu.be%2F|%2Fv%2F)([^#\\&\\?\\n]*)";
    public static String NETWORK_TYPE = null;

    public static Comparator<String> dateComparator = new Comparator<String>() {
        public int compare(String o1, String o2) {
            o1 = o1.replaceAll("-", "");
            o2 = o2.replaceAll("-", "");
            Integer i1 = Integer.parseInt(o1);
            Integer i2 = Integer.parseInt(o2);
            return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
        }
    };


    public static Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static String getVideoId(String url) {
        Pattern compiledPattern = Pattern.compile(YOUTUBE_VIDEO_ID_PATTERN);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    public static String getUserEmail(Context context) {
        Pattern mPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context.getApplicationContext()).getAccounts();

        for (Account account : accounts) {
            if (mPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return null;
    }


    public static String getDeviceDetails() {
        return getDeviceDetailsJson().toString();
    }

    public static JsonObject getDeviceDetailsJson(){
        JsonObject extraStringJson = new JsonObject();
        extraStringJson.addProperty("VERSION.RELEASE", Build.VERSION.RELEASE);
        extraStringJson.addProperty("VERSION.INCREMENTAL", Build.VERSION.INCREMENTAL);
        extraStringJson.addProperty("VERSION.SDK", Build.VERSION.SDK);
        extraStringJson.addProperty("BOARD", Build.BOARD);
        extraStringJson.addProperty("BRAND", Build.BRAND);
        extraStringJson.addProperty("DEVICE", Build.DEVICE);
        extraStringJson.addProperty("FINGERPRINT", Build.FINGERPRINT);

        extraStringJson.addProperty("DEVICE_OS", "android");
        extraStringJson.addProperty("DEVICE_DENSITY", getDeviceDensity());
        extraStringJson.addProperty("NETWORK_TYPE", NETWORK_TYPE != null ? NETWORK_TYPE : "Unknown");
        return extraStringJson;
    }

   /* public static String getRequestUrl(String url) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(url)
                .append(!url.contains("?") ? "?" : "")
                .append("&app_version=")
                .append(MySmartPriceApp.getInstance().getApplicationContext().getResources().getString(R.string.app_version))
                .append("&device_os=android")
                .append("&density=")
                .append(getDeviceDensity())
                .append("&network=" + (NETWORK_TYPE != null ? NETWORK_TYPE : "Unknown"));
        return urlBuilder.toString();
    }*/

/*
    public static String getRequestUrlWithUserInfo(String url) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(url)
                .append(!url.contains("?") ? "?" : "")
                .append("&android_uid=")
                .append(ParseInstallation.getCurrentInstallation().getInstallationId())
                .append("&user_email=")
                .append(getUserEmail(MySmartPriceApp.getInstance()))
                .append("&app_version=")
                .append(MySmartPriceApp.getInstance().getApplicationContext().getResources().getString(R.string.app_version))
                .append("&density=")
                .append(getDeviceDensity())
                .append("&network=" + (NETWORK_TYPE != null ? NETWORK_TYPE : "Unknown"));
        return urlBuilder.toString();
    }
*/

    public static int getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) ApplicationController.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     *//*
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    *//**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     *//*
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    *//**
     * This method gets the user's current location
     *
     * @return A location object containing user's lat long values
     */
    public static Location getMyLocation(Context context) {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (myLocation == null) {
            myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }

        List<String> providers = lm.getProviders(true);
        if (myLocation == null) {
            for (int i = providers.size() - 1; i >= 0; i--) {
                myLocation = lm.getLastKnownLocation(providers.get(i));
                if (myLocation != null) {
                    return myLocation;
                }
            }
        }

        return myLocation;
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager service = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * @param context
     * @param startHour
     * @param startMinutes
     * @param intervalMinutes
     */
    /*public static void setServerPingAlarms(Context context, int startHour, int startMinutes, int intervalMinutes) {

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        *//* Set the alarm to start at <startHour>:<startMinutes> *//*
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        calendar.set(Calendar.MINUTE, startMinutes);

        Intent intent = new Intent(context, OnAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        *//* Repeating on every <intervalMinutes> interval *//*
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intervalMinutes * 1000, pendingIntent);
    }

    public static void setBrowserHistoryReadAlarms(Context context, int startHour, int startMinutes, int intervalMinutes) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        *//* Set the alarm to start at <startHour>:<startMinutes> *//*
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        Random random = new Random();
        startMinutes = random.nextInt(60);
        calendar.set(Calendar.MINUTE, startMinutes);

        Intent intent = new Intent(context, KinesisService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        *//* Repeating on every <intervalMinutes> interval *//*
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intervalMinutes * 1000, pendingIntent);
    }


    *//**
     /** @param context */


    public static JsonArray getBH(Context context) {
        String storeLastVisitedAt,userEntered,created;
        JsonObject browserHistoryItem ;
        JsonArray browserHistoryList = new JsonArray();
        String[] proj = new String[] { BookmarkColumns.TITLE, BookmarkColumns.URL, BookmarkColumns.DATE, BookmarkColumns.CREATED };
        String sel = BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
        Cursor mCur = context.getContentResolver().query(BookmarkColumns.BOOKMARKS_URI, proj, sel, null, null);
        mCur.moveToFirst();
        @SuppressWarnings("unused")
        String title = "";
        @SuppressWarnings("unused")
        String url = "";
        if (mCur.moveToFirst() && mCur.getCount() > 0) {
            boolean cont = true;
            while (mCur.isAfterLast() == false && cont) {
                browserHistoryItem = new JsonObject();

                title = mCur.getString(mCur.getColumnIndex(BookmarkColumns.TITLE));
                url = mCur.getString(mCur.getColumnIndex(BookmarkColumns.URL));
                storeLastVisitedAt = mCur.getString(mCur.getColumnIndex(BookmarkColumns.DATE));
                String dateFormat = "dd/MM/yyyy hh:mm:ss.SSS";
                SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

                // Create a calendar object that will convert the date and time value in milliseconds to date.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(storeLastVisitedAt));
                String date =  formatter.format(calendar.getTime());
                //userEntered = mCur.getString(mCur.getColumnIndex(BookmarkColumns.USER_ENTERED));
                created = mCur.getString(mCur.getColumnIndex(BookmarkColumns.CREATED));
                long time = System.currentTimeMillis();
                long diff = time - Long.parseLong(storeLastVisitedAt);
                Log.d("Data:", storeLastVisitedAt + "  : " + date + " : " + diff + " : " + created + " : " + time);
                if(diff < 86400000) {
                    browserHistoryItem.addProperty("title", title);
                    browserHistoryItem.addProperty("url", url);
                    browserHistoryList.add(browserHistoryItem);
                }
                // Do something with title and url
                mCur.moveToNext();
            }
        }
        return browserHistoryList;
    }

    public static void setBrowserHistoryReadAlarms(Context context, int startHour, int startMinutes, int intervalMinutes) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        /* Set the alarm to start at <startHour>:<startMinutes> */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        Random random = new Random();
        startMinutes = random.nextInt(60);
        calendar.set(Calendar.MINUTE, startMinutes);

        Intent intent = new Intent(context, BrowserHistory.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        /* Repeating on every <intervalMinutes> interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intervalMinutes * 1000, pendingIntent);
    }

    public static JsonArray getBrowserHistory(Context context) {
        String storeUrl, storeLastVisitedAt;
        JsonArray browserHistoryList = new JsonArray();
        JsonObject browserHistoryItem;
        ArrayList<Uri> USER_BROWSERS_URI_LIST = new ArrayList<>();
        Cursor cursor;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // AOSP browser history
            Uri AOSP_BROWSER_BOOKMARKS_URI = BookmarkColumns.BOOKMARKS_URI;
            USER_BROWSERS_URI_LIST.add(AOSP_BROWSER_BOOKMARKS_URI);

            // Chrome browser history
            Uri CHROME_BROWSER_BOOKMARKS_URI = Uri.parse("content://com.android.chrome.browser/bookmarks");
            if (AOSP_BROWSER_BOOKMARKS_URI != CHROME_BROWSER_BOOKMARKS_URI) {
                USER_BROWSERS_URI_LIST.add(CHROME_BROWSER_BOOKMARKS_URI);
            }

            String[] selectColumns = new String[]{BookmarkColumns.URL, BookmarkColumns.DATE};
            //String conditions = BookmarkColumns.BOOKMARK + " = 0 and " + BookmarkColumns.DATE + " > " + lastReadTimestamp; // 0 = history, 1 = bookmark
            String conditions = BookmarkColumns.BOOKMARK + " = 0 "; // 0 = history, 1 = bookmark

            for (Uri browser : USER_BROWSERS_URI_LIST) {
                cursor = null;
                try {
                    cursor = context.getContentResolver().query(browser, selectColumns, conditions, null, null);
                } catch (Exception e) {
                }

                if (cursor != null) {
                    cursor.moveToFirst();
                    if (cursor.moveToFirst() && cursor.getCount() > 0) {
                        while (cursor.isAfterLast() == false) {
                            storeUrl = cursor.getString(cursor.getColumnIndex(BookmarkColumns.URL));

                            if (isValidStore(storeUrl)) {
                                storeLastVisitedAt = cursor.getString(cursor.getColumnIndex(BookmarkColumns.DATE));

                                browserHistoryItem = new JsonObject();
                                browserHistoryItem.addProperty("installation_id", ParseInstallation.getCurrentInstallation().getInstallationId());
                                browserHistoryItem.addProperty("email_id", retrieveEmail());
                                browserHistoryItem.addProperty("client_logtime", storeLastVisitedAt);
                                browserHistoryItem.addProperty("event_name", "android_browser_history");
                                browserHistoryItem.addProperty("event_data", storeUrl);

                                JsonObject extraInfo = getDeviceDetailsJson();
                                extraInfo.addProperty("browser", browser.getHost());
                                browserHistoryItem.addProperty("extra_info", extraInfo.toString());

                                browserHistoryList.add(browserHistoryItem);
                            }

                            cursor.moveToNext();
                        }
                    }
                }
            }

            Log.d("BrowserHistory", browserHistoryList.toString());
        }

        return browserHistoryList;
    }

    /*
    /**
     * @param url
     * @return
     */
    private static boolean isValidStore(String url) {
        return true;
    }


    public static String retrieveEmail() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(ApplicationController.getInstance().getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }

        return null;
    }

    /**
     *
     */

    public static Map<String, String> retrieveAppPackages(Context context) {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Map<String, String> apps = new HashMap<String, String>();
        for (ApplicationInfo applicationInfo : packages) {
            apps.put(applicationInfo.packageName, pm.getApplicationLabel(applicationInfo).toString());
        }
        return apps;
    }

    public static int indexToModifyAppTrack(String packageName) {
        for (int i = 0; i < Constants.NO_OF_APPS_TRACKED; i++) {
            if (packageName.equalsIgnoreCase(Constants.APPS_TRACK[i])) {
                return i;
            }
        }
        return -1;
    }


    public static String convertPackagesToJsonString(Map<String, String> packages) {

        StringBuilder setBitForApps = new StringBuilder(new String(new char[Constants.NO_OF_APPS_TRACKED]).replace("\0", "0"));
        int tempIndexForModify = -1;
        for (Map.Entry<String, String> packageEntry : packages.entrySet()) {
            String key = packageEntry.getKey();
            //String value = packageEntry.getValue();
            tempIndexForModify = indexToModifyAppTrack(key);
            if (tempIndexForModify != -1)
                setBitForApps.setCharAt(tempIndexForModify, '1');
        }
        return setBitForApps.toString();
    }

    public static JsonArray getContacts(Context context){

        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        JsonArray contactList = new JsonArray();
        JsonObject contactDetails;
        while (phones.moveToNext())
        {
            contactDetails = new JsonObject();
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactDetails.addProperty("name",name);
            contactDetails.addProperty("phoneNumber",phoneNumber);
            contactList.add(contactDetails);
            //Toast.makeText(ApplicationController.getInstance().getApplicationContext(),name, Toast.LENGTH_LONG).show();
            Log.d("NAmes are", name);

        }
        phones.close();
        return contactList;

    }


    public static final JsonObject getPackages(Context context) {


        JsonObject smsTransactionItem = new JsonObject();
        if (context == null)
            return null;

        Map<String, String> apps = retrieveAppPackages(context);
        /*smsTransactionItem.addProperty("installation_id", ParseInstallation.getCurrentInstallation().getInstallationId());
        smsTransactionItem.addProperty("email_id", retrieveEmail());
        smsTransactionItem.addProperty("client_logtime", System.currentTimeMillis());*/
        smsTransactionItem.addProperty("event_name", "android_installed_apps");
        smsTransactionItem.addProperty("event_data", convertPackagesToJsonString(apps));

       /* JsonObject extraInfo = new JsonObject();
        extraInfo.addProperty("browser", "chrome");
        smsTransactionItem.addProperty("extra_info", extraInfo.toString());*/
        return smsTransactionItem;
    }

    /**
     *
     * @param address from which store is it
     * @param body    content of the message
     */
    public static int filterSms(String address, String body) {

        if (address == null || body == null)
            return -1;

        String lowerCaseAddress = address.toLowerCase();
        String lowerCaseBody = body.toLowerCase();
        boolean containAddress = false;
        boolean containBody = false;
        int i;
        for (i = 0; i < Constants.NO_OF_STORE; i++) {
            if (lowerCaseAddress.contains(Constants.SMS_ADDRESS_FILTER[i])) {
                containAddress = true;
                break;
            }
        }
        if (containAddress && i >= 10) {
            return i;
        }
        if (containAddress) {
            for (int j = 0, k = 0; j < Constants.NO_OF_BODY_FILTER; j++) {
                if (lowerCaseBody.contains(Constants.SMS_BODY_FILTER[j])) {
                    k++;
                }
                if (k >= 2 && lowerCaseBody.contains("order"))
                    containBody = true;
            }
        }
        if (containAddress && containBody)
            return i;
        else
            return -1;
    }


    /**
     *
     * @param cursor
     * @return
     */

    public static JsonObject getSms(Cursor cursor, boolean firstTimeRead) {

        JsonObject smsTransactionItem = new JsonObject();
        StringBuilder smsStoreTransaction = new StringBuilder(new String(new char[Constants.NO_OF_STORE+1]).replace("\0", "0"));
        String address = null, body = null;
        int indexToIncrease = -1;

        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        if (!cursor.isAfterLast() && cursor.moveToFirst() && firstTimeRead) { // must check the result to prevent exception
            do {
                indexToIncrease = -1;
                address = cursor.getString(cursor.getColumnIndex("address"));
                body = cursor.getString(cursor.getColumnIndex("body"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                long msgTime = Long.parseLong(date);
                long currentTime = System.currentTimeMillis();
                int daysBeforeSms = (int) ((currentTime - msgTime) / (24 * 60 * 60 * 1000));

                if (daysBeforeSms <= Constants.NO_OF_DAYS_SMS)
                    indexToIncrease = Utils.filterSms(address, body);

                else
                    break;

                if (indexToIncrease != -1) {
                    smsStoreTransaction.setCharAt(indexToIncrease, '1');
                }
                smsStoreTransaction.setCharAt(Constants.NO_OF_STORE, '1');
                // use msgData
            } while (cursor.moveToNext());

        } else if (cursor.isAfterLast() == false) {
            address = cursor.getString(cursor.getColumnIndex("address"));
            body = cursor.getString(cursor.getColumnIndex("body"));
            indexToIncrease = Utils.filterSms(address, body);
            if (indexToIncrease != -1) {
                smsStoreTransaction.setCharAt(indexToIncrease, '1');
            }
        }

        /*smsTransactionItem.addProperty("installation_id", ParseInstallation.getCurrentInstallation().getInstallationId());
        smsTransactionItem.addProperty("email_id", retrieveEmail());
        smsTransactionItem.addProperty("client_logtime", System.currentTimeMillis());*/
        smsTransactionItem.addProperty("event_name", "android_sms_history");
        smsTransactionItem.addProperty("event_data", smsStoreTransaction.toString());

        /*JsonObject extraInfo = new JsonObject();
        extraInfo.addProperty("browser", "chrome");
        smsTransactionItem.addProperty("extra_info", extraInfo.toString());*/
        return smsTransactionItem;
    }

    /**
     * Get Object For accessibility event
     */

    /*public static JsonObject getAccessEventObject(String eventName, String eventData, String storeName, String productTitle, String mspId, String PriceOnStore, String priceByOurAPI) {
        JsonObject accessibilityEventObject = new JsonObject();

        accessibilityEventObject.addProperty("installation_id", ParseInstallation.getCurrentInstallation().getInstallationId());
        accessibilityEventObject.addProperty("email_id", retrieveEmail());
        accessibilityEventObject.addProperty("client_logtime", System.currentTimeMillis());
        accessibilityEventObject.addProperty("event_name", eventName);
        accessibilityEventObject.addProperty("event_data", eventData);

        JsonObject extraInfo = new JsonObject();
        extraInfo.addProperty("store_name", storeName);
        extraInfo.addProperty("product_title", productTitle);
        extraInfo.addProperty("msp_id", mspId);
        extraInfo.addProperty("price_on_store", PriceOnStore);
        extraInfo.addProperty("price_by_our_api", priceByOurAPI);
        accessibilityEventObject.addProperty("extra_info", extraInfo.toString());

        return accessibilityEventObject;
    }

    *//**
     * adding events to facebook skd..
     *//*

    public static void addFbEvents(AppEventsLogger fbEventsLogger, String eventName, String mspId, String subCategory) {

        Bundle fbBundle = new Bundle();
        fbBundle.putString(Constants.FB_EVENT_NAME, eventName);
        if (mspId != null)
            fbBundle.putString(Constants.PRODUCT_ID, mspId);
        if (subCategory != null)
            fbBundle.putString(Constants.SUBCATEGORY, subCategory);

        fbEventsLogger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, fbBundle);
    }

    *//**
     *
     *//*
    public static String getCurrentApp(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        String currentPackageName;

        if (Build.VERSION.SDK_INT > 20) {
            currentPackageName = activityManager.getRunningAppProcesses().get(0).processName;
        } else {
            currentPackageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        return currentPackageName;
    }

    *//**
     *
     */
    public static JsonObject getCurrentAppObject(int bitForPackage, long startTime, long endTime) {

        JsonObject smsTransactionItem = new JsonObject();
        StringBuilder smsStoreTransaction = new StringBuilder(new String(new char[Constants.NO_OF_APPS_TRACKED]).replace("\0", "0"));

        if (bitForPackage >= 0 && bitForPackage < Constants.NO_OF_APPS_TRACKED)
            smsStoreTransaction.setCharAt(bitForPackage, '1');

        smsTransactionItem.addProperty("installation_id", ParseInstallation.getCurrentInstallation().getInstallationId());
        smsTransactionItem.addProperty("email_id", retrieveEmail());
        smsTransactionItem.addProperty("client_logtime", System.currentTimeMillis());
        smsTransactionItem.addProperty("event_name", "android_current_app");
        smsTransactionItem.addProperty("event_data", smsStoreTransaction.toString());

        JsonObject extraInfo = new JsonObject();
        extraInfo.addProperty("start_time", startTime);
        extraInfo.addProperty("end_time", endTime);
        smsTransactionItem.addProperty("extra_info", extraInfo.toString());
        return smsTransactionItem;
    }
/*
    *//**
     * @param context
     * @param currentTimestamp
     * @param lastTimestamp
     *//*
    public static void sendAnalyticsPing(Context context, long currentTimestamp, long lastTimestamp) {
        if (currentTimestamp - lastTimestamp >= Constants.KINESIS_ANALYTICS_INTERVAL_MILLISECONDS) {
            context.startService(new Intent(context, KinesisService.class));
        }
    }

    *//**
     * Check whether this view id exists or not
     *//*

    public static String findValueOfViewById(AccessibilityNodeInfo root, String viewId, String productOrPrice, int isViewPager) {

        if (root != null) {
            List<AccessibilityNodeInfo> listOfNodesWithViewId = root.findAccessibilityNodeInfosByViewId(viewId);
            if (!listOfNodesWithViewId.isEmpty()) {
                int listSize = listOfNodesWithViewId.size();
                if (listSize > 0) {
                    if ((listSize == 1 && productOrPrice.equalsIgnoreCase("product"))) {
                        AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get(0);
                        return nodeInfo.getText() != null ? nodeInfo.getText().toString() : null;
                    } else if (listSize == 1) {
//                    Log.e("buy now ", viewId);
                        AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get(0);
                        return nodeInfo.getText() != null ? getReplacedString(nodeInfo.getText().toString()) : null;
                    } else if (listSize > 1 && productOrPrice.equalsIgnoreCase("product")) {
//                        for (int i = 0; i < listSize; i++) {
//                            AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get(i);
//                            Log.e("product ", Integer.toString(i));
//                            Log.e("product for", nodeInfo.getText().toString());
//                            if (nodeInfo.isVisibleToUser())
//                                Log.e("is Visible To user", Boolean.toString(nodeInfo.isVisibleToUser()));
//                            if (nodeInfo.isFocusable())
//                                Log.e("is View Focused ", Boolean.toString(nodeInfo.isFocusable()));
//                            if (nodeInfo.isFocused())
//                                Log.e("view is focused ", Boolean.toString(nodeInfo.isFocused()));
//                            if (nodeInfo.isAccessibilityFocused())
//                                Log.e("is Accessibility focused", Boolean.toString(nodeInfo.isAccessibilityFocused()));
//                            if (nodeInfo.isSelected())
//                                Log.e("is view selected", Boolean.toString(nodeInfo.isSelected()));
//                        }
                        AccessibilityNodeInfo nodeInfo = null;
                        for (int i = 0; i < listSize; i++) {
                            nodeInfo = listOfNodesWithViewId.get(i);
//                            Log.e("this is the number", Integer.toString(i));
                            if (nodeInfo.isVisibleToUser() && (isViewPager >= 0)) {
//                                Log.e("is Visible To user", Boolean.toString(nodeInfo.isVisibleToUser()));
                                return nodeInfo.getText() != null ? nodeInfo.getText().toString() : null;
                            }
//                            if (nodeInfo.isFocusable())
//                                Log.e("is View Focused ", Boolean.toString(nodeInfo.isFocusable()));
//                            if (nodeInfo.isFocused())
//                                Log.e("view is focused ", Boolean.toString(nodeInfo.isFocused()));
//                            if (nodeInfo.isAccessibilityFocused())
//                                Log.e("is Accessibility focused", Boolean.toString(nodeInfo.isAccessibilityFocused()));
//                            if (nodeInfo.isSelected())
//                                Log.e("is view selected", Boolean.toString(nodeInfo.isSelected()));
                        }
                        return nodeInfo.getText() != null ? nodeInfo.getText().toString() : null;
                    } else {
                        String previousValue = null;
//                        for (int i = 0; i < listSize; i++) {
//                            AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get(i);
//                            Log.e("Number of Price", Integer.toString(i));
//                            Log.e("price value for", nodeInfo.getText().toString());
//                            if (nodeInfo.isVisibleToUser() && (isViewPager >= 0))
//                                Log.e("is Visible To user", Boolean.toString(nodeInfo.isVisibleToUser()));
//                            if (nodeInfo.isFocusable())
//                                Log.e("is View Focused ", Boolean.toString(nodeInfo.isFocusable()));
//                            if (nodeInfo.isFocused())
//                                Log.e("view is focused ", Boolean.toString(nodeInfo.isFocused()));
//                            if (nodeInfo.isAccessibilityFocused())
//                                Log.e("is Accessibility focused", Boolean.toString(nodeInfo.isAccessibilityFocused()));
//                            if (nodeInfo.isSelected())
//                                Log.e("is view selected", Boolean.toString(nodeInfo.isSelected()));
//                        }

//                    if(isViewPager && (listSize >= 3)){
//                        AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get((2*listSize/3) - 1);
//                        if(nodeInfo != null) {
//                            if (nodeInfo.getText() != null) {
//                                String currentValue = nodeInfo.getText().toString();
//                                currentValue = getReplacedString(currentValue);
//                                return currentValue;
//                            }
//                        }
//                    }
                        for (int i = 0; i < listSize; i++) {
                            AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get(i);
                            if (nodeInfo != null && nodeInfo.isVisibleToUser()) {
                                if (nodeInfo.getText() != null) {
//                                    Log.e("find " + viewId + " for you", nodeInfo.getText().toString());
                                    if (previousValue == null) {
                                        previousValue = nodeInfo.getText().toString();
                                        previousValue = getReplacedString(previousValue);
                                    } else {
                                        String currentValue = nodeInfo.getText().toString();
                                        currentValue = getReplacedString(currentValue);
                                        if (currentValue != null && previousValue != null && Integer.valueOf(currentValue) < Integer.valueOf(previousValue)) {
                                            previousValue = currentValue;
                                        }
                                    }
                                }
                            }
                        }
                        return previousValue;
                    }
                }
            }
        }
        return null;
    }


    *//**
     * Check the current View is ViewPager Or not
     *//*

    public static int isViewPager(AccessibilityNodeInfo root, String viewId, String productOrPrice) {

        if (root != null) {
            List<AccessibilityNodeInfo> listOfNodesWithViewId = root.findAccessibilityNodeInfosByViewId(viewId);
            if (!listOfNodesWithViewId.isEmpty()) {
                int listSize = listOfNodesWithViewId.size();
                if (listSize >= 2) {
//                for(int i = 0 ; i < listSize ; i++){
//                    AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get(i);
//                    if(nodeInfo.isVisibleToUser()){
//                     return i;
//                    }
//                }
                    return 1;
                }
            }
        }
        return -1;
    }

    *//**
     * Get the subtitle of the correct product so we can match with the node
     *//*

    public static String currentProductSubtitle(AccessibilityNodeInfo root, String viewId) {
        if (root != null) {
            List<AccessibilityNodeInfo> listOfNodesWithViewId = root.findAccessibilityNodeInfosByViewId(viewId);
            if (!listOfNodesWithViewId.isEmpty()) {
                int listSize = listOfNodesWithViewId.size();
                if (listSize >= 2) {
                    for (int i = 0; i < listSize; i++) {
                        AccessibilityNodeInfo nodeInfo = listOfNodesWithViewId.get(i);
                        if (nodeInfo.isVisibleToUser()) {
                            return nodeInfo.getText() != null ? nodeInfo.getText().toString() : null;
                        }
                    }
                }
            }
        }
        return null;
    }

    *//**
     * check that this view contain this view Id data or not
     *//*

    public static boolean isViewIdPresent(AccessibilityNodeInfo root, String viewId) {
        if (root != null) {
            List<AccessibilityNodeInfo> listOfNodesWithViewId = root.findAccessibilityNodeInfosByViewId(viewId);
            if (!listOfNodesWithViewId.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    *//**
     * Return substring last part after a specific character
     *//*

    public static String getReplacedString(String text) {
        Pattern pattern = Pattern.compile("[0-9,]+\\.");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            text = (matcher.group(0) != null ? matcher.group(0).replaceAll("[^0-9]", "") : null);
        } else {
            text = (text != null ? text.replaceAll("[^0-9]", "") : null);
        }
        return text;
    }

    *//**
     * Create file and dump JsonArray
     *//*
    public static boolean createFileForDump(File fileDirectory, String fileName) {
        File file = new File(fileDirectory, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
//            Log.e("why file not created ", e.getMessage());
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    *//**
     * Dump Data to the created File
     *//*
    public static boolean dumpDataToFile(FileOutputStream outputStream, JsonArray configArray) {
        try {
            outputStream.write(configArray.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    *//**
     * Read file and put in to a jsonArray
     *//*

    public static String readFileDump(BufferedReader inputReader) {
        StringBuffer stringBuffer = new StringBuffer();
        String inputString;
        try {
            //Reading data line by line and storing it into the stringbuffer
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }
//            if (stringBuffer.toString() != null)
//                Log.e("input string", stringBuffer.toString());
//            if(stringBuffer.toString() != null)
//                Log.e("input string" , stringBuffer.toString());
        } catch (IOException e) {
//            Log.e("why here " , e.getMessage());
//            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    *//**
     * Reading from an xml file using xpath
     *//*

    public static String readFromXmlXpath(File file, String expression, boolean isProduct) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        if (file == null || expression == null)
            return null;
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(file);
        XPath xpath = XPathFactory.newInstance().newXPath();
        // XPath Query for showing all nodes value
        XPathExpression expr = xpath.compile(expression);
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        int nodeLength = nodes.getLength();
        for (int i = 0; i < nodeLength; i++) {
            if (nodes.item(i) != null) {
                if (!isProduct)
                    return getReplacedString(nodes.item(i).getNodeValue());
                else
                    return nodes.item(i).getNodeValue();
            }
        }
        return null;
    }

    *//**
     * Function For Printing the hierarchy of the Layout we
     * are getting from the current Screen
     *//*

    public static void generateHierarchy(AccessibilityNodeInfo root, FileOutputStream dumpOutputStream) {
        if (dumpOutputStream == null) {
            AccessibilityNodeInfoDumper.dumpWindowToFile(root);
        } else {
            AccessibilityNodeInfoDumper.dumpWindowToFile(root, dumpOutputStream);
        }
        return;
    }

    *//**
     * Checks Whether user has a sd card and its writable
     *//*

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
//        Log.e("state of storage ", state);

        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            Log.e("why it is return ", "-");
            return true;
        }
        return false;
    }

    public static View createViewForExtractImage(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
    {
        View localView = LayoutInflater.from(paramContext).inflate(paramInt1, null);
        localView.layout(0, 0, paramContext.getResources().getDimensionPixelSize(paramInt2), paramContext.getResources().getDimensionPixelSize(paramInt3));
        return localView;
    }

    public static Bitmap getBitmap(View paramView)
    {
        int j = paramView.getHeight();
        int i = paramView.getWidth();
        j = View.MeasureSpec.makeMeasureSpec(j, View.MeasureSpec.EXACTLY);
        paramView.measure(View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.EXACTLY), j);
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());
        Bitmap localBitmap = Bitmap.createBitmap(paramView.getWidth(), paramView.getHeight(), Bitmap.Config.ARGB_8888);
        paramView.draw(new Canvas(localBitmap));
        return localBitmap;
    }

*/
    public static Boolean isBluetoothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return false;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                return false;
            } else
                return true;
        }

    }
    public static String getNetworkClass(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null || !info.isConnectedOrConnecting()) {
            return null;
        }

        if (ConnectivityManager.TYPE_WIFI == info.getType()) {
            return "WIFI";
        } else if (ConnectivityManager.TYPE_WIMAX == info.getType()) {
            return "WIMAX";
        } else if (ConnectivityManager.TYPE_MOBILE == info.getType()) {
            TelephonyManager mTelephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
                default:
                    return "Unknown";
            }
        }

        return "Unknown";
    }

    public static void setNetworkType(String networkType) {
        NETWORK_TYPE = networkType;
    }

    public static class BookmarkColumns {
        public static final String URL = "url";
        public static final String VISITS = "visits";
        public static final String DATE = "date";
        public static final String BOOKMARK = "bookmark";
        public static final String TITLE = "title";
        public static final String CREATED = "created";
        public static final String FAVICON = "favicon";

        public static final String THUMBNAIL = "thumbnail";

        public static final String TOUCH_ICON = "touch_icon";

        public static final String USER_ENTERED = "user_entered";

        public static final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
    }
}

