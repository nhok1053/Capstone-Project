package com.example.huynhha.cookandshare.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_notification, container, false);
        android.support.v4.app.FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        LoginFragment prmf=new LoginFragment();
        ft.replace(R.id.fl_notifycation,prmf);
        ft.addToBackStack(null);
        ft.commit();
        return v;
    }

}
