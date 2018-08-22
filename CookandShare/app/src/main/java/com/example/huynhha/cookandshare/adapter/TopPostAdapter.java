package com.example.huynhha.cookandshare.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.EditPostActivity;
import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.fragment.CommentFragment;
import com.example.huynhha.cookandshare.fragment.ProfileFragment;
import com.example.huynhha.cookandshare.fragment.ReportFragment;
import com.example.huynhha.cookandshare.fragment.ViewProfileFragment;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.FavouriteDBHelper;
import com.example.huynhha.cookandshare.model.LikeDBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopPostAdapter extends RecyclerView.Adapter<TopPostAdapter.PostViewHolder> {
    private ArrayList<Post> posts;
    private OnAdapterClick onAdapterClick;
    Context context;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String currentUser;
    private CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post");
    private CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
    private CollectionReference notiRef = FirebaseFirestore.getInstance().collection("Notification");
    private CollectionReference commentRef = FirebaseFirestore.getInstance().collection("Comment");
    private String documentID = "";
    private int count = 0;
    private RecyclerView recyclerView;
    private TopPostAdapter topPostAdapter;
    private ArrayList<String> listPostID;
    private String documentNoti = "";
    private List<Map<String, Object>> listNoti = new ArrayList<>();
    private List<Map<String, Object>> listComment = new ArrayList<>();
    private ArrayList<NotificationDetails> listNotiDetails = new ArrayList<>();
    private int checkCount = 0;
    private int sizeComment = 0;


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        private ImageView userAvatar;
        private TextView userName;
        private TextView time;
        private ImageView imgContent;
        private TextView title;
        private TextView description;
        private TextView like;
        private TextView comment;
        private Button btnLike;
        private Button btnComment;
        private View view2;
        private FrameLayout fl_comment;
        private Button cvTopPostBtnShowMore;
        private RecyclerView rc_top_post;


        public PostViewHolder(final View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.cvTopPostUserName);
            userAvatar = itemView.findViewById(R.id.cvTopPostUserAvatar);
            time = itemView.findViewById(R.id.cvTopPostPostTime);
            imgContent = itemView.findViewById(R.id.cvTopPostImgContent);
            title = itemView.findViewById(R.id.cvTopPostTvTitle);
            description = itemView.findViewById(R.id.cvTopPostTvDescription);
            like = itemView.findViewById(R.id.cvTopPostTvLike);
            comment = itemView.findViewById(R.id.cvTopPostTvComment);
            btnComment = itemView.findViewById(R.id.cvTopPostBtnComment);
            view2 = itemView.findViewById(R.id.checkFragment);
            cvTopPostBtnShowMore = itemView.findViewById(R.id.cvTopPostBtnShowMore);
            rc_top_post = itemView.findViewById(R.id.rvPost);
            btnLike = itemView.findViewById(R.id.cvTopPostBtnLike);
        }
    }


    //private ArrayList<Post> posts;
    public TopPostAdapter(ArrayList<Post> posts, Context context, RecyclerView recyclerView) {
        this.context = context;
        this.posts = posts;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_top_post, parent, false);
        PostViewHolder pvh = new PostViewHolder(v);
        listPostID = new ArrayList<>();
        loadDataFromDBOffline();
        return pvh;
    }

    public void loadUserName(String userID, final PostViewHolder holder) {
        userRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("firstName");
                        holder.userName.setText(userName);
                        count++;
                    }
                }
            }
        });
    }


    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {

        final Post post = posts.get(position);
        final int adapterPosition = position;
        loadlistComment(post.getPostID(),holder);
        if (firebaseAuth.getCurrentUser() != null) {
            currentUser = firebaseAuth.getUid().toString();

        }
        holder.like.setText("Thích : " + post.getLike());
        if (isLike(post.getPostID())) {
            holder.btnLike.setEnabled(false);

        } else {
            holder.btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Post post1 = posts.get(adapterPosition);
                    saveLike(post1);
                    postRef.whereEqualTo("postID", post1.getPostID()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    documentID = documentSnapshot.getId();
                                    System.out.println("Vao day roi:1");
                                    checkCount++;
                                    int likeNumber = documentSnapshot.getLong("like").intValue() + 1;
                                    postRef.document(documentID).update("like", likeNumber);
                                    holder.like.setText("Thích : " + likeNumber);
                                }
                                if (checkCount == 1) {
                                    loadNotification(post1.getUserID().toString(), documentID, post1.getPostID());
                                    checkCount = 0;
                                }
                            }
                        }
                    });
                    holder.btnLike.setEnabled(false);
                }
            });
        }
        loadUserName(post.getUserID().toString(), holder);
        Picasso.get().load(post.getUserImgUrl()).transform(new RoundedTransformation()).fit().centerCrop().into(holder.userAvatar);
        holder.userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("userID", post.getUserID());
                ViewProfileFragment profileFragment = new ViewProfileFragment();
                profileFragment.setArguments(bundle);
                ft.replace(R.id.fl_main, profileFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("userID", post.getUserID());
                ViewProfileFragment profileFragment = new ViewProfileFragment();
                profileFragment.setArguments(bundle);
                ft.replace(R.id.fl_main, profileFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //holder.userName.setText(post.getUserID());
        holder.time.setText(post.getTime());
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        Picasso.get().load(post.getUrlImage()).resize(pxWidth, 0).into(holder.imgContent);
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        System.out.println("SIZE comment " + sizeComment);
        holder.comment.setText("Bình luận : "+0);

        holder.imgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID", post.getPostID());
                bundle.putString("userID", post.getUserID());
                bundle.putString("userName", holder.userName.getText().toString());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post1 = posts.get(adapterPosition);
                System.out.println("ADAPTER position " + adapterPosition);
                CommentFragment commentFragment = new CommentFragment();
                Bundle bundle = new Bundle();
                System.out.println("POSTID :aa " + post1.getPostID());
                bundle.putString("postID", post1.getPostID());
                bundle.putString("userID", post1.getUserID());
                commentFragment.setArguments(bundle);
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, commentFragment).addToBackStack(null).commit();

            }
        });
        holder.cvTopPostBtnShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser.equals(post.getUserID())) {
                    PopupMenu popupMenu = new PopupMenu(context, holder.cvTopPostBtnShowMore);
                    popupMenu.getMenuInflater().inflate(R.menu.post_option_user, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.editPost:
                                    System.out.println("AS: edit");
                                    Intent intent = new Intent(context, EditPostActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("postID", post.getPostID());
                                    context.startActivity(intent);
                                    ((MainActivity) context).finish();
                                    return true;
                                case R.id.deletePost:
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setTitle("Xác nhận");
                                    alert.setMessage("Bạn muốn xoá bài viết này?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            postRef.whereEqualTo("postID", post.getPostID().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                            documentID = queryDocumentSnapshot.getId();
                                                            postRef.document(documentID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    System.out.println("Vao xoa");
                                                                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                                                    posts.remove(adapterPosition);
                                                                    topPostAdapter = new TopPostAdapter(posts, context, recyclerView);
                                                                    recyclerView.setAdapter(topPostAdapter);
                                                                }
                                                            });

                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                    System.out.println("AS: delete");
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                } else {
                    PopupMenu popupMenu = new PopupMenu(context, holder.cvTopPostBtnShowMore);
                    popupMenu.getMenuInflater().inflate(R.menu.post_option, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            ReportFragment reportFragment = new ReportFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("postID", post.getPostID());
                            bundle.putString("userID", post.getUserID());
                            bundle.putString("userName", holder.userName.getText().toString());
                            System.out.println("USername" + holder.userName.getText().toString());
                            reportFragment.setArguments(bundle);
                            ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, reportFragment).setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right).addToBackStack(null).commit();
                            return true;
                        }
                    });
                    popupMenu.show();
                }

            }
        });


//        holder.like.setText(post.getLike()+" "+holder.like.getText());
//        holder.comment.setText(post.getComment()+" "+holder.comment.getText());
    }

    public void saveLike(Post post) {
        LikeDBHelper likeDBHelper = new LikeDBHelper(context);
        SQLiteDatabase db = likeDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContext.LikeDB.COLUMN_POST_ID, post.getPostID());
        long newRowId = db.insert(DBContext.LikeDB.TABLE_NAME, null, values);
    }

    public void loadDataFromDBOffline() {
        LikeDBHelper likeDBHelper = new LikeDBHelper(context);
        SQLiteDatabase db = likeDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBContext.LikeDB.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String postID = cursor.getString(cursor.getColumnIndex("postID"));
                listPostID.add(postID);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    public boolean isLike(String postID) {
        Boolean isLike = false;
        if (listPostID != null) {
            for (int i = 0; i < listPostID.size(); i++) {
                if (!postID.equals(listPostID.get(i))) {
                    isLike = false;
                } else {
                    isLike = true;
                    break;
                }
            }
        }
        return isLike;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public interface OnAdapterClick {
        void OnCommentClicked(String postId, String userID);
    }

    public void setOnAdapterClick(OnAdapterClick onAdapterClick) {

        this.onAdapterClick = onAdapterClick;
    }

    public void loadNotification(String userID, final String documentID, final String postID) {
        System.out.println("Vao day roi:2");
        firebaseAuth = FirebaseAuth.getInstance();
        String currentUser = firebaseAuth.getUid().toString();
        if (currentUser.equals(userID)) {
            System.out.println("Nothing");
        } else {
            System.out.println("UserID " + userID);
            notiRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            documentNoti = document.getId();
                            System.out.println("documentNoti: " + documentID);
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
                                    notificationDetails.setUserID(listNoti.get(i).get("userID").toString());
                                    listNotiDetails.add(notificationDetails);
                                    count++;
                                }
                            }
                            System.out.println("Vao day roi:Zo roi");
                            addNoti(documentNoti, postID);
                            System.out.println("Vao day roi:Zo roi2");
                            count = 0;
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

    public void addNoti(final String documentID, String postID) {
        firebaseAuth = FirebaseAuth.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        final String date = df.format(Calendar.getInstance().getTime());
        System.out.println("Vao day roi:3");
        Map<String, Object> updateNoti = new HashMap<>();
        updateNoti.put("postID", postID);
        updateNoti.put("time", date);
        updateNoti.put("type", 2);
        updateNoti.put("userID", firebaseAuth.getCurrentUser().getUid().toString());
        updateNoti.put("userUrlImage", firebaseAuth.getCurrentUser().getPhotoUrl().toString());
        updateNoti.put("content", firebaseAuth.getCurrentUser().getDisplayName().toString() + " đã thích bài viết của bạn");
        if (listNoti == null) {
            listNoti = new ArrayList<>();
            listNoti.add(updateNoti);
        } else {
            listNoti.add(updateNoti);
        }
        System.out.println("check noti comment");
        notiRef.document(documentID).update("notification", listNoti);
        Toast.makeText(context, "Add Noti Success", Toast.LENGTH_SHORT).show();
    }

    public void loadlistComment(String postID, final PostViewHolder holder) {
        commentRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listComment = (List<Map<String, Object>>) document.get("comment");
                        if (listComment != null) {
                            sizeComment = listComment.size();
                            holder.comment.setText("Bình luận : "+sizeComment);
                            System.out.println("sizeComment " + sizeComment);
                        }

                    }
                }
            }
        });
    }
}
