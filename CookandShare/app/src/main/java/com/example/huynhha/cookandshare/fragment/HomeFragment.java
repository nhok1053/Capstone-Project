package com.example.huynhha.cookandshare.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.TopAttributeAdapter;
import com.example.huynhha.cookandshare.adapter.TopPostAdapter;
import com.example.huynhha.cookandshare.adapter.TopRecipeAdapter;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //    @BindView(R.id.rvChef)
    RecyclerView rvChef;
    //    @BindView(R.id.rvRecipe)
    RecyclerView rvRecipe;
    //    @BindView(R.id.rvPost)
    RecyclerView rvPost;

    public FrameLayout frameLayout;

    public HomeFragment() {
        // Required empty public constructor
    }
    ArrayList<Post> posts=new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("Post/KTAzBIZWafjsve4F9p4U");
    private CollectionReference notebookRef = db.collection("Post");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,new LoginFragment());
//        transaction.commit();
        rvPost = v.findViewById(R.id.rvPost);
        rvChef = v.findViewById(R.id.rvChef);
        rvRecipe = v.findViewById(R.id.rvRecipe);
//        ButterKnife.bind(v);
        notebookRef.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                try{
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Post post=documentSnapshot.toObject(Post.class);
                    post.setPostID(documentSnapshot.get("userID").toString());
                    int x=post.getLike();
                    int y=post.getComment();
                    posts.add(post);
                }}
                catch (Exception ex){

                }
            }
        });

        importTopAttribute();
        importTopRecipes();
        return v;
    }



    @Override
    public void onStart() {
        super.onStart();
        importTopPost();

    }

    public void importTopPost() {
        rvPost.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rvPost.setLayoutManager(lln);
        TopPostAdapter postAdapter = new TopPostAdapter(posts);
        rvPost.setAdapter(postAdapter);
    }

    public void importTopAttribute() {
        rvChef.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvChef.setLayoutManager(lln);
        TopAttributeAdapter postAdapter = new TopAttributeAdapter((ArrayList<User>) User.initDataToTopAttribute());
        rvChef.setAdapter(postAdapter);
    }

    public void importTopRecipes() {
        rvRecipe.setNestedScrollingEnabled(false);
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvRecipe.setLayoutManager(lln);
        TopRecipeAdapter postAdapter = new TopRecipeAdapter((ArrayList<Post>) Post.initDataToTopRecipe());
        rvRecipe.setAdapter(postAdapter);
    }

}
