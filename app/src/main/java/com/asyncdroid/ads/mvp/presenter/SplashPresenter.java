package com.asyncdroid.ads.mvp.presenter;

import com.asyncdroid.ads.manager.SharedPrefManager;
import com.asyncdroid.ads.mvp.view.iview.SplashView;
import com.asyncdroid.ads.util.SharedPrefConstants;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashView> {

    private SplashView splashView;
    private SharedPrefManager sharedPrefManager;

    @Inject
    SplashPresenter(SharedPrefManager sharedPrefManager) {
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public void bind(SplashView view) {
        this.splashView = view;
    }

    public void decideNavigation() {
        if (sharedPrefManager.getLong(SharedPrefConstants.USER_ID) > 0) {
            splashView.navigateToDashboard();
        } else {
            splashView.navigateToLogin();
        }
    }
}
