package com.mountreachsolution.farmlabourscheduling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class selectcategory extends AppCompatActivity {
    CardView cvfarmer,cvLabour;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selectcategory);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION


        );

        cvfarmer=findViewById(R.id.cvFramer);
        cvLabour=findViewById(R.id.cvLabour);

        cvfarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="Farmer";
                Intent i = new Intent(selectcategory.this,languageSelect.class);
                i.putExtra("category",category);
                startActivity(i);
            }
        });
        cvLabour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="Labour";
                Intent i = new Intent(selectcategory.this,languageSelect.class);
                i.putExtra("category",category);
                startActivity(i);
            }
        });


    }
}