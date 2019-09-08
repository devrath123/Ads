package com.asyncdroid.ads.di.component;


import com.asyncdroid.ads.di.module.ActivityModule;
import com.asyncdroid.ads.di.module.AppModule;
import com.asyncdroid.ads.di.module.FragmentModule;
import com.asyncdroid.ads.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    ActivityComponent plus(ActivityModule activityModule);
    FragmentComponent plus(FragmentModule fragmentModule);
}
