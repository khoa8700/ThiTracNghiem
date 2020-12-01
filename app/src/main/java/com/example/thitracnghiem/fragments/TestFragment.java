package com.example.thitracnghiem.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thitracnghiem.R;
import com.example.thitracnghiem.TestActivity;

public class TestFragment extends Fragment {
    private View v;
    private Button btnStart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_test, container, false);
        btnStart=v.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(v.getContext(), TestActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        return v;


    }
}