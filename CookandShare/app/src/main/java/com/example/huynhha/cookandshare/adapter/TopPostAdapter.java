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
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopPostAdapter extends RecyclerView.Adapter<TopPostAdapter.PostViewHolder> {


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

        public PostViewHolder(View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.cvTopPostUserName);
            userAvatar=itemView.findViewById(R.id.cvTopPostUserAvatar);
            time=itemView.findViewById(R.id.cvTopPostPostTime);
            imgContent=itemView.findViewById(R.id.cvTopPostImgContent);
            title=itemView.findViewById(R.id.cvTopPostTvTitle);
            description=itemView.findViewById(R.id.cvTopPostTvDescription);
            like=itemView.findViewById(R.id.cvTopPostTvLike);
            comment=itemView.findViewById(R.id.cvTopPostTvComment);
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
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        Post post = posts.get(position);
        Picasso.get().load(post.getUserImgUrl()).transform(new RoundedTransformation()).fit().centerCrop().into(holder.userAvatar);
        holder.userName.setText(post.getUserID());
        holder.time.setText(post.getTime());
        Picasso.get().load(post.getUrlImage()).into(holder.imgContent);
        holder.title.setText(post.getTitle());
        holder.description.setText(post.getDescription());
        holder.like.setText( "Like :" + post.getLike());
        holder.comment.setText( "Comment :" + post.getComment());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
