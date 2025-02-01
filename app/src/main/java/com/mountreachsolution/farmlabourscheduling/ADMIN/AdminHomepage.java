package com.mountreachsolution.farmlabourscheduling.ADMIN;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.datatransport.runtime.backends.BackendFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mountreachsolution.farmlabourscheduling.FARMER.FarmerHomepage;
import com.mountreachsolution.farmlabourscheduling.R;

public class AdminHomepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_homepage);

        getWindow().setStatusBarColor(ContextCompat.getColor(AdminHomepage.this,R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AdminHomepage.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomAdmin);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.adminffarmer);

    }
    Farmerlist farmerlist=new Farmerlist();
    labourlist labourlist1=new labourlist();
    profil profil1 =new profil();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.adminffarmer){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,farmerlist).commit();
        }else if(item.getItemId()==R.id.adminlabour){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,labourlist1).commit();
        } else if(item.getItemId()==R.id.adminprofl){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,profil1).commit();
        }
        return true;
    }
}