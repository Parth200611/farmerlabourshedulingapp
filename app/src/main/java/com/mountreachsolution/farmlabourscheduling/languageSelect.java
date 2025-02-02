package com.mountreachsolution.farmlabourscheduling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class languageSelect extends AppCompatActivity {

    Button btnEnglish, btnMarathi, btnHindi;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loadLocale();


        setContentView(R.layout.activity_language_select);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(languageSelect.this, R.color.lightbrown));

        category = getIntent().getStringExtra("category");

        btnEnglish = findViewById(R.id.btnEnglish);
        btnMarathi = findViewById(R.id.btnMarathi);
        btnHindi = findViewById(R.id.btnHindi);


        btnEnglish.setOnClickListener(v -> {
            setLocale("en"); // English
            navigateToNextPage();
        });

        btnMarathi.setOnClickListener(v -> {
            setLocale("mr"); // Marathi
            navigateToNextPage();
        });

        btnHindi.setOnClickListener(v -> {
            setLocale("hi"); // Hindi
            navigateToNextPage();
        });
    }


    private void setLocale(String langCode) {

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", langCode);
        editor.apply();

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = getResources().getConfiguration();
        config.setLocale(locale);


        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en");
        setLocale(language);
    }


    private void navigateToNextPage() {
        Intent intent = null;
        if (category != null) {
            if (category.equals("Farmer")) {
                intent = new Intent(languageSelect.this, Farmerregiter.class);
            } else if (category.equals("Labour")) {
                intent = new Intent(languageSelect.this, LabourRegister.class);
            }

            if (intent != null) {
                intent.putExtra("category", category);
                startActivity(intent);
            }
        }
    }
}
