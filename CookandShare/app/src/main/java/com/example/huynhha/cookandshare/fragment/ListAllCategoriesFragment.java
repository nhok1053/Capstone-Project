package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.ListCategoriesAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Category;
import com.example.huynhha.cookandshare.entity.Post;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAllCategoriesFragment extends Fragment {
    RecyclerView rvCategories;
    ListCategoriesAdapter listCategoriesAdapter;

    public ListAllCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_all_categories, container, false);
        rvCategories = v.findViewById(R.id.rcListCategory);
        importListCategories();
        return v;
    }

    public void importListCategories() {
        GridLayoutManager gln = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvCategories.setLayoutManager(gln);
        listCategoriesAdapter = new ListCategoriesAdapter(Category.addListCategory());
        rvCategories.setAdapter(listCategoriesAdapter);
        System.out.println("Add lai categories");
    }

}
