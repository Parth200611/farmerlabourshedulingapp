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

        // Load the saved language setting before setting content view
        loadLocale();

        // Set the layout after language change
        setContentView(R.layout.activity_language_select);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(languageSelect.this, R.color.lightbrown));

        category = getIntent().getStringExtra("category");

        btnEnglish = findViewById(R.id.btnEnglish);
        btnMarathi = findViewById(R.id.btnMarathi);
        btnHindi = findViewById(R.id.btnHindi);

        // Set click listeners for changing the language
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

    // Function to change the language and apply it immediately
    private void setLocale(String langCode) {
        // Save the selected language in SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", langCode);  // Save the language code
        editor.apply();  // Apply the changes

        // Set the locale for the app dynamically
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = getResources().getConfiguration();
        config.setLocale(locale);

        // Apply the updated configuration
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    // Load the saved language when the app restarts
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en"); // Default to English if not set
        setLocale(language);
    }

    // Navigate to the next page based on the category
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
