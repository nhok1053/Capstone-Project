package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SliderCookingAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String postID;
    private int numberOfStep;
    private ArrayList<PostStep> postSteps;


    @Override
    public int getCount() {
        System.out.println("Size of list"+numberOfStep);
        return numberOfStep;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    public SliderCookingAdapter(Context context, String postID, int numberOfStep, ArrayList<PostStep> postSteps) {
        this.context = context;
        this.postID = postID;
        this.postSteps = postSteps;
        this.numberOfStep = numberOfStep;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_cooking, container, false);
        ImageView imgStep = view.findViewById(R.id.step_img);
        TextView txtTime = view.findViewById(R.id.step_time);
        TextView txtDescription = view.findViewById(R.id.step_details);
       // TextView txtTemp = view.findViewById(R.id.step_temp);
        TextView txtStep = view.findViewById(R.id.step_count);
        System.out.println("Position  "+position);
        System.out.println("Url "+postSteps.get(position).getImgURL());
        txtStep.setText(postSteps.get(position).getNumberOfStep().toString());
        txtTime.setText(postSteps.get(position).getTime_duration().toString());
        txtDescription.setText(postSteps.get(position).getDescription().toString());
       // txtTemp.setText(postSteps.get(position).getTemp().toString());
        Picasso.get().load(postSteps.get(position).getImgURL()).fit().centerCrop().into(imgStep);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }


}
