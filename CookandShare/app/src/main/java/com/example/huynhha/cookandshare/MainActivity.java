package com.example.huynhha.cookandshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.PagerAdapter;
import com.example.huynhha.cookandshare.fragment.CategoryFragment;
import com.example.huynhha.cookandshare.fragment.HomeFragment;
import com.example.huynhha.cookandshare.fragment.NotificationFragment;
import com.example.huynhha.cookandshare.fragment.PersonalFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_categories,
            R.drawable.notificaton_icon,
            R.drawable.ic_personal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        appIntro();
        setTabLayout();
    }

    private void setTabLayout() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        //adding fragment
        pagerAdapter.addFragment(new HomeFragment(), "");
        pagerAdapter.addFragment(new CategoryFragment(), "");
        pagerAdapter.addFragment(new NotificationFragment(), "");
        pagerAdapter.addFragment(new PersonalFragment(), "");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        System.out.println("App id : " + BuildConfig.APPLICATION_ID);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void appIntro() {
        SharedPreferences prefs = getSharedPreferences("check", MODE_PRIVATE);
        String restoredText = prefs.getString("firstTime", null);
        Toast.makeText(this, "++++++"+restoredText+"'''''''''", LENGTH_LONG).show();
        if (restoredText != "N") {
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
        }
    }
}
