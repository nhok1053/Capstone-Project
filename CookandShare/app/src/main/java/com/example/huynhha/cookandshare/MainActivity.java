package com.example.huynhha.cookandshare;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.PagerAdapter;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.YouTube;
import com.example.huynhha.cookandshare.fragment.CategoryFragment;
import com.example.huynhha.cookandshare.fragment.CommentFragment;
import com.example.huynhha.cookandshare.fragment.HomeFragment;
import com.example.huynhha.cookandshare.fragment.ListAllCategoriesFragment;
import com.example.huynhha.cookandshare.fragment.ListTipsFragment;
import com.example.huynhha.cookandshare.fragment.NotificationFragment;
import com.example.huynhha.cookandshare.fragment.PersonalFragment;
import com.example.huynhha.cookandshare.fragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentCall,CommentFragment.onCloseClick {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Button btn_add_recipe;
    Button btn_seach;
    View view ;
    private AppBarLayout appBarLayout;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_categories,
            R.drawable.ic_home,
            R.drawable.notificaton_icon,
            R.drawable.ic_personal
    };
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        btn_add_recipe = findViewById(R.id.btn_add_recipe);
        btn_seach = findViewById(R.id.btn_search);
        view = findViewById(R.id.checkFragment);
        view.setVisibility(View.GONE);
//        appIntro();
        sharePrefIntro();
        setTabLayout();
        addRecipe();
        searchAction();
    }

    public void addRecipe() {
        btn_add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostRecipe.class);
                startActivity(intent);
            }
        });
    }

    public void searchAction(){
        btn_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Search.class);
                startActivity(intent);
            }
        });
    }

    private void setTabLayout() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        //adding fragment
        HomeFragment homeFragment = new HomeFragment();
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setOnCloseClick(this);
        homeFragment.setOnFragmentCall(this);
        pagerAdapter.addFragment(homeFragment, "");
        pagerAdapter.addFragment(new ListAllCategoriesFragment(), "");
        pagerAdapter.addFragment(new ListTipsFragment(), "");
        pagerAdapter.addFragment(new NotificationFragment(), "");
        pagerAdapter.addFragment(new ProfileFragment(), "");
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        System.out.println("App id : " + BuildConfig.APPLICATION_ID);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }


//    private void appIntro() {
//        SharedPreferences prefs = getSharedPreferences("check", MODE_PRIVATE);
//        String restoredText = prefs.getString("firstTime", null);
//        Toast.makeText(this, restoredText, LENGTH_LONG).show();
//        if (restoredText != "N") {
//            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
//            startActivity(intent);
//        }
//    }

    private void sharePrefIntro() {
        SharedPreferences sp = getSharedPreferences("check", MODE_PRIVATE);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Intent intent = new Intent(this, IntroActivity.class); // Call the AppIntro java class
            startActivity(intent);
        }
    }


    @Override
    public void onCommentClicked(String postID,String userID) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("postID",postID);
        bundle.putString("userID",userID);
        commentFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().add(R.id.fl_main,commentFragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCloseCommentClick() {
        view.setVisibility(View.GONE);
        System.out.println("Tat roi nha");
    }
}
