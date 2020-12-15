package com.example.thitracnghiem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thitracnghiem.MainActivity;
import com.example.thitracnghiem.R;
import com.example.thitracnghiem.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {
    EditText mFullName,mAge,mClass,mSchool;
    Button mProfileBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseFirestore   = FirebaseFirestore.getInstance();
        mFullName           = findViewById(R.id.fullName);
        mAge                = findViewById(R.id.Age);
        mClass              = findViewById(R.id.Class);
        mSchool             = findViewById(R.id.School);
        mProfileBtn         = findViewById(R.id.profileBtn);

        mAuth = FirebaseAuth.getInstance();
        mProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name,Class,School,Age;
                Name=mFullName.getText().toString();
                Class=mClass.getText().toString();
                Age=mAge.getText().toString();
                School=mSchool.getText().toString();
                User user=new User(Name,Integer.parseInt(Age),School,Class);
                firebaseFirestore.collection("Info").document(mAuth.getCurrentUser().getUid())
                        .set(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    finish();
                                }
                            }
                        });
                Toast.makeText(ProfileActivity.this,"Profile saved",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}