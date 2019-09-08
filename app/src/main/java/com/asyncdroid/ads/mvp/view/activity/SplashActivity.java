package com.asyncdroid.ads.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.asyncdroid.ads.R;
import com.asyncdroid.ads.di.AppDI;
import com.asyncdroid.ads.mvp.presenter.SplashPresenter;
import com.asyncdroid.ads.mvp.view.iview.SplashView;
import com.asyncdroid.ads.util.Constants;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashView {

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        AppDI.getActivityComponent(this).inject(this);
        splashPresenter.bind(this);

        decideNavigationAfterTimer();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_splash;
    }

    private void decideNavigationAfterTimer(){
        new Handler().postDelayed(() -> splashPresenter.decideNavigation(), Constants.SPLASH_TIMER);
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void navigateToDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
