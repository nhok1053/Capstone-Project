package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.ListCategoriesAdapter;
import com.example.huynhha.cookandshare.adapter.ListTipsAdapter;
import com.example.huynhha.cookandshare.entity.Category;
import com.example.huynhha.cookandshare.entity.YouTube;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTipsFragment extends Fragment {
    RecyclerView rvTips;

    public ListTipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_list_tips, container, false);
        rvTips=v.findViewById(R.id.rvListTip);
        importListCategories();
        return v;
    }
    public void importListCategories() {

        GridLayoutManager gln = new GridLayoutManager(this.getActivity(),2,GridLayoutManager.VERTICAL,false);
        rvTips.setLayoutManager(gln);
        ListTipsAdapter listTipsAdapter = new ListTipsAdapter(YouTube.addListYoutube());
        rvTips.setAdapter(listTipsAdapter);
    }

}
