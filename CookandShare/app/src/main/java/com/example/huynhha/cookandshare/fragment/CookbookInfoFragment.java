package com.example.huynhha.cookandshare.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.huynhha.cookandshare.CookBookActivity;
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
import com.google.firebase.auth.FirebaseAuth;
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
    @BindView(R.id.btnFragmentCookbookInfoClose)
    Button btnClose;
    @BindView(R.id.tvWarningCookbook)
    TextView tvWarning;
    Cookbook cb;
    private ArrayList<String> listpost;
    private ArrayList<Post> posts = new ArrayList<>();
    private String userID;
    private String userName;
    private String userUrlImage;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    private CookbookInfoFragment cookbookInfoFragment;
    private int count;

    public CookbookInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cookbook_info, container, false);
        ButterKnife.bind(this, v);
        cookbookInfoFragment = this;
        count = 0;
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getString("cookbookID") != null) {
                cookbookID = bundle.getString("cookbookID");
            } else {
                cookbookID = "";
            }
            if (bundle.getString("userID") != null) {
                userID = bundle.getString("userID");
            } else {
                userID = "";
            }
            if (bundle.getString("username") != null) {
                userName = bundle.getString("username");
            } else {
                userName = "";
            }
            if (bundle.getString("userimage") != null) {
                userUrlImage = bundle.getString("userimage");
            } else {
                userUrlImage = "";
            }
        }
        if (!userID.equals(currentUser)) {
            btnMore.setVisibility(View.GONE);
        }

        loadInfoCookbook();
        clickMore();
        close();
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
                                        if (name.getText().toString().trim().length() == 0 || name.getText().toString().trim().length() > 60) {
                                            Toast.makeText(getActivity(), "Tên cookbook không được để trống và phải ít hơn 60 kí tự!!!", Toast.LENGTH_SHORT).show();
                                        } else if (des.getText().toString().trim().length() == 0 || des.getText().toString().trim().length() > 200) {
                                            Toast.makeText(getActivity(), "Mô tả cookbook không được để trống và phải ít hơn 200 kí tự!!!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            cookbookRef.document(cb.getCookbookID()).update("cookbookName", name.getText().toString());
                                            cookbookRef.document(cb.getCookbookID()).update("cookbookDescription", des.getText().toString());
                                            dialog.cancel();
                                            posts.clear();
                                            if (getFragmentManager() != null) {
                                                getFragmentManager().beginTransaction().detach(CookbookInfoFragment.this).attach(CookbookInfoFragment.this).commit();
                                            }
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
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setTitle("Xóa Cookbook");
                                alert.setMessage("Bạn muốn xóa Cookbook?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                cookbookRef.document(cookbookID).delete();
                                                if (getFragmentManager() != null) {
                                                    getFragmentManager().beginTransaction().detach(CookbookInfoFragment.this).commit();
                                                    Toast.makeText(getActivity(), "Đã xóa Cookbook", Toast.LENGTH_SHORT).show();
                                                }
                                                getActivity().finish();
//                                                Toast.makeText(getActivity(), "Đã xóa Cookbook", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(), CookBookActivity.class);
                                                intent.putExtra("getUserID", currentUser);
                                                startActivity(intent);
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

    }

    private void loadInfoCookbook() {
        try {
            cookbookRef.document(cookbookID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            listpost = (ArrayList<String>) documentSnapshot.get("postlist");
                            int numberpost = listpost.size();
                            final String[] image = {"https://firebasestorage.googleapis.com/v0/b/capstone-project-1d078.appspot.com/o/cookbookdefault.jpg?alt=media&token=4e6740ba-acf1-46a8-a91d-0235943685a6"};
                            MainActivity.db.collection("Post").whereEqualTo("postID", listpost.get(0).toString())
                                    .limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                    count++;
                                    if (task1.isSuccessful()) {
                                        QuerySnapshot querySnapshot = task1.getResult();
                                        if (task1.getResult() != null) {
                                            for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot) {
                                                if (queryDocumentSnapshot.get("urlImage").toString() != null || !queryDocumentSnapshot.get("urlImage").toString().equals("")) {
                                                    image[0] = queryDocumentSnapshot.get("urlImage").toString();
                                                }

                                            }
                                        }
                                        Picasso.get().load(image[0]).fit().centerCrop().into(imgMain);
                                        loadListPost(listpost);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                            tvUserName.setText(userName);
                            Picasso.get().load(userUrlImage).transform(new RoundedTransformation()).fit().centerCrop().into(imgUserImage);
                            tvTitle.setText(documentSnapshot.get("cookbookName").toString());
                            tvDescriptopn.setText(documentSnapshot.get("cookbookDescription").toString());
                            tvNumberPost.setText(numberpost + " công thức");
                            cb = new Cookbook(documentSnapshot.getId().toString(), documentSnapshot.get("cookbookName").toString(), documentSnapshot.get("cookbookDescription").toString());
//                            loadListPost(listpost);
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

    private void close() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment(cookbookInfoFragment);
            }
        });
    }

    public void removeFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

    }

    private void loadListPost(final ArrayList<String> list) {
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
                    final int[] cnt = {0};
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot) {
                            final String postID = queryDocumentSnapshot.get("postID").toString();
                            final String postRate = queryDocumentSnapshot.get("numberOfRate").toString();
                            final String postTitle = queryDocumentSnapshot.get("title").toString();
                            final String postUrlImage = queryDocumentSnapshot.get("urlImage").toString();
                            final String userIDOfPost = queryDocumentSnapshot.get("userID").toString();
                            final String[] userNameOfRecipe = new String[1];
                            MainActivity.db.collection("User").whereEqualTo("userID", userIDOfPost).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    cnt[0]++;
                                    if (task.isSuccessful()) {
                                        QuerySnapshot querySnapshot1 = task.getResult();
                                        for (QueryDocumentSnapshot queryDocumentSnapshot1 : querySnapshot1) {
                                            userNameOfRecipe[0] = queryDocumentSnapshot1.get("firstName").toString();
                                        }

                                        Post post = new Post(postRate, userNameOfRecipe[0], postID, postTitle, postUrlImage);
                                        post.setUserID(userIDOfPost);
                                        posts.add(post);
                                    }
                                    if (cnt[0] == task.getResult().size()) {
                                        CookbookListPostAdapter cookbookListPostAdapter = new CookbookListPostAdapter(getActivity(), posts, cookbookID, userIDOfPost, userNameOfRecipe[0], userUrlImage, userID, userName);
                                        rv.setAdapter(cookbookListPostAdapter);
                                        //check post da bi xoa
                                        if (posts.size() != list.size()) {
                                            tvWarning.setVisibility(View.VISIBLE);
                                        } else if (posts.size() == list.size()) {
                                            tvWarning.setVisibility(View.GONE);
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
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }
}
