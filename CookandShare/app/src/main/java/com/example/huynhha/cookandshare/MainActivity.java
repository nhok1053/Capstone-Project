package com.example.huynhha.cookandshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        SharedPreferences prefs = getSharedPreferences("check", MODE_PRIVATE);
        String restoredText = prefs.getString("firstTime", null);
        if (restoredText != "N") {
            Intent intent=new Intent(MainActivity.this,IntroActivity.class);
            startActivity(intent);
        }
    }
}
