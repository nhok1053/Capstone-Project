package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.huynhha.cookandshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginWithPhoneFragment extends Fragment {

    @BindView(R.id.send_Phone_Number)
    Button btnSignInWithPhone;
    @BindView(R.id.edt_Phone_Number)
    EditText edtSignInWithPhone;
    public LoginWithPhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login_with_phone, container, false);
        ButterKnife.bind(this,v);
        setSendPhoneNumber();
        return v;
    }
    public void setSendPhoneNumber(){
        btnSignInWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginWithPhoneAuthFragment loginWithPhoneAuth = new LoginWithPhoneAuthFragment();
                final Bundle bundle = new Bundle();
                bundle.putString("phoneNumber",""+edtSignInWithPhone.getText().toString());
                loginWithPhoneAuth.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction()
                        .replace(R.id.fl, loginWithPhoneAuth);
                transaction.commit();
            }
        });
    }
}
