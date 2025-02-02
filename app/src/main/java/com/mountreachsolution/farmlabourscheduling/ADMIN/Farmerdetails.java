package com.mountreachsolution.farmlabourscheduling.ADMIN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;
import com.mountreachsolution.farmlabourscheduling.FARMER.AboutUs;
import com.mountreachsolution.farmlabourscheduling.R;

public class Farmerdetails extends AppCompatActivity {
    TextView tvname, tvage, tvnumber, tvaddress, tvadharno;
    Button btnRemoveuser;
    FarmerRegistration farmerRegistration;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_farmerdetails);
        getWindow().setStatusBarColor(ContextCompat.getColor(Farmerdetails.this, R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Farmerdetails.this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Get the Farmer ID from the intent
        id = getIntent().getStringExtra("Farmerid");

        // Initialize Views
        tvname = findViewById(R.id.tvnameValue);
        tvage = findViewById(R.id.agevalue);
        tvnumber = findViewById(R.id.Mobilenovalue);
        tvaddress = findViewById(R.id.addressValue);
        tvadharno = findViewById(R.id.adharvalue);
        btnRemoveuser = findViewById(R.id.btnremoveuser);

        // Initialize FarmerRegistration instance
        farmerRegistration = new FarmerRegistration(Farmerdetails.this);

        // Set click listener for the Remove User button
        btnRemoveuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteuser(id);
            }
        });

        // Fetch and display farmer data
        getdata(id);
    }

    private void getdata(String id) {
        Cursor cursor = farmerRegistration.getUserById(id); // Use the correct ID

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String mobile = cursor.getString(cursor.getColumnIndex("mobileno"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String adharno = cursor.getString(cursor.getColumnIndex("adharno"));

            // Set values to TextViews
            tvname.setText(name);
            tvnumber.setText(mobile);
            tvage.setText(age);
            tvaddress.setText(address);
            tvadharno.setText(adharno);

            cursor.close();
        }
    }

    private void deleteuser(String id) {
        boolean isDeleted = farmerRegistration.deleteUserById(id);

        if (isDeleted) {
            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Farmerdetails.this, AdminHomepage.class);
            startActivity(i);
        } else {
            // Handle failure case
            Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show();
        }
    }
}
