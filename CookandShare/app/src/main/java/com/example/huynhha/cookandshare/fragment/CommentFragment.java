package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.CommentAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
import com.example.huynhha.cookandshare.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {
    private EditText edt_comment;
    private Button btn_comment;
    private RecyclerView rc_comment;
    private FrameLayout fl_comment;
    private ImageView img_exit;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> list;
    //private String postID = "DEMO";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private String documentID = "";
    private CollectionReference postRef = db.collection("Comment");
    private CollectionReference notiRef = db.collection("Notification");
    private CollectionReference userRef = db.collection("User");
    private List<Map<String, Object>> list1;
    private List<Map<String, Object>> listNoti;
    private ArrayList<NotificationDetails> listNotiDetails;
    private View view2;
    private onCloseClick onCloseClick;
    public CommentFragment commentFragment;
    private FirebaseAuth firebaseAuth;
    private String documentNoti = "";
    private String postID = "";
    private int count =0;
    private String userID;
    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comment, container, false);
        setUpData(v);
        commentFragment = this;
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        listNoti = new ArrayList<>();
        listNotiDetails = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        postID = getArguments().getString("postID");
        userID = getArguments().getString("userID");
        addComment();
        loadComment(postID);
        closeFragment();
        return v;
    }

    public void closeFragment() {
        img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment(commentFragment);
            }
        });
    }

    public void removeFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

    }

    public void loadComment(String postID) {
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rc_comment.setLayoutManager(lln);
        MainActivity.db.collection("Comment").whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("ID :" + document.getId());
                        documentID = document.getId();
                        try {
                            list1 = (List<Map<String, Object>>) document.get("comment");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (list1 == null) {
                            Toast.makeText(getContext(), "Chưa có bình luận nào.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < list1.size(); i++) {
                                Comment comment = new Comment();
                                comment.setUserID(list1.get(i).get("userID").toString());
                                comment.setUserImgUrl(list1.get(i).get("userImgUrl").toString());
                                comment.setUserName(list1.get(i).get("userName").toString());
                                comment.setCommentContent(list1.get(i).get("commentContent").toString());
                                list.add(comment);
                            }
                            System.out.println(list.toString());
                            commentAdapter = new CommentAdapter(list, getContext());

                        }
                        rc_comment.setAdapter(commentAdapter);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void addComment() {
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = edt_comment.getText().toString();
                Comment comment1 = new Comment();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                comment1.setUserName(currentFirebaseUser.getDisplayName().toString());
                comment1.setUserImgUrl(currentFirebaseUser.getPhotoUrl().toString());
                comment1.setUserID(currentFirebaseUser.getUid().toString());
                comment1.setCommentContent(comment);
                list.add(comment1);
                //list1.add((Map<String, Object>) comment1);
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("userName", currentFirebaseUser.getDisplayName().toString());
                updateMap.put("userImgUrl", currentFirebaseUser.getPhotoUrl().toString());
                updateMap.put("userID", currentFirebaseUser.getUid().toString());
                updateMap.put("commentContent", comment);
                if (list1 != null) {
                    list1.add(updateMap);
                } else {
                    list1 = new ArrayList<>();
                    list1.add(updateMap);
                }
                db.collection("Comment").document(documentID).update("comment", list1);
                edt_comment.setText("");
                commentAdapter = new CommentAdapter(list, getContext());
                rc_comment.setAdapter(commentAdapter);
                getNotification();
            }

        });


    }

    public void getNotification() {
        firebaseAuth = FirebaseAuth.getInstance();
        String currentUser = firebaseAuth.getUid().toString();
        if(currentUser.equals(userID)){
            System.out.println("Nothing");
        }else{
        System.out.println("UserID " + userID);
        notiRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        documentNoti = document.getId();
                        System.out.println("documentNoti: "+documentID);
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
                        }

                        if(listNoti==null){
                            addNoti(documentNoti);
                        }
                        else if(count== listNoti.size()){
                            addNoti(documentNoti);
                        }

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });}
    }

    public void addNoti(final String documentID) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        final String date = df.format(Calendar.getInstance().getTime());
        userRef.whereEqualTo("userID",userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Map<String, Object> updateNoti = new HashMap<>();
                        updateNoti.put("postID", postID);
                        updateNoti.put("time", date);
                        updateNoti.put("type", 1);
                        updateNoti.put("userID", userID);
                        updateNoti.put("userUrlImage", documentSnapshot.get("imgUrl"));
                        updateNoti.put("userName", documentSnapshot.get("firstName"));
                        updateNoti.put("content", documentSnapshot.get("firstName")+ " đã bình luận vào bài viết của bạn");
                        if(listNoti==null){
                            listNoti = new ArrayList<>();
                            listNoti.add(updateNoti);
                        }else{
                            listNoti.add(updateNoti);
                        }

                        notiRef.document(documentID).update("notification", listNoti);
                        Toast.makeText(getContext(), "Add Noti Success", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }

    public void setUpData(View view) {
        img_exit = view.findViewById(R.id.btn_close_comment);
        edt_comment = view.findViewById(R.id.edt_comment);
        btn_comment = view.findViewById(R.id.btn_send_comment);
        rc_comment = view.findViewById(R.id.rc_comment);
    }

    public interface onCloseClick {
        void onCloseCommentClick();
    }

    public void setOnCloseClick(CommentFragment.onCloseClick onCloseClick) {
        this.onCloseClick = onCloseClick;
    }
}
