package com.asyncdroid.ads.mvp.presenter;

import com.asyncdroid.ads.mvp.view.iview.HomeView;

import javax.inject.Inject;

public class HomeFragmentPresenter extends BasePresenter<HomeView> {

    private HomeView homeView;

    @Inject
    HomeFragmentPresenter() {
    }

    @Override
    public void bind(HomeView view) {
        this.homeView = homeView;
    }
}
