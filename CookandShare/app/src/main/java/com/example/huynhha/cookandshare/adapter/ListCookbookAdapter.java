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
import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Cookbook;
import com.example.huynhha.cookandshare.fragment.CookbookInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListCookbookAdapter extends RecyclerView.Adapter<ListCookbookAdapter.CookbookViewHolder> {
    private Context context;
    private List<Cookbook> cookbooks;
    String getUserID;

    public ListCookbookAdapter(Context context, List<Cookbook> cookbooks, String getUserID) {
        this.context = context;
        this.cookbooks = cookbooks;
        this.getUserID = getUserID;
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
        final String[] userName = new String[1];
        final String[] userUrlImage = new String[1];
        Picasso.get().load(cookbook.getCookbookMainImage()).fit().centerCrop().into(holder.img);
        holder.tvCookbookName.setText(cookbook.getCookbookName());
        holder.tvNumberRecipe.setText(cookbook.getNumberRecipe() + " công thức");
        MainActivity.db.collection("User").whereEqualTo("userID", getUserID)
                .limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        userName[0] = queryDocumentSnapshot.getString("firstName");
                        userUrlImage[0] = queryDocumentSnapshot.getString("imgUrl");
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookbookInfoFragment cookbookInfoFragment = new CookbookInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", userName[0]);
                bundle.putString("userimage", userUrlImage[0]);
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
