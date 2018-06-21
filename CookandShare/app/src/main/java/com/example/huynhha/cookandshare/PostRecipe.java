package com.example.huynhha.cookandshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.PostRecipeTabLayoutAdapter;
import com.example.huynhha.cookandshare.adapter.SubTabAdapter;
import com.example.huynhha.cookandshare.fragment.ListAllCategoriesFragment;
import com.example.huynhha.cookandshare.fragment.PostRecipeMaterialFragment;
import com.example.huynhha.cookandshare.fragment.PostRecipeStepFragment;

import com.example.huynhha.cookandshare.fragment.ListTipsFragment;

import com.marlonmafra.android.widget.SegmentedTab;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PostRecipe extends AppCompatActivity {
    SegmentedTab segmentedTab;
    ViewPager viewPager;

    private PostRecipeStepFragment postRecipeStepFragment;
    private PostRecipeMaterialFragment postRecipeMaterialFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_recipe);
        segmentedTab = findViewById(R.id.segmented_post_recipe);
        viewPager = findViewById(R.id.view_post_recipe);
        getSupportActionBar().hide();
        setTabLayout();
    }

    private void setTabLayout() {

        postRecipeStepFragment = new PostRecipeStepFragment();
        postRecipeMaterialFragment = new PostRecipeMaterialFragment();
        PostRecipeTabLayoutAdapter postRecipeTabLayoutAdapter=new PostRecipeTabLayoutAdapter(getSupportFragmentManager());
        postRecipeTabLayoutAdapter.addFragment(postRecipeMaterialFragment,"1. Materials");
        postRecipeTabLayoutAdapter.addFragment(postRecipeStepFragment,"2. Step");

        viewPager.setAdapter(postRecipeTabLayoutAdapter);
        segmentedTab.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("check1", "onActivityResult: ");
        postRecipeStepFragment.onActivityResult(requestCode,resultCode,data);
}

}
