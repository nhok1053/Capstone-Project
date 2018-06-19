package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopRecipeAdapter extends RecyclerView.Adapter<TopRecipeAdapter.PostViewHolder> {

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopRecipe;
        ImageView imgTopRecipe;

        public PostViewHolder(View itemView) {
            super(itemView);
            tvTopRecipe = itemView.findViewById(R.id.cvTopRecipeTvTopRecipeName);
            imgTopRecipe = itemView.findViewById(R.id.cvTopRecipeImgViewTopRecipe);
        }
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_top_recipe, parent, false);
        TopRecipeAdapter.PostViewHolder pvh = new TopRecipeAdapter.PostViewHolder(v);
        return pvh;
    }

    private ArrayList<Post> topRecipes;

    public TopRecipeAdapter(ArrayList<Post> topRecipes) {
        this.topRecipes = topRecipes;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post recipe = topRecipes.get(position);
        Picasso.get().load(recipe.getUrlImage()).transform(new RoundedTransformation()).fit().centerCrop().into(holder.imgTopRecipe);
        holder.tvTopRecipe.setText(recipe.getTitle());
    }

    @Override
    public int getItemCount() {
        return topRecipes.size();
    }


}
