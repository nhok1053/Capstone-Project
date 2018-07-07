package com.example.huynhha.cookandshare;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.huynhha.cookandshare.adapter.PostsListAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.marlonmafra.android.widget.SegmentedTab;

import java.util.List;

public class Search extends AppCompatActivity {
    SegmentedTab segmentedTab;
    ViewPager viewPager;
    Button btn_close_activity;
    Button btn_search;
    public List<PostStep> postSteps;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference postRef = db.collection("Post");
    final Post post = new Post();
    private int count = 0;
    private RecyclerView mResultList;
    private List<Post> postsList;
    private List<Post> listPostData;
    private PostsListAdapter postsListAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog =new ProgressDialog(this);
        setContentView(R.layout.activity_search);
        btn_close_activity = findViewById(R.id.btn_close);
        btn_search = findViewById(R.id.btn_search);
        storageReference = FirebaseStorage.getInstance().getReference();
        getSupportActionBar().hide();
        closeActivity();
    }

    public void closeActivity() {
        btn_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.this.finish();
            }
        });
    }


}
