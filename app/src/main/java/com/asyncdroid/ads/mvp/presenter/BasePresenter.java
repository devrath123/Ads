package com.asyncdroid.ads.mvp.presenter;

import com.asyncdroid.ads.mvp.view.iview.BaseView;

public abstract class BasePresenter<V extends BaseView> {

    public abstract void bind(V view);
}
