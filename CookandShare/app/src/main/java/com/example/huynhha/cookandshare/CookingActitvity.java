package com.example.huynhha.cookandshare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huynhha.cookandshare.adapter.SliderCookingAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CookingActitvity extends AppCompatActivity {
    private ViewPager startCookingViewpager;
    private LinearLayout dotViewPager;
    private SliderCookingAdapter sliderCookingAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private ArrayList<PostStep> postSteps;
    private CollectionReference postRef = db.collection("Post");
    private Context context;
    private int count;
    private TextView[] mDots;
    private int currentPage;
    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_actitvity);
        context = this;
        getSupportActionBar().hide();
        startCookingViewpager = findViewById(R.id.start_cooking_view_pager);
        dotViewPager = findViewById(R.id.dot_step);
        btnFinish = findViewById(R.id.btn_finish);
        postSteps = new ArrayList<>();
        String postID = getIntent().getExtras().getString("postIDStep");
        getData(postID);
        setFinish();
        btnFinish.setVisibility(View.INVISIBLE);
        btnFinish.setEnabled(false);
        startCookingViewpager.addOnPageChangeListener(viewListener);
    }
    public void setFinish(){
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getData(final String postID) {

        postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        List<Map<String, Object>> list1 = (List<Map<String, Object>>) document.get("postSteps");
                        for (int i = 0; i < list1.size(); i++) {
                            PostStep postStep = new PostStep();
                            postStep.setImgURL(list1.get(i).get("imgURL").toString());
                            postStep.setNumberOfStep("" + (i + 1));
//                            postStep.setTime_duration(list1.get(i).get("time_duration").toString());
                            postStep.setDescription(list1.get(i).get("description").toString());
                            postStep.setTemp(list1.get(i).get("temp").toString());
                            postSteps.add(postStep);
                            count++;
                        }
                        if (count == list1.size()) {
                            sliderCookingAdapter = new SliderCookingAdapter(context, postID, count, postSteps);
                            startCookingViewpager.setAdapter(sliderCookingAdapter);
                            addDot(0);

                        }

                    }
                } else {
                    Log.d("Check", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void addDot(int position) {
        mDots = new TextView[10];
        dotViewPager.removeAllViews();
        for (int i = 0; i < count; i++) {
            mDots[i] = new TextView(context);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparent));
            dotViewPager.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDot(position);
            currentPage = position;
            if (position == 0) {
                btnFinish.setEnabled(false);
                btnFinish.setVisibility(View.INVISIBLE);
            } else if (position == count - 1) {
                btnFinish.setVisibility(View.VISIBLE);
                btnFinish.setEnabled(true);
                btnFinish.setText("Finish");
            } else {
                btnFinish.setEnabled(false);
                btnFinish.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
