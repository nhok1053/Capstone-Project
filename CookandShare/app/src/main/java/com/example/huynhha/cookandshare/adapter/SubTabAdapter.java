package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubTabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitles = new ArrayList<>();
    int numberOfTab;

    public SubTabAdapter(FragmentManager fm) {
        super(fm);
        this.numberOfTab = numberOfTab;
    }

    public void addFragment(Fragment fragment, String titles) {
        fragmentList.add(fragment);
        fragmentListTitles.add(titles);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }
}
