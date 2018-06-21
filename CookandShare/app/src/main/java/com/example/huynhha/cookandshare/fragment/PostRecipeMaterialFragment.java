package com.example.huynhha.cookandshare.fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.adapter.MaterialAdapter;
import com.example.huynhha.cookandshare.adapter.TopRecipeAdapter;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.Post;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostRecipeMaterialFragment extends Fragment {
    @BindView(R.id.cb_breakfast)
    CheckBox cb_breakfast;
    @BindView(R.id.cb_cookie)
    CheckBox cb_cookie;
    @BindView(R.id.cb_dessert)
    CheckBox cb_dessert;
    @BindView(R.id.cb_diet)
    CheckBox cb_diet;
    @BindView(R.id.cb_dinner)
    CheckBox cb_dinner;
    @BindView(R.id.cb_drink)
    CheckBox cb_drink;
    @BindView(R.id.cb_fast_and_easy)
    CheckBox cb_fast_and_easy;
    @BindView(R.id.cb_fast_food)
    CheckBox cb_fast_food;
    @BindView(R.id.cb_for_gym)
    CheckBox cb_for_gym;
    @BindView(R.id.cb_for_kid)
    CheckBox cb_for_kid;
    @BindView(R.id.cb_healthy)
    CheckBox cb_healthy;
    @BindView(R.id.cb_hotpot)
    CheckBox cb_hotpot;
    @BindView(R.id.cb_lunch)
    CheckBox cb_lunch;
    @BindView(R.id.cb_noodle)
    CheckBox cb_noodle;
    @BindView(R.id.cb_salad)
    CheckBox cb_salad;
    @BindView(R.id.cb_sauce)
    CheckBox cb_sauce;
    @BindView(R.id.cb_soup)
    CheckBox cb_soup;
    @BindView(R.id.cb_vegetable)
    CheckBox cb_vegetable;
    @BindView(R.id.btn_add_image)
    Button btn_add_image;
    @BindView(R.id.img_recipe)
    ImageView img_recipe;
    @BindView(R.id.edt_recipe_name)
    EditText edt_recipe_name;
    @BindView(R.id.edt_recipe_description)
    EditText edt_recipe_description;
    @BindView(R.id.rl_difficult)
    RelativeLayout rl_difficult;
    @BindView(R.id.tv_difficult)
    TextView tv_difficult;
    @BindView(R.id.rl_time)
    RelativeLayout rl_time;
    @BindView(R.id.tv_time_cook)
    TextView tv_time_cook;
    @BindView(R.id.spinner_people)
    Spinner spinner_people_use;
    @BindView(R.id.edt_name_of_material)
    EditText edt_namme_of_material;
    @BindView(R.id.edt_quatity)
    EditText edt_quatity;
    @BindView(R.id.material_quantity_type)
    Spinner material_quantity_type;
    @BindView(R.id.btn_add_material)
    Button btn_add_material;
    @BindView(R.id.rc_material)
    RecyclerView rc_material;
    List<Material> materials = new ArrayList<>();
    private static final String[] number_of_people = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "10+"};

    public PostRecipeMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_recipe_material, container, false);
        ButterKnife.bind(this, view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, number_of_people);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_people_use.setAdapter(adapter);
        pickTime();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        addMaterial();

    }

    public void addMaterial() {
        btn_add_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materials.add(new Material("", edt_namme_of_material.getText().toString(), edt_quatity.getText().toString() + material_quantity_type.getSelectedItem().toString(), ""));
                importPostMaterial();

            }
        });
    }

    public void importPostMaterial() {
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rc_material.setLayoutManager(lln);
        MaterialAdapter materialAdapter = new MaterialAdapter(materials);
        rc_material.setAdapter(materialAdapter);
    }

    public void pickTime() {
        tv_time_cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tv_time_cook.setText(i + " hour " + i1 + " minute");
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
    }
}
