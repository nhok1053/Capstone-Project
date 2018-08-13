package com.example.huynhha.cookandshare.adapter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.fragment.CommentFragment;
import com.example.huynhha.cookandshare.fragment.ProfileFragment;
import com.example.huynhha.cookandshare.fragment.ReportFragment;
import com.example.huynhha.cookandshare.fragment.ViewProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopPostAdapter extends RecyclerView.Adapter<TopPostAdapter.PostViewHolder> {
    private ArrayList<Post> posts;
    private OnAdapterClick onAdapterClick;
    Context context;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar;
        TextView userName;
        TextView time;
        ImageView imgContent;
        TextView title;
        TextView description;
        TextView like;
        TextView comment;
        Button btnLike;
        Button btnComment;
        View view2;
        FrameLayout fl_comment;
        Button cvTopPostBtnShowMore;


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
        }
    }


    //private ArrayList<Post> posts;
    public TopPostAdapter(ArrayList<Post> posts, Context context) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_top_post, parent, false);
        PostViewHolder pvh = new PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        final Post post = posts.get(position);
        final String currentUser = firebaseAuth.getUid().toString();
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
        holder.userName.setText(post.getUserID());
        holder.time.setText(post.getTime());
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        Picasso.get().load(post.getUrlImage()).resize(pxWidth, 0).into(holder.imgContent);
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        holder.like.setText("Like :" + post.getLike());
        holder.comment.setText("Comment :" + post.getComment());
        holder.imgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID", post.getPostID());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdapterClick.OnCommentClicked(post.getPostID().toString(), post.getUserID().toString());
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
                                    return true;
                                case R.id.deletePost:
                                    System.out.println("AS: delete");
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                }
                else {
                    PopupMenu popupMenu = new PopupMenu(context,holder.cvTopPostBtnShowMore);
                    popupMenu.getMenuInflater().inflate(R.menu.post_option,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            ReportFragment reportFragment = new ReportFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("postID",post.getPostID());
                            bundle.putString("userID",post.getUserID());
                            bundle.putString("userName",post.getUserName());
                            System.out.println("USername"+post.getUserName());
                            reportFragment.setArguments(bundle);
                            ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fl_main,reportFragment).addToBackStack(null).commit();
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
}
