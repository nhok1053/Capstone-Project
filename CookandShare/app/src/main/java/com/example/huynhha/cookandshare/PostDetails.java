package com.example.huynhha.cookandshare;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDetails extends AppCompatActivity {
    private static final String TAG = "Loi";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference postRef = db.collection("Post");
    private ImageView btn_back;
    private ImageView btn_favourite;
    private ImageView btn_go_market;
    private ImageView recipe_detail_image;
    private TextView name_of_food;
    private TextView create_by_name;
    private RatingBar ratingBar;
    private TextView txt_recipe_description;
    private TextView txt_duration;
    private TextView txt_number_of_people_eat_details;
    private TextView txt_difficult;
    private TextView txt_material;
    private RecyclerView rc_list_recipe;
    private Button btn_start_cooking;
    public Post post = new Post();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_details);
        progressDialog =new ProgressDialog(this);
        String postID =getIntent().getExtras().getString("postID");
        setUp();
        storageReference = FirebaseStorage.getInstance().getReference();
        getData(postID);


    }
    public void setUp(){
        btn_back = findViewById(R.id.btn_back);
        btn_favourite = findViewById(R.id.btn_add_favourite);
        btn_go_market = findViewById(R.id.btn_add_go_market);
        recipe_detail_image = findViewById(R.id.recipe_image_details);
        name_of_food = findViewById(R.id.name_of_recipe);
        create_by_name = findViewById(R.id.create_by_name);
        ratingBar = findViewById(R.id.ratingBarSmalloverall);
        txt_recipe_description = findViewById(R.id.tv_description);
        txt_duration = findViewById(R.id.txt_duration);
        txt_number_of_people_eat_details = findViewById(R.id.number_of_people_eat_detail);
        txt_difficult = findViewById(R.id.difficult_type);
        txt_material = findViewById(R.id.tv_material_details);
        rc_list_recipe = findViewById(R.id.rc_list_recipe);
        btn_start_cooking = findViewById(R.id.start_cooking);
    }
    public Post getData(String postID){
        postRef.whereEqualTo("postID",postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        post.setUrlImage(document.get("urlImage").toString());
                        post.setTitle(document.get("title").toString());
                        post.setUserID(document.get("userID").toString());
                        post.setUserName(document.get("userName").toString());
                        post.setNumberOfPeople(document.get("numberOfPeople").toString());
                        post.setDescription(document.get("description").toString() );
                        post.setDifficult(document.get("difficult").toString());
                        List<Material> list = new ArrayList<>();
                        List<Map<String,Object>> list1 = (List<Map<String, Object>>) document.get("materials");
                        for (int i= 0; i<list1.size();i++){
                            Material material = new Material();
                            material.setMaterialName(list1.get(i).get("materialName").toString());
                            material.setQuantity(list1.get(i).get("quantity").toString());
                            list.add(material);
                        }
                        Log.d(TAG, "onComplete: "+list1.get(0).get("materialName").toString());
                        System.out.println("Quatity"+list1.get(0).toString());
                        post.setMaterials(list);
                        startLoading(post);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return post;
    }

    public void setData(Post post){
        String str ="";
        Picasso.get().load(post.getUrlImage()).resize(650,0).into(recipe_detail_image);
        name_of_food.setText(post.getTitle().toString());
        create_by_name.setText(post.getUserName().toString());
        ratingBar.setNumStars(post.getNumberOfRate());
        txt_recipe_description.setText(post.getDescription().toString());
        txt_difficult.setText(post.getDifficult().toString());
        txt_number_of_people_eat_details.setText(post.getNumberOfPeople().toString());
        List<Material> list1= post.getMaterials();
        for (int i= 0;i<list1.size();i++){
            String quatity = list1.get(i).getQuantity();
            String name = list1.get(i).getMaterialName();
            str += "  - "+quatity+" "+name+"\n";
        }
        txt_material.setText(str);
        progressDialog.dismiss();
    }
    private void startLoading(Post post) {
        progressDialog.setTitle("Đang tải ...");
        progressDialog.show();
        setData(post);
    }

}
