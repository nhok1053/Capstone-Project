package com.example.huynhha.cookandshare.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginWithPhoneAuthFragment extends Fragment {
    @BindView(R.id.edt_verify_code)
    EditText verifyCode;
    @BindView(R.id.send_verify_code)
    Button btnVerify;
    @BindView(R.id.resend_verify_code)
    Button btnResendCode;
    @BindView(R.id.time_remaning)
    TextView txtTimeRemaning;
    private static final String TAG = "PhoneAuth";
    private String phoneVerifyID;
    String phoneNumber;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private PhoneAuthProvider mPhoneAuthProvider;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");
    private CollectionReference followRef = db.collection("Follow");
    private CollectionReference notiRef = db.collection("Notification");


    public LoginWithPhoneAuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login_with_phone_auth, container, false);
        ButterKnife.bind(this, v);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            phoneNumber = "+84"+ getArguments().getString("phoneNumber");
            System.out.println("Key phone " + phoneNumber);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        mPhoneAuthProvider = PhoneAuthProvider.getInstance();
        setUpVerifyCallback();
        sendCode();
        setVerifyCode();
        resendCode();
        setTime();
        return v;

    }

    public void setTime() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                txtTimeRemaning.setText("" +
                        "" + millisUntilFinished / 1000 + "s");
                //here you can have your logic to set text to edittext
            }
            public void onFinish() {
                txtTimeRemaning.setText("0s");
            }

        }.start();
    }

    public void resendCode() {
        btnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(phoneNumber, resendToken);
            }
        });
    }

    public void sendCode() {
        System.out.println("Repair for send code");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                verificationCallbacks);
        System.out.println("Send code finish");
    }

    private void setUpVerifyCallback() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithAuthCredital(phoneAuthCredential);
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                Toast.makeText(getContext(),"Login Successfull",Toast.LENGTH_LONG);
                UpdateProfileFragment loginWithPhoneAuth = new UpdateProfileFragment();
                final Bundle bundle = new Bundle();
                bundle.putString("phoneNumber",phoneNumber);
                loginWithPhoneAuth.setArguments(bundle);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Map<String, Object> data = new HashMap<>();
                data.put("userID", user.getUid().toString());
                Toast.makeText(getContext(), "Login Successfull", Toast.LENGTH_LONG);
                notiRef.add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                });
                followRef.add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                });
                userRef.whereEqualTo("userID", user.getUid().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        System.out.println("Intent2");
                        if (queryDocumentSnapshots != null && queryDocumentSnapshots.size() != 0) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_login, new UpdateProfileFragment()).addToBackStack(null);
                            transaction.commit();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidUserException) {
                    Log.d(TAG, "Invalid code : " + e.getLocalizedMessage());
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.d(TAG, "SMS many : ");
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                phoneVerifyID = verificationId;
                resendToken = forceResendingToken;

            }
        };

    }

    private void signInWithAuthCredital(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    System.out.println("demo");
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {

                    }
                }
            }
        });
    }

    public void setVerifyCode() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verifyCode.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerifyID, code);
                signInWithAuthCredital(credential);
            }
        });
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                verificationCallbacks,         // OnVerificationStateChangedCallbacks
                token);
        // ForceResendingToken from callbacks
    }

}
