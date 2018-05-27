package com.example.huynhha.cookandshare;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

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
        addSlide(AppIntroFragment.newInstance("Title","Description",R.drawable.ic_done_white, Color.CYAN));
        setFadeAnimation();
        SharedPreferences.Editor editor = getSharedPreferences("check", MODE_PRIVATE).edit();
        editor.putString("firstTime","N");
        editor.apply();

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
