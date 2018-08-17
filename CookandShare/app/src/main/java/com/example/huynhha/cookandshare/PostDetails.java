package com.example.huynhha.cookandshare;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.DBHelper;
import com.example.huynhha.cookandshare.model.FavouriteDBHelper;
import com.example.huynhha.cookandshare.model.MaterialDBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class PostDetails extends AppCompatActivity {
    private static final String TAG = "Loi";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference postRef = db.collection("Post");
    private ImageView btn_back;
    private ImageView btn_favourite;
    private ImageView btn_go_market;
    private ImageView recipe_detail_image;
    private TextView name_of_food;
    private TextView create_by_name;
    private RatingBar ratingBar;
    private TextView txt_recipe_description;
    private TextView txt_duration;
    private TextView txt_number_of_people_eat_details;
    private TextView txt_difficult;
    private TextView txt_material;
    private RecyclerView rc_list_recipe;
    private Button btn_start_cooking;
    public Post post = new Post();
    private ProgressDialog progressDialog;
    private List<Material> list;
    private int count = 0;
    private String postID;
    private String documentID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_details);
        progressDialog = new ProgressDialog(this);
        postID = getIntent().getExtras().getString("postID");
        setUp();
        storageReference = FirebaseStorage.getInstance().getReference();
        getData(postID);
        saveDataGoMarket();
        setFavourite();
        setBtnStartCooking();
        setRatingBar();
    }

    public void setBtnStartCooking() {
        btn_start_cooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postID = getIntent().getExtras().getString("postID");
                Intent intent = new Intent(PostDetails.this, CookingActitvity.class);
                intent.putExtra("postIDStep", postID);
                startActivity(intent);
            }
        });

    }

    public void setUp() {
        btn_back = findViewById(R.id.btn_back);
        btn_favourite = findViewById(R.id.btn_add_favourite);
        btn_go_market = findViewById(R.id.btn_add_go_market);
        recipe_detail_image = findViewById(R.id.recipe_image_details);
        name_of_food = findViewById(R.id.name_of_recipe);
        create_by_name = findViewById(R.id.create_by_name);
        ratingBar = findViewById(R.id.ratingBarSmall);
        txt_recipe_description = findViewById(R.id.tv_description);
        txt_duration = findViewById(R.id.txt_duration);
        txt_number_of_people_eat_details = findViewById(R.id.number_of_people_eat_detail);
        txt_difficult = findViewById(R.id.difficult_type);
        txt_material = findViewById(R.id.tv_material_details);
        rc_list_recipe = findViewById(R.id.rc_list_recipe);
        btn_start_cooking = findViewById(R.id.start_cooking);
    }

    public void setFavourite() {
        btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postID = getIntent().getExtras().getString("postID");
                FavouriteDBHelper favouriteDBHelper = new FavouriteDBHelper(getApplicationContext());
                SQLiteDatabase db1 = favouriteDBHelper.getWritableDatabase();
                SQLiteDatabase db2 = favouriteDBHelper.getReadableDatabase();

                if (count % 2 == 0) {
                    ContentValues values = new ContentValues();
                    values.put(DBContext.FavouriteDB.COLUMN_POST_ID, postID);
                    long row = db1.insert(DBContext.FavouriteDB.TABLE_NAME, null, values);
                    Toast.makeText(PostDetails.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                    System.out.println("Row : " + row);
                } else {
                    String selection = DBContext.FavouriteDB.COLUMN_POST_ID + " LIKE ?";
                    String[] selectionArgs = {postID};
                    int deletedRows = db2.delete(DBContext.FavouriteDB.TABLE_NAME, selection, selectionArgs);
                    System.out.println("Row : " + deletedRows);
                    Toast.makeText(PostDetails.this, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show();
                }

                count++;
            }
        });
    }

    public boolean checkData(String postID) {

        return true;
    }

    public Post getData(String postID) {
        postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        post.setUrlImage(document.get("urlImage").toString());
                        post.setTitle(document.get("title").toString());
                        post.setUserID(document.get("userID").toString());
                        post.setUserName(document.get("userName").toString());
                        post.setNumberOfPeople(document.get("numberOfPeople").toString());
                        post.setDescription(document.get("description").toString());
                        post.setDifficult(document.get("difficult").toString());
                        post.setNumberOfRate(document.get("numberOfRate").toString());
                        list = new ArrayList<>();
                        List<Map<String, Object>> list1 = (List<Map<String, Object>>) document.get("materials");
                        for (int i = 0; i < list1.size(); i++) {
                            Material material = new Material();
                            material.setMaterialName(list1.get(i).get("materialName").toString());
                            material.setQuantity(list1.get(i).get("quantity").toString());
                            list.add(material);
                        }
                        Log.d(TAG, "onComplete: " + list1.get(0).get("materialName").toString());
                        System.out.println("Quatity" + list1.get(0).toString());
                        post.setMaterials(list);
                        setData(post);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return post;
    }

    public void setData(Post post) {
        String str = "";
        Picasso.get().load(post.getUrlImage()).resize(650, 0).into(recipe_detail_image);
        name_of_food.setText(post.getTitle().toString());
        create_by_name.setText(post.getUserName().toString());
        ratingBar.setRating(Float.parseFloat(post.getNumberOfRate()));
        txt_recipe_description.setText(post.getDescription().toString());
        txt_difficult.setText(post.getDifficult().toString());
        txt_number_of_people_eat_details.setText(post.getNumberOfPeople().toString());
        List<Material> list1 = post.getMaterials();
        for (int i = 0; i < list1.size(); i++) {
            String quatity = list1.get(i).getQuantity();
            String name = list1.get(i).getMaterialName();
            str += "  - " + quatity + " " + name + "\n";
        }
        txt_material.setText(str);
        progressDialog.dismiss();

    }

//    private void startLoading(Post post) {
//        progressDialog.setTitle("Đang tải ...");
//        progressDialog.show();
//
//    }

    private void saveDataGoMarket() {
        btn_go_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void saveData() {
        DBHelper mDbHelper = new DBHelper(getApplicationContext());
        MaterialDBHelper materialDBHelper = new MaterialDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        SQLiteDatabase db1 = materialDBHelper.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContext.FeedEntry.COLUMN_NAME_OF_RECIPE, post.getTitle());
        values.put(DBContext.FeedEntry.COLUMN_IMG_URL, post.getUrlImage());
        values.put(DBContext.FeedEntry.COLUMN_TIME, date);
        values.put(DBContext.FeedEntry.COLUMN_USERID,post.getUserID());
        values.put(DBContext.FeedEntry.COLUMN_POST_ID,postID);
        System.out.println(values);


// Insert the new row, returning the primary key value of the new row

        long newRowId = db.insert(DBContext.FeedEntry.TABLE_NAME, null, values);

        for (int i = 0; i < list.size(); i++) {
            ContentValues contentValues = new ContentValues();
            System.out.println("RowID " + newRowId);
            contentValues.put(DBContext.MaterialDB.COLUMN_ID, String.valueOf(newRowId));
            contentValues.put(DBContext.MaterialDB.COLUMN_NAME_OF_MATERIAL, list.get(i).getMaterialName().toString());
            contentValues.put(DBContext.MaterialDB.COLUMN_QUANTITY, list.get(i).getQuantity().toString());
            contentValues.put(DBContext.MaterialDB.COLUMN_CHECK, "0");
            long rowMaterials = db1.insert(DBContext.MaterialDB.TABLE_NAME, null, contentValues);
            System.out.println("So 2 : " + rowMaterials);
            System.out.println("Ten Nguyen Lieu " + list.get(i).getMaterialName().toString());
            System.out.println(contentValues.toString());

        }
        System.out.println("So : " + newRowId);
        Toast.makeText(this, "Lưu thành công ", Toast.LENGTH_SHORT).show();

    }


    @SuppressLint("ClickableViewAccessibility")
    public void setRatingBar() {
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Dialog dialog;
                dialog = new Dialog(PostDetails.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.rating_dialog);
                final RatingBar rateBar = dialog.findViewById(R.id.ratingBar);
                Button btnSendRate = dialog.findViewById(R.id.sendRating);

                btnSendRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        documentID = documentSnapshot.getId();
                                        String strRate = documentSnapshot.getString("numberOfRate");
                                        System.out.println("RATE: " + strRate);
                                        System.out.println("Kaka: " + rateBar.getRating());
                                        addRate(strRate, rateBar.getRating());
                                        dialog.cancel();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                });
                dialog.show();

                return false;
            }
        });
    }


    public void addRate(String strRate, float newRate) {
        float oldRate = Float.parseFloat(strRate);
        float rate = (oldRate*5 + newRate) / 6;
        System.out.println("New Rate :" + rate);
        String rateNew = String.valueOf(rate);
        postRef.document(documentID).update("rate", rateNew);
        Toast.makeText(this, "Cảm ơn bạn đã đánh giá công thức!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }
}


