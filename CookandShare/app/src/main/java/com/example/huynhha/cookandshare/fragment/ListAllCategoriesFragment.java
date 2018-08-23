package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.ListCategoriesAdapter;
import com.example.huynhha.cookandshare.adapter.ListTipsAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Category;
import com.example.huynhha.cookandshare.entity.Post;
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
public class ListAllCategoriesFragment extends Fragment {

    RecyclerView rvCategories;
    ListCategoriesAdapter listCategoriesAdapter;
    private ArrayList<Category> categories;
    int categoryID;
    String categoryName;
    String categoryUrlImg;
    ArrayList<String> postID;

    public ListAllCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_all_categories, container, false);
        rvCategories = v.findViewById(R.id.rcListCategory);
        categories = new ArrayList<>();
        importListCategories();
        return v;
    }

    public void importListCategories() {
        GridLayoutManager gln = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvCategories.setLayoutManager(gln);
        MainActivity.db.collection("Category").orderBy("categoryID", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            categoryID = Integer.parseInt(documentSnapshot.get("categoryID").toString());
                            categoryName = documentSnapshot.get("categoryName").toString();
                            categoryUrlImg = documentSnapshot.get("categoryUrlImage").toString();
//                            postID = (ArrayList<String>) documentSnapshot.get("postID");
                            Category category = new Category(categoryID, categoryName, categoryUrlImg);
                            categories.add(category);
                        }
                        listCategoriesAdapter = new ListCategoriesAdapter(getActivity(), categories);
                        rvCategories.setAdapter(listCategoriesAdapter);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
