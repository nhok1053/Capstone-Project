package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Material;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder> {

    private List<Material> materials;
    private int type = 12;


    public MaterialAdapter(List<Material> materials, int type) {
        this.materials = materials;
        this.type = type;
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView cvMaterialTvMaterialName;
        TextView getCvMaterialTvMaterialQuantity;
        ImageView imgDeleteMaterial;

        public MaterialViewHolder(View v) {
            super(v);
            cvMaterialTvMaterialName = v.findViewById(R.id.tv_material_name);
            getCvMaterialTvMaterialQuantity = v.findViewById(R.id.tv_quatity);
            imgDeleteMaterial = v.findViewById(R.id.btn_delete_material);
            imgDeleteMaterial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    materials.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_material, parent, false);
        MaterialAdapter.MaterialViewHolder mvh = new MaterialAdapter.MaterialViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        Material material = materials.get(position);
        holder.cvMaterialTvMaterialName.setText(material.getMaterialName());
        if(type==0){
            holder.getCvMaterialTvMaterialQuantity.setText(material.getQuantity()+" " + material.getType());
        }else if(type == 1){
            holder.getCvMaterialTvMaterialQuantity.setText(material.getQuantity());
        }
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }


}
