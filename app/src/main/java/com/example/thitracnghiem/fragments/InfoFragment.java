package com.example.thitracnghiem.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thitracnghiem.R;
import com.example.thitracnghiem.SetupInfoActivity;
import com.example.thitracnghiem.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ThrowOnExtraProperties;


public class InfoFragment extends Fragment {
    private View v;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView name,classs,school,age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_info, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        name=v.findViewById(R.id.fullName);
        age=v.findViewById(R.id.Age);
        school=v.findViewById(R.id.School);
        classs=v.findViewById(R.id.Class);
        firebaseFirestore.collection("Info").document(firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null){
                            if(value.exists()){
                                User user=new User(value.get("name").toString(),Integer.parseInt(value.get("age").toString()),value.get("school").toString(),value.get("classs").toString());
                                age.setText(user.getAge()+"");
                                name.setText(user.getName());
                                school.setText(user.getSchool());
                                classs.setText(user.getClasss());

                            }
                            else{
                                Intent setupIntent=new Intent(v.getContext(), SetupInfoActivity.class);
                                getActivity().startActivity(setupIntent);
                                getActivity().finish();
                            }

                        }
                        else{
                            Toast.makeText(v.getContext(), "chua co users", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return v;
    }
}