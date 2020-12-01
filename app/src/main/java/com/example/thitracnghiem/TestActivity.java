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
    private ArrayList<String> index=new ArrayList<>();
    private int numQues=10;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        firebaseFirestore=FirebaseFirestore.getInstance();
        for(int i=0;i<numQues;++i){
            ans.add("X");
            index.add("Câu "+(i+1)+":");
        }
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new QuesAdapter(mainList,ans,index);
        rv.setAdapter(adapter);
        firebaseFirestore.collection("dethi").document("LOP12")
                .collection("TOAN")
                .document("Logarit")
                .collection("LuyThua")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                Question cur=doc.getDocument().toObject(Question.class);
                                mainList.add(cur);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        countDown = findViewById(R.id.countDown);
        // sao ko hien time
        long duration = TimeUnit.MINUTES.toMillis(30);
        new CountDownTimer(duration, 1000){
            @Override
            public void onTick(long l) {
//                countDown.setText(""+String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
//                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
//                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
//                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
//                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
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
                    // cho lai 5s de test
                    Log.d("ansss","first = "+ans.get(i)+"   second = "+mainList.get(i).getA());
                    if(ans.get(i).equals(mainList.get(i).getA())){
                        ++cnt; // ok lm j nx save vo high score ak ua
                    }
                }
                Toast.makeText(TestActivity.this, "kq : "+cnt+"/"+numQues, Toast.LENGTH_LONG).show();
                Map<String,Double> mapPost=new HashMap<>();
                mapPost.put("diem",getMark(cnt));
                firebaseFirestore.collection("HighScore").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("highscores").add(mapPost).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(TestActivity.this, "added", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent mainIntent=new Intent(TestActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();

            }
        }.start(); // lam j ddaay alo
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
                    Toast.makeText(this, "chua load xong :v", Toast.LENGTH_SHORT).show();
                }
                else{
                    int cnt=0;
                    for(int i=0;i<numQues;++i){
                        Log.d("ansss","first = "+ans.get(i)+"   second = "+mainList.get(i).getA());
                        if(ans.get(i).equals(mainList.get(i).getA())){
                            ++cnt; // ok lm j nx save vo high score ak ua
                        }
                    }
                    Toast.makeText(this, "kq : "+cnt+"/"+numQues, Toast.LENGTH_SHORT).show();
                    Map<String,Double> mapPost=new HashMap<>();
                    mapPost.put("diem",getMark(cnt));
                    firebaseFirestore.collection("HighScore").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("highscores").add(mapPost).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(TestActivity.this, "added", Toast.LENGTH_SHORT).show();
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