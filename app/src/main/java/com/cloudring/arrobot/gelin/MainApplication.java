package com.cloudring.arrobot.gelin;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    private static Context mContext;
    private static MainApplication instance;
    public static final String BASE_URL = "http://prod.czbsit.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static MainApplication getInstance() {
        return instance;
    }
}
