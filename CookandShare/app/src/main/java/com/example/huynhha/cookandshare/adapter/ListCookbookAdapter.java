package com.example.huynhha.cookandshare.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huynhha.cookandshare.CookBookActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Cookbook;
import com.example.huynhha.cookandshare.fragment.CookbookInfoFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListCookbookAdapter extends RecyclerView.Adapter<ListCookbookAdapter.CookbookViewHolder> {
    private Context context;
    private List<Cookbook> cookbooks;

    public ListCookbookAdapter(Context context, List<Cookbook> cookbooks) {
        this.context = context;
        this.cookbooks = cookbooks;
    }

    @NonNull
    @Override
    public ListCookbookAdapter.CookbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_cookbook, parent, false);
        ListCookbookAdapter.CookbookViewHolder pvh = new ListCookbookAdapter.CookbookViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListCookbookAdapter.CookbookViewHolder holder, int position) {
        final Cookbook cookbook = cookbooks.get(position);
        Picasso.get().load(cookbook.getCookbookMainImage()).fit().centerCrop().into(holder.img);
        holder.tvCookbookName.setText(cookbook.getCookbookName());
        holder.tvNumberRecipe.setText(cookbook.getNumberRecipe() + " công thức");
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookbookInfoFragment cookbookInfoFragment = new CookbookInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("cookbookID", cookbook.getCookbookID());
                cookbookInfoFragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_cookbook, cookbookInfoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return cookbooks.size();
    }

    public class CookbookViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img;
        TextView tvCookbookName;
        TextView tvNumberRecipe;
        CardView cv;

        public CookbookViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvCookbook);
            img = itemView.findViewById(R.id.imgMainCookbook);
            tvCookbookName = itemView.findViewById(R.id.tvCookbookName);
            tvNumberRecipe = itemView.findViewById(R.id.tvNumberRecipe);
            cv = itemView.findViewById(R.id.cvCookbook);
        }
    }
}
