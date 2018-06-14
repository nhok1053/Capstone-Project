package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.huynhha.cookandshare.R;

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
            edt_description = itemView.findViewById(R.id.edt_step_description);
            edt_tips = itemView.findViewById(R.id.step_tip);
            edt_secret_materials = itemView.findViewById(R.id.material_tip);
            img_step = itemView.findViewById(R.id.img_step_recipe);
            
        }
    }
}
