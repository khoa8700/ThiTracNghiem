package com.example.thitracnghiem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thitracnghiem.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SetupInfoActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private EditText edtName,edtSchool,edtClass,edtAge;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        edtName=findViewById(R.id.fullName);
        edtSchool=findViewById(R.id.School);
        edtClass=findViewById(R.id.Class);
        edtAge=findViewById(R.id.Age);
        btnSave=findViewById(R.id.btn_save);
        firebaseFirestore.collection("Info").document(firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null){
                            if(value.exists()){
                                User user=new User(value.get("name").toString(),Integer.parseInt(value.get("age").toString()),value.get("school").toString(),value.get("classs").toString());
                                edtAge.setText(user.getAge()+"");
                                edtName.setText(user.getName());
                                edtSchool.setText(user.getSchool());
                                edtClass.setText(user.getClasss());

                            }
                        }

                    }
                });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name,Class,School,Age;
                Name=edtName.getText().toString();
                Class=edtClass.getText().toString();
                Age=edtAge.getText().toString();
                School=edtSchool.getText().toString();
                if(!TextUtils.isEmpty(Name)&&!TextUtils.isEmpty(Class)&&!TextUtils.isEmpty(Age)&&!TextUtils.isEmpty(School)){
                    User user=new User(Name,Integer.parseInt(Age),School,Class);
                    firebaseFirestore.collection("Info").document(firebaseAuth.getCurrentUser().getUid()).set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SetupInfoActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SetupInfoActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

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