package com.example.huynhha.cookandshare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.CategoryPostAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {
    @BindView(R.id.rvPostActivity)
    RecyclerView rvPost;

    String postID, userID, time, imgUrl, title, description, userImgUrl, userName;
    int like, comment;
    CategoryPostAdapter postAdapter;
    ArrayList<Post> posts;
    ArrayList<String> postCategorys;
    private CollectionReference notebookRef = MainActivity.db.collection("Post");
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
        postCategorys = getIntent().getStringArrayListExtra("categorypost");
        importTopPost();
        if ((postCategorys != null)) {
            importTopPost();
        } else {
            Toast.makeText(this, "Không có món ăn nào trong danh sách này", Toast.LENGTH_LONG).show();
        }
    }

    public void importTopPost() {
        rvPost.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this);
        rvPost.setLayoutManager(lln);
        for (String postcategory : postCategorys) {
            notebookRef.whereEqualTo("postID", postcategory)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                postID = documentSnapshot.get("postID").toString();
                                userID = documentSnapshot.get("time").toString();
                                imgUrl = documentSnapshot.get("urlImage").toString();
                                title = documentSnapshot.get("title").toString();
                                userName = documentSnapshot.get("userName").toString();
                                Post post = new Post(userName, postID, userID, title, imgUrl);
                                posts.add(post);
                            }
                            postAdapter = new CategoryPostAdapter(posts, context);
                            rvPost.setAdapter(postAdapter);
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
