package com.example.huynhha.cookandshare.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.entity.Cookbook;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.fragment.CookbookInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CookbookListPostAdapter extends RecyclerView.Adapter<CookbookListPostAdapter.CookbookListViewHolder> {
    Context context;
    ArrayList<Post> posts;
    private String cookbookID;
    private String userID;
    private String userName;
    private String userUrlImage;
    private CollectionReference cookbookRef = MainActivity.db.collection("Cookbook");
    private CollectionReference userRef = MainActivity.db.collection("User");
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    private ArrayList<String> arrUpdate;
    private String userIDOfCookbook;
    private String userNameOfCookbook;
    private String userIDofPost;

    public CookbookListPostAdapter(Context context, ArrayList<Post> posts, String cookbookID, String userID, String userName, String userUrlImage, String userIDOfCookbook, String userNameOfCookbook) {
        this.context = context;
        this.posts = posts;
        this.cookbookID = cookbookID;
        this.userID = userID;
        this.userName = userName;
        this.userUrlImage = userUrlImage;
        this.userIDOfCookbook = userIDOfCookbook;
        this.userNameOfCookbook = userNameOfCookbook;
    }

    public class CookbookListViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView createBy;
        RatingBar rb;
        Button btn;
        CardView cv;

        public CookbookListViewHolder(View itemView) {
            super(itemView);
            arrUpdate = new ArrayList<>();
            img = itemView.findViewById(R.id.imgCookbookListpostMainImage);
            title = itemView.findViewById(R.id.tvCookbookListpostPostTitle);
            createBy = itemView.findViewById(R.id.tvCookbookListpostUserCreated);
            rb = itemView.findViewById(R.id.rbCookbookListpostPostRate);
            btn = itemView.findViewById(R.id.btnCookbookListpostMore);
            cv = itemView.findViewById(R.id.cvPostInCookbook);
            if (!userIDOfCookbook.equals(currentUser) || posts.size() < 2) {
                btn.setVisibility(View.GONE);
            }
        }
    }

    @NonNull
    @Override
    public CookbookListPostAdapter.CookbookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_cookbook_listpost, parent, false);
        CookbookListPostAdapter.CookbookListViewHolder pvh = new CookbookListPostAdapter.CookbookListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CookbookListPostAdapter.CookbookListViewHolder holder, int position) {
        final Post post = posts.get(position);
        Picasso.get().load(post.getUrlImage()).fit().centerCrop().into(holder.img);
        holder.title.setText(post.getTitle());
        holder.createBy.setText(post.getUserName());
        holder.rb.setRating(Float.parseFloat(post.getNumberOfRate()));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btn);
                popupMenu.getMenuInflater().inflate(R.menu.cookbook_post_option, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delCookbookPost:
                                System.out.println("DelCookbookPost");
                                posts.remove(post);
                                for (Post p : posts) {
                                    arrUpdate.add(p.getPostID());
                                }
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Xóa công thức");
                                alert.setMessage("Bạn muốn xóa công thức khỏi cookbook này?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                cookbookRef.document(cookbookID).update("postlist", arrUpdate);
                                                Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                                if (((FragmentActivity) context).getSupportFragmentManager() != null) {
                                                    CookbookInfoFragment cookbookInfoFragment = new CookbookInfoFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("cookbookID", cookbookID);
                                                    bundle.putString("userID", userIDOfCookbook);
                                                    bundle.putString("username", userNameOfCookbook);
                                                    bundle.putString("userimage", userUrlImage);
                                                    cookbookInfoFragment.setArguments(bundle);
                                                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fl_cookbook,
                                                            cookbookInfoFragment)
                                                            .commit();
                                                }
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetails.class);
                intent.putExtra("postID", post.getPostID());
                intent.putExtra("userID", post.getUserID());
                loadUserName(post.getUserID(), holder, intent);
            }
        });
    }

    public void loadUserName(String userID, final CookbookListPostAdapter.CookbookListViewHolder holder, final Intent intent) {
        userRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userNameQuerry = "";
                        userNameQuerry = document.getString("firstName");
                        intent.putExtra("userName", userNameQuerry);
                        context.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
