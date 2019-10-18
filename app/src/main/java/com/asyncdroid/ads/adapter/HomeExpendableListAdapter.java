package com.asyncdroid.ads.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asyncdroid.ads.R;
import com.asyncdroid.ads.mvp.model.HomeCategoryModel;

import java.util.List;
import java.util.Map;

public class HomeExpendableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<HomeCategoryModel> headerList;
    private Map<HomeCategoryModel, List<String>> childMap;

    public HomeExpendableListAdapter(Context context, List<HomeCategoryModel> headerList, Map<HomeCategoryModel, List<String>> childMap) {
        this.context = context;
        this.headerList = headerList;
        this.childMap = childMap;
    }

    @Override
    public int getGroupCount() {
        return headerList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childMap.get(headerList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return headerList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childMap.get(headerList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        HomeCategoryModel homeCategoryModel = headerList.get(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.home_category_row, null);
        }

        TextView headerTextView = view.findViewById(R.id.category_tv);
        headerTextView.setText(homeCategoryModel.getCategoryName());

        if (b){
            headerTextView.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            headerTextView.setTextColor(context.getResources().getColor(R.color.dark_gray));
        }

        ImageView headerImageView = view.findViewById(R.id.category_iv);
        headerImageView.setImageResource(homeCategoryModel.getCategoryImage());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String child = (String) getChild(i, i1);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.home_subcategory_row, null);
        }

        TextView childTextView = view.findViewById(R.id.subcategory_tv);
        childTextView.setText(child);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
