package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.entity.User;
import com.example.huynhha.cookandshare.fragment.ViewProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.ViewHolder> {
    private ArrayList<User> users;
    private Context context;

//    public FollowListAdapter(ArrayList<User> users) {
//        this.users = users;
//    }

    public FollowListAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
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
        final String userID = user.getUserID();
        Picasso.get().load(user.getImgUrl()).transform(new RoundedTransformation()).fit().centerCrop().into(holder.avatar);
        holder.name.setText(user.getFirstName());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                ViewProfileFragment profileFragment = new ViewProfileFragment();
                profileFragment.setArguments(bundle);
                ft.replace(R.id.fl_main, profileFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.followUserAvatar);
            name = itemView.findViewById(R.id.followUserName);
            cv = itemView.findViewById(R.id.cvFollowFollowing);
        }

    }
}
