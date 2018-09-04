package com.example.huynhha.cookandshare.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;
import com.example.huynhha.cookandshare.Validate.ValidateFunction;
import com.example.huynhha.cookandshare.adapter.PostStepAdapter;
import com.example.huynhha.cookandshare.entity.PostStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostRecipeStepFragment extends Fragment {

    private RecyclerView rc_postStep;
    List<PostStep> postSteps = new ArrayList<>();
    private Button btn_add_step;
    private int clickPosition;
    private PostStepAdapter postStepAdapter;

    public PostRecipeStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_recipe_step, container, false);
        rc_postStep = view.findViewById(R.id.rc_post_step);
        btn_add_step = view.findViewById(R.id.btn_add_step);
        postSteps.add(new PostStep("", "",  "", "", "",""));
        importPostStep();
        addStep();
        return view;
    }

    public void valid(){
        validStep();
    }

    public void importPostStep() {
        LinearLayoutManager lln = new LinearLayoutManager(this.getActivity());
        rc_postStep.setNestedScrollingEnabled(false);
        rc_postStep.setLayoutManager(lln);
        postStepAdapter = new PostStepAdapter(getActivity(), postSteps);
        postStepAdapter.setOnItemStepClick(new OnItemApdaterClicked());
        rc_postStep.setAdapter(postStepAdapter);

    }


    public void addStep() {
        btn_add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addPostList().size() == 0) {
                    postSteps.add(new PostStep("", "", "", "", "", ""));
                    importPostStep();
                } else {
                    if (addPostList().get(addPostList().size() - 1).getUri().trim().length() == 0 || addPostList().get(addPostList().size() - 1).getDescription().trim().length() == 0
                            ) {
                        Toast.makeText(getActivity(), "Trước khi thêm bước mới, hãy điền hết thông tin của bước hiện tại!!!", Toast.LENGTH_LONG).show();
                    } else {
                        postSteps.add(new PostStep("", "", "", "", "", ""));
                        importPostStep();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Test 1", "onActivityResult: " + requestCode + "   " + resultCode + "" + data);
        System.out.println(data.getStringExtra("position") + "  check position");
        PostStep postStep = postSteps.get(clickPosition);
        postStep.setUri("" + data.getData());
        importPostStep();
    }

    public class OnItemApdaterClicked implements PostStepAdapter.OnItemStepClick {
        @Override
        public void onClick(int postion) {
            clickPosition = postion;
            Toast.makeText(getContext(), "postion: " + clickPosition, Toast.LENGTH_SHORT).show();
        }
    }

    public class OnSendData implements PostStepAdapter.OnPostSend {
        @Override
        public void onClick(List<PostStep> postStep) {
            postSteps = postStep;
        }
    }

    public List<PostStep> addPostList() {
        List<PostStep> postStepList = new ArrayList<>();
        postStepAdapter = new PostStepAdapter(getActivity(), postSteps);
        postStepList = postStepAdapter.getPostSteps();
        return postStepList;
    }
    public void validStep() {
        Context context = getActivity();
        ValidateFunction vf = new ValidateFunction();
        EditText edtStepRes = getView().findViewById(R.id.etAddNewCookbookName);
        EditText edtStepTime = getView().findViewById(R.id.etEditCookbookEditDes);
        EditText edtStepTemp = getView().findViewById(R.id.etAddNewCookbookDes);
        if (!vf.checkPostStepDescription(edtStepRes.getText().toString())) {
            Toast.makeText(context, "Mô tả bước công thức không được để trống và phải ít hơn 200 kí tự!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!vf.checkPostStepTime(edtStepTime.getText().toString())) {
            Toast.makeText(context, "Thời gian bước không được trống!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!vf.checkPostStepTemperature(edtStepTemp.getText().toString())) {
            Toast.makeText(context, "Nhiệt độ không đúng tiêu chuẩn!!!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            final Map<String, Object> step = new HashMap<>();
            step.put("description", edtStepRes.getText().toString());
            step.put("time", edtStepTime.getText().toString());
            step.put("temp", edtStepTemp.getText().toString());
            Toast.makeText(context, "Thêm bước thành công!!!", Toast.LENGTH_SHORT).show();
        }
    }

}
