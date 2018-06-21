package com.example.huynhha.cookandshare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
    private Context context;
    private static final int RESULT_LOAD_IMAGE = 1;


    public PostStepAdapter(Context context, List<PostStep> postSteps) {
        this.context = context;
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
        holder.txt_step.setText("" + position);
        holder.edt_description.setText(postStep.getDescription().toString());
        System.out.println("Holder" + holder.edt_description.getText().toString());

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
            edt_description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    System.out.println("Text change");
                    postSteps.get(getAdapterPosition()).setDescription(edt_description.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edt_tips.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    postSteps.get(getAdapterPosition()).setDescription(edt_tips.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edt_secret_materials.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    postSteps.get(getAdapterPosition()).setSecret_material(edt_secret_materials.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            btn_add_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });
        }
    }

    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        ((Activity) context).startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }

}
