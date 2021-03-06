package com.example.huynhha.cookandshare.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.Validate.ValidateFunction;
import com.example.huynhha.cookandshare.adapter.MaterialAdapter;
import com.example.huynhha.cookandshare.entity.Comment;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;
import com.example.huynhha.cookandshare.entity.PostStep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditMaterialFragment extends Fragment {
    private CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post");
    private CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
    private Post post;
    private List<Map<String, Object>> listMaterials;
    private ArrayList<Material> materials;
    public ImageView imgEditMaterials;
    public EditText edtEditRecipeName;
    public EditText edtEditDescription;
    public RecyclerView rcEditMaterials;
    public EditText edtEditNameMaterials;
    public EditText edtEditQuatityMaterials;
    public Spinner spinnerEditMaterials;
    private String postID = "";
    private MaterialAdapter materialAdapter;
    private Button btnAddEditMaterials;
    private int count = 0;
    private String urlImage = "";
    private String documentID = "";

    public EditMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_material, container, false);
        setUp(v);
        postID = getActivity().getIntent().getExtras().getString("postID");
        System.out.println("POSTIDDD: " + postID);
        loadData();
        addMaterial();
        return v;
    }

    public void calValidate(){
        validateMaterial();
    }

    public List<Material> getMaterial() {
        return materials;
    }

    public String getDescription() {
        String description = edtEditDescription.getText().toString();
        return description;
    }

    public String getRecipeTitle() {
        String title = edtEditRecipeName.getText().toString();
        return title;
    }

    public String getImgUrl() {
        return urlImage;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setUp(View v) {
        listMaterials = new ArrayList<>();
        materials = new ArrayList<>();
        imgEditMaterials = v.findViewById(R.id.img_edit_recipe);
        edtEditRecipeName = v.findViewById(R.id.edt_edit_recipe_name);
        edtEditDescription = v.findViewById(R.id.edt__edit_recipe_description);
        rcEditMaterials = v.findViewById(R.id.rc_edit_material);
        edtEditNameMaterials = v.findViewById(R.id.edt_edit_name_of_material);
        edtEditQuatityMaterials = v.findViewById(R.id.edt_edit_quatity);
        btnAddEditMaterials = v.findViewById(R.id.btn_edit_add_material);
        spinnerEditMaterials = v.findViewById(R.id.material_edit_quantity_type);

    }

    public void loadData() {
        postRef.whereEqualTo("postID", postID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        documentID = queryDocumentSnapshot.getId();
                        Post post = new Post();
                        post.setUrlImage(queryDocumentSnapshot.getString("urlImage"));
                        urlImage = queryDocumentSnapshot.getString("urlImage");
                        post.setTitle(queryDocumentSnapshot.getString("title"));
                        post.setDescription(queryDocumentSnapshot.getString("description"));
                        listMaterials = (List<Map<String, Object>>) queryDocumentSnapshot.get("materials");
                        for (int i = 0; i < listMaterials.size(); i++) {
                            Material material = new Material();
                            material.setMaterialName(listMaterials.get(i).get("materialName").toString());
                            material.setQuantity(listMaterials.get(i).get("quantity").toString());
                            materials.add(material);
                            count++;
                        }
                        if (count == listMaterials.size()) {
                            loadDataToView(post);
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


    public void addMaterial() {
        btnAddEditMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEditNameMaterials.getText().toString().trim().length() == 0 || edtEditQuatityMaterials.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Tên nguyên liệu và số lượng không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    materials.add(new Material("", edtEditNameMaterials.getText().toString(), edtEditQuatityMaterials.getText().toString() + spinnerEditMaterials.getSelectedItem().toString(), ""));
                    importPostMaterial();
                    edtEditNameMaterials.setText("");
                    edtEditQuatityMaterials.setText("");
                }
            }
        });
    }

    public void importPostMaterial() {
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rcEditMaterials.setLayoutManager(lln);
        MaterialAdapter materialAdapter = new MaterialAdapter(materials, 1);
        rcEditMaterials.setAdapter(materialAdapter);
    }

    public void loadDataToView(Post post) {
        Picasso.get().load(post.getUrlImage().toString()).centerCrop().fit().into(imgEditMaterials);
        edtEditRecipeName.setText(post.getTitle().toString());
        edtEditDescription.setText(post.getDescription().toString());
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rcEditMaterials.setLayoutManager(lln);
        materialAdapter = new MaterialAdapter(materials, 1);
        rcEditMaterials.setAdapter(materialAdapter);
    }

    public void validateMaterial() {
        ValidateFunction validateFunction = new ValidateFunction();
        boolean valid = validateFunction.checkEditEditTextMaterials(edtEditNameMaterials.getText().toString(), edtEditQuatityMaterials.getText().toString());
        if (!valid) {
            Toast.makeText(getActivity(), "Tên nguyên liệu và số lượng không được để trống!!!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            importPostMaterial();
        }
    }
}
