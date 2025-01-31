package com.mountreachsolution.farmlabourscheduling.FARMER;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mountreachsolution.farmlabourscheduling.R;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);
        getWindow().setStatusBarColor(ContextCompat.getColor(AboutUs.this, R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AboutUs.this, R.color.white));

        SharedPreferences sharedPreferences = getSharedPreferences("Userdata", Context.MODE_PRIVATE);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}