package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.PostStep;

import java.util.List;

public class PostStepAdapter extends RecyclerView.Adapter<PostStepAdapter.PostStepViewHolder> {
    private List<PostStep> postSteps;


    public PostStepAdapter(List<PostStep> postSteps) {
        this.postSteps = postSteps;
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PostStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_post_step, parent, false);
        PostStepAdapter.PostStepViewHolder pvh = new PostStepViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostStepAdapter.PostStepViewHolder holder, int position) {
            PostStep postStep = postSteps.get(position);
            holder.txt_step.setText(""+position);
    }

    @Override
    public int getItemCount() {
        return postSteps.size();
    }

    public class PostStepViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_step;
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
            btn_add_image = itemView.findViewById(R.id.btn_step_add_image);
            img_delete_image = itemView.findViewById(R.id.img_delete_image_step);
            img_delete_step = itemView.findViewById(R.id.img_delete_step);
            txt_step = itemView.findViewById(R.id.txt_step);
            img_delete_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postSteps.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
    }

}
