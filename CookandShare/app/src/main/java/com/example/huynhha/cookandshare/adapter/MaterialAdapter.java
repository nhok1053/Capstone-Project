package com.example.huynhha.cookandshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder> {

    public class MaterialViewHolder extends RecyclerView.ViewHolder {
        public MaterialViewHolder(View v){
            super(v);
        }
    }

    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
