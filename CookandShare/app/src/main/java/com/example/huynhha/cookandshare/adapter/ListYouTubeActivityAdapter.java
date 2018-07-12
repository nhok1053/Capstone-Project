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

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.YoutubeActivity;
import com.example.huynhha.cookandshare.entity.YouTube;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListYouTubeActivityAdapter extends RecyclerView.Adapter<ListYouTubeActivityAdapter.ListTipViewHolder> {
    private Context ctx;
    private List<YouTube> youTubes;

    public ListYouTubeActivityAdapter(Context ctx, ArrayList<YouTube> youTubes) {
        this.ctx = ctx;
        this.youTubes = youTubes;
    }

    public class ListTipViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView tipImg;
        TextView tipTitle;

        public ListTipViewHolder(View itemView) {
            super(itemView);
            tipImg = itemView.findViewById(R.id.imgYoutubeVideo);
            tipTitle = itemView.findViewById(R.id.tvYoutubeDes);
            cardView = itemView.findViewById(R.id.cardViewYouTubeActivity);
        }
    }

    @NonNull
    @Override
    public ListYouTubeActivityAdapter.ListTipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_youtube_activity, parent, false);
        ListYouTubeActivityAdapter.ListTipViewHolder listTipViewHolder = new ListYouTubeActivityAdapter.ListTipViewHolder(v);
        return listTipViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListYouTubeActivityAdapter.ListTipViewHolder holder, int position) {
        final YouTube youTube = youTubes.get(position);
        Picasso.get().load("https://img.youtube.com/vi/" + youTube.getYoutubeUrl() + "/0.jpg").fit().centerCrop().into(holder.tipImg);
        holder.tipTitle.setText(youTube.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoutubeActivity.cnt=2;
                YouTube.youTubeFix=new YouTube(youTube.getTipID(),youTube.getTitle(),youTube.getYoutubeUrl());
                System.out.println(YoutubeActivity.cnt+"XDSSSSSSSS"+youTube.getYoutubeUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return youTubes.size();
    }
}
