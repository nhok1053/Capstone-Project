package com.example.huynhha.cookandshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.huynhha.cookandshare.fragment.LoginFragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        addSlide(IntroSlide.newInstance(R.layout.intro_slide_1));
        addSlide(IntroSlide.newInstance(R.layout.intro_slide_2));
        addSlide(IntroSlide.newInstance(R.layout.intro_slide_3));
        addSlide(IntroSlide.newInstance(R.layout.intro_slide_4));
        setFadeAnimation();
        showSkipButton(false);
        SharedPreferences.Editor editor = getSharedPreferences("check", MODE_PRIVATE).edit();
        editor.putString("firstTime", "N");
        editor.apply();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
