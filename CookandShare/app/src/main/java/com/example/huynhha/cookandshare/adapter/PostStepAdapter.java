package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PostStepAdapter extends RecyclerView.Adapter<PostStepAdapter.PostStepViewHolder> {


    @Override
    public PostStepAdapter.PostStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostStepAdapter.PostStepViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PostStepViewHolder extends RecyclerView.ViewHolder {
        private EditText edt_description;
        private EditText edt_tips;
        private EditText edt_secret_materials;
        private ImageView img_step;
        private Button btn_add_image;
        private ImageView img_delete_image;
        private ImageView img_delete_step;
        private RelativeLayout duration_step;
        public PostStepViewHolder(View itemView) {
            super(itemView);
        }
    }
}
