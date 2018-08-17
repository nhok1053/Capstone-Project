package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Cookbook;
import com.example.huynhha.cookandshare.entity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CookbookListPostAdapter extends RecyclerView.Adapter<CookbookListPostAdapter.CookbookListViewHolder> {
    Context context;
    ArrayList<Post> posts;

    public CookbookListPostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public class CookbookListViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView createBy;
        RatingBar rb;
        Button btn;

        public CookbookListViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCookbookListpostMainImage);
            title = itemView.findViewById(R.id.tvCookbookListpostPostTitle);
            createBy = itemView.findViewById(R.id.tvCookbookListpostUserCreated);
            rb = itemView.findViewById(R.id.rbCookbookListpostPostRate);
            btn = itemView.findViewById(R.id.btnCookbookListpostMore);
        }
    }

    @NonNull
    @Override
    public CookbookListPostAdapter.CookbookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_cookbook_listpost, parent, false);
        CookbookListPostAdapter.CookbookListViewHolder pvh = new CookbookListPostAdapter.CookbookListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CookbookListPostAdapter.CookbookListViewHolder holder, int position) {
        Post post = posts.get(position);
        Picasso.get().load(post.getUrlImage()).fit().centerCrop().into(holder.img);
        holder.title.setText(post.getTitle());
        holder.createBy.setText(post.getUserName());
        holder.rb.setRating(Float.parseFloat(post.getNumberOfRate()));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btn);
                popupMenu.getMenuInflater().inflate(R.menu.cookbook_post_option, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delCookbookPost:
                                System.out.println("DelCookbookPost");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
