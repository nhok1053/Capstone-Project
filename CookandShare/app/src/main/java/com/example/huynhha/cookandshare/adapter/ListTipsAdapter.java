package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.YoutubeActivity;
import com.example.huynhha.cookandshare.entity.YouTube;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListTipsAdapter extends RecyclerView.Adapter<ListTipsAdapter.ListTipViewHolder> {
    private Context ctx;
    private List<YouTube> youTubes;

    public ListTipsAdapter(Context ctx, ArrayList<YouTube> youTubes) {
        this.ctx = ctx;
        this.youTubes = youTubes;
    }

    public class ListTipViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView tipImg;
        TextView tipTitle;

        public ListTipViewHolder(View itemView) {
            super(itemView);
            tipImg = itemView.findViewById(R.id.cvTipImg);
            tipTitle = itemView.findViewById(R.id.cvTipTitle);
            cardView = itemView.findViewById(R.id.cvTip);
        }
    }

    @NonNull
    @Override
    public ListTipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_tip, parent, false);
        ListTipsAdapter.ListTipViewHolder listTipViewHolder = new ListTipsAdapter.ListTipViewHolder(v);
        return listTipViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListTipViewHolder holder, int position) {
        final YouTube youTube = youTubes.get(position);
        Picasso.get().load("https://img.youtube.com/vi/"+youTube.getYoutubeUrl()+"/0.jpg").fit().centerCrop().into(holder.tipImg);
        holder.tipTitle.setText(youTube.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, YoutubeActivity.class);
                intent.putExtra("youtube",youTube.getYoutubeUrl());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return youTubes.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
