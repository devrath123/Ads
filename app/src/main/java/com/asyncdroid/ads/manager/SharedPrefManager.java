package com.asyncdroid.ads.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.asyncdroid.ads.util.SharedPrefConstants;

public class SharedPrefManager {

    private Context context;

    public SharedPrefManager(Context context){
        this.context = context;
    }

    private SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences(SharedPrefConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value){
        getSharedPreferences().edit().putString(key,value).apply();
    }

    public String getString(String key){
        return getSharedPreferences().getString(key, "");
    }

    public void putBoolean(String key, boolean value){
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key){
        return getSharedPreferences().getBoolean(key, false);
    }

    public void putLong(String key, long value){
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    public Long getLong(String key){
        return getSharedPreferences().getLong(key, 0);
    }
}
