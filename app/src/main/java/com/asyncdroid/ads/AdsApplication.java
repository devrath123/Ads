package com.asyncdroid.ads;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class AdsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeLibraries();
    }

    private void initializeLibraries(){
        FirebaseApp.initializeApp(this);
    }
}