package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.CookingActitvity;
import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Notification;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
import com.example.huynhha.cookandshare.fragment.ViewProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_notification, parent, false);
        NotificationViewHolder notificationViewHolder = new NotificationViewHolder(v);
        return notificationViewHolder;
    }

    public ArrayList<NotificationDetails> notificationDetails;
    private Context context;


    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        int newposition = notificationDetails.size() - position - 1;
        final NotificationDetails notificationDetail = notificationDetails.get(newposition);
        holder.txtContent.setText(notificationDetail.getContent().toString());
        System.out.println("notification" + notificationDetail.getContent().toString());
        holder.txtTime.setText(notificationDetail.getTime().toString());
        final String type = notificationDetail.getType().toString();

        holder.setItemClickListener(new ItemClickListener() {

            @Override
            public void onClick(View view, int newposition, boolean isLongClick) {
                System.out.println("Click click" + type);
                if (type.equals("0")) {
                    ViewProfileFragment viewProfileFragment = new ViewProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("userID", notificationDetail.getUserID().toString());
                    viewProfileFragment.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_main, viewProfileFragment).addToBackStack(null)
                            .commit();
                } else if (type.equals("1")) {
                    Intent intent = new Intent(context, PostDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("postID", notificationDetail.getPostID().toString());
                    bundle.putString("userID", notificationDetail.getUserID().toString());
                    bundle.putString("userName", "");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else if (type.equals("2")) {
                    ViewProfileFragment viewProfileFragment = new ViewProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("userID", notificationDetail.getUserID().toString());
                    viewProfileFragment.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_main, viewProfileFragment).addToBackStack(null)
                            .commit();
                }else if(type.equals("3")){
                    Intent intent = new Intent(context, CookingActitvity.class);
                    intent.putExtra("postIDStep",notificationDetail.getPostID());
                    context.startActivity(intent);
                }
            }

        });
        Picasso.get().load(notificationDetail.getUserUrlImage().toString()).fit().centerCrop().into(holder.imgNoti);

    }

    public NotificationAdapter(ArrayList<NotificationDetails> notificationDetails, Context context) {
        this.notificationDetails = notificationDetails;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return notificationDetails.size();
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);

    }
}

class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public NotificationAdapter.ItemClickListener itemClickListener;
    public ImageView imgNoti;
    public TextView txtContent;
    public TextView txtTime;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        imgNoti = itemView.findViewById(R.id.img_notification);
        txtContent = itemView.findViewById(R.id.txt_notification);
        txtTime = itemView.findViewById(R.id.txt_time_noti);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }


    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }

    public void setItemClickListener(NotificationAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}


