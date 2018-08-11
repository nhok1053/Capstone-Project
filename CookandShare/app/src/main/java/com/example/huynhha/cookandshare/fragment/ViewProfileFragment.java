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
import com.example.huynhha.cookandshare.entity.Follow;
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
import java.util.HashMap;
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
    @BindView(R.id.btnViewProfileUnFollow)
    Button btnUnFollow;
    @BindView(R.id.rvViewProfileImgPost)
    RecyclerView rvImgPost;
    private String postID;
    private String userID;
    private String imgUrl;
    private String getUserID;
    private ArrayList<Post> posts;
    private ArrayList<Follow> unfollows;
    private Follow userFollowing;
    private Follow userFollower;
    private String sFollowing;
    private String sFollower;
    private List<Map<String, Object>> list1;
    private List<Map<String, Object>> listFollowing;
    private List<Map<String, Object>> listFollower;
    private List<Map<String, Object>> listUnFollowing;
    private List<Map<String, Object>> listUnFollower;
    private Map<String, Object> mapFollowing = new HashMap<>();
    private Map<String, Object> mapFollower = new HashMap<>();
    private CollectionReference notebookRefUser = MainActivity.db.collection("User");
    private CollectionReference notebookRefPost = MainActivity.db.collection("Post");
    private CollectionReference notebookRefFollow = MainActivity.db.collection("Follow");
    private String currentUser = "lPCl0cwAb9PYliqt5acSTLYBI4t1";
    private int count;

    public ViewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_profile, container, false);
        ButterKnife.bind(this, v);
        count = 0;
        posts = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            getUserID = bundle.getString("userID");
        } else {
            getUserID = "";
        }
        listUnFollowing = new ArrayList<>();
        listUnFollower = new ArrayList<>();
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
        followOther();
        unFollowOther();
        close();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

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
        notebookRefFollow.whereEqualTo("userID", currentUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                count = 1;
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    try {
                        list1 = (List<Map<String, Object>>) documentSnapshot.get("following");
                        for (int i = 0; i < list1.size(); i++) {
                            String s = list1.get(i).get("userID").toString();
                            if (s.equals(getUserID)) {
                                btnFollow.setVisibility(View.GONE);
                                btnUnFollow.setVisibility(View.VISIBLE);
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        btnFollow.setVisibility(View.VISIBLE);
                        btnUnFollow.setVisibility(View.GONE);
                    }
                }
            }
        });
        notebookRefUser.whereEqualTo("userID", getUserID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                count = 1;
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    String userID = documentSnapshot.get("userID").toString();
                    String userName = documentSnapshot.get("secondName").toString();
                    String userImgUrl = documentSnapshot.get("imgUrl").toString();

                    userFollowing = new Follow(userID, userName, userImgUrl);

                    Picasso.get().load(userImgUrl).transform(new RoundedTransformation()).fit().centerCrop().into(imgAvatar);
                    txtUsername.setText(userName);
                    txtUserDateOfBirth.setText(documentSnapshot.get("dateOfBirth").toString());
                    if (currentUser.equals(getUserID.trim())) {
                        btnFollow.setVisibility(View.INVISIBLE);
                        btnUnFollow.setVisibility(View.GONE);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
        notebookRefUser.whereEqualTo("userID", currentUser).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                count = 1;
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    String userID = documentSnapshot.get("userID").toString();
                    String userName = documentSnapshot.get("secondName").toString();
                    String userImgUrl = documentSnapshot.get("imgUrl").toString();
                    userFollower = new Follow(userID, userName, userImgUrl);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void loadFollowUserInfo() {
        if (count > 0) {
            try {
                notebookRefFollow.whereEqualTo("userID", currentUser).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 2;
                        System.out.println(count+"AAAAAAAAAAAAAA");
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            sFollowing = documentSnapshot.getId();
                            try {
                                listFollowing = (List<Map<String, Object>>) documentSnapshot.get("following");
                                mapFollowing.put("userID", userFollowing.getUserID());
                                mapFollowing.put("userName", userFollowing.getUserName());
                                mapFollowing.put("userUrlImage", userFollowing.getUserUrlImage());
                                listFollowing.add(mapFollowing);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                });
                notebookRefFollow.whereEqualTo("userID", getUserID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 2;
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            sFollower = documentSnapshot.getId();
                            try {
                                listFollower = (List<Map<String, Object>>) documentSnapshot.get("follower");
                                mapFollower.put("userID", userFollower.getUserID());
                                mapFollower.put("userName", userFollower.getUserName());
                                mapFollower.put("userUrlImage", userFollower.getUserUrlImage());
                                listFollower.add(mapFollower);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                });
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void loadUnFollowUserInfo() {
        if (count > 0) {
            try {
                notebookRefFollow.whereEqualTo("userID", currentUser).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 2;
                        System.out.println(count+"AAAAAAAAAAAAAA");
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            sFollowing = documentSnapshot.getId();
                            try {
                                Map<String, Object> updateMap = new HashMap<>();
                                listFollowing = (List<Map<String, Object>>) documentSnapshot.get("following");
                                for (int i = 0; i < listFollowing.size(); i++) {
                                    if (!listFollowing.get(i).get("userID").equals(userFollowing.getUserID())) {
                                        updateMap.put("userID", listFollowing.get(i).get("userID"));
                                        updateMap.put("userName", listFollowing.get(i).get("userName"));
                                        updateMap.put("userUrlImage", listFollowing.get(i).get("userUrlImage"));
                                    }
                                }
                                listUnFollowing.add(updateMap);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                });
                notebookRefFollow.whereEqualTo("userID", getUserID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 2;
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            sFollower = documentSnapshot.getId();
                            try {
                                Map<String, Object> updateMap = new HashMap<>();
                                listFollower = (List<Map<String, Object>>) documentSnapshot.get("follower");
                                for (int i = 0; i < listFollower.size(); i++) {
                                    if (!listFollower.get(i).get("userID").equals(userFollower.getUserID())) {
                                        updateMap.put("userID", listFollower.get(i).get("userID"));
                                        updateMap.put("userName", listFollower.get(i).get("userName"));
                                        updateMap.put("userUrlImage", listFollower.get(i).get("userUrlImage"));
                                    }
                                }
                                listUnFollower.add(updateMap);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                });
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void followOther() {
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFollowUserInfo();
                System.out.println(count);
                if (count > 1) {
                    if (listFollower.size() > 0 && listFollowing.size() > 0) {
                        notebookRefFollow.document(sFollowing).update("following", listFollowing);
                        notebookRefFollow.document(sFollower).update("follower", listFollower);
                        if (getFragmentManager() != null) {
                            getFragmentManager().beginTransaction().detach(ViewProfileFragment.this).attach(ViewProfileFragment.this).commit();
                        }
                    }
                }
            }
        });
    }

    private void unFollowOther() {
        btnUnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUnFollowUserInfo();
                System.out.println(count);
                if (count > 1) {
                    if (listUnFollower.size() > 0 && listUnFollowing.size() > 0) {
                        notebookRefFollow.document(sFollowing).update("following", listUnFollowing);
                        notebookRefFollow.document(sFollower).update("follower", listUnFollower);
                        if (getFragmentManager() != null) {
                            getFragmentManager().beginTransaction().detach(ViewProfileFragment.this).attach(ViewProfileFragment.this).commit();
                        }
                    }
                }

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
                bundle.putString("getUserID", getUserID);
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
                bundle.putString("getUserID", getUserID);
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
