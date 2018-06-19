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
import com.example.huynhha.cookandshare.entity.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListCategoriesAdapter extends RecyclerView.Adapter<ListCategoriesAdapter.CategoryViewHolder> {


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView nameCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.cvCategoryCategoryImg);
            nameCategory = itemView.findViewById(R.id.cvCategoryCategoryName);
        }


    }

    private List<Category> categories = new ArrayList<>();

    public ListCategoriesAdapter(List<Category> categories) {
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
        Category category = categories.get(position);
        Picasso.get().load(category.getCategoryImgUrl()).fit().centerCrop().into(holder.imgCategory);
        holder.nameCategory.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


}
