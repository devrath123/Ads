package com.asyncdroid.ads.di.component;

import com.asyncdroid.ads.di.module.FragmentModule;
import com.asyncdroid.ads.mvp.view.fragment.HomeFragment;
import com.asyncdroid.ads.mvp.view.fragment.MessagesFragment;
import com.asyncdroid.ads.mvp.view.fragment.MyAdsFragment;
import com.asyncdroid.ads.mvp.view.fragment.PostAdFragment;

import dagger.Subcomponent;

@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(HomeFragment homeFragment);
    void inject(PostAdFragment postAdFragment);
    void inject(MyAdsFragment myAdsFragment);
    void inject(MessagesFragment messagesFragment);
}
