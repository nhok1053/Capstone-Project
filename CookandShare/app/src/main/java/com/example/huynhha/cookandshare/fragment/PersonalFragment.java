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


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, container, false);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_category, new UpdateProfileFragment());
        transaction.commit();
        return v;
    }


}
