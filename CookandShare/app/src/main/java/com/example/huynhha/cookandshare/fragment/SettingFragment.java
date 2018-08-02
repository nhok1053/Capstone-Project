package com.example.huynhha.cookandshare.fragment;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.huynhha.cookandshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    @BindView(R.id.btnSettingFeedback)
    Button btnFeedback;
    @BindView(R.id.btnSettingAboutUs)
    Button btnAboutUs;
    @BindView(R.id.btnSettingLogout)
    Button btnSignOut;
    @BindView(R.id.imgSettingFeedback)
    ImageView imgFeedback;
    @BindView(R.id.imgSettingAboutUs)
    ImageView imgAboutUs;
    @BindView(R.id.imgSettingLogout)
    ImageView imgLogout;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutUsClick();
            }
        });
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackClick();
            }
        });
        return v;
    }

    public void feedbackClick() {
        String info = "OS version: " + System.getProperty("os.version") + "\n" + "API Level: " + android.os.Build.VERSION.SDK
                + "\n" + "Device: " + android.os.Build.DEVICE + "\n" + "Model: " + android.os.Build.MODEL
                + "\n" + "Product: " + android.os.Build.PRODUCT;
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipientList = new String[1];
        recipientList[0] = "hungltse04132@fpt.edu.vn";
//        intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        intent.putExtra(Intent.EXTRA_EMAIL, recipientList);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Cook And Share");
        intent.putExtra(Intent.EXTRA_TEXT, info + "\nPlease send us feedback\n");
        intent.setType("message/rfc822");
//        startActivity(Intent.createChooser(intent, "choose an email client"));
        startActivity(intent);
    }

    public void aboutUsClick() {
        android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        AboutUsFragment auf = new AboutUsFragment();
        ft.replace(R.id.fl_setting, auf);
        ft.addToBackStack(null);
        ft.commit();
    }

}
