package com.asyncdroid.ads.mvp.presenter;

import com.asyncdroid.ads.manager.SharedPrefManager;
import com.asyncdroid.ads.mvp.view.iview.DashboardView;
import com.asyncdroid.ads.util.RequestProperty;
import com.asyncdroid.ads.util.SharedPrefConstants;
import com.asyncdroid.ads.util.StringUtil;

import javax.inject.Inject;

public class DashboardPresenter extends BasePresenter<DashboardView> {

    private DashboardView dashboardView;
    private SharedPrefManager sharedPrefManager;

    @Inject
    DashboardPresenter(SharedPrefManager sharedPrefManager) {
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public void bind(DashboardView view) {
        this.dashboardView = view;
    }

    public void getUserDetails() {
        dashboardView.setUserInfo(sharedPrefManager.getString(SharedPrefConstants.USER_NAME),
                sharedPrefManager.getString(SharedPrefConstants.USER_EMAIL));
    }

    public void logoutUser() {
        clearSharedPrefData();
        dashboardView.logoutUser();
    }

    private void clearSharedPrefData() {

        if (sharedPrefManager.getString(SharedPrefConstants.USER_REGISTRATION_TYPE).equals(RequestProperty.REGISTRATION_TYPE_FACEBOOK)) {
            dashboardView.facebookLogout();
        } else if (sharedPrefManager.getString(SharedPrefConstants.USER_REGISTRATION_TYPE).equals(RequestProperty.REGISTRATION_TYPE_GOOGLE)) {
            dashboardView.googleLogout();
        }

        sharedPrefManager.putLong(SharedPrefConstants.USER_ID, 0);
        sharedPrefManager.putString(SharedPrefConstants.USER_NAME, StringUtil.EMPTY);
        sharedPrefManager.putString(SharedPrefConstants.USER_EMAIL, StringUtil.EMPTY);
        sharedPrefManager.putString(SharedPrefConstants.USER_REGISTRATION_TYPE, StringUtil.EMPTY);
    }
}
