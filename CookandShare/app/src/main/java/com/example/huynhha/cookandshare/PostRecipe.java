package com.example.huynhha.cookandshare;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huynhha.cookandshare.adapter.PostRecipeTabLayoutAdapter;
import com.example.huynhha.cookandshare.adapter.SubTabAdapter;
import com.example.huynhha.cookandshare.fragment.ListAllCategoriesFragment;
import com.example.huynhha.cookandshare.fragment.PostRecipeMaterialFragment;
import com.example.huynhha.cookandshare.fragment.PostRecipeStepFragment;

import com.example.huynhha.cookandshare.fragment.ListTipsFragment;

import com.marlonmafra.android.widget.SegmentedTab;

public class PostRecipe extends AppCompatActivity {
    SegmentedTab segmentedTab;
    ViewPager viewPager;

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
        PostRecipeTabLayoutAdapter postRecipeTabLayoutAdapter = new PostRecipeTabLayoutAdapter(getSupportFragmentManager());
        postRecipeTabLayoutAdapter.addFragment(new PostRecipeMaterialFragment(), "1. Materials");
        postRecipeTabLayoutAdapter.addFragment(new PostRecipeStepFragment(), "2. Step");
        viewPager.setAdapter(postRecipeTabLayoutAdapter);
        segmentedTab.setupWithViewPager(viewPager);
    }
}
