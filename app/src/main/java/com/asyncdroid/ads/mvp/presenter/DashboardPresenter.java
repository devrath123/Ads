package com.asyncdroid.ads.mvp.presenter;

import com.asyncdroid.ads.mvp.view.iview.DashboardView;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class DashboardPresenter extends BasePresenter<DashboardView> {

    private DashboardView dashboardView;
    private FirebaseAuth firebaseAuth;

    @Inject
    DashboardPresenter(FirebaseAuth firebaseAuth){
         this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void bind(DashboardView view) {
        this.dashboardView = view;
    }

    public void getLoggedInUserDetails(){
        dashboardView.setUserInfo(firebaseAuth.getCurrentUser());
    }

    public void logoutUser(){
        firebaseAuth.signOut();
        dashboardView.logoutUser();
    }
}
