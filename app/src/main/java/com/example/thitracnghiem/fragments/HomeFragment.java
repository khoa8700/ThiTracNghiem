package com.example.thitracnghiem.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thitracnghiem.activity.adapters.ScoreAdapter;
import com.example.thitracnghiem.R;
import com.example.thitracnghiem.model.Diem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private View v;
    private List<Double> mainList=new ArrayList<>();
    private ScoreAdapter adapter;
    private RecyclerView rv;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_home, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        rv=v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter=new ScoreAdapter(mainList);
        rv.setAdapter(adapter);
        firebaseFirestore.collection("HighScore").document(firebaseAuth.getCurrentUser().getUid())
                .collection("highscores").orderBy("diem", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                Diem scoreNoww=doc.getDocument().toObject(Diem.class);
                                Double scoreNow=scoreNoww.getDiem();
                                mainList.add(scoreNow);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        return v;
    }
}