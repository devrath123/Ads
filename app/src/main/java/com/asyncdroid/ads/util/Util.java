package com.asyncdroid.ads.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getCategories(){
        List<String> categoriesList = new ArrayList<>();
        categoriesList.add("Select category");
        categoriesList.add("Electronics");
        categoriesList.add("Jobs");
        categoriesList.add("Personal");
        categoriesList.add("Services");
        categoriesList.add("Vehicles");

        return categoriesList;
    }

    public static List<String> getElectronicsSubCategoryList(boolean fromPostAd) {
        List<String> electronicsList = new ArrayList<>();
        if (fromPostAd){
            electronicsList.add("Select sub-category");
        }
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

        return electronicsList;
    }

    public static List<String> getJobsSubCategoryList(boolean fromPostAd) {
        List<String> jobsList = new ArrayList<>();
        if (fromPostAd){
            jobsList.add("Select sub-category");
        }
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

        return jobsList;
    }

    public static List<String> getPersonalSubCategoryList(boolean fromPostAd){
        List<String> personalList = new ArrayList<>();
        if (fromPostAd){
            personalList.add("Select sub-category");
        }

        personalList.add("Affairs");
        personalList.add("BDSM");
        personalList.add("Couple looking for Men");
        personalList.add("Couple looking for Women");
        personalList.add("Couple seeking couple");
        personalList.add("Couple massage");
        personalList.add("Female escorts");
        personalList.add("Fetish");
        personalList.add("Male escorts");
        personalList.add("Men looking for Men");
        personalList.add("Men looking for Women");
        personalList.add("Massage for male");
        personalList.add("Massage for female");
        personalList.add("Matrimonial");
        personalList.add("Swingers");
        personalList.add("Women looking for Men");
        personalList.add("Women looking for Women");
        personalList.add("Other personal services");

        return personalList;
    }

    public static List<String> getServicesSubCategoryList(boolean fromPostAd){
        List<String> servicesList = new ArrayList<>();
        if (fromPostAd){
            servicesList.add("Select sub-category");
        }
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

        return servicesList;
    }

    public static List<String> getVehiclesSubCategoryList(boolean fromPostAd){
        List<String> vehiclesList = new ArrayList<>();
        if (fromPostAd){
            vehiclesList.add("Select sub-category");
        }
        vehiclesList.add("Bike");
        vehiclesList.add("Bus");
        vehiclesList.add("Car");
        vehiclesList.add("Cycle");
        vehiclesList.add("Scooter");
        vehiclesList.add("Scooty");
        vehiclesList.add("Trucks");
        vehiclesList.add("Other Vehicles");

        return vehiclesList;
    }
}
