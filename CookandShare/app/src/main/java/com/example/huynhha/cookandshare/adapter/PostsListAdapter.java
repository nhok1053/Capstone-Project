package com.example.huynhha.cookandshare.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.CircleTransform;
import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.ViewHolder> {
    public List<Post> postsList;
    Context context;
    private CollectionReference userRef = MainActivity.db.collection("User");

    public PostsListAdapter(List<Post> postsList, Context context) {
        this.postsList = postsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.nameText.setText(postsList.get(position).getTitle());
        holder.descriptionText.setText(postsList.get(position).getDescription());
        holder.imageView.setImageURI(Uri.parse(postsList.get(position).getUrlImage()));
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Picasso.get().load(postsList.get(position).getUrlImage())
                .resize(100, 100)
                .centerInside()
                .transform(new CircleTransform()).into(holder.imageView);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] userName = {""};
                userRef.whereEqualTo("userID", postsList.get(position).getUserID()).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot :
                                task.getResult()) {
                            userName[0] = queryDocumentSnapshot.getString("firstName");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
                Intent intent = new Intent(context, PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", postsList.get(position).getUserID());
                bundle.putString("userName", userName[0]);
                bundle.putString("type", "1");
                bundle.putString("postID", postsList.get(position).getPostID());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView nameText;
        public TextView descriptionText;
        public ImageView imageView;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameText = (TextView) mView.findViewById(R.id.name_text);
            descriptionText = (TextView) mView.findViewById(R.id.description_text);
            imageView = (ImageView) mView.findViewById(R.id.image_data);
            cv = itemView.findViewById(R.id.card_view_search_post);
        }
    }
}
