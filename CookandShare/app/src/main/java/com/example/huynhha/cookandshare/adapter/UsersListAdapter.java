package com.example.huynhha.cookandshare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.User;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {
    public List<User> userList;
    public UsersListAdapter(List<User> userList){
        this.userList = userList;
    }

    @Override
    public UsersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        return  new UsersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersListAdapter.ViewHolder holder, int position) {
        holder.nameText.setText(userList.get(position).getFirstName());
        holder.descriptionText.setText(userList.get(position).getSecondName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView nameText;
        public TextView descriptionText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nameText = (TextView) mView.findViewById(R.id.name_text);
            descriptionText = (TextView) mView.findViewById(R.id.description_text);
        }
    }
}
