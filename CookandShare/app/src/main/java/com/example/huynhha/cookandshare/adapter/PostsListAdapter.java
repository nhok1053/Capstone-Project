package com.example.huynhha.cookandshare.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.CircleTransform;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.ViewHolder> {
    public List<Post> postsList;
    Context context;
    public PostsListAdapter(List<Post> postsList){
        this.postsList = postsList;
    }
    public PostsListAdapter(List<Post> postsList,Context context){
        this.postsList = postsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameText.setText(postsList.get(position).getTitle());
        holder.descriptionText.setText(postsList.get(position).getDescription());
        holder.imageView.setImageURI(Uri.parse(postsList.get(position).getUrlImage()));
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Picasso.get().load(postsList.get(position).getUrlImage())
                .resize(100,100)
                .centerInside()
                .transform(new CircleTransform()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID",postsList.get(position).getPostID());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView nameText;
        public TextView descriptionText;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nameText = (TextView) mView.findViewById(R.id.name_text);
            descriptionText = (TextView) mView.findViewById(R.id.description_text);
            imageView = (ImageView) mView.findViewById(R.id.image_data);
        }
    }
}
