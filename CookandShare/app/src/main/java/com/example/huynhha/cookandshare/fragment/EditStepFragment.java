package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.PostStepAdapter;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditStepFragment extends Fragment {
    private RecyclerView rcEditStep;
    private CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post");
    private CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
    private PostStepAdapter postStepAdapter;
    public List<Map<String, Object>> listStep;
    public ArrayList<PostStep> postSteps;
    private Button btnAddStep;
    private int count = 0;
    private String postID ="";

    public EditStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_step, container, false);
        listStep = new ArrayList<>();
        postSteps = new ArrayList<>();
        btnAddStep = v.findViewById(R.id.btn_add_edit_step);
        rcEditStep = v.findViewById(R.id.rc_edit_post_step);
        postID = getActivity().getIntent().getExtras().getString("postID");
        getData(postID);
        return v;
    }

    public void getData(String postID) {
        postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listStep = (List<Map<String, Object>>) document.get("postSteps");
                        if (listStep != null) {
                            for (int i = 0; i < listStep.size(); i++) {
                                PostStep postStep = new PostStep();
                                postStep.setTemp(listStep.get(i).get("temp").toString());
                                postStep.setTime_duration(listStep.get(i).get("time_duration").toString());
                                postStep.setDescription(listStep.get(i).get("description").toString());
                                postStep.setImgURL(listStep.get(i).get("imgURL").toString());
                                postSteps.add(postStep);
                                count++;
                            }
                            if (count == listStep.size()) {
                                System.out.println("Set dat zo");
                                setData();
                            }

                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void setData() {
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rcEditStep.setLayoutManager(lln);
        postStepAdapter = new PostStepAdapter(getContext(), postSteps);
        rcEditStep.setAdapter(postStepAdapter);
    }

    public List<PostStep> getListStep() {
        List<PostStep> postStepList;
        postStepAdapter = new PostStepAdapter(getContext(), postSteps);
        postStepList = postStepAdapter.getPostSteps();
        return postStepList;
    }

    public void demo() {
        postRef.orderBy("postTime", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Map<String, Object> map = queryDocumentSnapshot.getData();
                        Date date = (Date) map.get("postTime");
                        System.out.println("I need energy: " + date);
                        String postID = queryDocumentSnapshot.getString("postID");
                        System.out.println("I need energy more: " + postID);
                    }
                }
            }
        });
    }
}
