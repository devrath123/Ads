package com.asyncdroid.ads.mvp.view.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.asyncdroid.ads.R;
import com.asyncdroid.ads.adapter.HomeExpendableListAdapter;
import com.asyncdroid.ads.di.AppDI;
import com.asyncdroid.ads.mvp.model.HomeCategoryModel;
import com.asyncdroid.ads.mvp.presenter.HomeFragmentPresenter;
import com.asyncdroid.ads.mvp.view.iview.HomeView;
import com.asyncdroid.ads.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements HomeView, ExpandableListView.OnGroupExpandListener {

    @BindView(R.id.ad_categories_elv)
    ExpandableListView ad_categories_elv;

    @Inject
    HomeFragmentPresenter homeFragmentPresenter;

    private List<HomeCategoryModel> headerList = new ArrayList<>();
    private Map<HomeCategoryModel, List<String>> childMap = new HashMap<>();

    private int lastGroupExpandPosition;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDI.getFragmentComponent(this).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        homeFragmentPresenter.bind(this);

        setUpList();

        HomeExpendableListAdapter homeExpendableListAdapter = new HomeExpendableListAdapter(getActivity(), headerList, childMap);
        ad_categories_elv.setAdapter(homeExpendableListAdapter);

        ad_categories_elv.setOnGroupExpandListener(this);

        return view;
    }

    private void setUpList() {

        headerList.clear();
        childMap.clear();

        headerList.add(new HomeCategoryModel("Electronics", R.drawable.ic_electronics));
        headerList.add(new HomeCategoryModel("Jobs", R.drawable.ic_job));
        headerList.add(new HomeCategoryModel("Personal", R.drawable.ic_account_grey));
        headerList.add(new HomeCategoryModel("Services", R.drawable.ic_services));
        headerList.add(new HomeCategoryModel("Vehicles", R.drawable.ic_vehicle));

        childMap.put(headerList.get(0), Util.getElectronicsSubCategoryList(false));
        childMap.put(headerList.get(1), Util.getJobsSubCategoryList(false));
        childMap.put(headerList.get(2), Util.getPersonalSubCategoryList(false));
        childMap.put(headerList.get(3), Util.getServicesSubCategoryList(false));
        childMap.put(headerList.get(4), Util.getVehiclesSubCategoryList(false));

    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (lastGroupExpandPosition != -1 && groupPosition != lastGroupExpandPosition) {
            ad_categories_elv.collapseGroup(lastGroupExpandPosition);
        }
        lastGroupExpandPosition = groupPosition;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
