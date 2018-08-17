package com.example.huynhha.cookandshare.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.adapter.PersonalAllPostAdapter;
import com.example.huynhha.cookandshare.entity.Follow;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
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
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    private FirebaseAuth firebaseAuth;
    private int count;
    private CollectionReference notiRef = MainActivity.db.collection("Notification");
    private String documentNoti;
    private List<Map<String, Object>> listNoti;
    private ArrayList<NotificationDetails> listNotiDetails;

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
        listNoti = new ArrayList<>();
        listNotiDetails = new ArrayList<>();
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
        try {
            notebookRefFollow.whereEqualTo("userID", getUserID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                if (task.getResult() != null || task.getResult().size() != 0) {
                                    tv.setText(((List<Map<String, Object>>) documentSnapshot.get(s)).size() - 1 + "");
                                } else {
                                    tv.setText("0");
                                }
                            }
                        }
                    });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    public void userInfo() {
        //check list following cua minh xem co thang day ko
        notebookRefFollow.whereEqualTo("userID", currentUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                count = 1;
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    if (task.getResult() != null && task.getResult().size() != 0) {
                        list1 = (List<Map<String, Object>>) documentSnapshot.get("following");
                        for (int i = 0; i < list1.size(); i++) {
                            String s = list1.get(i).get("userID").toString();
                            if (s.equals(getUserID)) {
                                //co thi btn unfollow hien len de unfollow
                                btnFollow.setVisibility(View.GONE);
                                btnUnFollow.setVisibility(View.VISIBLE);
                                break;
                            }
                        }
                    }
                }
            }
        });

        //lay du lieu cua thang co userid
        notebookRefUser.whereEqualTo("userID", getUserID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                count = 1;
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    if (task.getResult() != null && task.getResult().size() != 0) {
                        String userID = documentSnapshot.get("userID").toString();
                        String userName = documentSnapshot.get("secondName").toString();
                        String userImgUrl = documentSnapshot.get("imgUrl").toString();
                        //lay du lieu sau nay unfollow no
                        userFollowing = new Follow(userID, userName, userImgUrl);
                        Picasso.get().load(userImgUrl).transform(new RoundedTransformation()).fit().centerCrop().into(imgAvatar);
                        txtUsername.setText(userName);
                        txtUserDateOfBirth.setText(documentSnapshot.get("dateOfBirth").toString());
                        //neu thang day trung voi minh thi ko hien thi btn follow voi unfollow
                        if (currentUser.equals(getUserID.trim())) {
                            //vi neu de 2btn gone thi mat ca ten lan ngay sinh nen de 1 cai invisible
                            btnFollow.setVisibility(View.INVISIBLE);
                            btnUnFollow.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });

        //lay thong tin cua minh de xoa trong follower cua thang minh follow khi nhan vao unfollow
        notebookRefUser.whereEqualTo("userID", currentUser).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                count = 1;
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    if (task.getResult() != null && task.getResult().size() != 0) {
                        String userID = documentSnapshot.get("userID").toString();
                        String userName = documentSnapshot.get("secondName").toString();
                        String userImgUrl = documentSnapshot.get("imgUrl").toString();
                        userFollower = new Follow(userID, userName, userImgUrl);
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

    private void loadFollowUserInfo() {
        if (count > 0) {
            try {
                //lay thong tin cua thang minh follow them vao cai map follow cua minh ban dau
                notebookRefFollow.whereEqualTo("userID", currentUser).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 2;
                        System.out.println(count + "AAAAAAAAAAAAAA");
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            count = 2;
                            sFollowing = documentSnapshot.getId();
                            listFollowing = (List<Map<String, Object>>) documentSnapshot.get("following");
                            mapFollowing.put("userID", userFollowing.getUserID());
                            mapFollowing.put("userName", userFollowing.getUserName());
                            mapFollowing.put("userUrlImage", userFollowing.getUserUrlImage());
                            listFollowing.add(mapFollowing);
                        }
                    }
                });
                //lay thong tin cua minh add vao danh sach follower da co cua thang minh follow
                //user follower chinh la minh da dc them o tren
                notebookRefFollow.whereEqualTo("userID", getUserID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            count = 2;
                            sFollower = documentSnapshot.getId();
                            listFollower = (List<Map<String, Object>>) documentSnapshot.get("follower");
                            mapFollower.put("userID", userFollower.getUserID());
                            mapFollower.put("userName", userFollower.getUserName());
                            mapFollower.put("userUrlImage", userFollower.getUserUrlImage());
                            listFollower.add(mapFollower);
                        }
                    }
                });
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
        }
    }

    private void loadUnFollowUserInfo() {
        if (count > 0) {
            try {
                //lay thong tin trong bang following cua minh, update cai bang tru di userfollowing thi ra bang moi da unfollow
                notebookRefFollow.whereEqualTo("userID", currentUser).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 2;
                        listUnFollowing = new ArrayList<>();
                        System.out.println(count + "AAAAAAAAAAAAAA");
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            if (task.getResult() != null && task.getResult().size() != 0) {
                                sFollowing = documentSnapshot.getId();
                                listFollowing = (List<Map<String, Object>>) documentSnapshot.get("following");
                                for (int i = 0; i < listFollowing.size(); i++) {
                                    Map<String, Object> updateMap = new HashMap<>();
                                    if (!listFollowing.get(i).get("userID").equals(userFollowing.getUserID())) {
                                        updateMap.put("userID", listFollowing.get(i).get("userID"));
                                        updateMap.put("userName", listFollowing.get(i).get("userName"));
                                        updateMap.put("userUrlImage", listFollowing.get(i).get("userUrlImage"));
                                        listUnFollowing.add(updateMap);
                                    }
                                }
//                                HashSet<Map<String, Object>> hashSet = new HashSet<>();
//                                hashSet.addAll(listUnFollowing);
//                                listUnFollowing.clear();
//                                listUnFollowing.addAll(hashSet);
                            }
                        }
                    }
                });
                //lay thong tin trong bang follower cua thang kia, update cai bang tru di userfollower chinh la minh
                notebookRefFollow.whereEqualTo("userID", getUserID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        count = 2;
                        listUnFollower = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            if (task.getResult() != null && task.getResult().size() != 0) {
                                sFollower = documentSnapshot.getId();
                                listFollower = (List<Map<String, Object>>) documentSnapshot.get("follower");
                                for (int i = 0; i < listFollower.size(); i++) {
                                    Map<String, Object> updateMap = new HashMap<>();
                                    if (!listFollower.get(i).get("userID").equals(userFollower.getUserID())) {
                                        updateMap.put("userID", listFollower.get(i).get("userID"));
                                        updateMap.put("userName", listFollower.get(i).get("userName"));
                                        updateMap.put("userUrlImage", listFollower.get(i).get("userUrlImage"));
                                        listUnFollower.add(updateMap);
                                    }
                                }
//                                HashSet<Map<String, Object>> hashSet = new HashSet<>();
//                                hashSet.addAll(listUnFollower);
//                                listUnFollower.clear();
//                                listUnFollower.addAll(hashSet);
                            }
                        }
                    }
                });
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
        }
    }

    private void followOther() {
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFollowUserInfo();
                System.out.println(count);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Theo dõi");
                alert.setMessage("Bạn muốn theo dõi?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (count > 1) {
                                    if (listFollower.size() > 0 && listFollowing.size() > 0) {
                                        getNotification();

                                        notebookRefFollow.document(sFollowing).update("following", listFollowing);
                                        notebookRefFollow.document(sFollower).update("follower", listFollower);
                                        if (getFragmentManager() != null) {
                                            getFragmentManager().beginTransaction().detach(ViewProfileFragment.this).attach(ViewProfileFragment.this).commit();
                                        }
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void unFollowOther() {
        btnUnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUnFollowUserInfo();
                System.out.println(count);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Hủy theo dõi");
                alert.setMessage("Bạn muốn hủy theo dõi?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (count > 1) {
                                    if (listUnFollower.size() > 0 && listUnFollowing.size() > 0) {
                                        notebookRefFollow.document(sFollowing).update("following", listUnFollowing);
                                        notebookRefFollow.document(sFollower).update("follower", listUnFollower);
                                        if (getFragmentManager() != null) {
                                            getFragmentManager().beginTransaction().detach(ViewProfileFragment.this).attach(ViewProfileFragment.this).commit();
                                        }
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
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

    public void getNotification() {
        firebaseAuth = FirebaseAuth.getInstance();
        String currentUser = firebaseAuth.getUid().toString();
        if (currentUser.equals(getUserID)) {
            System.out.println("Nothing");
        } else {
            System.out.println("UserID " + getUserID);
            notiRef.whereEqualTo("userID", getUserID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            documentNoti = document.getId();
                            try {
                                listNoti = (List<Map<String, Object>>) document.get("notification");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (listNoti == null) {
                                System.out.println("Khong co noti");
                            } else {
                                for (int i = 0; i < listNoti.size(); i++) {
                                    NotificationDetails notificationDetails = new NotificationDetails();
                                    notificationDetails.setType(listNoti.get(i).get("type").toString());
                                    notificationDetails.setUserUrlImage(listNoti.get(i).get("userUrlImage").toString());
                                    notificationDetails.setPostID(listNoti.get(i).get("postID").toString());
                                    notificationDetails.setTime(listNoti.get(i).get("time").toString());
                                    notificationDetails.setContent(listNoti.get(i).get("content").toString());
                                    notificationDetails.setUserName(listNoti.get(i).get("userName").toString());
                                    notificationDetails.setUserID(listNoti.get(i).get("userID").toString());
                                    listNotiDetails.add(notificationDetails);
                                    count++;
                                }
                                System.out.println("Vao cai nay");
                                addNoti(documentNoti);
//                                if (listNotiDetails == null) {
//                                    addNoti(documentNoti);
//                                    System.out.println("Add noti success");
//                                } else if (count == listNotiDetails.size()) {
//                                    addNoti(documentNoti);
//                                    System.out.println("Add noti success");
//                                    count = 0;
//                                }
                            }

                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    public void addNoti(final String documentID) {
        System.out.println("DOCU " + documentID);
        firebaseAuth = FirebaseAuth.getInstance();
        final String userID = firebaseAuth.getCurrentUser().getUid().toString();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        System.out.println("Current USer:" + currentUser);
        final String date = df.format(Calendar.getInstance().getTime());
        Map<String, Object> updateNoti = new HashMap<>();
        updateNoti.put("postID", "");
        updateNoti.put("time", date);
        updateNoti.put("type", 0);
        updateNoti.put("userID", currentUser);
        updateNoti.put("userUrlImage", firebaseAuth.getCurrentUser().getPhotoUrl().toString());
        updateNoti.put("userName", firebaseAuth.getCurrentUser().getDisplayName().toString());
        updateNoti.put("content", firebaseAuth.getCurrentUser().getDisplayName().toString() + " đã bắt đầu theo dõi bạn");
        System.out.println("Vao day luon roi");
        if (listNoti == null) {
            System.out.println("bang null");
            listNoti = new ArrayList<>();
            listNoti.add(updateNoti);
        } else {
            System.out.println("Ko null");
            listNoti.add(updateNoti);
        }

        notiRef.document(documentID).update("notification", listNoti);
        Toast.makeText(getContext(), "Add Noti Success", Toast.LENGTH_SHORT).show();

    }
}
