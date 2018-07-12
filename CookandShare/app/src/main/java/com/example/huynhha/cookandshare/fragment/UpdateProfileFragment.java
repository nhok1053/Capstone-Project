package com.example.huynhha.cookandshare.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostRecipe;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends Fragment {
    @BindView(R.id.drop_down_list)
    Spinner sex_drop_down;
    @BindView(R.id.edt_date_of_birth)
    EditText edt_date_of_birth;
    @BindView(R.id.edt_phone_number_update)
    EditText edt_phone_number;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_first_name)
    EditText edt_first_name;
    @BindView(R.id.edt_second_name)
    EditText edt_second_name;
    @BindView(R.id.btn_update)
    Button btn_Update;
    private FirebaseAuth mAuth;
    private static final String[] paths = {"Nam", "Nữ", "Giới tính thứ 3"};
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");
    public  boolean isNull = true;
    String phoneNumber;
    public static final String TAG = "none";

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_update_profile, container, false);
        ButterKnife.bind(this, v);
        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_drop_down.setAdapter(adapter);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            phoneNumber = "+84" + getArguments().getString("phoneNumber");
            System.out.println("Key phone " + phoneNumber);
        }
        getInfo();
        setBtn_Update();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getInfo() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            for (UserInfo user : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                if (user.getProviderId().equals("facebook.com")) {
                    System.out.println("User is signed in with Facebook");
                } else if (user.getProviderId().equals("google.com")) {
                    String userID = mAuth.getCurrentUser().getUid();
                    edt_email.setText(mAuth.getCurrentUser().getEmail());
                    edt_first_name.setText(mAuth.getCurrentUser().getDisplayName());
                    edt_phone_number.setText(mAuth.getCurrentUser().getPhoneNumber());
                } else {
                    edt_phone_number.setText(phoneNumber);
                }
            }
        }

    }

    public void setBtn_Update() {
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                if(edt_first_name.getText()==null||edt_second_name.getText()==null||edt_date_of_birth.getText()==null||edt_email.getText()==null||edt_phone_number==null){
                    Toast.makeText(getActivity(), "Wrong input or null blank", Toast.LENGTH_SHORT).show();
                }else {
                    user.setFirstName(edt_first_name.getText().toString());
                    user.setSecondName(edt_second_name.getText().toString());
                    user.setDateOfBirth(edt_date_of_birth.getText().toString());
                    user.setImgUrl(mAuth.getCurrentUser().getPhotoUrl().toString());
                    user.setPhone(edt_phone_number.getText().toString());
                    user.setSex(sex_drop_down.getSelectedItem().toString());
                    user.setUserID(mAuth.getCurrentUser().getUid().toString());
                    user.setPostID(null);
                    isNull = false;
                }
                if (isNull==false){
                    loadData(user);
                }
            }
        });
    }
    private void loadData(User user){
        userRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "Update data success fully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
