package com.example.huynhha.cookandshare.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private ArrayList<Comment> comments;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String currentUserID = "";
    private String documentID = "";
    private List<Map<String, Object>> list1;
    private ArrayList<Comment> list;
    private String postID = "";
    private CommentAdapter commentAdapter;
    private RecyclerView rcComment;
    private CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
    int count = 0;


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_comment, parent, false);
        CommentAdapter.CommentViewHolder commentViewHolder = new CommentAdapter.CommentViewHolder(v);
        return commentViewHolder;
    }

    Context context;

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, final int position) {
        final Comment comment = comments.get(position);
        holder.img_settings.setVisibility(View.INVISIBLE);
        loadUserName(comment.getUserID(), holder);
        Picasso.get().load(comment.getUserImgUrl()).fit().centerCrop().into(holder.imgUser);
        holder.txt_content.setText(comment.getCommentContent());
        if (currentUserID.equals(comment.getUserID())) {
            holder.img_settings.setVisibility(View.VISIBLE);
            holder.img_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, holder.img_settings);
                    popupMenu.getMenuInflater().inflate(R.menu.comment_option, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Xác nhận");
                            alert.setMessage("Bạn muốn xoá bài viết này?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loadComment(postID, position);
                                    

                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }

                            }).show();

                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        } else {
            System.out.println("Nothing at All");
        }

        System.out.println(comment.getCommentContent());
    }

    public void loadUserName(String userID, final CommentViewHolder holder) {
        userRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("firstName");
                        holder.txt_userName.setText(userName);
                        count++;
                    }
                }
            }
        });
    }

    public void loadComment(String postID, final int postion) {
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
                            Toast.makeText(context, "Chưa có bình luận nào.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < list1.size(); i++) {
                                Comment comment = new Comment();
                                comment.setUserID(list1.get(i).get("userID").toString());
                                comment.setUserImgUrl(list1.get(i).get("userImgUrl").toString());
                                comment.setCommentContent(list1.get(i).get("commentContent").toString());
                                list.add(comment);
                            }
                            System.out.println(list.toString());
                            removeComment(postion);

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

    public void removeComment(int postion) {
        for (int i = 0; i < list1.size(); i++) {
            if (i == postion) {
                list1.remove(postion);
                break;
            }
        }

        for (int i = 0; i < comments.size(); i++) {
            if (i == postion) {
                comments.remove(postion);
                break;
            }
        }
        MainActivity.db.collection("Comment").document(documentID).update("comment", list1);
        commentAdapter = new CommentAdapter(comments, context, postID, rcComment);
        rcComment.setAdapter(commentAdapter);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public CommentAdapter(ArrayList<Comment> comments, Context context, String postID, RecyclerView recyclerView) {
        this.context = context;
        this.comments = comments;
        this.postID = postID;
        this.rcComment = recyclerView;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgUser;
        private TextView txt_userName;
        private TextView txt_content;
        private ImageView img_settings;
        private RecyclerView rc_comment;


        public CommentViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.user_url_image);
            txt_userName = itemView.findViewById(R.id.txt_user_name);
            txt_content = itemView.findViewById(R.id.txt_content);
            img_settings = itemView.findViewById(R.id.comment_setting);
            currentUserID = firebaseAuth.getCurrentUser().getUid();
            rc_comment = itemView.findViewById(R.id.rc_comment);
            list = new ArrayList<>();
            list1 = new ArrayList<>();
        }
    }
}
