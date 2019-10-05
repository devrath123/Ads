package com.asyncdroid.ads.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.asyncdroid.ads.R;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import android.view.MenuItem;

import com.asyncdroid.ads.di.AppDI;
import com.asyncdroid.ads.mvp.presenter.DashboardPresenter;
import com.asyncdroid.ads.mvp.view.fragment.HomeFragment;
import com.asyncdroid.ads.mvp.view.fragment.MessagesFragment;
import com.asyncdroid.ads.mvp.view.fragment.MyAdsFragment;
import com.asyncdroid.ads.mvp.view.fragment.PostAdFragment;
import com.asyncdroid.ads.mvp.view.iview.DashboardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        DashboardView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.toggle_iv)
    ImageView toggle_iv;

    @BindView(R.id.drawer_navigation_menu)
    NavigationView drawer_navigation_menu;

    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView bottom_navigation_menu;

    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;

    @Inject
    HomeFragment homeFragment;

    @Inject
    PostAdFragment postAdFragment;

    @Inject
    MyAdsFragment myAdsFragment;

    @Inject
    MessagesFragment messagesFragment;

    @Inject
    DashboardPresenter dashboardPresenter;

    TextView user_name_tv;
    TextView user_email_tv;
    ImageView user_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AppDI.getActivityComponent(this).inject(this);

        setHeaderViewAndListener();

        dashboardPresenter.bind(this);
        dashboardPresenter.getLoggedInUserDetails();

    }

    private void setHeaderViewAndListener() {
        View headerView = drawer_navigation_menu.getHeaderView(0);
        user_name_tv = headerView.findViewById(R.id.user_name_tv);
        user_email_tv = headerView.findViewById(R.id.user_email_tv);
        user_iv = headerView.findViewById(R.id.user_iv);

        toggle_iv.setVisibility(View.VISIBLE);

        drawer_navigation_menu.setNavigationItemSelectedListener(this);
        bottom_navigation_menu.setOnNavigationItemSelectedListener(this);

        drawer_navigation_menu.setCheckedItem(R.id.nav_home);
        bottom_navigation_menu.setSelectedItemId(R.id.bottom_menu_home);
    }

    @OnClick(R.id.toggle_iv)
    public void toggleClick() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            drawer_layout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    public void setUserInfo(String name, String email) {
        user_name_tv.setText(name);
        user_email_tv.setText(email);
    }

    @Override
    public void logoutUser() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                bottom_navigation_menu.setSelectedItemId(R.id.bottom_menu_home);
                homeNavigation();
                break;

            case R.id.nav_post_ad:
                bottom_navigation_menu.setSelectedItemId(R.id.bottom_menu_post_ad);
                postAdNavigation();
                break;

            case R.id.nav_my_ads:
                bottom_navigation_menu.setSelectedItemId(R.id.bottom_menu_my_ads);
                myAdsNavigation();
                break;

            case R.id.nav_messages:
                bottom_navigation_menu.setSelectedItemId(R.id.bottom_menu_messages);
                messagesNavigation();
                break;

            case R.id.nav_favourite:
                break;

            case R.id.nav_settings:
                break;

            case R.id.nav_logout:
                dashboardPresenter.logoutUser();
                break;

            case R.id.bottom_menu_home:
                drawer_navigation_menu.setCheckedItem(R.id.nav_home);
                homeNavigation();
                break;

            case R.id.bottom_menu_post_ad:
                drawer_navigation_menu.setCheckedItem(R.id.nav_post_ad);
                postAdNavigation();
                break;

            case R.id.bottom_menu_my_ads:
                drawer_navigation_menu.setCheckedItem(R.id.nav_my_ads);
                myAdsNavigation();
                break;

            case R.id.bottom_menu_messages:
                drawer_navigation_menu.setCheckedItem(R.id.nav_messages);
                messagesNavigation();
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void homeNavigation() {
        replaceFragment(R.id.frame_layout, homeFragment);
    }

    private void postAdNavigation() {
        replaceFragment(R.id.frame_layout, postAdFragment);
    }

    private void myAdsNavigation() {
        replaceFragment(R.id.frame_layout, myAdsFragment);
    }

    private void messagesNavigation() {
        replaceFragment(R.id.frame_layout, messagesFragment);
    }
}
