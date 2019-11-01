package com.asyncdroid.ads;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.FirebaseApp;

public class AdsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeLibraries();
    }

    private void initializeLibraries(){
        FirebaseApp.initializeApp(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Places.initialize(getApplicationContext(), getString(R.string.places_api_key));
    }
}
