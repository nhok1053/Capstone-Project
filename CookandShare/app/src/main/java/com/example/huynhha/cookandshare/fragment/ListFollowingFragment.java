package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.FollowListAdapter;
import com.example.huynhha.cookandshare.adapter.PersonalAllPostAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFollowingFragment extends Fragment {
    @BindView(R.id.rvFollow)
    RecyclerView rvFollow;
    private CollectionReference notebookRefUser = MainActivity.db.collection("Follow");
    private String currentUser = "4SqPgH6eUIYqzT5mKIUXw0hbqSy1";
    String getAttribute;
    private ArrayList<User> users;
    private List<Map<String, Object>> list1;
String getUserID;

    public ListFollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_following, container, false);
        ButterKnife.bind(this, v);
        users = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            getAttribute = bundle.getString("attribute");
            if(bundle.getString("getUserID")!=null){
                getUserID=bundle.getString("getUserID");
            }else {
                getUserID=currentUser;
            }
        }

        importListFollow();
        return v;
    }

    public void importListFollow() {

        rvFollow.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rvFollow.setLayoutManager(lln);
        if (getAttribute.equals("following")) {
            notebookRefUser.whereEqualTo("userID", getUserID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                list1 = (List<Map<String, Object>>) documentSnapshot.get("following");
                                for (int i = 0; i < list1.size(); i++) {
                                    String userID = list1.get(i).get("userID").toString();
                                    String userName = list1.get(i).get("userName").toString();
                                    String avatar = list1.get(i).get("userUrlImage").toString();
                                    User user = new User(userID, userName, avatar);
                                    users.add(user);
                                }
                            }
                            FollowListAdapter followListAdapter = new FollowListAdapter(users);
                            rvFollow.setAdapter(followListAdapter);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } else if (getAttribute.equals("follower")) {
            notebookRefUser.whereEqualTo("userID", getUserID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                list1 = (List<Map<String, Object>>) documentSnapshot.get("follower");
                                for (int i = 0; i < list1.size(); i++) {
                                    String userID = list1.get(i).get("userID").toString();
                                    String userName = list1.get(i).get("userName").toString();
                                    String avatar = list1.get(i).get("userUrlImage").toString();
                                    User user = new User(userID, userName, avatar);
                                    users.add(user);
                                }
                            }
                            FollowListAdapter followListAdapter = new FollowListAdapter(users);
                            rvFollow.setAdapter(followListAdapter);

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
