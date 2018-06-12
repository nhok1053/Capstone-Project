package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.BuildConfig;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.PagerAdapter;
import com.example.huynhha.cookandshare.adapter.SubTabAdapter;
import com.marlonmafra.android.widget.SegmentedTab;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    SegmentedTab segmentedTab;
    ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_categories,
    };

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        segmentedTab = v.findViewById(R.id.segmentedTabCategories);
        viewPager = v.findViewById(R.id.viewPagerCategories);
        setTabLayout();
        return v;
    }

    private void setupTabIcons() {
        segmentedTab.getTabAt(0).setIcon(tabIcons[0]);
        segmentedTab.getTabAt(1).setIcon(tabIcons[1]);
    }
    private void setTabLayout() {
        SubTabAdapter subTabAdapter=new SubTabAdapter(getFragmentManager());
        subTabAdapter.addFragment(new ListAllCategoriesFragment(),"Categories");
        subTabAdapter.addFragment(new TipFragment(),"Tips");
        viewPager.setAdapter(subTabAdapter);
        segmentedTab.setupWithViewPager(viewPager);


    }

}
