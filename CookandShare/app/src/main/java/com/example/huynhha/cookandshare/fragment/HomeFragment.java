package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.ListTipsAdapter;
import com.example.huynhha.cookandshare.adapter.TopAttributeAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.adapter.TopRecipeAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.User;
import com.example.huynhha.cookandshare.entity.YouTube;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    @BindView(R.id.rvChef)
    RecyclerView rvChef;
    @BindView(R.id.rvRecipe)
    RecyclerView rvRecipe;
    @BindView(R.id.rvPost)
    RecyclerView rvPost;

    public HomeFragment() {
        // Required empty public constructor
    }

    String postID, userID, time, imgUrl, title, description, userImgUrl;
    int like, comment;
    TopPostAdapter postAdapter;
    ArrayList<Post> posts;
    private CollectionReference notebookRef = MainActivity.db.collection("Post");

    private OnFragmentCall onFragmentCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,new LoginFragment());
//        transaction.commit();
        rvPost = v.findViewById(R.id.rvPost);
        rvChef = v.findViewById(R.id.rvChef);
        rvRecipe = v.findViewById(R.id.rvRecipe);
        posts = new ArrayList<>();
        ButterKnife.bind(v);
        importTopPost();
        importTopAttribute();
        importTopRecipes();
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public void importTopPost() {
        rvPost.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rvPost.setLayoutManager(lln);
        MainActivity.db.collection("Post")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            postID = documentSnapshot.get("postID").toString();
                            userID = documentSnapshot.get("userID").toString();
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
                        postAdapter = new TopPostAdapter(posts,getContext());
                        postAdapter.setOnAdapterClick(new TopPostAdapter.OnAdapterClick() {
                            @Override
                            public void OnCommentClicked(String postId) {
                                onFragmentCall.onCommentClicked(postId);
                            }
                        });
                        rvPost.setAdapter(postAdapter);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void importTopAttribute() {
        rvChef.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvChef.setLayoutManager(lln);
        TopAttributeAdapter postAdapter = new TopAttributeAdapter((ArrayList<User>) User.initDataToTopAttribute());
        rvChef.setAdapter(postAdapter);
    }

    public void importTopRecipes() {
        rvRecipe.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvRecipe.setLayoutManager(lln);
        TopRecipeAdapter postAdapter = new TopRecipeAdapter((ArrayList<Post>) Post.initDataToTopRecipe());
        rvRecipe.setAdapter(postAdapter);
    }

    public interface OnFragmentCall{
        void onCommentClicked(String postID);
    }

    public void setOnFragmentCall(OnFragmentCall onFragmentCall) {
        this.onFragmentCall = onFragmentCall;
    }
}
