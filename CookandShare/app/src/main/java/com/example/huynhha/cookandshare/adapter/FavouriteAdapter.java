package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.FavouriteDBHelper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private ArrayList<Post> posts;
    private Context context;

    public FavouriteAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_favourite, parent, false);
        FavouriteAdapter.FavouriteViewHolder favouriteViewHolder = new FavouriteAdapter.FavouriteViewHolder(v);
        return favouriteViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final FavouriteViewHolder holder, int position) {

        final Post post = posts.get(position);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        // Picasso.get().load(post.getUrlImage()).resize(pxWidth / 2, 0).into(holder.contentImgFavouritePost);
        Picasso.get().load(post.getUrlImage()).fit().centerCrop().into(holder.contentImgFavouritePost);
        holder.txtFavouritePostTitle.setText(post.getTitle());
        holder.txtFavouritePostCreatedBy.setText(post.getUserName());
        holder.contentImgFavouritePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID", post.getPostID());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.ratingBarFavouritePost.setNumStars(3);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView contentImgFavouritePost;
        private TextView txtFavouritePostTitle;
        private TextView txtFavouritePostCreatedBy;
        private RatingBar ratingBarFavouritePost;
        private ImageView deleteIcon;

        public FavouriteViewHolder(View itemView) {
            super(itemView);
            contentImgFavouritePost = itemView.findViewById(R.id.contentImgFavouritePost);
            txtFavouritePostCreatedBy = itemView.findViewById(R.id.txtFavouritePostCreatedBy);
            txtFavouritePostTitle = itemView.findViewById(R.id.txtFavouritePostTitle);
            ratingBarFavouritePost = itemView.findViewById(R.id.ratingBarFavouritePost);
            deleteIcon = itemView.findViewById(R.id.favourite_3dot);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, deleteIcon);
                    popupMenu.getMenuInflater().inflate(R.menu.favourite_option, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            deleteDBOffline(getAdapterPosition());
                            posts.get(getAdapterPosition()).getPostID();
                            posts.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            return true;
                        }
                    });


                    popupMenu.show();
                }
            });
        }

        public void deleteDBOffline(int position) {
            FavouriteDBHelper favouriteDBHelper = new FavouriteDBHelper(context);
            SQLiteDatabase db = favouriteDBHelper.getWritableDatabase();
            String selection = DBContext.FavouriteDB.COLUMN_POST_ID + " LIKE ?";
            Post post = posts.get(position);
            String[] selectionArgs = {posts.get(position).getPostID().toString()};
            int deletedRows = db.delete(DBContext.FavouriteDB.TABLE_NAME, selection, selectionArgs);
        }
    }
}
