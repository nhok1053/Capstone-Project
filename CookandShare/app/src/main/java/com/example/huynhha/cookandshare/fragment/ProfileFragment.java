package com.example.huynhha.cookandshare.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.FavouriteActivity;
import com.example.huynhha.cookandshare.GoMarketActivity;
import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.adapter.PersonalAllPostAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    @BindView(R.id.profileUserAvatar)
    ImageView imgAvatar;
    @BindView(R.id.profileUserNumberFollowing)
    TextView txtNumberFollowing;
    @BindView(R.id.txtProfileUserFollowing)
    TextView txtFollowing;
    @BindView(R.id.profileUserNumberPost)
    TextView txtNumberAllPost;
    @BindView(R.id.txtProfileUserPost)
    TextView txtAllPost;
    @BindView(R.id.profileUserNumberFollower)
    TextView txtNumberFollower;
    @BindView(R.id.txtProfileUserFollower)
    TextView txtFollower;
    @BindView(R.id.profileUserName)
    TextView txtUsername;
    @BindView(R.id.profileUserDateOfBirth)
    TextView txtUserDateOfBirth;
    @BindView(R.id.btnProfileUpdateProfile)
    Button btnUpdate;
    @BindView(R.id.btnProfileCookbook)
    Button btnCookbook;
    @BindView(R.id.btnProfileGoMarket)
    Button btnGoMarket;
    @BindView(R.id.btnProfileFavorite)
    Button btnFavorite;
    @BindView(R.id.btnProfileSetting)
    Button btnSetting;
    @BindView(R.id.rvProfileImgPost)
    RecyclerView rvImgPost;
    private String postID;
    private String userID;
    private String imgUrl;
    ArrayList<Post> posts;
    private CollectionReference notebookRefUser = MainActivity.db.collection("User");
    private CollectionReference notebookRefPost = MainActivity.db.collection("Post");
    private String currentUser = "4SqPgH6eUIYqzT5mKIUXw0hbqSy1";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        posts = new ArrayList<>();
        userInfo();
        importTopPost();
        setBtnGoMarket();
        countPost();
        clickAllPost(txtAllPost);
        clickAllPost(txtNumberAllPost);
        settingClick();
        setBtnFavorite();
        return v;
    }

    public void settingClick() {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SettingFragment sf = new SettingFragment();
                ft.replace(R.id.fl_profile, sf);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void clickAllPost(TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvImgPost.requestFocus();
            }
        });
    }

    private void countPost() {
        notebookRefPost.whereEqualTo("userID", currentUser).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                txtNumberAllPost.setText(queryDocumentSnapshots.size() + "");
            }
        });
    }

    public void setBtnGoMarket() {
        btnGoMarket.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(getActivity(), GoMarketActivity.class);
                                               startActivity(intent);
                                           }
                                       }
        );
    }

    public void userInfo() {
        notebookRefUser.whereEqualTo("userID", currentUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Picasso.get().load(documentSnapshot.get("imgUrl").toString()).transform(new RoundedTransformation()).fit().centerCrop().into(imgAvatar);
                    txtUsername.setText(documentSnapshot.get("firstName").toString());
                    txtUserDateOfBirth.setText(documentSnapshot.get("dateOfBirth").toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void importTopPost() {
        rvImgPost.setNestedScrollingEnabled(false);
        GridLayoutManager gln = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvImgPost.setLayoutManager(gln);
        notebookRefPost.whereEqualTo("userID", currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            postID = documentSnapshot.get("postID").toString();
                            userID = documentSnapshot.get("userID").toString();
                            imgUrl = documentSnapshot.get("urlImage").toString();
                            Post post = new Post(postID, userID, imgUrl);
                            posts.add(post);
                        }
                        PersonalAllPostAdapter personalAllPostAdapter = new PersonalAllPostAdapter(posts, getActivity());
                        rvImgPost.setAdapter(personalAllPostAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    private void setBtnFavorite(){
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavouriteActivity.class);
                startActivity(intent);
            }
        });

    }

}

