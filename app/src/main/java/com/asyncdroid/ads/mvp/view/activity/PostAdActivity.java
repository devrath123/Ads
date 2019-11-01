package com.asyncdroid.ads.mvp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.asyncdroid.ads.R;
import com.asyncdroid.ads.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class PostAdActivity extends BaseActivity {

    @BindView(R.id.ad_category_sp)
    Spinner ad_category_sp;

    @BindView(R.id.ad_sub_category_sp)
    Spinner ad_sub_category_sp;

    @BindView(R.id.location_et)
    EditText location_et;

    @BindView(R.id.ad_description_et)
    EditText ad_description_et;

    private ArrayAdapter subCategoryAdapter;
    private String selectedCategory;
    private String selectedSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(this), android.R.layout.simple_spinner_dropdown_item, Util.getCategories());
        ad_category_sp.setAdapter(arrayAdapter);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_post_ad;
    }

    @OnItemSelected(R.id.ad_category_sp)
    void selectedCategory(int selectedPosition) {
        selectedCategory = Util.getCategories().get(selectedPosition);
        switch (selectedPosition) {
            case 0:
                List<String> emptyArrayList = new ArrayList<>();
                emptyArrayList.add("Select sub-category");
                subCategoryAdapter = new ArrayAdapter<>(Objects.requireNonNull(this), android.R.layout.simple_spinner_item, emptyArrayList);
                break;

            case 1:
                subCategoryAdapter = new ArrayAdapter<>(Objects.requireNonNull(this), android.R.layout.simple_spinner_item, Util.getElectronicsSubCategoryList(true));
                break;

            case 2:
                subCategoryAdapter = new ArrayAdapter<>(Objects.requireNonNull(this), android.R.layout.simple_spinner_item, Util.getJobsSubCategoryList(true));
                break;

            case 3:
                subCategoryAdapter = new ArrayAdapter<>(Objects.requireNonNull(this), android.R.layout.simple_spinner_item, Util.getPersonalSubCategoryList(true));
                break;

            case 4:
                subCategoryAdapter = new ArrayAdapter<>(Objects.requireNonNull(this), android.R.layout.simple_spinner_item, Util.getServicesSubCategoryList(true));
                break;

            case 5:
                subCategoryAdapter = new ArrayAdapter<>(Objects.requireNonNull(this), android.R.layout.simple_spinner_item, Util.getVehiclesSubCategoryList(true));
                break;

        }
        if (subCategoryAdapter != null) {
            subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        ad_sub_category_sp.setAdapter(subCategoryAdapter);
    }

    @OnItemSelected(R.id.ad_sub_category_sp)
    void selectedSubCategory(Spinner spinner, int selectedPosition){
        selectedSubCategory = (String)spinner.getSelectedItem();
    }
}
