package com.example.huynhha.cookandshare.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.CookingActitvity;
import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailsMaterialFragment extends Fragment {


    private android.widget.Button btn_start_cooking;
    private android.widget.TextView txt_recipe_description;
    private android.widget.TextView txt_duration;
    private android.widget.TextView txt_number_of_people_eat_details;
    private android.widget.TextView txt_difficult;
    private android.widget.TextView txt_material;
    private RecyclerView rc_list_recipe;
    private String str = "";
    private CollectionReference postRef = MainActivity.db.collection("Post");
    private String postID = "";
    private List<Material> list;



    public PostDetailsMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post_details_material, container, false);
        setUp(v);
        postID = getActivity().getIntent().getExtras().getString("postID");
        setBtnStartCooking();
        setUpDataLoading();
        return v;
    }

    public void setBtnStartCooking() {
        btn_start_cooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postID = getActivity().getIntent().getExtras().getString("postID");
                Intent intent = new Intent(getContext(), CookingActitvity.class);
                intent.putExtra("postIDStep", postID);
                startActivity(intent);
            }
        });

    }

    public void setUpDataLoading() {
        postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Post post = new Post();
                        post.setUrlImage(document.get("urlImage").toString());
                        post.setTitle(document.get("title").toString());
                        post.setUserID(document.get("userID").toString());
                        post.setUserName(document.get("userName").toString());
                        post.setNumberOfPeople(document.get("numberOfPeople").toString());
                        post.setDescription(document.get("description").toString());
                        post.setDifficult(document.get("difficult").toString());
                        post.setNumberOfRate(document.get("numberOfRate").toString());
                        post.setTime(document.get("time").toString());

                        list = new ArrayList<>();
                        List<Map<String, Object>> list1 = (List<Map<String, Object>>) document.get("materials");
                        for (int i = 0; i < list1.size(); i++) {
                            Material material = new Material();
                            material.setMaterialName(list1.get(i).get("materialName").toString());
                            material.setQuantity(list1.get(i).get("quantity").toString());
                            list.add(material);
                        }

                        System.out.println("Quatity" + list1.get(0).toString());
                        post.setMaterials(list);
                        setData(post);
                    }
                } else {
                }
            }
        });
    }

    public void setUp(View v) {
        txt_recipe_description = v.findViewById(R.id.tv_description);
        txt_duration = v.findViewById(R.id.txt_duration);
        txt_number_of_people_eat_details = v.findViewById(R.id.number_of_people_eat_detail);
        txt_difficult = v.findViewById(R.id.difficult_type);
        txt_material = v.findViewById(R.id.tv_material_details);
        rc_list_recipe = v.findViewById(R.id.rc_list_recipe);
        btn_start_cooking = v.findViewById(R.id.start_cooking);
    }

    public void setData(Post post) {
        txt_recipe_description.setText(post.getDescription().toString());
        txt_difficult.setText(post.getDifficult().toString());
        txt_number_of_people_eat_details.setText(post.getNumberOfPeople().toString());
        txt_duration.setText(post.getTime().toString());
        List<Material> list1 = post.getMaterials();
        for (int i = 0; i < list1.size(); i++) {
            String quatity = list1.get(i).getQuantity();
            String name = list1.get(i).getMaterialName();
            str += "  - " + quatity + " " + name + "\n";
        }
        txt_material.setText(str);
    }

}
