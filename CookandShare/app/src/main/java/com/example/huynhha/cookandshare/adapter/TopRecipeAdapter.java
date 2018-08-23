package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class TopRecipeAdapter extends RecyclerView.Adapter<TopRecipeAdapter.PostViewHolder> {
    private CollectionReference userRef = MainActivity.db.collection("User");

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopRecipe;
        ImageView imgTopRecipe;
        TextView tvlike;
        TextView tvComment;
        CardView cardView;

        public PostViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvTopRecipe);
            tvTopRecipe = itemView.findViewById(R.id.cvTopRecipeTvTopRecipeName);
            imgTopRecipe = itemView.findViewById(R.id.cvTopRecipeImgViewTopRecipe);
            tvlike = itemView.findViewById(R.id.cvTopRecipeTvTopRecipeLike);
            tvComment = itemView.findViewById(R.id.cvTopRecipeTvTopRecipeComment);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_top_recipe, parent, false);
        TopRecipeAdapter.PostViewHolder pvh = new TopRecipeAdapter.PostViewHolder(v);
        return pvh;
    }

    private Context context;
    private ArrayList<Post> topRecipes;

    public TopRecipeAdapter(Context context, ArrayList<Post> topRecipes) {
        this.context = context;
        this.topRecipes = topRecipes;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final Post recipe = topRecipes.get(position);
        final String[] userName = {""};
        userRef.whereEqualTo("userID", recipe.getUserID()).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        userName[0] = queryDocumentSnapshot.getString("firstName");
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
        Picasso.get().load(recipe.getUrlImage()).fit().centerCrop().into(holder.imgTopRecipe);
        holder.tvTopRecipe.setText(recipe.getTitle());
        holder.tvlike.setText(recipe.getLike() + " Thích");
        holder.tvComment.setText(recipe.getComment() + " Bình luận");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID", recipe.getPostID());
                bundle.putString("userName", userName[0]);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topRecipes.size();
    }
}
