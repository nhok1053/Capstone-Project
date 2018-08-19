package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
import com.example.huynhha.cookandshare.entity.Report;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    private Button reportButton;
    private Button btnSend;
    private RadioGroup groupReport;
    private RadioButton radioButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference reportRef = db.collection("Report");
    private String userID;
    private String postID;
    private String userName;
    private String documentID;
    private List<Map<String, Object>> listReport;
    private ArrayList<Report> list;
    private int count = 0;
    private ReportFragment reportFragment;
    @ServerTimestamp
    Date date;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        reportFragment = this;
        reportButton = v.findViewById(R.id.report_close);
        btnSend = v.findViewById(R.id.btn_send_report);
        groupReport = v.findViewById(R.id.group_report);
        userID = getArguments().getString("userID");
        postID = getArguments().getString("postID");
        userName = getArguments().getString("userName");
        list = new ArrayList<>();
        listReport = new ArrayList<>();
        date = new Date();
        getData();
        closeFragment();
        return v;

    }

    public void closeFragment() {
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment(reportFragment);
            }
        });
    }

    public void removeFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

    }

    public void getData() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                int selectedID = groupReport.getCheckedRadioButtonId();
                String str = "";
                switch (selectedID) {
                    case R.id.rp_1:
                        str = "Bài viết chứa ngôn từ không phù hợp,đả kích";
                        break;
                    case R.id.rp_2:
                        str = "Có những nguyên liệu quý hiếm và bị cấm";
                        break;
                    case R.id.rp_3:
                        str = "Công thức chứa nội dung không lành mạnh";
                        break;
                    case R.id.rp_4:
                        str = "Spam";
                        break;
                    case R.id.rp_5:
                        str = "Tin giả,quấy rối";
                        break;

                }
                System.out.println();
                final String finalStr = str;
                reportRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("ID :" + document.getId());
                                documentID = document.getId();
                                addData(finalStr, documentID);
                                Toast.makeText(getContext(), "Gửi thành công!\n Cảm ơn bạn đã giúp đỡ chúng tôi loại bỏ những bài đăng không an toàn!", Toast.LENGTH_SHORT).show();
                                removeFragment(reportFragment);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Report: Khong cok id");
                    }
                });
            }
        });
    }

    public void addData(String content, String documentID) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("content", content);
        updateMap.put("userID", userID);
        updateMap.put("userName", userName);
        updateMap.put("time", date);
        reportRef.document(documentID).collection("listreport").add(updateMap);
    }

}

