package com.example.huynhha.cookandshare;

import android.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huynhha.cookandshare.fragment.LoginFragment;
import com.example.huynhha.cookandshare.fragment.LoginWithPhoneFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        setFragment();

    }
    private void setFragment(){
        LoginFragment loginFragment = new LoginFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fl_login,loginFragment).addToBackStack(null);;
        fragmentTransaction.commit();
    }
}
