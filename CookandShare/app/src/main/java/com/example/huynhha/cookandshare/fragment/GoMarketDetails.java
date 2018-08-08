package com.example.huynhha.cookandshare.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
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

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.GoMarketAdapter;
import com.example.huynhha.cookandshare.entity.GoMarket;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.model.DBContext;
import com.example.huynhha.cookandshare.model.DBHelper;
import com.example.huynhha.cookandshare.model.MaterialDBHelper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_go_market_details, container, false);
        setUp(v);
        String id = getArguments().getString("id"); //fetching value by key
        System.out.println("Get ID :" + id);
        goMarketDetails =this;
        getData();
        setData();
        saveData();
        setBtnGoMarketClose();
        return v;

    }

    public void  setBtnGoMarketClose(){
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
    }
    public void removeFragment(Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
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

}
