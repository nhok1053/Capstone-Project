package com.example.huynhha.cookandshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.huynhha.cookandshare.adapter.PagerAdapter;
import com.example.huynhha.cookandshare.adapter.PostRecipeTabLayoutAdapter;
import com.example.huynhha.cookandshare.adapter.PostStepAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.example.huynhha.cookandshare.fragment.PostRecipeMaterialFragment;
import com.example.huynhha.cookandshare.fragment.PostRecipeStepFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marlonmafra.android.widget.SegmentedTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Timer;
import java.util.TimerTask;

public class PostRecipe extends AppCompatActivity {

    ViewPager viewPager;
    Button btn_close_activity;
    Button btn_finish;
    private PostRecipeStepFragment postRecipeStepFragment;
    private PostRecipeMaterialFragment postRecipeMaterialFragment;
    public List<PostStep> postSteps;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference postRef = db.collection("Post");
    private CollectionReference reportRef = db.collection("Report");
    private CollectionReference userRef = db.collection("User");
    private CollectionReference commentRef = db.collection("Comment");

    final Post post = new Post();
    private int count = 0;
    private ProgressDialog progressDialog;
    private TabLayout tabLayout;
private  String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_post_recipe);
        viewPager = findViewById(R.id.view_post_recipe);
        btn_close_activity = findViewById(R.id.btn_close);
        btn_finish = findViewById(R.id.btn_add_recipe);
        tabLayout = findViewById(R.id.post_recipe_tab_layout);
        storageReference = FirebaseStorage.getInstance().getReference();
        getSupportActionBar().hide();
        setTabLayout();
        uuid = UUID.randomUUID().toString().replace("-", "");
        setPostListener(uuid, uuid);
        closeActivity();
    }

    private void setTabLayout() {
        postRecipeStepFragment = new PostRecipeStepFragment();
        postRecipeMaterialFragment = new PostRecipeMaterialFragment();
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(postRecipeMaterialFragment, "1. Nguyên liệu");
        pagerAdapter.addFragment(postRecipeStepFragment, "2. Bước thực hiện");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("check1", "onActivityResult: " + data);
        System.out.println(data.getStringExtra("position") + "  check position");
        if (requestCode == 2) {
            postRecipeStepFragment.onActivityResult(requestCode, resultCode, data);

        }
        if (requestCode == 3) {
            postRecipeMaterialFragment.onActivityResult(requestCode, resultCode, data);

        }
    }


    public void closeActivity() {
        btn_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostRecipe.this.finish();
            }
        });
    }

    public List<PostStep> getStepImgUrlFromStorage(String postID, int size, final List<PostStep> postSteps) {

        for (int i = 0; i <= postSteps.size(); i++) {
            final int a = 0;
            storageReference.child("images/" + postID + (i + 1) + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    postSteps.get(a).setImgURL(uri.toString());
                    System.out.println("IMG2" + uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e);
                }
            });
        }
        return postSteps;
    }

    public void setPostListener(final String postID, final String userID) {
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSteps = postRecipeStepFragment.a();
                startPushing(postID);

            }
        });
    }

    private void startPushing(String postID) {
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        pushRecipeImageToFireStore(postRecipeMaterialFragment.getImageUri(), postID);
    }

    private void pushRecipeImageToFireStore(final Uri uri, final String postId) {
//
        StorageReference childStore = storageReference.child("images/" + postId + ".jpg");
        childStore.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Uploaded", "onSuccess: " + uri.toString());
                        post.setUrlImage(uri.toString());
                        pushStepImageToFireStorage(postId);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void pushStepImageToFireStorage(final String postId) {
        StorageReference childStore = storageReference.child("images/" + postId + count + ".jpg");
        childStore.putFile(Uri.parse(postSteps.get(count).getUri())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Uploaded", "onSuccess: " + uri.toString());
                        postSteps.get(count).setImgURL(uri.toString());
                        count++;
                        if (count < (postSteps.size())) {
                            pushStepImageToFireStorage(postId);
                            System.out.println("Count step : "+count);

                        }
                        if (count == postSteps.size()) {
                            System.out.println("Count" + count);
                            post.setPostID(postId);
                            post.setTitle(postRecipeMaterialFragment.getRecipeTitle());
                            post.setDescription(postRecipeMaterialFragment.getDescription());
                            post.setMaterials(postRecipeMaterialFragment.getMaterial());
                            System.out.println("Material:" + postRecipeMaterialFragment.getMaterial());
                            post.setTime(postRecipeMaterialFragment.getDuration());
                            post.setComment(0);
                            post.setLike(0);
                            post.setNumberOfPeople("0");
                            try {
                                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                post.setUserID(currentFirebaseUser.getUid());
                                post.setUserName(currentFirebaseUser.getDisplayName());
                                post.setUserImgUrl(currentFirebaseUser.getPhotoUrl().toString());
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                            final String date = df.format(Calendar.getInstance().getTime());
                            post.setPostTime(date.toString());
                            post.setNumberOfRate("3");
                            post.setDifficult("Dễ");
                            post.setPostSteps(postSteps);
                            loadData(post);
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void loadData(final Post post) {
        postRef.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Map<String, Object> data = new HashMap<>();
                data.put("postID", uuid);
                reportRef.add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        System.out.println("Report add success!");
                    }
                });
                commentRef.add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        System.out.println("Comment add success!");

                    }
                });

                Log.d("Posted", "onSuccess: ");
                progressDialog.dismiss();
                Toast.makeText(PostRecipe.this, "Post Recipe Success", Toast.LENGTH_SHORT).show();
                PostRecipe.this.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
