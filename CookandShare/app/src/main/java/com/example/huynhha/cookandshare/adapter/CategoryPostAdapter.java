package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryPostAdapter extends RecyclerView.Adapter<CategoryPostAdapter.ViewHolder> {
    private ArrayList<Post> posts;
    Context context;
    private CollectionReference userRef = MainActivity.db.collection("User");

    public CategoryPostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        //        TextView userName;
        TextView title;
        private RatingBar ratingBar;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.contentImgCategoryPost);
//            userName = itemView.findViewById(R.id.txtCategoryPostCreatedBy);
            title = itemView.findViewById(R.id.txtCategoryPostTitle);
            ratingBar = itemView.findViewById(R.id.ratingBarCategoryPost);
            cardView = itemView.findViewById(R.id.cvCategoryPost);
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
        Picasso.get().load(post.getUrlImage()).centerCrop().fit().into(holder.img);
        holder.title.setText(post.getTitle());
        holder.ratingBar.setRating(Float.parseFloat(post.getNumberOfRate()));
        //ko query dc username de hien thi ra nhung van phai truyen vao de den post detail
        final String[] userName = {""};
        userRef.whereEqualTo("userID", post.getUserID()).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()
                        ) {
                    userName[0] = queryDocumentSnapshot.getString("firstName");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetails.class);
                intent.putExtra("postID", post.getPostID());
                intent.putExtra("userName", userName[0]);
                intent.putExtra("userID", post.getUserID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
