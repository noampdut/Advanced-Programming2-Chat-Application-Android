package com.example.myapplication;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context context;

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        MyApplication.baseUrl = baseUrl;
    }

    public static String baseUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        baseUrl = "http://10.0.2.2:5001/api/";
    }
}
