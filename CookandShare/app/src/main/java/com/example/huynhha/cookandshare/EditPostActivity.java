package com.example.huynhha.cookandshare;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.huynhha.cookandshare.adapter.PagerAdapter;
import com.example.huynhha.cookandshare.fragment.EditMaterialFragment;
import com.example.huynhha.cookandshare.fragment.LoginFragment;
import com.example.huynhha.cookandshare.fragment.ProfileFragment;

public class EditPostActivity extends AppCompatActivity {
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_categories,
            R.drawable.ic_home,
            R.drawable.notificaton_icon,
            R.drawable.ic_personal
    };
    private ViewPager editPostViewPager;
    private TabLayout editTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        editPostViewPager = findViewById(R.id.edit_post_viewpager);
        editTabLayout = findViewById(R.id.edit_post_tablayout);
        getSupportActionBar().hide();
        setEditTabLayout();
    }
    public void setEditTabLayout(){
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EditMaterialFragment(), "Nguyên liệu");
        pagerAdapter.addFragment(new LoginFragment(), "Bước thực hiện");
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

}
