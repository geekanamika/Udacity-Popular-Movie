package com.example.android.popularmoviemvvmproject;

import android.app.Application;
import android.content.Context;

/**
 * Created by Anamika Tripathi on 12/10/18.
 */
public class App extends Application {

    private static App mInstance;

    public static Context getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = (App) getApplicationContext();
    }
}
