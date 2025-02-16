package com.mountreachsolution.farmlabourscheduling.FARMER;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mountreachsolution.farmlabourscheduling.DATABASE.Postwork;
import com.mountreachsolution.farmlabourscheduling.R;

public class updatePost extends AppCompatActivity {
    EditText  etLabour,etWages;
    AppCompatButton btnUpdate;
    String id,wage,labour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_post);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(updatePost.this,R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(updatePost.this,R.color.white));
        id=getIntent().getStringExtra("id");
        
        etWages=findViewById(R.id.etWages);
        etLabour=findViewById(R.id.etLabour);
        btnUpdate=findViewById(R.id.btnUpdate);
        
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wage=etWages.getText().toString().trim();
                labour=etLabour.getText().toString().trim();
                if (wage.isEmpty() || labour.isEmpty()){
                    Toast.makeText(updatePost.this, "Enter The Data", Toast.LENGTH_SHORT).show();
                }else{
                    Updateword(wage,labour,id);
                }
                
            }
        });
       
    }

    private void Updateword(String wage, String labour, String id) {
        Postwork dbHelper = new Postwork(this);
        dbHelper.updateWagesAndLabour(id, wage, labour);
        Intent i = new Intent(updatePost.this,AllPostwork.class);
        startActivity(i);

        Toast.makeText(this, "Work details updated successfully", Toast.LENGTH_SHORT).show();
    }
}