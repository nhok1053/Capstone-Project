package com.example.huynhha.cookandshare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.FavouriteAdapter;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.DBHelper;
import com.example.huynhha.cookandshare.model.FavouriteDBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView rcFavourite;
    private ArrayList<String> postsName;
    public ArrayList<Post> posts;
    private FavouriteAdapter favouriteAdapter;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postRef = db.collection("Post");
    private Post post;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        rcFavourite = findViewById(R.id.rc_favourite);
        postsName = new ArrayList<>();
        posts = new ArrayList<>();
        context = this;
        getData();
        LinearLayoutManager lln = new LinearLayoutManager(this);
        rcFavourite.setLayoutManager(lln);


    }

    public void deleteDBOffline(){
        FavouriteDBHelper favouriteDBHelper = new FavouriteDBHelper(getApplicationContext());
        SQLiteDatabase db = favouriteDBHelper.getWritableDatabase();
        String selection = DBContext.FavouriteDB.COLUMN_POST_ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { "MyTitle" };
// Issue SQL statement.
        int deletedRows = db.delete(DBContext.FavouriteDB.TABLE_NAME, selection, selectionArgs);
    }
    public void getDataFromDBOffline() {
        FavouriteDBHelper favouriteDBHelper = new FavouriteDBHelper(getApplicationContext());
        SQLiteDatabase db = favouriteDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBContext.FavouriteDB.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String post = cursor.getString(cursor.getColumnIndex("postID"));
                postsName.add(post);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    public void getData() {
        getDataFromDBOffline();

        if (postsName.size() == 0) {
            Toast.makeText(context, "Bạn chưa có công thức yêu thích nào", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < postsName.size(); i++) {
                postRef.whereEqualTo("postID", postsName.get(i)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Post post = new Post();
                                post.setTitle(documentSnapshot.get("title").toString());
                                post.setUrlImage(documentSnapshot.get("urlImage").toString());
                                post.setPostID(documentSnapshot.get("postID").toString());
                                post.setNumberOfRate("4");
                                post.setUserName(documentSnapshot.get("userName").toString());
                                System.out.println("Name: " + documentSnapshot.get("title").toString());
                                count++;
                                posts.add(post);
                                System.out.println("NAME: " + post.getTitle().toString());
                            }

                            if (count == postsName.size()) {
                                System.out.println("Size post: " + posts.size());
                                favouriteAdapter = new FavouriteAdapter(posts, context);
                                rcFavourite.setAdapter(favouriteAdapter);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });

            }
        }

    }

}

