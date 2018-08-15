package com.example.huynhha.cookandshare.fragment;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.google.firebase.auth.FirebaseAuth;

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
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private SettingFragment settingFragment;
    private Button settingClose;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);
        settingClose = v.findViewById(R.id.settings_close);
        settingFragment = this;
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
        logOut();
        closeFragment();
        return v;
    }

    public void removeFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

    }

    public void closeFragment() {
        settingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment(settingFragment);
            }
        });

    }

    public void logOut() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Đăng xuất");
                alert.setMessage("Bạn muốn đăng xuất tài khoản?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new LoginFragment()).setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right).addToBackStack(null).commit();
                                removeFragment(settingFragment);
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
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
        android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
        AboutUsFragment auf = new AboutUsFragment();
        ft.replace(R.id.fl_setting, auf);
        ft.addToBackStack(null);
        ft.commit();
    }

}
