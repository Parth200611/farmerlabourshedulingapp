package com.mountreachsolution.farmlabourscheduling;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class languageSelect extends AppCompatActivity {

     Button btnEnglish, btnMarathi, btnHindi;
     String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_language_select);
        category=getIntent().getStringExtra("category");
        Log.d("FarmerRegistration", "Category received: " + category);

        btnEnglish = findViewById(R.id.btnEnglish);
        btnMarathi = findViewById(R.id.btnMarathi);
        btnHindi = findViewById(R.id.btnHindi);

        // Set click listeners for changing the language
        btnEnglish.setOnClickListener(v -> changeLanguage());
        btnMarathi.setOnClickListener(v -> changeLanguage());
        btnHindi.setOnClickListener(v -> changeLanguage());

    }

    private void changeLanguage() {
        if (category.equals("Farmer")){
            Intent i = new Intent(languageSelect.this,Farmerregiter.class);
            i.putExtra("category",category);
            startActivity(i);
        }
        else if(category.equals("Labour")){
            Intent i = new Intent(languageSelect.this,LabourRegister.class);
            i.putExtra("category",category);
            startActivity(i);

        }
    }


}