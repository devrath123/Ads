package com.asyncdroid.ads.mvp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.asyncdroid.ads.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
    }

    abstract int getLayoutId();

    private void setStatusBarColor() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void replaceFragment(int layoutId, Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(layoutId, fragment)
                .commit();
    }
}
