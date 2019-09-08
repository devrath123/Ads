package com.asyncdroid.ads.di;


import androidx.fragment.app.Fragment;

import com.asyncdroid.ads.di.component.ActivityComponent;
import com.asyncdroid.ads.di.component.AppComponent;
import com.asyncdroid.ads.di.component.DaggerAppComponent;
import com.asyncdroid.ads.di.component.FragmentComponent;
import com.asyncdroid.ads.di.module.ActivityModule;
import com.asyncdroid.ads.di.module.AppModule;
import com.asyncdroid.ads.di.module.FragmentModule;
import com.asyncdroid.ads.di.module.NetworkModule;
import com.asyncdroid.ads.mvp.view.activity.BaseActivity;

public class AppDI {

    private static AppComponent getAppComponent(BaseActivity baseActivity){
        return DaggerAppComponent.builder()
                                 .appModule(new AppModule(baseActivity.getApplication()))
                                 .networkModule(new NetworkModule())
                                 .build();
    }

    public static ActivityComponent getActivityComponent(BaseActivity baseActivity){
        return getAppComponent(baseActivity).plus(new ActivityModule(baseActivity));
    }

    private static AppComponent getAppComponent(Fragment fragment){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(fragment.getActivity().getApplication()))
                .networkModule(new NetworkModule())
                .build();
    }


    public static FragmentComponent getFragmentComponent(Fragment fragment){
        return getAppComponent(fragment).plus(new FragmentModule(fragment));
    }
}
