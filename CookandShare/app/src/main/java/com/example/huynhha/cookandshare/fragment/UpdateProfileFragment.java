package com.example.huynhha.cookandshare.fragment;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostRecipe;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends Fragment {

    @BindView(R.id.edt_date_of_birth)
    EditText edt_date_of_birth;
    @BindView(R.id.edt_phone_number_update)
    EditText edt_phone_number;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_first_name)
    EditText edt_first_name;
    @BindView(R.id.btn_update)
    Button btn_Update;
    @BindView(R.id.gender_group_button)
    RadioGroup radioGroup;
    @BindView(R.id.avatarUpdate)
    ImageView imgAvatar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");
    public boolean isNull = true;
    String phoneNumber;
    public static final String TAG = "none";
    private String userID = "";
    private Boolean isMail;
    private String type;
    private UpdateProfileFragment updateProfileFragment;


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
        updateProfileFragment = this;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = getArguments().getString("type");
        }
        if (type.equals("1")) {
            System.out.println("Update:1");
            userID = getArguments().getString("userID");
            loadUserInfoFromProfile();
        } else if (type.equals("0")) {
            System.out.println("Update:2");
            getInfo();
        }
        setBtn_UpdateListener();
        datePicker();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void loadUserInfoFromProfile() {
        userRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        String name = documentSnapshot.getString("firstName");
                        String DOB = documentSnapshot.getString("dateOfBirth");
                        String phone = documentSnapshot.getString("phone");
                        String imgUrl = documentSnapshot.getString("imgUrl");
                        String mail = documentSnapshot.getString("mail");
                        edt_email.setText(mail);
                        edt_first_name.setText(name);
                        edt_date_of_birth.setText(DOB);
                        edt_phone_number.setText(phone);
                        Picasso.get().load(imgUrl).centerCrop().fit().into(imgAvatar);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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
                    Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).centerCrop().fit().into(imgAvatar);
                }
            }
        }

    }
    public void datePicker(){
        edt_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edt_date_of_birth.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }



    public void setBtn_UpdateListener() {
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idOfSelected = radioGroup.getCheckedRadioButtonId();
                User user = new User();
                if (edt_first_name.getText().toString().trim().length() == 0 || edt_date_of_birth.getText().toString().trim().length() == 0 ||
                        edt_email.getText().toString().trim().length() == 0 || edt_phone_number.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                } else if (!edt_first_name.getText().toString().matches( "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯẠẢẤẦẨẪẬẮẰẲẴẶ" +
                        "ẸẺẼỀẾỂưạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
                        "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$" )) {
                    Toast.makeText(getActivity(), "Họ và Tên không được chứa kí tự đặc biệt (trừ khoảng trắng và dấu gạch nối)", Toast.LENGTH_LONG).show();
                } else if (edt_first_name.getText().toString().trim().length() > 30) {
                    Toast.makeText(getActivity(), "Họ và Tên không được nhiều hơn 30 kí tự!!!", Toast.LENGTH_SHORT).show();
                } else if (!edt_email.getText().toString().matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+")) {
                    Toast.makeText(getActivity(), "Vui lòng điền đúng định dạng email (vd: cookandshare@fpt.edu)", Toast.LENGTH_SHORT).show();
                } else if (!edt_phone_number.getText().toString().matches("^[+]?[0-9]{10,13}$")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại đúng định dạng và có độ dài 10-13 kí tự!!!", Toast.LENGTH_SHORT).show();
                } else {
                    user.setStatus(true);
                    user.setFirstName(edt_first_name.getText().toString());
                    user.setDateOfBirth(edt_date_of_birth.getText().toString());
                    user.setImgUrl(mAuth.getCurrentUser().getPhotoUrl().toString());
                    user.setPhone(edt_phone_number.getText().toString());
                    user.setDeletePostNum(0);
                    switch (idOfSelected) {
                        case R.id.rdmen:
                            user.setSex("Nam");
                        case R.id.rdwomen:
                            user.setSex("Nữ");
                        default:
                            user.setSex("Nam");
                    }
                    user.setUserID(mAuth.getCurrentUser().getUid().toString());
                    user.setPostID(null);

                    user.setMail(edt_email.getText().toString());
                    isNull = false;
                }
                if (isNull == false) {
                    if (type.equals("0")) {
                        loadData(user);
                    } else {
                        updateProfile(user);
                    }
                }
            }
        });
    }

    private void loadData(User user) {

        userRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                documentReference.getId();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void removeFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

    }

    private void updateProfile(final User user) {
        userRef.whereEqualTo("userID", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doucument : task.getResult()) {
                        userID = doucument.getId();
                        userRef.document(userID).update("dateOfBirth", user.getDateOfBirth());
                        userRef.document(userID).update("firstName", user.getFirstName());
                        userRef.document(userID).update("phone", user.getPhone());
                        userRef.document(userID).update("mail", user.getMail());
                        Toast.makeText(getActivity(), "Update data success fully", Toast.LENGTH_SHORT).show();
                        removeFragment(updateProfileFragment);
                        ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fl_profile,new ProfileFragment()).commit();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
