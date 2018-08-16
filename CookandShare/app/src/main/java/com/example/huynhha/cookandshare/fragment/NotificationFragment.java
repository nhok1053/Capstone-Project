package com.example.huynhha.cookandshare.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.NotificationAdapter;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private String userID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postRef = db.collection("Notification");
    private ArrayList<NotificationDetails> list;
    private NotificationAdapter notificationAdapter;
    private RecyclerView rcNoti;
    private int count = 0;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        rcNoti = v.findViewById(R.id.rc_noti);
        if(firebaseAuth.getCurrentUser()!=null){
            userID = firebaseAuth.getCurrentUser().getUid().toString();
        }
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        list = new ArrayList<>();
        System.out.println("run");
        System.out.println("UserID " + userID);
        System.out.println("AR: Oncreate");
        return v;
    }

    public void getData(String userID) {
        LinearLayoutManager lln = new LinearLayoutManager(getContext());
        rcNoti.setLayoutManager(lln);

        if(list.size()!=0){
            list.clear();
            System.out.println("AR: Vao2");
            notificationAdapter.notifyDataSetChanged();
        }
        System.out.println("Vao1");
        postRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        System.out.println("Vao1");
                        List<Map<String, Object>> list1 = (List<Map<String, Object>>) document.get("notification");

                        if(list1!=null){
                            for (int i = 0; i < list1.size(); i++) {
                                NotificationDetails notificationDetails = new NotificationDetails();
                                notificationDetails.setContent(list1.get(i).get("content").toString());
                                notificationDetails.setTime(list1.get(i).get("time").toString());
                                notificationDetails.setType(list1.get(i).get("type").toString());
                                notificationDetails.setUserID(list1.get(i).get("userID").toString());
                                notificationDetails.setPostID(list1.get(i).get("postID").toString());
                                notificationDetails.setUserUrlImage(list1.get(i).get("userUrlImage").toString());
                                list.add(notificationDetails);
                                count++;
                            }
                            System.out.println("AR: "+count);
                            if (count == list1.size()) {
                                System.out.println("Vao2");
                                notificationAdapter = new NotificationAdapter(list, getContext());
                                rcNoti.setAdapter(notificationAdapter);
                                count = 0;
                            }

                        }else {
                            System.out.println("Null roiiii");
                        }



                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("AR: Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.printf("AR: Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("AR: Stop");
    }

    @Override
    public void onStart() {
        super.onStart();
        getData(userID);
        System.out.println("AR: Start");
    }
}
