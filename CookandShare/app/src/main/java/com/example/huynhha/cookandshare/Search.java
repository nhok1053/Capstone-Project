package com.example.huynhha.cookandshare;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huynhha.cookandshare.adapter.PostsListAdapter;
import com.example.huynhha.cookandshare.adapter.UsersListAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.example.huynhha.cookandshare.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.marlonmafra.android.widget.SegmentedTab;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {
    private static final String TAG = "SEARCH_TAG";
    ViewPager viewPager;
    Button btn_close_activity;
    Button btn_title,btn_user,btn_tag,btn_material;
    private List<Post> postsList;
    private List<Post> postListData;
    private List<User> userList;
    private List<User> userListData;
    private PostsListAdapter postsListAdapter;
    private UsersListAdapter usersListAdapter;
    private EditText mSearchField;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    final Post post = new Post();
    private int count = 0;
    private RecyclerView mResultList;
    private ProgressDialog progressDialog;
    String postID, userID, time, imgUrl, title,titleLower, description, userImgUrl;
    String dateOfBirth, firstName, firstNameLower, secondName, phone, sex;
    int like, comment;
    private String strCheckBtnClickValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog =new ProgressDialog(this);
        setContentView(R.layout.activity_search);
        btn_close_activity = findViewById(R.id.btn_close);
        btn_title = findViewById(R.id.btn_title);
        btn_user = findViewById(R.id.btn_user);
        btn_tag =findViewById(R.id.btn_tag);
        btn_material = findViewById(R.id.btn_material);
        mSearchField = (EditText) findViewById(R.id.search_field);
        mResultList = (RecyclerView) findViewById(R.id.result_list);
        strCheckBtnClickValue = "Title";
        postsList = new ArrayList<>();
        postListData = new ArrayList<>();
        userList = new ArrayList<>();
        userListData = new ArrayList<>();
        postsListAdapter = new PostsListAdapter(postsList,this);
        usersListAdapter = new UsersListAdapter(userList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        storageReference = FirebaseStorage.getInstance().getReference();
        getSupportActionBar().hide();
        addPostDataToList();
        addUserDataToList();
        searchButtonActivity();
        searchTextFieldChange();
        closeActivityListener();
    }

    public void closeActivityListener() {
        btn_close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.this.finish();
            }
        });
    }

    public void searchButtonActivity(){

        btn_title.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                strCheckBtnClickValue = "Title";
                mResultList.setAdapter(postsListAdapter);
                String searchText = mSearchField.getText().toString();
                setButtonBackground();
                btn_title.setBackground(getDrawable(R.drawable.button_background_grey));
                firestorePostSearch(searchText);

            }
        });
        btn_user.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                strCheckBtnClickValue = "User";
                mResultList.setAdapter(usersListAdapter);
                setButtonBackground();
                btn_user.setBackground(getDrawable(R.drawable.button_background_grey));
                String searchText = mSearchField.getText().toString();
                firestoreUserSearch(searchText);
            }
        });
        btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strCheckBtnClickValue = "Tag";
            }
        });
        btn_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strCheckBtnClickValue = "Material";
            }
        });
    }

    public String covertStringToUnsigned(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addPostDataToList(){
        db.collection("Post")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            postID = documentSnapshot.get("postID").toString();
                            userID = documentSnapshot.get("time").toString();
                            time = documentSnapshot.get("time").toString();
                            imgUrl = documentSnapshot.get("urlImage").toString();
                            title = documentSnapshot.get("title").toString();
                            description = documentSnapshot.get("description").toString();
                            userImgUrl = documentSnapshot.get("userImgUrl").toString();
                            like = Integer.parseInt(documentSnapshot.get("like").toString());
                            comment = Integer.parseInt(documentSnapshot.get("comment").toString());
                            titleLower = covertStringToUnsigned(title);
                            Post post = new Post(postID, userID, time, imgUrl, title,titleLower, description, userImgUrl, like, comment);
                            postListData.add(post);
                            Log.d(TAG,"Post Title: "+ title);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    public void addUserDataToList(){
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            userID = documentSnapshot.get("userID").toString();
                            dateOfBirth = documentSnapshot.get("dateOfBirth").toString();
                            firstName = documentSnapshot.get("firstName").toString();
                            secondName = documentSnapshot.get("secondName").toString();
                            userImgUrl = documentSnapshot.get("imgUrl").toString();
                            sex = documentSnapshot.get("sex").toString();
                            phone = documentSnapshot.get("phone").toString();
                            firstNameLower = covertStringToUnsigned(firstName);
                            User user = new User(userID, userImgUrl, phone, sex, firstName,firstNameLower, secondName, dateOfBirth);
                            userListData.add(user);
                            Log.d(TAG,"User first name: "+ firstName);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    private void firestorePostSearch(String searchText) {
        postsList.clear();
        //notification when click search button
        Toast.makeText(Search.this, "Started Search", Toast.LENGTH_LONG).show();
        searchText = covertStringToUnsigned(searchText);
        int searchListLength = postListData.size();
        for (int i = 0; i < searchListLength; i++) {
            if (postListData.get(i).getTitleLower().contains(searchText)) {
                postsList.add(postListData.get(i));
                postsListAdapter.notifyDataSetChanged();
            }
        }
    }
    private void firestoreUserSearch(String searchText) {
        userList.clear();
        //notification when click search button
        Toast.makeText(Search.this, "Started Search", Toast.LENGTH_LONG).show();
        searchText = covertStringToUnsigned(searchText);
        int searchListLength = userListData.size();
        for (int i = 0; i < searchListLength; i++) {
            if (userListData.get(i).getFirstNameLower().contains(searchText)) {
                userList.add(userListData.get(i));
                usersListAdapter.notifyDataSetChanged();
            }
        }
    }

    @SuppressLint("NewApi")
    public void setButtonBackground(){
        btn_title.setBackground(getDrawable(R.drawable.button_background));
        btn_material.setBackground(getDrawable(R.drawable.button_background));
        btn_tag.setBackground(getDrawable(R.drawable.button_background));
        btn_user.setBackground(getDrawable(R.drawable.button_background));
    }




    private void searchTextFieldChange(){
       mSearchField.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               postsList.clear();
               userList.clear();
           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               String searchText = mSearchField.getText().toString();
               if(searchText.equals("")){
                   postsList.clear();
                   userList.clear();

               }else{
                   if(strCheckBtnClickValue.equals("Title")){
                       postsList.clear();
                       mResultList.setAdapter(postsListAdapter);
                       firestorePostSearch(searchText);
                   }
                   if(strCheckBtnClickValue.equals("User")){
                       userList.clear();
                       mResultList.setAdapter(usersListAdapter);
                       firestoreUserSearch(searchText);
                   }
               }

           }
       });
    }

}
