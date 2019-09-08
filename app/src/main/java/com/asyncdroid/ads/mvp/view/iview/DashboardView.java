package com.asyncdroid.ads.mvp.view.iview;

import com.google.firebase.auth.FirebaseUser;

public interface DashboardView extends BaseView{
    void setUserInfo(FirebaseUser firebaseUser);
    void logoutUser();
}
