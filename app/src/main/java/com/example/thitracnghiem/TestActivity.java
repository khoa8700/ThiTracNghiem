package com.example.thitracnghiem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.example.thitracnghiem.activity.adapters.QuesAdapter;
import com.example.thitracnghiem.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private TextView countDown;
    private RecyclerView rv;
    private QuesAdapter adapter;
    private List<Question> mainList=new ArrayList<>();
    private List<String> ans=new ArrayList<>();
    private int numQues;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<Boolean> statusList=new ArrayList<>();
    private String dethi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        firebaseFirestore=FirebaseFirestore.getInstance();

        if(getIntent().hasExtra("LuyThua")) {dethi=getIntent().getStringExtra("LuyThua");}
        if(getIntent().hasExtra("Loga")){ dethi=getIntent().getStringExtra("Loga");}
        if(getIntent().hasExtra("HamsoLoga")){ dethi=getIntent().getStringExtra("HamsoLoga");}

        firebaseFirestore.collection("dethi").document("LOP12")
                .collection("TOAN")
                .document("Logarit")
                .collection(dethi)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                Question cur=doc.getDocument().toObject(Question.class);
                                mainList.add(cur);
                                statusList.add(false);
                                ans.add("X");
                                adapter.notifyDataSetChanged();
                            }
                        }
                        numQues= value.getDocuments().size();
                        countDown = findViewById(R.id.countDown);
                        long duration = numQues*3000*60/2;
                        new CountDownTimer(duration, 1000){
                            @Override
                            public void onTick(long l) {
                                String sDuration = String.format(Locale.ENGLISH,"%02d:%02d"
                                        ,TimeUnit.MILLISECONDS.toMinutes(l)
                                        ,TimeUnit.MILLISECONDS.toSeconds(l)-
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                                countDown.setText(sDuration);
                            }
                            public void onFinish() {
                                countDown.setText("done!");
                                int cnt=0;
                                for(int i=0;i<numQues;++i){
                                    if(ans.get(i).equals(mainList.get(i).getA())){
                                        ++cnt;
                                    }
                                }
                                Toast.makeText(TestActivity.this, "Correct "+cnt+"/"+numQues, Toast.LENGTH_LONG).show();
                                Map<String,Double> mapPost=new HashMap<>();
                                mapPost.put("diem",getMark(cnt));
                                firebaseFirestore.collection("HighScore").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection(dethi).add(mapPost).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                    }
                                });
                                Intent mainIntent=new Intent(TestActivity.this,MainActivity.class);
                                startActivity(mainIntent);
                                finish();

                            }
                        }.start();
                        Toast.makeText(TestActivity.this,"cc", Toast.LENGTH_SHORT).show();
                    }
                });

        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new QuesAdapter(mainList,ans,statusList);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.test_menu,menu);
        return true;
    }

    double getMark(int cnt){
        return 10.0*cnt/numQues;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.act_submit:
                if(mainList.size()<numQues){
                    Toast.makeText(this, "chưa load hết câu hỏi", Toast.LENGTH_SHORT).show();
                }
                else{
                    int cnt=0;
                    for(int i=0;i<numQues;++i){
                        if(ans.get(i).equals(mainList.get(i).getA())){
                            ++cnt;
                        }
                    }

                    Toast.makeText(TestActivity.this, "Correct "+cnt+"/"+numQues, Toast.LENGTH_LONG).show();
                    Map<String,Double> mapPost=new HashMap<>();
                    mapPost.put("diem",getMark(cnt));
                    firebaseFirestore.collection("HighScore").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection(dethi).add(mapPost).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                        }
                    });
                    Intent mainIntent=new Intent(TestActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

                return true;
        }
        return false;
    }

}