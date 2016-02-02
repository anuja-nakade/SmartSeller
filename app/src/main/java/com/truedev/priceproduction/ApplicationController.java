package com.truedev.priceproduction;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Yash on 04/09/15.
 */
public class ApplicationController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Parse.initialize(this, "1202aqGgq09W8L9tCXrZOeUYE5ZBs0QpMHB7FCLq", "mI9pbjJwjrYZZrANNleIVi28nPtvHkYm7QxyHdDd");
        Parse.initialize(this, "tOfdELlnGOXzVz5EcM9hUT8KXeAeRsKVt2bO3Isx", "s3pt2Jc0jiLcUbRUCiPJpAsbFldY4fy9PAI9jrGk");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
