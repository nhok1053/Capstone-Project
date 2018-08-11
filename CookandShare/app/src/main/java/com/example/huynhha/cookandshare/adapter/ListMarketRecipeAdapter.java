package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.GoMarket;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.fragment.GoMarketDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListMarketRecipeAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    private ArrayList<Post> posts;
    private Context context;

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_go_market, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(v);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final Post post = posts.get(position);
        holder.txt_name_recipe.setText(post.getTitle().toString());
        holder.time_create.setText("Thêm vào lúc: "+post.getTime().toString());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                GoMarketDetails goMarketDetails = new GoMarketDetails();
                Bundle bundle = new Bundle();
                bundle.putString("id",post.getPostID());
                bundle.putString("name",post.getTitle().toString());
                bundle.putString("time",post.getTime().toString());
                bundle.putString("img",post.getUrlImage().toString());
                goMarketDetails.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_go_market, goMarketDetails).addToBackStack(null)
                        .commit();
                System.out.println("CLick "+position);
            }
        });
        Picasso.get().load(post.getUrlImage()).fit().centerCrop().into(holder.img_recipe);

    }

    @Override
    public int getItemCount() {
       return posts.size();
    }

    public ListMarketRecipeAdapter(ArrayList<Post> posts,Context context) {
        this.posts = posts;
        this.context = context;
    }


    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }


}

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{

    public TextView txt_name_recipe;
    public ImageView img_recipe;
    public TextView time_create;
    public ListMarketRecipeAdapter.ItemClickListener itemClickListener;

    public RecyclerViewHolder(View itemView) {

        super(itemView);
        txt_name_recipe = itemView.findViewById(R.id.name_of_recipe_go_market);
        time_create = itemView.findViewById(R.id.add_date);
        img_recipe = itemView.findViewById(R.id.market_img);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ListMarketRecipeAdapter.ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
