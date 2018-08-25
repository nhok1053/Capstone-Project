package com.example.huynhha.cookandshare;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.PagerAdapter;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.example.huynhha.cookandshare.fragment.EditMaterialFragment;
import com.example.huynhha.cookandshare.fragment.EditStepFragment;
import com.example.huynhha.cookandshare.fragment.LoginFragment;
import com.example.huynhha.cookandshare.fragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditPostActivity extends AppCompatActivity {
    private CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post");
    private int[] tabIcons = {
            R.drawable.ic_cart,
            R.drawable.ic_categories
    };
    private ViewPager editPostViewPager;
    private TabLayout editTabLayout;
    private Button updateRecipe;
    private Button closeEditPost;
    public List<PostStep> postSteps;
    private List<Map<String, Object>> listStep;
    private List<Map<String, Object>> listMaterials;
    private EditStepFragment editStepFragment;
    private EditMaterialFragment editMaterialFragment;
    private String documentID = "";
    private int count = 0;
    String postID = "";
    String type = "";
    String userID = "";
    String userNamePost ="";
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        editPostViewPager = findViewById(R.id.edit_post_viewpager);
        editTabLayout = findViewById(R.id.edit_post_tablayout);
        updateRecipe = findViewById(R.id.btn_add_edit_recipe);
        closeEditPost = findViewById(R.id.edit_post_back);
        editStepFragment = new EditStepFragment();
        editMaterialFragment = new EditMaterialFragment();
        listStep = new ArrayList<>();
        listMaterials = new ArrayList<>();
        postSteps = new ArrayList<>();
        context = this;
        postID = getIntent().getExtras().getString("postID");
        type = getIntent().getExtras().getString("type");
        if (type.equals("0")) {
            userID = getIntent().getExtras().getString("userID");
            userNamePost = getIntent().getExtras().getString("userName");
        }
        loadDocumentID();
        getSupportActionBar().hide();
        setUpEditTabLayout();
        setBtnUpdateRecipeListener();
    }

    public void loadDocumentID() {
        postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        documentID = documentSnapshot.getId();
                        System.out.println("documentIDDD: a " + documentID);
                    }
                }
            }
        });
    }

    public void setUpEditTabLayout() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(editMaterialFragment, "Nguyên liệu");
        pagerAdapter.addFragment(editStepFragment, "Bước thực hiện");
        editPostViewPager.setAdapter(pagerAdapter);
        // editPostViewPager.setOffscreenPageLimit(2);
        editTabLayout.setupWithViewPager(editPostViewPager);
        setupTabIcons();
        System.out.println("App id : " + BuildConfig.APPLICATION_ID);
    }

    private void setupTabIcons() {
        editTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        editTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void loadListStep() {
        postSteps = editStepFragment.getListStep();
        System.out.println("Demo 1 " + postSteps.size());
        for (int i = 0; i < postSteps.size(); i++) {
            Map<String, Object> updateStep = new HashMap<>();
            updateStep.put("description", postSteps.get(i).getDescription());
            updateStep.put("time_duration", postSteps.get(i).getTime_duration());
            updateStep.put("temp", postSteps.get(i).getTemp());
            updateStep.put("imgURL", postSteps.get(i).getImgURL());
            listStep.add(updateStep);
            count++;
        }
        System.out.println("documentIDDD: " + documentID);
        if (count == postSteps.size()) {
            postRef.document(documentID).update("postSteps", listStep);
        }
    }

    private void loadMaterialRecipe() {
        String getDescription = editMaterialFragment.getDescription();
        String getTitle = editMaterialFragment.getRecipeTitle();
        List<Material> getMaterials = editMaterialFragment.getMaterial();
        for (int i = 0; i < getMaterials.size(); i++) {
            Map<String, Object> updateMaterials = new HashMap<>();
            updateMaterials.put("materialName", getMaterials.get(i).getMaterialName());
            updateMaterials.put("quantity", getMaterials.get(i).getQuantity());
            listMaterials.add(updateMaterials);
        }
        postRef.document(documentID).update("materials", listMaterials);
        postRef.document(documentID).update("description", getDescription);
        postRef.document(documentID).update("title", getTitle);
    }

    private void setBtnUpdateRecipeListener() {
        updateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
                alert.setTitle("Xác nhận cập nhật bài viết");
                alert.setMessage("Bạn đã hoàn thành tất cả rồi chứ?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (validationSuccess()) {
                            loadListStep();
                            loadMaterialRecipe();
                            Toast.makeText(EditPostActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                            finish();
                            if (type.equals("0")) {
                                Intent intent = new Intent(context, PostDetails.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("postID", postID);
                                bundle.putString("userID", userID);
                                bundle.putString("userName",userNamePost);
                                bundle.putString("type", "3");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                            }

                        }
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });
    }

    private Boolean validationSuccess() {
        if (editMaterialFragment.getRecipeTitle().trim().length() == 0 || editMaterialFragment.getRecipeTitle().trim().length() > 50) {
            Toast.makeText(getApplicationContext(), "Tên của món ăn không được để trống và phải ít hơn 50 kí tự!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editMaterialFragment.getDescription().trim().length() == 0 || editMaterialFragment.getDescription().trim().length() > 500) {
            Toast.makeText(getApplicationContext(), "Mô tả của món ăn không được để trống và phải ít hơn 300 kí tự!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editMaterialFragment.getMaterial().size() == 0) {
            Toast.makeText(getApplicationContext(), "Phải có ít nhất một nguyên liệu!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editStepFragment.getListStep().size() == 0) {
            Toast.makeText(getApplicationContext(), "Phải có ít nhất một bước!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        Boolean Check = true;
        for (int i = 0; i < editStepFragment.getListStep().size(); i++) {
            if (editStepFragment.getListStep().get(i).getImgURL().trim().length() == 0 ||
                    editStepFragment.getListStep().get(i).getDescription().trim().length() == 0 ||
                    editStepFragment.getListStep().get(i).getTemp().trim().length() == 0 ||
                    editStepFragment.getListStep().get(i).getTime_duration().trim().length() == 0) {
                Check = false;
                Toast.makeText(getApplicationContext(), "Ảnh, mô tả, nhiệt độ, thời gian không được để trống!!!", Toast.LENGTH_SHORT).show();
            }
        }
        if (Check == false) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
