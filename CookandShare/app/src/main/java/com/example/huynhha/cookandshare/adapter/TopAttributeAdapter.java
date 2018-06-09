package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopAttributeAdapter extends RecyclerView.Adapter<TopAttributeAdapter.PostViewHolder> {

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public PostViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cvTopChefImgViewTopChef);
        }
    }

    private ArrayList<User> topAttributes;

    public TopAttributeAdapter(ArrayList<User> topAttributes) {
        this.topAttributes = topAttributes;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public TopAttributeAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_top_chef, parent, false);
        TopAttributeAdapter.PostViewHolder pvh = new TopAttributeAdapter.PostViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TopAttributeAdapter.PostViewHolder holder, int position) {
        User topAttribute=topAttributes.get(position);
        Picasso.get().load(topAttribute.getImgUrl()).transform(new RoundedTransformation()).fit().centerCrop().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return topAttributes.size();
    }


}
