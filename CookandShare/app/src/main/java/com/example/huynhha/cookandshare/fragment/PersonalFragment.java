package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.BuildConfig;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.PagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_categories,
    };

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, container, false);
        tabLayout = v.findViewById(R.id.tabLayoutCategories);
        viewPager = v.findViewById(R.id.viewPagerCategories);
        setTabLayout();
        return v;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setTabLayout() {
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        //adding fragment
        pagerAdapter.addFragment(new ListAllCategoriesFragment(), "");
        pagerAdapter.addFragment(new TipFragment(), "");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        System.out.println("App id : " + BuildConfig.APPLICATION_ID);
    }
}
