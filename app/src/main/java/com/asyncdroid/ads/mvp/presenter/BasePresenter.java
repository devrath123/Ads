package com.asyncdroid.ads.mvp.presenter;

import android.os.Bundle;

import com.asyncdroid.ads.mvp.view.iview.BaseView;
import com.google.firebase.analytics.FirebaseAnalytics;

public abstract class BasePresenter<V extends BaseView> {

    public abstract void bind(V view);

    void trackEvent(FirebaseAnalytics firebaseAnalytics, String event){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, event);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
