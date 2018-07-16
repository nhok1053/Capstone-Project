package com.example.huynhha.cookandshare;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.PostsListAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.marlonmafra.android.widget.SegmentedTab;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {
    private static final String TAG = "SEARCH_TAG";
    SegmentedTab segmentedTab;
    ViewPager viewPager;
    Button btn_close_activity;
    Button btn_search;
    private List<Post> postsList;
    private List<Post> listPostData;
    private PostsListAdapter postsListAdapter;
    private EditText mSearchField;
    private FirebaseFirestore mFirestore;
    public List<PostStep> postSteps;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference postRef = db.collection("Post");
    final Post post = new Post();
    private int count = 0;
    private RecyclerView mResultList;
    private ProgressDialog progressDialog;
    String postID, userID, time, imgUrl, title,titleLower, description, userImgUrl;
    int like, comment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog =new ProgressDialog(this);
        setContentView(R.layout.activity_search);
        btn_close_activity = findViewById(R.id.btn_close);
        btn_search = findViewById(R.id.btn_search);
        mSearchField = (EditText) findViewById(R.id.search_field);
        mResultList = (RecyclerView) findViewById(R.id.result_list);
        postsList = new ArrayList<>();
        listPostData = new ArrayList<>();
        postsListAdapter = new PostsListAdapter(postsList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultList.setAdapter(postsListAdapter);
        storageReference = FirebaseStorage.getInstance().getReference();
        getSupportActionBar().hide();
        addDataToList();
        searchButtonActivity();
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

    public void searchButtonActivity(){
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postsList.clear();
                String searchText = mSearchField.getText().toString();
                //searchText = "Cháo";
                firestorePostSearch(searchText);
            }
        });
    }

    public String covertStringToUnsigned(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addDataToList(){
        db.collection("Post")
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
                            titleLower = covertStringToUnsigned(title);
                            Post post = new Post(postID, userID, time, imgUrl, title,titleLower, description, userImgUrl, like, comment);
                            listPostData.add(post);
                            Log.d(TAG, "Size: "+ listPostData.size() + " Title name: " + title);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void firestorePostSearch(String searchText) {
        postsList.clear();
        //notification when click search button
        Toast.makeText(Search.this, "Started Search", Toast.LENGTH_LONG).show();
        searchText = covertStringToUnsigned(searchText);
        int searchListLength = listPostData.size();
        for (int i = 0; i < searchListLength; i++) {
            if (listPostData.get(i).getTitleLower().contains(searchText)) {
                postsList.add(listPostData.get(i));
                postsListAdapter.notifyDataSetChanged();
            }
        }


    }

}
