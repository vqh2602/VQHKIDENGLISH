package com.example.vqhkidenglish;

import android.app.Application;

import com.example.vqhkidenglish.ads.AppOpenManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MyApplication extends Application {

    /** The Application class that manages AppOpenManager. */
AppOpenManager  appOpenManager;
        @Override
        public void onCreate() {
            super.onCreate();
            MobileAds.initialize(
                    this,
                    new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {}
                    });

            appOpenManager = new AppOpenManager(this);
        }
    }


