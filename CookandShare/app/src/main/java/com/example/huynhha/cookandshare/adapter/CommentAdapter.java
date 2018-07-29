package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private ArrayList<Comment> comments;

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_comment, parent, false);
        CommentAdapter.CommentViewHolder commentViewHolder = new CommentAdapter.CommentViewHolder(v);
        return commentViewHolder;
    }
    Context context;

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        Picasso.get().load(comment.getUserImgUrl()).fit().centerCrop().into(holder.imgUser);
        holder.txt_userName.setText(comment.getUserName());
        holder.txt_content.setText(comment.getCommentContent());
        System.out.println(comment.getCommentContent());
    }

    @Override
    public int getItemCount() {
       return comments.size();
    }
    public CommentAdapter(ArrayList<Comment> comments, Context context) {
        this.context = context;
        this.comments  = comments;
    }
    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgUser;
        private TextView txt_userName;
        private TextView txt_content;


        public CommentViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.user_url_image);
            txt_userName = itemView.findViewById(R.id.txt_user_name);
            txt_content = itemView.findViewById(R.id.txt_content);
        }
    }
}
