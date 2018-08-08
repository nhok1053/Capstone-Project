package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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
import java.util.List;
import java.util.Map;

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
    private CollectionReference notebookRefFollow = MainActivity.db.collection("Follow");
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
        countFollowingFollower("following", txtNumberFollowing);
        countFollowingFollower("follower", txtNumberFollower);
        importTopPost();
        clickAllPost(txtAllPost);
        clickAllPost(txtNumberAllPost);
        clickFollowing(txtFollowing);
        clickFollowing(txtNumberFollowing);
        clickFowller(txtFollower);
        clickFowller(txtNumberFollower);
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

    private void countFollowingFollower(final String s, final TextView tv) {
        notebookRefFollow.whereEqualTo("userID", getUserID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            tv.setText(((List<Map<String, Object>>) documentSnapshot.get(s)).size() + "");
                        }
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

    private void clickFollowing(TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("attribute", "following");
                bundle.putString("getUserID",getUserID);
                ListFollowingFragment listFollowingFragment = new ListFollowingFragment();
                listFollowingFragment.setArguments(bundle);
                ft.replace(R.id.fl_main, listFollowingFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void clickFowller(TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("attribute", "follower");
                bundle.putString("getUserID",getUserID);
                ListFollowingFragment listFollowingFragment = new ListFollowingFragment();
                listFollowingFragment.setArguments(bundle);
                ft.replace(R.id.fl_main, listFollowingFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void close() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().finish();
//                getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
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
}
