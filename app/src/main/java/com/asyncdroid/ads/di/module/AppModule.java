package com.asyncdroid.ads.di.module;

import android.app.Application;

import com.asyncdroid.ads.manager.SharedPrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Singleton
    @Provides
    Application providesApplication(){
        return application;
    }

    @Singleton
    @Provides
    FirebaseAnalytics providesFirebaseAnalytics(){
        return FirebaseAnalytics.getInstance(application);
    }

    @Singleton
    @Provides
    SharedPrefManager providesSharedPrefManager(){
        return new SharedPrefManager(application);
    }
}
