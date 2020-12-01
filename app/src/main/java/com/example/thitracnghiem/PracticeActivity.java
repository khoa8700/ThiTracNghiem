package com.example.thitracnghiem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.thitracnghiem.activity.adapters.QuesAdapter;
import com.example.thitracnghiem.model.Question;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PracticeActivity extends AppCompatActivity {
    private RecyclerView rv;
    private QuesAdapter adapter;
    private List<Question> mainList=new ArrayList<>();
    private List<String> ans=new ArrayList<>();
    private ArrayList<String> index=new ArrayList<>();
    private int numQues=5;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        firebaseFirestore=FirebaseFirestore.getInstance();
        for(int i=0;i<numQues;++i){
            ans.add("X");
            index.add("Câu "+(i+1)+":");
        }
        rv=findViewById(R.id.rvPrac);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new QuesAdapter(mainList,ans,index);
        rv.setAdapter(adapter);
        firebaseFirestore.collection("dethi").document("LOP12")
                .collection("TOAN")
                .document("SoPhuc")
                .collection("CongTru")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED) {
                                Question cur = doc.getDocument().toObject(Question.class);
                                mainList.add(cur);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

}