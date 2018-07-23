package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {
    private EditText edt_comment;
    private Button btn_comment;
    private RecyclerView rc_comment;
    private FrameLayout fl_comment;


    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comment, container, false);
        setUpData(v);

        return v;
    }
    public void loadComment(String postID){
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());

    }
    public void addComment(String postID){
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
    public void setUpData(View view){
        edt_comment = view.findViewById(R.id.edt_comment);
        btn_comment = view.findViewById(R.id.btn_send_comment);
        rc_comment = view.findViewById(R.id.rc_comment);
    }
}
