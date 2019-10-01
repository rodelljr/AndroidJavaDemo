package com.java.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.models.ResponseModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment {

    private static String MODEL_KEY = "model_key";
    private ArrayList<ResponseModel> models;

    public static MainFragment newInstance(ArrayList<ResponseModel> model) {
        MainFragment frag = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MODEL_KEY, model);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        models = getArguments().getParcelableArrayList(MODEL_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_frag_item_list, container, false);
        if(view instanceof RecyclerView) {
            Context myContext = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
            recyclerView.setAdapter(new DemoRecyclerViewAdapter(myContext, models));
        }
        return view;
    }
}
