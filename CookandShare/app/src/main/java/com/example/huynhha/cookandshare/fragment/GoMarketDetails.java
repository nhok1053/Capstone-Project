package com.example.huynhha.cookandshare.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.CookingActitvity;
import com.example.huynhha.cookandshare.GoMarketActivity;
import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.GoMarketAdapter;
import com.example.huynhha.cookandshare.entity.GoMarket;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.DBHelper;
import com.example.huynhha.cookandshare.model.MaterialDBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GoMarketDetails extends Fragment {
    private ImageView img_recipe;
    private TextView txt_name;
    private TextView txt_date;
    private RecyclerView rc_go_market_details;
    private List<Material> materials;
    private GoMarketAdapter goMarketAdapter;
    private Button btn_go_market_close;
    private Button btn_add_save_go_market;
    public GoMarketDetails goMarketDetails;
    public FirebaseAuth firebaseAuth;
    private String id = "";
    private String userID = "";
    private String recipeName = "";
    private String postID = "";
    private CollectionReference notiRef = FirebaseFirestore.getInstance().collection("Notification");
    private String documentNoti = "";
    private List<Map<String, Object>> listNoti;
    private ArrayList<NotificationDetails> listNotiDetails;
    private int count = 0;
    private double countCheck = 0;
    private Button startCooking;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_go_market_details, container, false);
        setUp(v);
        id = getArguments().getString("id"); //fetching value by key
        userID = getArguments().getString("userID");
        recipeName = getArguments().getString("name");
        postID = getArguments().getString("postID");
        System.out.println("Post Iod " + postID);

        listNoti = new ArrayList<>();
        listNotiDetails = new ArrayList<>();
        System.out.println("Get ID :" + id);
        goMarketDetails = this;
        getData();
        setData();
        saveData();
        setBtnGoMarketClose();
        setStartCooking();
        return v;

    }

    public void setBtnGoMarketClose() {
        btn_go_market_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment(goMarketDetails);
            }
        });
    }

    public void saveData() {
        btn_add_save_go_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDBHelper materialDBHelper = new MaterialDBHelper(getContext());
                SQLiteDatabase db1 = materialDBHelper.getReadableDatabase();
                countCheck = 0;
                for (int i = 0; i < materials.size(); i++) {
                    if (materials.get(i).isCheckGoMarket().equals("1")) {
                        countCheck++;
                    }
                }
                if (countCheck / materials.size() > 0.8) {
                    System.out.println("ZOooo");
                    getNotification();
                }
                // New value for one column
                for (int i = 0; i < materials.size(); i++) {
                    ContentValues values = new ContentValues();
                    values.put(DBContext.MaterialDB.COLUMN_CHECK, materials.get(i).isCheckGoMarket().toString());
                    String selection = DBContext.MaterialDB.COLUMN_NAME_OF_MATERIAL + " LIKE ?";
                    String name = materials.get(i).getMaterialName().toString();
                    String[] selectionArgs = {name};

                    int count = db1.update(
                            DBContext.MaterialDB.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);
                }

                removeFragment(goMarketDetails);
                Toast.makeText(getContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public void setUp(View v) {
        btn_go_market_close = v.findViewById(R.id.go_market_close);
        btn_add_save_go_market = v.findViewById(R.id.add_save_go_market);
        img_recipe = v.findViewById(R.id.recipe_image_goMarket);
        txt_date = v.findViewById(R.id.date_add_go_market);
        txt_name = v.findViewById(R.id.txt_name_go_market);
        rc_go_market_details = v.findViewById(R.id.rc_market_detail);
        startCooking = v.findViewById(R.id.btn_start_cooking);
    }

    public void removeFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        Intent intent = ((GoMarketActivity) getContext()).getIntent();
        ((GoMarketActivity) getContext()).finish();
        startActivity(intent);
        fragmentTransaction.commit();

    }

    public void setData() {
        String name = getArguments().getString("name");
        String date = getArguments().getString("date");
        String urlImage = getArguments().getString("img");
        txt_name.setText(name);
        txt_date.setText(date);
        Picasso.get().load(urlImage).fit().centerCrop().into(img_recipe);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rc_go_market_details.setLayoutManager(lln);
        goMarketAdapter = new GoMarketAdapter(materials);
        rc_go_market_details.setAdapter(goMarketAdapter);

    }

    public void setStartCooking() {
        startCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CookingActitvity.class);
                intent.putExtra("postIDStep", postID);
                startActivity(intent);
            }
        });
    }

    public void getData() {

        MaterialDBHelper materialDBHelper = new MaterialDBHelper(getContext());
        SQLiteDatabase db1 = materialDBHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DBContext.MaterialDB.COLUMN_NAME_OF_MATERIAL,
                DBContext.MaterialDB.COLUMN_QUANTITY,
                DBContext.MaterialDB.COLUMN_CHECK
        };

        String selection = DBContext.MaterialDB.COLUMN_ID + " = ?";
        String id = getArguments().getString("id"); //fetching value by key
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

    public void getNotification() {
        firebaseAuth = FirebaseAuth.getInstance();
        String currentUser = firebaseAuth.getUid().toString();
        System.out.println("UserID " + userID);
        notiRef.whereEqualTo("userID", currentUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        documentNoti = document.getId();
                        try {
                            listNoti = (List<Map<String, Object>>) document.get("notification");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (listNoti == null) {
                            System.out.println("Khong co noti");
                        } else {
                            for (int i = 0; i < listNoti.size(); i++) {
                                NotificationDetails notificationDetails = new NotificationDetails();
                                notificationDetails.setType(listNoti.get(i).get("type").toString());
                                notificationDetails.setUserUrlImage(listNoti.get(i).get("userUrlImage").toString());
                                notificationDetails.setPostID(listNoti.get(i).get("postID").toString());
                                notificationDetails.setTime(listNoti.get(i).get("time").toString());
                                notificationDetails.setContent(listNoti.get(i).get("content").toString());
                                notificationDetails.setUserName(listNoti.get(i).get("userName").toString());
                                notificationDetails.setUserID(listNoti.get(i).get("userID").toString());
                                listNotiDetails.add(notificationDetails);
                                count++;
                            }
                        }

                        if (listNoti == null) {
                            addNoti(documentNoti);
                        } else if (count == listNoti.size()) {
                            addNoti(documentNoti);
                            count = 0;
                        }

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void addNoti(final String documentID) {
        firebaseAuth = FirebaseAuth.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        final String date = df.format(Calendar.getInstance().getTime());
        double doneMaterials = 0;
        doneMaterials = Math.floor((countCheck / (materials.size())) * 100) / 100;
        Map<String, Object> updateNoti = new HashMap<>();
        updateNoti.put("postID", id);
        updateNoti.put("time", date);
        updateNoti.put("type", 3);
        updateNoti.put("userID", firebaseAuth.getCurrentUser().getUid().toString());
        updateNoti.put("userUrlImage", "https://firebasestorage.googleapis.com/v0/b/capstone-project-1d078.appspot.com/o/ic_laucher.png?alt=media&token=cc8d4d0d-afbd-4ca4-a832-865bed762a89");
        updateNoti.put("userName", firebaseAuth.getCurrentUser().getDisplayName().toString());
        updateNoti.put("content", "Món ăn ' " + recipeName + " ' đã hoàn thành " + doneMaterials * 100 + "% nguyên liệu. Nấu ăn ngay nào!!!");
        if (listNoti == null) {
            listNoti = new ArrayList<>();
            listNoti.add(updateNoti);
        } else {
            listNoti.add(updateNoti);
        }
        System.out.println("check noti comment");
        notiRef.document(documentID).update("notification", listNoti);
    }

}
