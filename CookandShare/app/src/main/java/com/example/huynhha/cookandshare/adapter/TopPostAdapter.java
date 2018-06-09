package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopPostAdapter extends RecyclerView.Adapter<TopPostAdapter.PostViewHolder> {


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cvTopPost)
        CardView cv;
        @BindView(R.id.cvTopPostUserAvatar)
        ImageView userAvatar;
        @BindView(R.id.cvTopPostUserName)
        TextView userName;
        @BindView(R.id.cvTopPostPostTime)
        TextView time;
        @BindView(R.id.cvTopPostImgContent)
        ImageView imgContent;
        @BindView(R.id.cvTopPostTvTitle)
        TextView title;
        @BindView(R.id.cvTopPostTvDescription)
        TextView description;
        @BindView(R.id.cvTopPostTvLike)
        TextView like;
        @BindView(R.id.cvTopPostTvComment)
        TextView comment;
        @BindView(R.id.cvTopPostBtnLike)
        Button btnLike;
        @BindView(R.id.cvTopPostBtnComment)
        Button btnComment;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

    }

    private ArrayList<Post> posts;

    public TopPostAdapter(ArrayList<Post> posts) {
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
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        Picasso.get().load(post.getUserImgUrl()).into(holder.userAvatar);
        holder.userName.setText(post.getUserID());
        holder.time.setText(post.getTime());
        Picasso.get().load(post.getImgUrl()).into(holder.imgContent);
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        holder.like.setText(holder.like.getText() + " :" + post.getLike().size());
        holder.comment.setText(holder.comment.getText() + " :" + post.getComment().size());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
