package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.CircleTransform;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.User;
import com.example.huynhha.cookandshare.fragment.ViewProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {
    public List<User> userList;
    Context context;

    public UsersListAdapter(List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public UsersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new UsersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersListAdapter.ViewHolder holder, final int position) {
        holder.nameText.setText(userList.get(position).getFirstName());
        Picasso.get().load(userList.get(position).getImgUrl())
                .resize(100, 100)
                .centerInside()
                .transform(new CircleTransform()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("userID", userList.get(position).getUserID());
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
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView nameText;
        public TextView descriptionText;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameText = (TextView) mView.findViewById(R.id.name_text);
            descriptionText = (TextView) mView.findViewById(R.id.description_text);
            imageView = (ImageView) mView.findViewById(R.id.image_data);
        }
    }
}
