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
import com.example.huynhha.cookandshare.entity.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.ViewHolder> {
    private ArrayList<User> users;

    public FollowListAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public FollowListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_follow, parent, false);
        FollowListAdapter.ViewHolder flav = new FollowListAdapter.ViewHolder(v);
        return flav;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        Picasso.get().load(user.getImgUrl()).transform(new RoundedTransformation()).fit().centerCrop().into(holder.avatar);
        holder.name.setText(user.getFirstName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.followUserAvatar);
            name = itemView.findViewById(R.id.followUserName);
        }

    }
}
