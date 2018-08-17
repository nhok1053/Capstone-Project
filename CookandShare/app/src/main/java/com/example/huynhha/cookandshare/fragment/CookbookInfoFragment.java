package com.example.huynhha.cookandshare.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.huynhha.cookandshare.MainActivity;
import com.example.huynhha.cookandshare.PostDetails;
import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.RoundedTransformation;
import com.example.huynhha.cookandshare.adapter.CookbookListPostAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.entity.Cookbook;
import com.example.huynhha.cookandshare.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CookbookInfoFragment extends Fragment {
    private String cookbookID;
    private CollectionReference cookbookRef = MainActivity.db.collection("Cookbook");
    @BindView(R.id.imgCookbookInfoMainImage)
    ImageView imgMain;
    @BindView(R.id.imgCookbookInfoUserImage)
    ImageView imgUserImage;
    @BindView(R.id.tvCookbookInfoUserName)
    TextView userName;
    @BindView(R.id.tvCookbookInfoCookbookTitle)
    TextView title;
    @BindView(R.id.tvCookbookInfoNumberPost)
    TextView numberPost;
    @BindView(R.id.tvCookbookInfoCookbookDescription)
    TextView descriptopn;
    @BindView(R.id.rvCookbookInfoListPostCookbook)
    RecyclerView rv;
    @BindView(R.id.btnFragmentCookbookInfoMore)
    Button btnMore;
    Cookbook cb;
    private List<Map<String, Object>> list1;
    ArrayList<Post> posts = new ArrayList<>();

    public CookbookInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cookbook_info, container, false);
        ButterKnife.bind(this, v);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getString("cookbookID") != null) {
                cookbookID = bundle.getString("cookbookID");
            } else {
                cookbookID = "";
            }
        }
        System.out.println(cookbookID + "HH");
        getInfoCookbook();
        clickMore();
        return v;

    }

    private void clickMore() {
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), btnMore);
                popupMenu.getMenuInflater().inflate(R.menu.cookbook_option, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editCookbook:
                                System.out.println("CB: edit");
                                final Dialog dialog;
                                dialog = new Dialog(getActivity());
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.editcookbook_dialog);
                                final EditText name = dialog.findViewById(R.id.etEditCookbookEditName);
                                final EditText des = dialog.findViewById(R.id.etEditCookbookEditDes);
                                Button btnSave = dialog.findViewById(R.id.btnEditCookbookSave);
                                Button btnCancel = dialog.findViewById(R.id.btnEditCookbookCancel);
                                name.setText(cb.getCookbookName());
                                des.setText(cb.getCookbookDescription());
                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        cookbookRef.document(cb.getCookbookID()).update("cookbookName", name.getText().toString());
                                        cookbookRef.document(cb.getCookbookID()).update("cookbookDescription", des.getText().toString());
                                        dialog.cancel();
                                        posts.clear();
                                        if (getFragmentManager() != null) {
                                            getFragmentManager().beginTransaction().detach(CookbookInfoFragment.this).attach(CookbookInfoFragment.this).commit();
                                        }
                                    }
                                });
                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        System.out.println("Click cancel");
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();
                                return true;
                            case R.id.deleteCookbook:
                                System.out.println("CB: delete");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

    }

    private void getInfoCookbook() {
        try {
            cookbookRef.document(cookbookID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            list1 = (List<Map<String, Object>>) documentSnapshot.get("postlist");
                            int numberpost = list1.size();
                            String image = list1.get(0).get("postUrlImage").toString();
                            userName.setText(documentSnapshot.get("userName").toString());
                            title.setText(documentSnapshot.get("cookbookName").toString());
                            descriptopn.setText(documentSnapshot.get("cookbookDescription").toString());
                            cb = new Cookbook(documentSnapshot.getId().toString(), documentSnapshot.get("cookbookName").toString(), documentSnapshot.get("cookbookDescription").toString());
                            Picasso.get().load(documentSnapshot.get("userUrlImage").toString()).transform(new RoundedTransformation()).fit().centerCrop().into(imgUserImage);
                            numberPost.setText(numberpost + " công thức");
                            Picasso.get().load(image).fit().centerCrop().into(imgMain);
                            loadListPost(list1);
                        } else {
                            System.out.println("Error");
                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    private void loadListPost(List<Map<String, Object>> list1) {
        rv.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(lln);
        for (int i = 0; i < list1.size(); i++) {
            String postID = list1.get(i).get("postID").toString();
            String postRate = list1.get(i).get("postRate").toString();
            String postTitle = list1.get(i).get("postTitle").toString();
            String postUrlImage = list1.get(i).get("postUrlImage").toString();
            String userName = list1.get(i).get("userName").toString();
            Post post = new Post(postRate, userName, postID, postTitle, postUrlImage);
            posts.add(post);
        }
        CookbookListPostAdapter cookbookListPostAdapter = new CookbookListPostAdapter(getActivity(), posts);
        rv.setAdapter(cookbookListPostAdapter);
    }
}
