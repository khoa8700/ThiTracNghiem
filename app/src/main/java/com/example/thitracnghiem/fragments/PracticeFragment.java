package com.example.thitracnghiem.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thitracnghiem.PracticeActivity;
import com.example.thitracnghiem.R;


public class PracticeFragment extends Fragment {
    private View v;
    private Button btnPrac;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_practice, container, false);
        btnPrac=v.findViewById(R.id.btn_practice);
        btnPrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(v.getContext(), PracticeActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        return v;
    }
}