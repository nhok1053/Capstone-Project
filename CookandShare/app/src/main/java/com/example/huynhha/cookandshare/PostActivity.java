package com.example.huynhha.cookandshare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
Context ctx;
    String postID, userID, time, imgUrl, title, description, userImgUrl;
    int like, comment;
    TopPostAdapter postAdapter;
    ArrayList<Post> posts;
    ArrayList<String> postCategorys;
    private CollectionReference notebookRef = MainActivity.db.collection("Post");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        ctx=this;
        postCategorys = getIntent().getStringArrayListExtra("categorypost");
        posts = new ArrayList<>();
        importTopPost();
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
                                time = documentSnapshot.get("time").toString();
                                imgUrl = documentSnapshot.get("urlImage").toString();
                                title = documentSnapshot.get("title").toString();
                                description = documentSnapshot.get("description").toString();
                                userImgUrl = documentSnapshot.get("userImgUrl").toString();
                                like = Integer.parseInt(documentSnapshot.get("like").toString());
                                comment = Integer.parseInt(documentSnapshot.get("comment").toString());
                                Post post = new Post(postID, userID, time, imgUrl, title, description, userImgUrl, like, comment);
                                posts.add(post);
                            }





                            postAdapter = new TopPostAdapter(ctx,posts);
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
