package com.example.huynhha.cookandshare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.CategoryPostAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {
    @BindView(R.id.rvPostActivity)
    RecyclerView rvPost;

    CategoryPostAdapter postAdapter;
    ArrayList<Post> posts;
    //    ArrayList<String> postCategorys;
    int categoryID;
    private CollectionReference postRef = MainActivity.db.collection("Post");
    private CollectionReference categoryRef = MainActivity.db.collection("Category");
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        posts = new ArrayList<>();
        String getIntent=getIntent().getExtras().getString("categoryID");

        categoryID = Integer.parseInt(getIntent);
        if ((getIntent().getStringExtra("categoryID") != null)) {
            importTopPost();
        } else {
            Toast.makeText(this, "Không có món ăn nào trong danh sách này", Toast.LENGTH_LONG).show();
        }
    }

    public void importTopPost() {
        final int[] count = {0};
        rvPost.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this);
        rvPost.setLayoutManager(lln);
        categoryRef.whereEqualTo("categoryID", categoryID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot queryDocumentSnapshot :
                            task.getResult()) {
                        ArrayList<String> listPost = new ArrayList<>();
                        listPost = (ArrayList<String>) queryDocumentSnapshot.get("postID");
                        for (final String postID : listPost
                                ) {
                            postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    count[0]++;
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot queryDocumentSnapshot1 : task.getResult()
                                                ) {
                                            String userID = queryDocumentSnapshot1.get("userID").toString();
                                            String imgUrl = queryDocumentSnapshot1.get("urlImage").toString();
                                            String title = queryDocumentSnapshot1.get("title").toString();
                                            String rate = queryDocumentSnapshot1.get("numberOfRate").toString();
                                            Post post = new Post();
                                            post.setPostID(postID);
                                            post.setUserID(userID);
                                            post.setTitle(title);
                                            post.setUrlImage(imgUrl);
                                            post.setNumberOfRate(rate);
                                            posts.add(post);
                                        }
                                        if (count[0] == task.getResult().size()) {
                                            postAdapter = new CategoryPostAdapter(posts, context);
                                            rvPost.setAdapter(postAdapter);
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
