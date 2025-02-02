package com.mountreachsolution.farmlabourscheduling.FARMER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.Locale;

public class FarmerHomepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_farmer_homepage);
        getWindow().setStatusBarColor(ContextCompat.getColor(FarmerHomepage.this,R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(FarmerHomepage.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomnevigatiomuserhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.FarmerHome);

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

    HomeFragment homeFragment=new HomeFragment();
    PostFragment postFragment=new PostFragment();
    ProfilFragment profilFragment=new ProfilFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.FarmerHome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,homeFragment).commit();
        }else if(item.getItemId()==R.id.FarmerPostwork){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,postFragment).commit();
        } else if(item.getItemId()==R.id.FarmerProfil){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,profilFragment).commit();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.farmermenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.FarmerRequest){
            Intent i = new Intent(FarmerHomepage.this,Allrequest.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.FarmerWaiting) {
            Intent i = new Intent(FarmerHomepage.this,waitingrequest.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.Farmerallpostwork) {
            Intent i = new Intent(FarmerHomepage.this,AllPostwork.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.FarmerAbvoutus) {
            Intent i = new Intent(FarmerHomepage.this, AboutUs.class);
            startActivity(i);

        }

        return true;
    }
}