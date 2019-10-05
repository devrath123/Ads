package com.asyncdroid.ads.mvp.view.iview;


public interface DashboardView extends BaseView{
    void setUserInfo(String name, String email);
    void logoutUser();
}
