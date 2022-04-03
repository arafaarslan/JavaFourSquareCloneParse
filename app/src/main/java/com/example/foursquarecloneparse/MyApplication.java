package com.example.foursquarecloneparse;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by aarslan on 03/04/2022.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("APP ID")
                .clientKey("CLIENT KEY")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
