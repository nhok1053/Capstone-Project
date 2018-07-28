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

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.CommentAdapter;
import com.example.huynhha.cookandshare.entity.Comment;
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

import java.util.ArrayList;
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
    private String postID ="DEMO";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private String documentID ="";
    private CollectionReference postRef = db.collection("Comment");
    private List<Map<String,Object>> list1;
    private View view2;
    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comment, container, false);
        setUpData(v);
        storageReference = FirebaseStorage.getInstance().getReference();
        loadComment(postID);
        addComment();
        closeFragment();
        return v;
    }
    public void closeFragment(){
            img_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
           }
    public void loadComment(String postID){
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rc_comment.setLayoutManager(lln);
        MainActivity.db.collection( "Comment").whereEqualTo("postID",postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list = new ArrayList<>();
                        System.out.println("ID :"+document.getId());
                        documentID = document.getId();
                        list1 = (List<Map<String, Object>>) document.get("comment");
                        for (int i = 0; i<list1.size();i++){
                            Comment comment = new Comment();
                            comment.setUserID(list1.get(i).get("userID").toString());
                            comment.setUserImgUrl(list1.get(i).get("userImgUrl").toString());
                            comment.setUserName(list1.get(i).get("userName").toString());
                            comment.setCommentContent(list1.get(i).get("commentContent").toString());
                            list.add(comment);
                        }
                        System.out.println(list.toString());
                        commentAdapter = new CommentAdapter(list,getContext());

                    }
                    rc_comment.setAdapter(commentAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public void addComment(){
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = edt_comment.getText().toString();
                Comment comment1 = new Comment();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                comment1.setUserName(currentFirebaseUser.getDisplayName().toString());
                comment1.setUserImgUrl(currentFirebaseUser.getPhotoUrl().toString());
                comment1.setUserID(currentFirebaseUser.getUid().toString());
                comment1.setCommentContent(comment);
                list.add(comment1);
                //list1.add((Map<String, Object>) comment1);
                Map<String,Object> updateMap = new HashMap<>();
                updateMap.put("userName",currentFirebaseUser.getDisplayName().toString());
                updateMap.put("userImgUrl",currentFirebaseUser.getPhotoUrl().toString());
                updateMap.put("userID",currentFirebaseUser.getUid().toString());
                updateMap.put("commentContent",comment);
                list1.add(updateMap);
                db.collection("Comment").document(documentID).update("comment",list1);
                edt_comment.setText("");
                commentAdapter = new CommentAdapter(list,getContext());
                rc_comment.setAdapter(commentAdapter);
            }
        });
    }
    public void setUpData(View view){
        img_exit = view.findViewById(R.id.btn_close_comment);
        edt_comment = view.findViewById(R.id.edt_comment);
        btn_comment = view.findViewById(R.id.btn_send_comment);
        rc_comment = view.findViewById(R.id.rc_comment);
       }
}
