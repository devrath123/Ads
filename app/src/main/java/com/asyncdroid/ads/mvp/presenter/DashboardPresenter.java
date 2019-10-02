package com.asyncdroid.ads.mvp.presenter;

import com.asyncdroid.ads.manager.SharedPrefManager;
import com.asyncdroid.ads.mvp.view.iview.DashboardView;

import javax.inject.Inject;

public class DashboardPresenter extends BasePresenter<DashboardView> {

    private DashboardView dashboardView;
    private SharedPrefManager sharedPrefManager;

    @Inject
    DashboardPresenter(SharedPrefManager sharedPrefManager){
         this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public void bind(DashboardView view) {
        this.dashboardView = view;
    }

    public void getLoggedInUserDetails(){
       // dashboardView.setUserInfo(firebaseAuth.getCurrentUser());
    }

    public void logoutUser(){
       // firebaseAuth.signOut();
        dashboardView.logoutUser();
    }
}
