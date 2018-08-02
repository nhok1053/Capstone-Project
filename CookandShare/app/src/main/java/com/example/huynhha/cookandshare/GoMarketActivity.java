package com.example.huynhha.cookandshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.huynhha.cookandshare.adapter.GoMarketAdapter;
import com.example.huynhha.cookandshare.adapter.ListMarketRecipeAdapter;
import com.example.huynhha.cookandshare.entity.GoMarket;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.DBHelper;
import com.example.huynhha.cookandshare.model.MaterialDBHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GoMarketActivity extends AppCompatActivity {
    private RecyclerView rcGoMarket;
    private ArrayList<Post> posts;
    private ListMarketRecipeAdapter listMarketRecipeAdapter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_market);
        getSupportActionBar().hide();
        rcGoMarket = findViewById(R.id.rc_go_market);

        context=this;
        getData();
        setMarketAdapter();
    }

    public void setMarketAdapter() {
        LinearLayoutManager lln = new LinearLayoutManager(this);
        rcGoMarket.setLayoutManager(lln);
        listMarketRecipeAdapter = new ListMarketRecipeAdapter(posts,context);
        rcGoMarket.setAdapter(listMarketRecipeAdapter);

    }

    public void getData() {

        DBHelper mDbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + DBContext.FeedEntry.TABLE_NAME, null);
        posts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Post post = new Post();
                post.setTitle(cursor.getString(cursor.getColumnIndex("name")));
                post.setUrlImage(cursor.getString(cursor.getColumnIndex("imageUrl")));
                post.setTime(cursor.getString(cursor.getColumnIndex("time")));
                post.setPostID(cursor.getString(cursor.getColumnIndex("_id")));
                posts.add(post);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

}
