package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
//    @BindView(R.id.rvChef)
//    RecyclerView rvChef;
//    @BindView(R.id.rvRecipe)
//    RecyclerView rvRecipe;
//    @BindView(R.id.rvPost)
    RecyclerView rvPost;

    public FrameLayout frameLayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,new LoginFragment());
//        transaction.commit();
        rvPost=v.findViewById(R.id.rvPost);
//        ButterKnife.bind(v);
        importTopPost();
        return v;
    }

    public void importTopPost() {
        rvPost.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rvPost.setLayoutManager(lln);
        TopPostAdapter postAdapter = new TopPostAdapter((ArrayList<Post>) Post.initData());
        rvPost.setAdapter(postAdapter);
    }

}
