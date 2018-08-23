package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.ListTipsAdapter;
import com.example.huynhha.cookandshare.entity.YouTube;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTipsFragment extends Fragment {
    RecyclerView rvTips;

    public ListTipsFragment() {
        // Required empty public constructor
    }

    private ArrayList<YouTube> youtubes;
    ListTipsAdapter listTipsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_tips, container, false);
        rvTips = v.findViewById(R.id.rvListTip);
        youtubes = new ArrayList<>();
        importListCategories();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void importListCategories() {
        GridLayoutManager gln = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvTips.setLayoutManager(gln);
        MainActivity.db.collection("Youtube").orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            YouTube youTube = new YouTube(documentSnapshot.get("title").toString(), documentSnapshot.get("youtubeUrl").toString());
                            youtubes.add(youTube);
                        }
                        listTipsAdapter = new ListTipsAdapter(getActivity(), youtubes);
                        rvTips.setAdapter(listTipsAdapter);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
