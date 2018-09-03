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
import android.widget.TextView;
import android.widget.Toast;


import com.example.huynhha.cookandshare.adapter.PagerAdapter;
import com.example.huynhha.cookandshare.adapter.PostRecipeTabLayoutAdapter;
import com.example.huynhha.cookandshare.adapter.PostStepAdapter;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.NotificationDetails;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marlonmafra.android.widget.SegmentedTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private List<Map<String, Object>> list1;
    private List<Integer> listCategory;
    private List<String> listPostID;
    private List<String> listUserID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference postRef = db.collection("Post");
    private CollectionReference reportRef = db.collection("Report");
    private CollectionReference commentRef = db.collection("Comment");
    private CollectionReference categoryRef = db.collection("Category");
    private CollectionReference followerRef = db.collection("Follow");
    private CollectionReference notiRef = db.collection("Notification");
    private FirebaseAuth firebaseAuth;
    private List<Map<String, Object>> listNoti;
    private ArrayList<NotificationDetails> listNotiDetails;
    final Post post = new Post();
    private int count = 0;
    private int countNoti = 0;
    private ProgressDialog progressDialog;
    private TabLayout tabLayout;
    private String uuid;
    private String documentNoti = "";
    @ServerTimestamp
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_post_recipe);
        viewPager = findViewById(R.id.view_post_recipe);
        btn_close_activity = findViewById(R.id.btn_close);
        btn_finish = findViewById(R.id.btn_add_recipe);
        tabLayout = findViewById(R.id.post_recipe_tab_layout);
        listCategory = new ArrayList<>();
        listPostID = new ArrayList<>();
        listUserID = new ArrayList<>();
        listNoti = new ArrayList<>();
        listNotiDetails = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        getSupportActionBar().hide();
        setTabLayout();
        date = new Date();
        uuid = UUID.randomUUID().toString().replace("-", "");
        setPostListener(uuid, uuid);
        closeActivityListener();
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
        if (data != null) {
            Log.d("check1", "onActivityResult: " + data);
            System.out.println(data.getStringExtra("position") + "  check position");
            if (requestCode == 2) {
                postRecipeStepFragment.onActivityResult(requestCode, resultCode, data);
            }
            if (requestCode == 3) {
                postRecipeMaterialFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    public void closeActivityListener() {
        btn_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostRecipe.this.finish();
            }
        });
    }


    public void setPostListener(final String postID, final String userID) {
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationSuccess()) {
                    postSteps = postRecipeStepFragment.addPostList();
                    startPushing(postID);
                }

            }
        });
    }

    private Boolean validationSuccess() {
        EditText name = findViewById(R.id.edt_recipe_name);
        EditText description = findViewById(R.id.edt_recipe_description);
        TextView timecook = findViewById(R.id.tv_time_cook);
        if (postRecipeMaterialFragment.getImageUri() == null) {
            Toast.makeText(getApplicationContext(), "Hãy chọn ảnh của món ăn!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.getText().toString().trim().length() == 0 || name.getText().toString().trim().length() > 50) {
            Toast.makeText(getApplicationContext(), "Tên của món ăn không được để trống và phải ít hơn 50 kí tự!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (description.getText().toString().trim().length() == 0 || description.getText().toString().trim().length() > 300) {
            Toast.makeText(getApplicationContext(), "Mô tả của món ăn không được để trống và phải ít hơn 300 kí tự!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (postRecipeMaterialFragment.getCategory().size() == 0) {
            Toast.makeText(getApplicationContext(), "Hãy chọn thể loại của món ăn!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (timecook.getText().toString().equalsIgnoreCase("0 hour 0 minute") ||
                timecook.getText().toString().equalsIgnoreCase("0p")) {
            Toast.makeText(getApplicationContext(), "Hãy nhập thời gian!!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (postRecipeMaterialFragment.getMaterial().size() == 0) {
            Toast.makeText(getApplicationContext(), "Phải có ít nhất một nguyên liệu!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (postRecipeStepFragment.addPostList().size() == 0) {
            Toast.makeText(getApplicationContext(), "Phải có ít nhất một bước!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        Boolean Check = true;
        for (int i = 0; i < postRecipeStepFragment.addPostList().size(); i++) {
            if (postRecipeStepFragment.addPostList().get(i).getUri().trim().length() == 0 ||
                    postRecipeStepFragment.addPostList().get(i).getDescription().trim().length() == 0
                    ) {
                Check = false;
                Toast.makeText(getApplicationContext(), "Ảnh, mô tả, nhiệt độ, thời gian của các bước thực hiện không được để trống!!!", Toast.LENGTH_SHORT).show();

            }
        }
        if (Check == false) {
            return false;
        }
        return true;
    }


    private void startPushing(String postID) {
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        pushRecipeImageToFireStore(postRecipeMaterialFragment.getImageUri(), postID);
    }

    private void pushRecipeImageToFireStore(final Uri uri, final String postId) {
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
                            System.out.println("Count step : " + count);

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
                            post.setNumberOfPeople("" + postRecipeMaterialFragment.getNumberOfPeople());
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
                            post.setNumberOfRate("0");
                            post.setDifficult(postRecipeMaterialFragment.getRecipeDifficult());
                            post.setPostSteps(postSteps);
                            post.setCountRate(0);
                            post.setCountView(0);
                            post.setNumberOfReported(0);
                            listCategory = postRecipeMaterialFragment.getCategory();
                            post.setListCategory(listCategory);
                            for (int i = 0; i < listCategory.size(); i++) {
                                categoryRef.whereEqualTo("categoryID", listCategory.get(i)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                String documentID = documentSnapshot.getId();
                                                try {
                                                    listPostID = (ArrayList<String>) documentSnapshot.get("postID");
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                if (listPostID == null) {
                                                    listPostID = new ArrayList<>();
                                                    listPostID.add(postId);

                                                } else {
                                                    listPostID.add(postId);
                                                }
                                                categoryRef.document(documentID).update("postID", listPostID);
                                                System.out.println("Add to category Success");
                                            }
                                        }
                                    }
                                });
                            }
                            System.out.println("List category:" + postRecipeMaterialFragment.getCategory());
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
                String documentID = documentReference.getId();
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("postTime", date);
                Map<String, Object> report = new HashMap<>();
                report.put("postID", uuid);
                report.put("numOfAcceptReport", 0);

                //   postRef.document(documentID).update("postTime",date);
                postRef.document(documentID).update(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                data.put("postID", uuid);
                reportRef.add(report).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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
                loadFollowUserIDList(post.getPostID());
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

    public void loadFollowUserIDList(final String postID) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        followerRef.whereEqualTo("userID", currentFirebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        try {
                            listUserID = (ArrayList<String>) documentSnapshot.get("follower");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (listUserID != null) {
                            if (listUserID.get(0) != null) {
                                for (int i = 0; i < listUserID.size(); i++) {
                                    System.out.println("recipe" + listUserID.get(i).toString());
                                    loadNotification(listUserID.get(i), postID);
                                }
                            }

                        }

                    }
                }

            }
        });
    }

    public void loadNotification(String userID, final String postID) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUser = firebaseAuth.getUid().toString();
        if (currentUser.equals(userID)) {
            System.out.println("Nothing");
        } else {
            System.out.println("UserID " + userID);
            notiRef.whereEqualTo("userID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String documentNoti = document.getId();
                            try {
                                listNoti = (List<Map<String, Object>>) document.get("notification");
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            if (listNoti == null) {
                                addNoti(documentNoti, postID);
                                System.out.println("recipe zo");
                                countNoti = 0;
                                System.out.println("Khong co noti");
                            } else {
                                for (int i = 0; i < listNoti.size(); i++) {
                                    NotificationDetails notificationDetails = new NotificationDetails();
                                    notificationDetails.setType(listNoti.get(i).get("type").toString());
                                    notificationDetails.setUserUrlImage(listNoti.get(i).get("userUrlImage").toString());
                                    notificationDetails.setPostID(listNoti.get(i).get("postID").toString());
                                    notificationDetails.setTime(listNoti.get(i).get("time").toString());
                                    notificationDetails.setContent(listNoti.get(i).get("content").toString());
                                    notificationDetails.setUserID(listNoti.get(i).get("userID").toString());
                                    listNotiDetails.add(notificationDetails);
                                    countNoti++;
                                    System.out.println("recipe countNoti " + countNoti + "= " + listNoti.size());
                                }

                            }
                            if (countNoti == listNoti.size()) {
                                System.out.println("recipe count" + countNoti + "= " + listNoti.size());
                                System.out.println("recipe count" + countNoti);
                                System.out.println("recipe zo2");
                                addNoti(documentNoti, postID);
                                countNoti = 0;
                            }

                        }

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    public void addNoti(final String documentID, String postID) {
        firebaseAuth = FirebaseAuth.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        final String date = df.format(Calendar.getInstance().getTime());
        Map<String, Object> updateNoti = new HashMap<>();
        updateNoti.put("postID", postID);
        updateNoti.put("time", date);
        updateNoti.put("type", 1);
        updateNoti.put("userID", firebaseAuth.getCurrentUser().getUid().toString());
        updateNoti.put("userUrlImage", firebaseAuth.getCurrentUser().getPhotoUrl().toString());
        updateNoti.put("content", firebaseAuth.getCurrentUser().getDisplayName().toString() + " đã thêm một công thức mới. Xem ngay nào.");
        if (listNoti == null) {
            listNoti = new ArrayList<>();
            listNoti.add(updateNoti);
        } else {
            listNoti.add(updateNoti);
        }
        System.out.println("check noti reicpe");
        notiRef.document(documentID).update("notification", listNoti);
    }
}

