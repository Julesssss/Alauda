package com.gmail.julianrosser91.alauda;

import android.app.Application;

public class Alauda extends Application {

    private static Alauda sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Alauda getInstance() {
        return sInstance;
    }

}
