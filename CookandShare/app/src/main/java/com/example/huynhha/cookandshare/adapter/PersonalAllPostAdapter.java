package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonalAllPostAdapter extends RecyclerView.Adapter<PersonalAllPostAdapter.AllPostViewHolder> {
    ArrayList<Post> posts;
    Context ctx;

    public PersonalAllPostAdapter(ArrayList<Post> posts, Context ctx) {
        this.posts = posts;
        this.ctx = ctx;
    }

    public class AllPostViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public AllPostViewHolder(View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.imgPersonalPost);
        }
    }

    @NonNull
    @Override
    public PersonalAllPostAdapter.AllPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_personal_allpost, parent, false);
        PersonalAllPostAdapter.AllPostViewHolder listPersonalPostViewHolder = new PersonalAllPostAdapter.AllPostViewHolder(v);
        return listPersonalPostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalAllPostAdapter.AllPostViewHolder holder, int position) {
        final Post post = posts.get(position);
        Picasso.get().load(post.getUrlImage()).fit().centerCrop().into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID", post.getPostID());
                bundle.putString("userID",post.getUserID());
                bundle.putString("type","1");
                intent.putExtras(bundle);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
