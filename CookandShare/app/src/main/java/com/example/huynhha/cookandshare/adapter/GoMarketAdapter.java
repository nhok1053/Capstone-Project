package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Material;

import java.util.List;

public class GoMarketAdapter extends RecyclerView.Adapter<GoMarketAdapter.GoMarketViewHolder> {

    private List<Material> materials;

    @NonNull
    @Override
    public GoMarketAdapter.GoMarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_material_go_market, parent, false);
        GoMarketAdapter.GoMarketViewHolder goMarketViewHolder = new GoMarketAdapter.GoMarketViewHolder(v);
        return goMarketViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoMarketAdapter.GoMarketViewHolder holder, int position) {

        Material material = materials.get(position);
        holder.name_material.setText("+ "+material.getQuantity().toString()+" "+material.getMaterialName().toString());
        if(material.isCheckGoMarket().equals("0")){
            holder.cb_done.setChecked(false);
        }else {
            holder.cb_done.setChecked(true);
        }
        
    }

    public GoMarketAdapter(List<Material> materials) {
        this.materials = materials;
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class GoMarketViewHolder extends RecyclerView.ViewHolder {

        private TextView name_material;
        private CheckBox cb_done;

        public GoMarketViewHolder(View itemView) {
            super(itemView);
            name_material = itemView.findViewById(R.id.material_market);
            cb_done = itemView.findViewById(R.id.cb_material_market);
            cb_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(cb_done.isChecked()){
                        materials.get(getAdapterPosition()).setCheckGoMarket("1");
                    }else {
                        materials.get(getAdapterPosition()).setCheckGoMarket("0");
                    }

                }
            });
        }
    }
}
