package com.asyncdroid.ads.mvp.view.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asyncdroid.ads.R;
import com.asyncdroid.ads.di.AppDI;


public class MyAdsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MyAdsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppDI.getFragmentComponent(this).inject(this);
        return inflater.inflate(R.layout.fragment_my_ads, container, false);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
