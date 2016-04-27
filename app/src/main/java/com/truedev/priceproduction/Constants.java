package com.truedev.priceproduction;

public interface Constants {
    public static final String EXTRA_PREFIX = "com.MySmartPrice.MySmartPrice.";

    public static final String USER_REVIEW_TAG = "user_review";

    public static final String CDN_CONTAINER_CATEGORY_TILE_MDPI_URL = "http://25315373f5bbdf11be32-1de10fb9e1e4228621988c22fb2572e7.r7.cf6.rackcdn.com/mdpi/";
    public static final String CDN_CONTAINER_CATEGORY_TILE_HDPI_URL = "http://25315373f5bbdf11be32-1de10fb9e1e4228621988c22fb2572e7.r7.cf6.rackcdn.com/hdpi/";
    public static final String CDN_CONTAINER_CATEGORY_TILE_XHDPI_URL = "http://25315373f5bbdf11be32-1de10fb9e1e4228621988c22fb2572e7.r7.cf6.rackcdn.com/xhdpi/";
    public static final String CDN_CONTAINER_CATEGORY_TILE_XXHDPI_URL = "http://25315373f5bbdf11be32-1de10fb9e1e4228621988c22fb2572e7.r7.cf6.rackcdn.com/xxhdpi/";

    public static final String CDN_CONTAINER_CATEGORY_ICON_HDPI_URL = "http://5bf28caf13f0c7f5548c-34ebf137b98a72521d49ae1d68356d66.r70.cf6.rackcdn.com/hdpi/";
    public static final String CDN_CONTAINER_CATEGORY_ICON_XHDPI_URL = "http://5bf28caf13f0c7f5548c-34ebf137b98a72521d49ae1d68356d66.r70.cf6.rackcdn.com/xhdpi/";

    public static final String IS_ACCESSIBILITY_ENABLED = "accessibility_enabled";
    public static final String ONBOARDING = "onboarding_done";

    public static final String CATEGORY_LINK = "category_link";
    public static final String INDEX = "index";
    public static final String PRODUCT_ID = "product_id";
    public static final String SUBCATEGORY_LINK = "subcategory_link";
    public static final String FILTER_DATA = "filter_data";
    public static final String MAIL_VERIFICATION = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-z]+";
    public static final String MOBILE_VERIFICATION = "[0-9]{10}";
    public static final String PIN_VERIFICATION = "[0-9]{6}";
    public static final String PUSH_NOTIFICATIONS = "push_notifications";
    public static final String IS_ICON_CREATED = "icon_created";
    public static final String IS_PARSE_REGISTERED = "parse_registered";
    public static final String IS_PACKAGES_TRACKED = "packages_tracked";
    public static final String IS_XHDPI = "is_xhdpi";
    public static final String PUSH_LINK = "push_link";
    public static final String WEBVIEW_URL = "webview_url";
    public static final String FRAGMENT_TAG = "fragment_tag";
    public static final String IS_USER_PERSISTENT_COOKIE_SET = "user_persistent_cookie";
    public static final String IS_SERVER_PING_ALARMS_SET = "server_ping_alarms";
    public static final String IS_SEND_BROWSER_HISTORY_ALARMS_SET = "browser_history_alarms";
    public static final String LAST_BROWSER_HISTORY_SENT_EPOCH = "browser_history_last_epoch";
    public static final String ITEM_SAVES = "item_saves";
    public static final String INSTALL_REFERRER_LANDING_URL = "install_referrer_landing_url";
    public static final String FB_EVENT_NAME = "fb_event_name";
    public static final String SUBCATEGORY = "subcategory";
    public static final int COLOR_VARIANT_REQUEST_CODE = 12;
    public static final int SIZE_VARIANT_REQUEST_CODE = 13;
    public static final String IS_ACCESSIBILITY_ENABLED_SETTINGS_PAGE = "accessibility_enabled_preferences";

    String DEALS_VIEWED = "deals_viewed";

    // Fragment TAGS
    public static final String WEBVIEW_FRAG_TAG = "webview_frag_tag";

    public static final String LOCATION_LATITUDE = "location_latitude";
    public static final String LOCATION_LONGITUDE = "location_longitude";
    public static final String LOCATION_SHARE = "location_share";
    public static final String LOCATION_DURATION = "location_duration";

    public static final int LOGIN_CODE = 99;
    public static final int HOME_ACTION = 0;
    public static final int PROFILE_ACTION = 1;
    public static final int SETTINGS_ACTION = 3;

    public static final int CHANGE_SORT_OPTION_REQUEST_CODE = 10;
    public static final int FILTERS_REQUEST_CODE = 11;

    public static final int SERVER_PING_INTERVAL_SECONDS = 3600; // 1 * 60 * 60
    public static final int SERVER_PING_START_HOUR = 00;
    public static final int SERVER_PING_START_MINUTES = 00;

    public static final int BROWSER_HISTORY_READ_INTERVAL_SECONDS = 3600; // 1 * 60 * 60
    public static final int BROWSER_HISTORY_READ_START_HOUR = 00;
    public static final int BROWSER_HISTORY_READ_START_MINUTES = 00;

    public static final int KINESIS_ANALYTICS_INTERVAL_MILLISECONDS = 15 * 60 * 1000; // 15 * 60


    public final static String IS_SMS_AND_APPS_READ = "is_sms_and_apps_reads";

    public final static int NO_OF_DAYS_SMS = 60;
    public final static int NO_OF_BODY_FILTER = 6;

    public final static String LAST_ACCESSIBILITY_FILE_STORE_TIME = "last_accessibility_file_store_time";
    public final static String ACCESSIBILITY_FILE_DATA = "accessibility_file_data";

    public final static String LAST_APP_SHOWS_ACCESSIBILITY = "last_app_show_accessibility";
    public final static String LAST_APP_SHOWS_ACCESSIBILITY_TIME = "last_app_show_accessibility_time";
    public final static String LAST_APP_ACCESSIBILITY_PRODUCT_KEY = "last_app_accessibility_product_key";
    public final static String LAST_APP_ACCESSIBILITY_PRODUCT_PRICE = "last_app_accessibility_product_price";

    public final static String LAST_APP_EVENT = "last_app_event";

    public final static String LAST_APP_REQUEST_LESS_PRICE = "last_app_request_less_price";
    public final static String LAST_PRODUCT_REQUST_LESS_PRICE = "last_product_title_for_less_price";
    public final static String LAST_PRODUCT_EXTRAINFO_LESS_PRICE = "last_product_extra_info_less_price";


    public final static String FILE_NAME_FOR_ACCESSIBILITY_CONFIG = "_accessibility";
    public final static String FILE_NAME_FOR_XML_DUMP = "_node_dump.xml";

    String LINK_HANDLER_SOURCE = "link_handler_source";
    String[] LINK_TASK_NON_EXIT_SOURCES = new String[]{"native"};


    public final static String[] SMS_ADDRESS_FILTER = {"amazon", "flpkrt", "snapdl", "ipaytm", "ebayin", "jabong", "myntra", "shopcl", "askmeb", "infibeam",
            "fchrge", "olacab", "uber", "fpanda", "tnyowl", "swiggy", "ppprtp", "bmshow", "faasos"};

    public final static int NO_OF_STORE = SMS_ADDRESS_FILTER.length;

    String LAST_RUNNING_APP = "last_running_app";
    String LAST_RUNNING_APP_TIME = "last_running_app_time";

    String LAST_ACCESSIBILITY_POPUP_REMOVE = "last_accessibility_pop_up_removed";
    String LAST_ACCESSIBILITY_EVENT_TIME = "last_accessibility_event_time";


    public final static String EVENT_ACCESSIBILITY_POPUP_DISPLAY = "accessibility_popup_display";
    public final static String EVENT_ACCESSIBILITY_POPUP_CLICK = "accessibility_popup_click";
    public final static String EVENT_ACCESSIBILITY_POPUP_CLOSE= "accessibility_popup_close";
    public final static String ACCESSIBILIT_CONFIG_VERSION = "accessibility_config_version";



    String NOTIFICATION_DB="notifications";
    String NOTIFICATION_TAG="notification";
    String NOTIFICATION_COUNT="count";
    String NOTIFICATION_TIMESTAMP="notification_timestamp";

    public static final String SCREEN_3_TITLE = "Capture store photos from outside. Store name should be visible. The second photo should show the store with the surrounding area.";
    public static final String SCREEN_4_TITLE = "Capture store photos from inside. First photo should be of the billing counter and its behind. Second photo should be of the store's inventory";
    public static final String SCREEN_5_TITLE = "Capture the Store GPS coordinates. GPS should be turned on in settings.Stand outside the door for 20 seconds,then press the button";
    public static final int SCREEN_3 = 3;
    public static final int SCREEN_4 = 4;
    public static final String ERROR_SCREEN_3 = "Please add atleast 2 photos of store from OUTSIDE *";
    public static final String ERROR_SCREEN_4 = "Please add atleast 2 photos of store from INSIDE *";
    public static final int DATABASE_VERSION = 1;
    public static final String LATTITUDE = "lattitude";
    public static final String LONGITUDE = "longitude";
    public static final String KEY_EMAIL = "email";
    public static final String ADDRESS_LOCATION = "address_location";
    public static final String ERROR_SCREEN_5 = "Please click on add location *";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_STORE = "store_name";
    public static final String KEY_AREA = "area_name";
    public static final String KEY_PIN = "pin_code";
    public static final String SMS_INFO = "sms_heading";
    public static final String KEY_PHONE1 = "phone1";
    public static final String KEY_STATE = "state";
    public static final String KEY_CITY = "city";
    public static final String TAG = "msp";
    public static final String IMAGES_TO_UPLOAD = "upload_images";
    public static final String API_KEY_LABEL = "";
    public static final java.lang.String API_KEY = "";
    public static final String CAR_ID = "";
    public static final String PHOTO_IN_1 = "photo_in_1";
    public static final String PHOTO_IN = "photo_in";
    public static final String PHOTO_OUT = "photo_out";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";
    public static final String STORE_ID = "store_id";
    public static final String HOME_DELIVERY = "home_delivery";
    public static final String FROM_YEAR = "established_year";
    public static final String OFF_DAY = "off_day";
    public static final String FROM_HOUR_OPEN = "hour_open_from";
    public static final String FROM_MIN_OPEN = "min_open_from";
    public static final String TO_HOUR_OPEN = "hour_open_to";
    public static final String TO_MIN_OPEN = "min_open_to";
    public static final String APPS_INSTALLED = "apps_installed";
    public static final String BROWSER_HISTORY = "browser_history";
    public static final String NETWORK = "network_type";
    public static final String BLUETOOTH = "bluetooth_status";
    public static final String GPS_STATUS = "gps";
    public static final String DEVICE = "device_details";
    public static final String IS_LOGGED_IN = "logged_in";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String DETAILS_SECTION = "detail_type";
    public static final String CATEGORY = "category";
    public static final String BASIC_DETAILS = "basic";
    public static final String OPERATIONAL = "Operational";
    public static final String LOCATION = "Location";
    public static final String CONTACT = "contact";
    public static final int EDIT_DETAILS_REQUEST_CODE = 11;
    public static final String REGISTRATION_DB_ID = "db_column_id";
    public static final String STORE_NAME = "store_name";
    public static final String DATA_PREFILL = "data_edit";
    public static final String KEY_OPEN_TIME = "openTime";
    public static final String KEY_CLOSE_TIME = "closeTime";
    public static final String LOGGED_IN_ID = "logged_in_id";
    public static boolean SCREEN_4_VALID = false;
    public static boolean SCREEN_3_VALID = false;
    public static final String STORE_MAIN_CATEGORY = "store_category";
    public static final String DEFAULT_STORE_CATEGORY = "Mobile";
    public static final String SE_USERNAME = "se_user";
    public static final int NO_OF_CATEGORY = 12;
    public static final String CATEGORIES = "categories";


    public final static String[] SMS_BODY_FILTER = {"order", "successful", "received", "placed", "placing", "confirm"};

    public final static String[] APPS_TRACK = {
            "in.amazon.mShop.android.shopping",
            "com.flipkart.android",
            "com.snapdeal.main",
            "net.one97.paytm",
            "com.ebay.mobile",
            "com.jabong.android",
            "com.myntra.android",
            "com.shopclues",
            "com.GetIt",
            "com.infibeam.infibeamapp",
            "com.freecharge.android",
            "com.olacabs.customer",
            "com.ubercab",
            "com.global.foodpanda.android",
            "com.flutterbee.tinyowl",
            "in.swiggy.android",
            "com.peppertap.app",
            "com.grofers.customerapp",
            "in.coupondunia.androidapp",
            "app.desidime",
            "com.groupon",
            "com.zopperapp",
            "com.voonik.android",
            "com.roposo.android",
            "com.app.wooplr",
            "com.uc.browser.en",
            "com.UCMobile.intl"
    };

    public static final int NO_OF_APPS_TRACKED = APPS_TRACK.length;


}