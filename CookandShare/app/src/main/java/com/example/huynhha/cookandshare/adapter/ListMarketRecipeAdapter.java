package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.GoMarket;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.fragment.GoMarketDetails;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.DBHelper;
import com.example.huynhha.cookandshare.model.FavouriteDBHelper;
import com.example.huynhha.cookandshare.model.MaterialDBHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListMarketRecipeAdapter extends RecyclerView.Adapter<ListMarketRecipeAdapter.RecyclerViewHolder> {
    public ArrayList<Post> posts;
    public Context context;
    public ArrayList<Material> materials;

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
        holder.time_create.setText("Thêm vào lúc: " + post.getTime().toString());
        System.out.println("Post IA: "+post.getPostID().toString());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                GoMarketDetails goMarketDetails = new GoMarketDetails();
                Bundle bundle = new Bundle();
                bundle.putString("id", post.getDescription());
                bundle.putString("name", post.getTitle().toString());
                bundle.putString("time", post.getTime().toString());
                bundle.putString("img", post.getUrlImage().toString());
                bundle.putString("userID",post.getUserID().toString());
                bundle.putString("postID",post.getPostID().toString());
                goMarketDetails.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_go_market, goMarketDetails).addToBackStack(null)
                        .commit();
                System.out.println("CLick " + position);
            }
        });
        loadData(post.getDescription().toString());
        int countMaterial = 0;
        System.out.println("Size count: "+materials.size());
        for (int i = 0; i < materials.size(); i++) {
            System.out.println("zo zo");
            if (materials.get(i).isCheckGoMarket().equals("1")) {
                countMaterial++;
                System.out.println("Count a" + countMaterial);
            }
        }
        holder.txt_number.setText("Đã mua " + countMaterial + "/" + materials.size() + " nguyên liệu");
        Picasso.get().load(post.getUrlImage()).fit().centerCrop().into(holder.img_recipe);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public ListMarketRecipeAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }


    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView txt_name_recipe;
        public ImageView img_recipe;
        public TextView time_create;
        public ImageView img_delete;
        public TextView txt_number;
        public ListMarketRecipeAdapter.ItemClickListener itemClickListener;

        public RecyclerViewHolder(View itemView) {

            super(itemView);
            txt_name_recipe = itemView.findViewById(R.id.name_of_recipe_go_market);
            time_create = itemView.findViewById(R.id.add_date);
            img_recipe = itemView.findViewById(R.id.market_img);
            img_delete = itemView.findViewById(R.id.go_market_delete);
            txt_number = itemView.findViewById(R.id.number_go_market);
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, img_delete);
                    popupMenu.getMenuInflater().inflate(R.menu.favourite_option, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            // System.out.println("Adapter position:"+getAdapterPosition());
                            // System.out.println("IDD: "+posts.get(getAdapterPosition()).getPostID().toString());
                            deleteDBOffline(Integer.parseInt(posts.get(getAdapterPosition()).getDescription().toString()));
                            posts.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            Toast.makeText(context, "Xoá khỏi đi chợ thành công!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    popupMenu.show();
                }
            });
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void deleteDBOffline(int position) {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String selection = DBContext.FeedEntry._ID + " LIKE ?";
            System.out.println("IDa");
            String _id = "" + position;
            String[] selectionArgs = {_id};
            int deletedRows = db.delete(DBContext.FeedEntry.TABLE_NAME, selection, selectionArgs);
            deleteDBOfflineMaterial(position);
        }

        public void deleteDBOfflineMaterial(int position) {
            MaterialDBHelper materialDBHelper = new MaterialDBHelper(context);
            SQLiteDatabase db = materialDBHelper.getWritableDatabase();
            String selection = DBContext.MaterialDB.COLUMN_ID + " LIKE ?";
            String _id = "" + position;
            String[] selectionArgs = {_id};
            int deletedRows = db.delete(DBContext.MaterialDB.TABLE_NAME, selection, selectionArgs);
        }

        public void setItemClickListener(ListMarketRecipeAdapter.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

    }

    public void loadData(String id) {

        MaterialDBHelper materialDBHelper = new MaterialDBHelper(context);
        SQLiteDatabase db1 = materialDBHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DBContext.MaterialDB.COLUMN_NAME_OF_MATERIAL,
                DBContext.MaterialDB.COLUMN_QUANTITY,
                DBContext.MaterialDB.COLUMN_CHECK
        };

        String selection = DBContext.MaterialDB.COLUMN_ID + " = ?";
        String[] selectionArgs = {"" + id};

        //  Cursor cursor1 = db1.rawQuery("SELECT _id, name, quatity, check FROM material WHERE id = "+id,projection);

        Cursor cursor = db1.query(
                DBContext.MaterialDB.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        materials = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Material material = new Material();
                material.setMaterialName(cursor.getString(cursor.getColumnIndex("name")));
                material.setQuantity(cursor.getString(cursor.getColumnIndex("quatity")));
                material.setCheckGoMarket(cursor.getString(cursor.getColumnIndex("checkBoolean")));
                materials.add(material);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

}
