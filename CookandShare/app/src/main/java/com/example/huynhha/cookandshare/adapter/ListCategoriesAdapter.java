package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.PostActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListCategoriesAdapter extends RecyclerView.Adapter<ListCategoriesAdapter.CategoryViewHolder> {
    private Context ctx;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgCategory;
        TextView nameCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvCategory);
            imgCategory = itemView.findViewById(R.id.cvCategoryCategoryImg);
            nameCategory = itemView.findViewById(R.id.cvCategoryCategoryName);
        }


    }

    private List<Category> categories = new ArrayList<>();

    public ListCategoriesAdapter(Context ctx, List<Category> categories) {
        this.ctx = ctx;
        this.categories = categories;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ListCategoriesAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category, parent, false);
        ListCategoriesAdapter.CategoryViewHolder pvh = new ListCategoriesAdapter.CategoryViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final Category category = categories.get(position);
        Picasso.get().load(category.getCategoryImgUrl()).fit().centerCrop().into(holder.imgCategory);
        holder.nameCategory.setText(category.getCategoryName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, PostActivity.class);
                intent.putStringArrayListExtra("categorypost", category.getPostCategory());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


}
