package com.example.huynhha.cookandshare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.huynhha.cookandshare.fragment.PostRecipeStepFragment;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class PostStepAdapter extends RecyclerView.Adapter<PostStepAdapter.PostStepViewHolder> {
    private List<PostStep> postSteps;
    private Context context;
    private static final int RESULT_LOAD_IMAGE = 2;
    private OnItemStepClick onItemStepClick;
    private OnPostSend onPostSend;
    public void setOnItemStepClick(OnItemStepClick onItemStepClick) {
        this.onItemStepClick = onItemStepClick;
    }

    public PostStepAdapter(Context context, List<PostStep> postSteps) {
        this.context = context;
        this.postSteps = postSteps;
    }
    public void setOnPostSend(OnPostSend onPostSend){
        this.onPostSend = onPostSend;
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
    public void onBindViewHolder(@NonNull PostStepAdapter.PostStepViewHolder holder, final int position) {
        PostStep postStep = postSteps.get(position);
        if(!postStep.getImgURL().equals("")){
            Picasso.get().load(postStep.getImgURL().toString()).centerCrop().fit().into(holder.img_step);
            holder.btn_add_image.setVisibility(View.INVISIBLE);

        }
        if(!postStep.getTemp().equals("")){
            holder.edtTemp.setText(postStep.getTemp().toString());
        }
        if(!postStep.getTime_duration().equals("")){
            holder.duration.setText(postStep.getTime_duration().toString());
        }
        holder.txt_step.setText(""+ (position+1));
        holder.edt_description.setText(postStep.getDescription().toString());
        holder.btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemStepClick.onClick(position);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                ((Activity) context).startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });
        if(postStep.getUri()!=null){
            Uri uri = Uri.parse(postStep.getUri());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(((Activity)context).getContentResolver(),uri);
                bitmap = resizedBitmap(bitmap,960,540);
                holder.btn_add_image.setVisibility(View.INVISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.img_step.setImageBitmap(bitmap);
            holder.edtTemp.setText(postStep.getTemp().toString());
            holder.duration.setText(postStep.getTime_duration().toString());

        }
        System.out.println("Holder" + holder.edt_description.getText().toString());

    }
    public List<PostStep> getPostSteps(){
        return postSteps;
    }
    public Bitmap resizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
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
        private EditText edtTemp;
        private EditText duration;


        public PostStepViewHolder(View itemView) {
            super(itemView);
            edt_description = itemView.findViewById(R.id.edt_step_description);
           // edt_tips = itemView.findViewById(R.id.step_tip);
            //edt_secret_materials = itemView.findViewById(R.id.material_tip);
            img_step = itemView.findViewById(R.id.img_step_recipe);
            btn_add_image = itemView.findViewById(R.id.btn_step_add_image);
            img_delete_image = itemView.findViewById(R.id.img_delete_image_step);
            img_delete_step = itemView.findViewById(R.id.img_delete_step);
            txt_step = itemView.findViewById(R.id.txt_step);
            duration = itemView.findViewById(R.id.edt_duration_step);
            edtTemp = itemView.findViewById(R.id.edt_temp);
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

            txt_step.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                postSteps.get(getAdapterPosition()).setNumberOfStep(txt_step.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            duration.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    postSteps.get(getAdapterPosition()).setTime_duration(duration.getText().toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtTemp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    postSteps.get(getAdapterPosition()).setTemp(edtTemp.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public interface OnItemStepClick{
        void onClick(int postion);
    }

    public interface OnPostSend{
        void onClick(List<PostStep> postStep);
    }


}
