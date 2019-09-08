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

    public HomeFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDI.getFragmentComponent(this).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind( this, view);
        homeFragmentPresenter.bind(this);

        setUpList();

        HomeExpendableListAdapter homeExpendableListAdapter = new HomeExpendableListAdapter(getActivity(), headerList, childMap);
        ad_categories_elv.setAdapter(homeExpendableListAdapter);

        ad_categories_elv.setOnGroupExpandListener(this);

        return view;
    }

    private void setUpList(){

        headerList.clear();
        childMap.clear();

        headerList.add(new HomeCategoryModel("Electronics", R.drawable.ic_electronics));
        headerList.add(new HomeCategoryModel("Jobs", R.drawable.ic_job));
        headerList.add(new HomeCategoryModel("Personal",R.drawable.ic_account_grey));
        headerList.add(new HomeCategoryModel("Services",R.drawable.ic_services));
        headerList.add(new HomeCategoryModel("Vehicles",R.drawable.ic_vehicle));

        List<String> electronicsList = new ArrayList<>();
        electronicsList.add("Air conditioner");
        electronicsList.add("Air cooler");
        electronicsList.add("Camera");
        electronicsList.add("Desktop");
        electronicsList.add("Ceiling/Table fan");
        electronicsList.add("Washing machine");
        electronicsList.add("Geyser");
        electronicsList.add("Laptop");
        electronicsList.add("Microwave oven");
        electronicsList.add("Music system");
        electronicsList.add("Printer");
        electronicsList.add("Refrigerator");
        electronicsList.add("Toaster griller");
        electronicsList.add("TV");
        electronicsList.add("Vaccum cleaners");
        electronicsList.add("Video game");
        electronicsList.add("Other electronics");


        List<String> jobsList = new ArrayList<>();
        jobsList.add("Accountant");
        jobsList.add("Admin");
        jobsList.add("Bartender");
        jobsList.add("Beautician");
        jobsList.add("Bouncer");
        jobsList.add("BPO/Call centre");
        jobsList.add("Cameraman");
        jobsList.add("Carpenter");
        jobsList.add("Cashier");
        jobsList.add("Chef");
        jobsList.add("Content writer");
        jobsList.add("Construction labour");
        jobsList.add("Data entry");
        jobsList.add("Data scientist");
        jobsList.add("Delivery");
        jobsList.add("Designer");
        jobsList.add("Dietician");
        jobsList.add("Doctor");
        jobsList.add("Driver");
        jobsList.add("Electrician");
        jobsList.add("Event manager");
        jobsList.add("Gardner");
        jobsList.add("Gym trainer");
        jobsList.add("HR");
        jobsList.add("Housekeeping");
        jobsList.add("Internship");
        jobsList.add("IT hardware");
        jobsList.add("IT software developer");
        jobsList.add("Journalist");
        jobsList.add("Maid");
        jobsList.add("Market research analyst");
        jobsList.add("Marketing");
        jobsList.add("Mechanic");
        jobsList.add("Medical assistant");
        jobsList.add("Lawyer");
        jobsList.add("Painter");
        jobsList.add("Part time");
        jobsList.add("Plumber");
        jobsList.add("Scientist");
        jobsList.add("Security guard");
        jobsList.add("Tailor");
        jobsList.add("Teacher");
        jobsList.add("Waiter");
        jobsList.add("Wireman");
        jobsList.add("Welder");
        jobsList.add("Yoga trainer");
        jobsList.add("Other jobs");

        List<String> personalList = new ArrayList<>();
        personalList.add("java");
        personalList.add("android");
        personalList.add("iOS");

        List<String> servicesList = new ArrayList<>();
        servicesList.add("Astrology");
        servicesList.add("Automobile services");
        servicesList.add("Beauty tips services");
        servicesList.add("Career consultation");
        servicesList.add("Construction services");
        servicesList.add("Contractors");
        servicesList.add("Delivery services");
        servicesList.add("Event management");
        servicesList.add("Health care services");
        servicesList.add("Home services");
        servicesList.add("Insurance services");
        servicesList.add("Garden services");
        servicesList.add("Legal services");
        servicesList.add("Logistics services");
        servicesList.add("Matrimonial services");
        servicesList.add("Office services");
        servicesList.add("Pet services");
        servicesList.add("Real estate services");
        servicesList.add("Software development");
        servicesList.add("Travel services");
        servicesList.add("Web services");
        servicesList.add("Other services");

        List<String> vehiclesList = new ArrayList<>();
        vehiclesList.add("Bike");
        vehiclesList.add("Bus");
        vehiclesList.add("Car");
        vehiclesList.add("Cycle");
        vehiclesList.add("Scooter");
        vehiclesList.add("Scooty");
        vehiclesList.add("Trucks");
        vehiclesList.add("Other Vehicles");

        childMap.put(headerList.get(0),electronicsList);
        childMap.put(headerList.get(1),jobsList);
        childMap.put(headerList.get(2), personalList);
        childMap.put(headerList.get(3), servicesList);
        childMap.put(headerList.get(4), vehiclesList);

    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (lastGroupExpandPosition != -1 && groupPosition != lastGroupExpandPosition){
            ad_categories_elv.collapseGroup(lastGroupExpandPosition);
        }
        lastGroupExpandPosition = groupPosition;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
