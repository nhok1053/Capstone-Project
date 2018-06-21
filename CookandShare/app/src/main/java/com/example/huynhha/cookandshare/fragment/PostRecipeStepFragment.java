package com.example.huynhha.cookandshare.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.MaterialAdapter;
import com.example.huynhha.cookandshare.adapter.PostStepAdapter;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.PostStep;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostRecipeStepFragment extends Fragment {

   private RecyclerView rc_postStep;
    List<PostStep> postSteps=new ArrayList<>();
    private Button btn_add_step;

    public PostRecipeStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_recipe_step, container, false);
        rc_postStep = view.findViewById(R.id.rc_post_step);
        btn_add_step = view.findViewById(R.id.btn_add_step);
        postSteps.add(new PostStep("","","","","",""));
        importPostStep();
        addStep();
        return view;
    }

    public void importPostStep() {
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rc_postStep.setNestedScrollingEnabled(false);
        rc_postStep.setLayoutManager(lln);
        PostStepAdapter postStepAdapter = new PostStepAdapter(getActivity(),postSteps);
        rc_postStep.setAdapter(postStepAdapter);
    }
    public void addStep(){
        btn_add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSteps.add(new PostStep("","","","","",""));
                importPostStep();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Test 1", "onActivityResult: "+requestCode+"   "+resultCode+ "");

    }
}
