package com.asyncdroid.ads.di.module;

import android.app.Activity;
import android.content.Context;

import com.asyncdroid.ads.mvp.view.activity.BaseActivity;
import com.asyncdroid.ads.mvp.view.fragment.HomeFragment;
import com.asyncdroid.ads.mvp.view.fragment.MessagesFragment;
import com.asyncdroid.ads.mvp.view.fragment.MyAdsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    Activity providesActivity() {
        return baseActivity;
    }

    @Provides
    Context providesContext() {
        return baseActivity;
    }

    @Provides
    HomeFragment providesHomeFragment(){
        return new HomeFragment();
    }

    @Provides
    MyAdsFragment providesMyAdsFragment(){
        return new MyAdsFragment();
    }

    @Provides
    MessagesFragment providesMessagesFragment(){
        return new MessagesFragment();
    }

}
