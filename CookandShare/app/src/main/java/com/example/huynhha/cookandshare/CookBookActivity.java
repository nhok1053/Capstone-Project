package com.example.huynhha.cookandshare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.huynhha.cookandshare.adapter.ListCategoriesAdapter;
import com.example.huynhha.cookandshare.adapter.ListCookbookAdapter;
import com.example.huynhha.cookandshare.entity.Category;
import com.example.huynhha.cookandshare.entity.Cookbook;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CookBookActivity extends AppCompatActivity {
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    private ListCookbookAdapter listCookbookAdapter;
    @BindView(R.id.rvCookbook)
    RecyclerView rv;
    @BindView(R.id.btnCookbookClose)
    Button btnClose;
    private Context context;
    private ArrayList<Cookbook> cookbooks;
    private List<Map<String, Object>> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        context = this;
        cookbooks = new ArrayList<>();
        close();
        importListCookbook();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void importListCookbook() {
        final GridLayoutManager gln = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gln);
        MainActivity.db.collection("Cookbook").whereEqualTo("userID", currentUser).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            if (task.getResult().size() > 0) {
                                String id = documentSnapshot.getId().toString();
                                list1 = (List<Map<String, Object>>) documentSnapshot.get("postlist");
                                int numberpost = list1.size();
                                String image = list1.get(0).get("postUrlImage").toString();
                                String cookbookName = documentSnapshot.get("cookbookName").toString();
                                String userID = documentSnapshot.get("userID").toString();
                                String userName = documentSnapshot.get("userName").toString();
                                Cookbook cookbook = new Cookbook(id, cookbookName, image, numberpost + "", userID, userName);
                                cookbooks.add(cookbook);
                            }
                        }
                        listCookbookAdapter = new ListCookbookAdapter(context, cookbooks);
                        rv.setAdapter(listCookbookAdapter);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void close() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
