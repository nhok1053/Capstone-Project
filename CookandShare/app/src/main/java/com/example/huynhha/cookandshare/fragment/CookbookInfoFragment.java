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
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    TextView tvUserName;
    @BindView(R.id.tvCookbookInfoCookbookTitle)
    TextView tvTitle;
    @BindView(R.id.tvCookbookInfoNumberPost)
    TextView tvNumberPost;
    @BindView(R.id.tvCookbookInfoCookbookDescription)
    TextView tvDescriptopn;
    @BindView(R.id.rvCookbookInfoListPostCookbook)
    RecyclerView rv;
    @BindView(R.id.btnFragmentCookbookInfoMore)
    Button btnMore;
    Cookbook cb;
    private ArrayList<String> listpost;
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
                            listpost = (ArrayList<String>) documentSnapshot.get("postlist");
                            int numberpost = listpost.size();
                            final String[] image = new String[1];
                            MainActivity.db.collection("Post").whereEqualTo("postID", listpost.get(0).toString()).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot querySnapshot = task.getResult();
                                        if (task.getResult() != null) {
                                            for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot) {
                                                image[0] = queryDocumentSnapshot.get("urlImage").toString();
                                            }
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                            final String[] userName = new String[1];
                            final String[] userUrlImage = new String[1];
                            MainActivity.db.collection("User").whereEqualTo("userID", documentSnapshot.get("userID")).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot querySnapshot = task.getResult();
                                        if (task.getResult() != null) {
                                            for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot) {
                                                userName[0] = queryDocumentSnapshot.get("firstName").toString();
                                                userUrlImage[0] = queryDocumentSnapshot.get("imgUrl").toString();
                                            }
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });

                            tvUserName.setText(userName[0]);
                            tvTitle.setText(documentSnapshot.get("cookbookName").toString());
                            tvDescriptopn.setText(documentSnapshot.get("cookbookDescription").toString());
                            tvNumberPost.setText(numberpost + "công thức");
//                            cb = new Cookbook(documentSnapshot.getId().toString(), documentSnapshot.get("cookbookName").toString(), documentSnapshot.get("cookbookDescription").toString());
                            Picasso.get().load(userUrlImage[0]).transform(new RoundedTransformation()).fit().centerCrop().into(imgUserImage);
                            Picasso.get().load(image[0]).fit().centerCrop().into(imgMain);
                            loadListPost(listpost);
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

    private void loadListPost(ArrayList<String> list) {
        rv.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(lln);
//        for (int i = 0; i < list1.size(); i++) {
//            String postID = list1.get(i).get("postID").toString();
//            String postRate = list1.get(i).get("postRate").toString();
//            String postTitle = list1.get(i).get("postTitle").toString();
//            String postUrlImage = list1.get(i).get("postUrlImage").toString();
//            String userName = list1.get(i).get("userName").toString();
//            Post post = new Post(postRate, userName, postID, postTitle, postUrlImage);
//            posts.add(post);
//        }
        for (String lst : list) {
            MainActivity.db.collection("Post").whereEqualTo("postID", lst).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot) {
                            String postID = queryDocumentSnapshot.get("postID").toString();
                            String postRate = queryDocumentSnapshot.get("numberOfRate").toString();
                            String postTitle = queryDocumentSnapshot.get("title").toString();
                            String postUrlImage = queryDocumentSnapshot.get("urlImage").toString();
                            String userID = queryDocumentSnapshot.get("userID").toString();
                            final String[] userName = new String[1];
                            MainActivity.db.collection("User").whereEqualTo("userID", userID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot querySnapshot1 = task.getResult();
                                        for (QueryDocumentSnapshot queryDocumentSnapshot1 : querySnapshot1) {
                                            userName[0] = queryDocumentSnapshot1.get("firstName").toString();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                            Post post = new Post(postRate, userName[0], postID, postTitle, postUrlImage);
                            posts.add(post);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        CookbookListPostAdapter cookbookListPostAdapter = new CookbookListPostAdapter(getActivity(), posts);
        rv.setAdapter(cookbookListPostAdapter);
    }
}
