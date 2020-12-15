package com.example.thitracnghiem.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thitracnghiem.activity.adapters.CustomAdapter;
import com.example.thitracnghiem.activity.adapters.ScoreAdapter;
import com.example.thitracnghiem.R;
import com.example.thitracnghiem.model.CustomItem;
import com.example.thitracnghiem.model.Diem;
import com.example.thitracnghiem.model.HighScore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private View v;
    private ScoreAdapter adapter;
    private RecyclerView rv;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    Spinner customSpinner;
    ArrayList<CustomItem> customList;
    private String dethi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_home, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        customList=getCustomList();
        CustomAdapter adapterC=new CustomAdapter(v.getContext(),customList);
        customSpinner=v.findViewById(R.id.dropList);
        if (customSpinner!=null) {
            customSpinner.setAdapter(adapterC);
            customSpinner.setOnItemSelectedListener(this);
        }

        return v;
    }

    private ArrayList<CustomItem> getCustomList() {
        customList=new ArrayList<>();
        customList.add(new CustomItem("LuyThua"));
        customList.add(new CustomItem("Loga"));
        customList.add(new CustomItem("HamsoLoga"));
        return customList;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CustomItem item=(CustomItem)adapterView.getSelectedItem();
        final List<HighScore> mainList=new ArrayList<>();
        dethi=item.getSpinnerItemName();
        firebaseFirestore.collection("HighScore")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(final DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                firebaseFirestore.collection("HighScore").document(doc.getDocument().toObject(HighScore.class).getId())
                                        .collection(dethi)
                                        .orderBy("diem",Query.Direction.DESCENDING)
                                        .limit(1)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                for(DocumentChange doc1:value.getDocumentChanges()){
                                                    if(doc1.getType()==DocumentChange.Type.ADDED){
                                                        HighScore cur=doc1.getDocument().toObject(HighScore.class);
                                                        HighScore cur1=doc.getDocument().toObject(HighScore.class);
                                                        Double scoreNow=cur.getDiem();
                                                        String idNow=cur1.getId();
                                                        mainList.add(new HighScore(scoreNow,idNow));
                                                        Collections.sort(mainList, new Comparator<HighScore>() {
                                                            @Override
                                                            public int compare(HighScore h1, HighScore h2) {
                                                                return -Integer.valueOf((int) h1.getDiem()).compareTo(Integer.valueOf((int) h2.getDiem()));
                                                            }
                                                        });
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
        rv=v.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter=new ScoreAdapter(mainList);
        rv.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}