package com.asyncdroid.ads.mvp.presenter;

import com.asyncdroid.ads.mvp.view.iview.SplashView;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashView> {

    private SplashView splashView;
    private FirebaseAuth firebaseAuth;

    @Inject
    SplashPresenter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void bind(SplashView view) {
        this.splashView = view;
    }

    public void decideNavigation(){
        if (firebaseAuth.getCurrentUser() != null){
            splashView.navigateToDashboard();
        }else {
            splashView.navigateToLogin();
        }
    }
}
