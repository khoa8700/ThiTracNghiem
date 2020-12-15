package com.example.thitracnghiem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.thitracnghiem.activity.adapters.QuesPracAdapter;
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
    private QuesPracAdapter adapter;
    private List<Question> mainList=new ArrayList<>();
    private List<String> ans=new ArrayList<>();
    private ArrayList<Boolean> statusList=new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private String dethi;
    private String chuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_practice);
        firebaseFirestore=FirebaseFirestore.getInstance();

        if(getIntent().hasExtra("BienHinh")) {dethi=getIntent().getStringExtra("BienHinh");chuong="KhongGian";}
        if(getIntent().hasExtra("TheTich")){ dethi=getIntent().getStringExtra("TheTich");chuong="KhongGian";}
        if(getIntent().hasExtra("CongTru")){ dethi=getIntent().getStringExtra("CongTru");chuong="SoPhuc";}
        if(getIntent().hasExtra("LyThuyet")){ dethi=getIntent().getStringExtra("LyThuyet");chuong="SoPhuc";}

        firebaseFirestore.collection("dethi").document("LOP12")
                .collection("TOAN")
                .document(chuong)
                .collection(dethi)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED) {
                                Question cur = doc.getDocument().toObject(Question.class);
                                mainList.add(cur);
                                statusList.add(false);
                                ans.add("X");
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        rv=findViewById(R.id.rvPrac);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new QuesPracAdapter(mainList,ans,statusList);
        rv.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }

}