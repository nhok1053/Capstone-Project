package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.FollowListAdapter;
import com.example.huynhha.cookandshare.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    private CollectionReference notebookRefFollow = MainActivity.db.collection("Follow");
    private CollectionReference notebookRefUser = MainActivity.db.collection("User");
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    String getAttribute;
    private ArrayList<User> usersFollowing;
    private ArrayList<User> usersFollower;
    private List<Map<String, Object>> list1;
    private ArrayList<String> listGetFollow;
    String getUserID;
    private int count;

    public ListFollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_following, container, false);
        ButterKnife.bind(this, v);
        usersFollower = new ArrayList<>();
        usersFollowing = new ArrayList<>();
        Bundle bundle = getArguments();
        count = 0;
        if (bundle != null) {
            getAttribute = bundle.getString("attribute");
            if (bundle.getString("getUserID") != null) {
                getUserID = bundle.getString("getUserID");
            } else {
                getUserID = currentUser;
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
            notebookRefFollow.whereEqualTo("userID", getUserID).limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                list1 = (List<Map<String, Object>>) documentSnapshot.get("following");
                                listGetFollow = (ArrayList<String>) documentSnapshot.get("following");
                                final String[] userName = new String[1];
                                final String[] avatar = new String[1];
                                for (String s : listGetFollow) {
                                    if (!getUserID.equals(s)) {
                                        final String userID = s;
                                        notebookRefUser.whereEqualTo("userID", s).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                count++;
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                        userName[0] = queryDocumentSnapshot.getString("firstName");
                                                        avatar[0] = queryDocumentSnapshot.getString("imgUrl");
                                                        User user = new User(userID, userName[0], avatar[0]);
                                                        usersFollowing.add(user);
                                                    }
                                                    if (usersFollowing.size() == count) {
                                                        FollowListAdapter followListAdapter = new FollowListAdapter(usersFollowing);
                                                        rvFollow.setAdapter(followListAdapter);
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
//                                for (int i = 0; i < list1.size(); i++) {
//                                    if (!getUserID.equals(list1.get(i).get("userID").toString())) {
//                                        String userID = list1.get(i).get("userID").toString();
//                                        String userName = list1.get(i).get("userName").toString();
//                                        String avatar = list1.get(i).get("userUrlImage").toString();
//                                        User user = new User(userID, userName, avatar);
//                                        usersFollowing.add(user);
//                                    }
//                                }
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } else if (getAttribute.equals("follower")) {
            notebookRefFollow.whereEqualTo("userID", getUserID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                listGetFollow = (ArrayList<String>) documentSnapshot.get("follower");
//                                list1 = (List<Map<String, Object>>) documentSnapshot.get("follower");
                                final String[] userName = new String[1];
                                final String[] avatar = new String[1];
                                for (String s : listGetFollow) {
                                    if (!getUserID.equals(s)) {
                                        final String userID = s;
                                        notebookRefUser.whereEqualTo("userID", s).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                count++;
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                        userName[0] = queryDocumentSnapshot.getString("firstName");
                                                        avatar[0] = queryDocumentSnapshot.getString("imgUrl");
                                                        User user = new User(userID, userName[0], avatar[0]);
                                                        usersFollower.add(user);
                                                    }
                                                    if (usersFollower.size() == count) {
                                                        FollowListAdapter followListAdapter = new FollowListAdapter(usersFollower);
                                                        rvFollow.setAdapter(followListAdapter);
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

//                                for (int i = 0; i < list1.size(); i++) {
//                                    if (!getUserID.equals(list1.get(i).get("userID").toString())) {
//                                        String userID = list1.get(i).get("userID").toString();
//                                        String userName = list1.get(i).get("userName").toString();
//                                        String avatar = list1.get(i).get("userUrlImage").toString();
//                                        User user = new User(userID, userName, avatar);
//                                        usersFollower.add(user);
//                                    }
//                                }
                            }
//                            FollowListAdapter followListAdapter = new FollowListAdapter(usersFollower);
//                            rvFollow.setAdapter(followListAdapter);
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
