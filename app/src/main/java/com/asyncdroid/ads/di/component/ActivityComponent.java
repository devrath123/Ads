package com.asyncdroid.ads.di.component;


import com.asyncdroid.ads.di.module.ActivityModule;
import com.asyncdroid.ads.di.module.FragmentModule;
import com.asyncdroid.ads.di.scope.ActivityScope;
import com.asyncdroid.ads.mvp.view.activity.DashboardActivity;
import com.asyncdroid.ads.mvp.view.activity.LoginActivity;
import com.asyncdroid.ads.mvp.view.activity.SignUpActivity;
import com.asyncdroid.ads.mvp.view.activity.SplashActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);
    void inject(LoginActivity loginActivity);
    void inject(SignUpActivity signUpActivity);
    void inject(DashboardActivity dashboardActivity);
}
