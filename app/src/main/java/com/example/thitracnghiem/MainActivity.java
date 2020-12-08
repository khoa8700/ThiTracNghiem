package com.example.thitracnghiem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.thitracnghiem.fragments.HomeFragment;
import com.example.thitracnghiem.fragments.InfoFragment;
import com.example.thitracnghiem.fragments.PracticeFragment;
import com.example.thitracnghiem.fragments.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView nav_bottom;
    private FrameLayout frame;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        nav_bottom=findViewById(R.id.bottomNavigationView);
        nav_bottom.setOnNavigationItemSelectedListener(listener);
        frame=findViewById(R.id.frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.act_logout:
                firebaseAuth.signOut();
                Intent intent=new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment tmp=null;
            switch(item.getItemId()){
                case R.id.nav_home:
                    tmp=new HomeFragment();
                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_practice:
                    tmp=new PracticeFragment();
                    Toast.makeText(MainActivity.this, "practice", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_test:
                    tmp=new TestFragment();
                    Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_info:
                    tmp=new InfoFragment();
                    Toast.makeText(MainActivity.this, "info", Toast.LENGTH_SHORT).show();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,tmp).commit();
            return true;
        }
    };
}