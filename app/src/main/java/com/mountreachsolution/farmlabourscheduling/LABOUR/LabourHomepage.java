package com.mountreachsolution.farmlabourscheduling.LABOUR;

import android.content.SharedPreferences;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mountreachsolution.farmlabourscheduling.FARMER.FarmerHomepage;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.Locale;

public class LabourHomepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_labour_homepage);

        getWindow().setStatusBarColor(ContextCompat.getColor(LabourHomepage.this,R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(LabourHomepage.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomlabour);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.LabourHome);

        loadLocale();
    }
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en"); // Default to English if not set
        setLocale(language);
    }

    // Function to apply the language dynamically
    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = getResources().getConfiguration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    labourprofil labourprofil1=new labourprofil();
    labourhome labourhome1=new labourhome();
    labourshedul labourshedul1=new labourshedul();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.LabourHome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,labourhome1).commit();
        }else if(item.getItemId()==R.id.LabourProfil){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,labourprofil1).commit();
        } else if(item.getItemId()==R.id.LabourShedual){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,labourshedul1).commit();
        }
        return true;
    }
}