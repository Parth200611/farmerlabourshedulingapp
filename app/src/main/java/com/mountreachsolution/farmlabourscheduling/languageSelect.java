package com.mountreachsolution.farmlabourscheduling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class languageSelect extends AppCompatActivity {

    Button btnEnglish, btnMarathi, btnHindi;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);

        category = getIntent().getStringExtra("category");
        Log.d("FarmerRegistration", "Category received: " + category);

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

    // Function to change the language
    private void setLocale(String langCode) {
        // Set the locale for the app dynamically
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Save the selected language in SharedPreferences to persist language even after app restart
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", langCode);  // Save the language code
        editor.apply();
    }

    // Load the saved language when the app restarts
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en"); // Default to English if not set
        setLocale(language);
    }

    // Handle navigation based on the category
    private void navigateToNextPage() {
        if (category != null) {
            Intent intent = null;
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

    @Override
    protected void onStart() {
        super.onStart();
        loadLocale(); // Load the saved language setting when the activity starts
    }
}
