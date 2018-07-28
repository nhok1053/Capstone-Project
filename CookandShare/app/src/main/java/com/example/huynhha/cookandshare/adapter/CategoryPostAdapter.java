package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryPostAdapter extends RecyclerView.Adapter<CategoryPostAdapter.ViewHolder> {
    private ArrayList<Post> posts;
    Context context;

    public CategoryPostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView userName;
        TextView title;
        private RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.contentImgCategoryPost);
            userName = itemView.findViewById(R.id.txtCategoryPostCreatedBy);
            title = itemView.findViewById(R.id.txtCategoryPostTitle);
            ratingBar = itemView.findViewById(R.id.ratingBarCategoryPost);
        }
    }

    @NonNull
    @Override
    public CategoryPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category_post, parent, false);
        CategoryPostAdapter.ViewHolder cpa = new CategoryPostAdapter.ViewHolder(v);
        return cpa;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPostAdapter.ViewHolder holder, int position) {
        final Post post = posts.get(position);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        Picasso.get().load(post.getUrlImage()).resize(pxWidth / 2, 0).into(holder.img);
        holder.title.setText(post.getTitle());
        holder.userName.setText(post.getUserName());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID",post.getPostID());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
