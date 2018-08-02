package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.adapter.PersonalAllPostAdapter;
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
public class ViewProfileFragment extends Fragment {
    @BindView(R.id.btnViewProfileClose)
    Button btnClose;
    @BindView(R.id.imgViewProfileUserAvatar)
    ImageView imgAvatar;
    @BindView(R.id.txtViewProfileUserNumberFollowing)
    TextView txtNumberFollowing;
    @BindView(R.id.txtViewProfileUserFollowing)
    TextView txtFollowing;
    @BindView(R.id.txtViewProfileUserNumberPost)
    TextView txtNumberAllPost;
    @BindView(R.id.txtViewProfileUserPost)
    TextView txtAllPost;
    @BindView(R.id.txtViewProfileUserNumberFollower)
    TextView txtNumberFollower;
    @BindView(R.id.txtViewProfileUserFollower)
    TextView txtFollower;
    @BindView(R.id.txtViewProfileUserName)
    TextView txtUsername;
    @BindView(R.id.txtViewProfileUserDateOfBirth)
    TextView txtUserDateOfBirth;
    @BindView(R.id.btnViewProfileFollow)
    Button btnFollow;
    @BindView(R.id.rvViewProfileImgPost)
    RecyclerView rvImgPost;
    private String postID;
    private String userID;
    private String imgUrl;
    private String getUserID;
    ArrayList<Post> posts;
    private CollectionReference notebookRefUser = MainActivity.db.collection("User");
    private CollectionReference notebookRefPost = MainActivity.db.collection("Post");
    private String currentUser = "4SqPgH6eUIYqzT5mKIUXw0hbqSy1";


    public ViewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_profile, container, false);
        ButterKnife.bind(this, v);
        posts = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            getUserID = bundle.getString("userID");
        } else {
            getUserID = "";
        }
        userInfo();
        countPost();
        importTopPost();
        clickAllPost(txtAllPost);
        clickAllPost(txtNumberAllPost);
        close();
        return v;
    }

    private void countPost() {
        notebookRefPost.whereEqualTo("userID", getUserID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                txtNumberAllPost.setText(queryDocumentSnapshots.size() + "");
            }
        });
    }

    public void userInfo() {
        notebookRefUser.whereEqualTo("userID", getUserID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Picasso.get().load(documentSnapshot.get("imgUrl").toString()).transform(new RoundedTransformation()).fit().centerCrop().into(imgAvatar);
                    txtUsername.setText(documentSnapshot.get("firstName").toString());
                    txtUserDateOfBirth.setText(documentSnapshot.get("dateOfBirth").toString());
                    if (currentUser.equals(getUserID.trim())) {
                        btnFollow.setVisibility(View.INVISIBLE);
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

    public void importTopPost() {
        rvImgPost.setNestedScrollingEnabled(false);
        GridLayoutManager gln = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvImgPost.setLayoutManager(gln);
        notebookRefPost.whereEqualTo("userID", getUserID)
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
    private void close(){
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().finish();
            }
        });
    }
    private void clickAllPost(TextView tv){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvImgPost.requestFocus();
            }
        });
    }
}
