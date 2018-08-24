package com.example.huynhha.cookandshare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.FavouriteAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.model.DBContext;
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

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView rcFavourite;
    private ArrayList<String> postsName;
    public ArrayList<Post> posts;
    private FavouriteAdapter favouriteAdapter;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postRef = db.collection("Post");
    private CollectionReference userRef = db.collection("User");
    private Post post;
    private int count = 1;
    private int countID = 0;

    private List<String> listUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        rcFavourite = findViewById(R.id.rc_favourite);
        postsName = new ArrayList<>();
        posts = new ArrayList<>();
        listUserName = new ArrayList<>();
        context = this;
        loadDataFromDBOffline();
        loadPostData();
        LinearLayoutManager lln = new LinearLayoutManager(this);
        rcFavourite.setLayoutManager(lln);

    }

    public void deleteDBOffline() {
        FavouriteDBHelper favouriteDBHelper = new FavouriteDBHelper(getApplicationContext());
        SQLiteDatabase db = favouriteDBHelper.getWritableDatabase();
        String selection = DBContext.FavouriteDB.COLUMN_POST_ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {"MyTitle"};
// Issue SQL statement.
        int deletedRows = db.delete(DBContext.FavouriteDB.TABLE_NAME, selection, selectionArgs);
    }

    public void loadDataFromDBOffline() {
        FavouriteDBHelper favouriteDBHelper = new FavouriteDBHelper(getApplicationContext());
        SQLiteDatabase db = favouriteDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBContext.FavouriteDB.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String post = cursor.getString(cursor.getColumnIndex("postID"));
                System.out.println("FAVOURITE+ "+post);
                postsName.add(post);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    public void loadPostData() {
        if (postsName.size() == 0) {
            Toast.makeText(context, "Bạn chưa có công thức yêu thích nào", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < postsName.size(); i++) {
                final String postID = postsName.get(i);
                System.out.println("FAVOURITE+ postID"+postID);
                postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                final String title = documentSnapshot.get("title").toString();
                                final String urlImage = documentSnapshot.get("urlImage").toString();
                               // final String postID = documentSnapshot.get("postID").toString();
                                final String rate = documentSnapshot.get("numberOfRate").toString();
                                System.out.println("FAVOURITE+ startload");
                                // post.setUserName(documentSnapshot.get("userName").toString());
                                postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        count++;
                                        if (task.isSuccessful()) {
                                            Post post;
                                            String userName = "";
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                userName = document.getString("userName").toString();
                                            }
                                            post = new Post(rate, userName, postID, title, urlImage);
                                            posts.add(post);
                                        }
                                        System.out.println("FAVOURITE+ count "+count +" postsize "+postsName.size());

                                        if (postsName.size() == count) {
                                            System.out.println("FAVOURITE+ addAdapter");
                                            favouriteAdapter = new FavouriteAdapter(posts, context);
                                            rcFavourite.setAdapter(favouriteAdapter);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
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
//checked

}

