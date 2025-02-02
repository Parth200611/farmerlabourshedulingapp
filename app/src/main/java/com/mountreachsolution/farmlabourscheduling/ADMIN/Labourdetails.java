package com.mountreachsolution.farmlabourscheduling.ADMIN;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.mountreachsolution.farmlabourscheduling.DATABASE.LabourREgistration;
import com.mountreachsolution.farmlabourscheduling.R;

public class Labourdetails extends AppCompatActivity {
    TextView tvname, tvage, tvnumber, tvaddress, tvadharno, tvskill;
    Button btnRemoveuser;
    LabourREgistration labourREgistration;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_labourdetails);
        getWindow().setStatusBarColor(ContextCompat.getColor(Labourdetails.this, R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Labourdetails.this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Get the Labour ID from the intent
        id = getIntent().getStringExtra("labourid");
        Log.d("Labourdetails", "Received Labour ID: " + id);

        // Initialize Views
        tvname = findViewById(R.id.tvnameValue);
        tvage = findViewById(R.id.agevalue);
        tvnumber = findViewById(R.id.Mobilenovalue);
        tvaddress = findViewById(R.id.addressValue);
//     tvskill = findViewById(R.id.skillValue);
        btnRemoveuser = findViewById(R.id.btnremoveuser);

        // Initialize LabourREgistration instance
        labourREgistration = new LabourREgistration(Labourdetails.this);

        // Set click listener for the Remove User button
        btnRemoveuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteuser(id);
            }
        });

        // Fetch and display labour data
        getdata(id);
    }

    private void getdata(String id) {
        LabourREgistration db = new LabourREgistration(this);
        Cursor cursor = db.getUserById(id);

        if (cursor != null) {
            Log.d("Labourdetails", "Cursor is not null.");

            if (cursor.moveToFirst()) {
                // Log all column names to verify the data structure
                String[] columnNames = cursor.getColumnNames();
                for (String column : columnNames) {
                    Log.d("Labourdetails", "Column: " + column);
                }

                // Ensure columns exist before accessing
                int nameIndex = cursor.getColumnIndex("name");
                int mobileIndex = cursor.getColumnIndex("mobileno");
                int ageIndex = cursor.getColumnIndex("age");
                int addressIndex = cursor.getColumnIndex("address");

                // Log the column indices to verify
                Log.d("Labourdetails", "Name Index: " + nameIndex);
                Log.d("Labourdetails", "Mobile Index: " + mobileIndex);
                Log.d("Labourdetails", "Age Index: " + ageIndex);
                Log.d("Labourdetails", "Address Index: " + addressIndex);

                // Check if all required columns are found
                if (nameIndex != -1 && mobileIndex != -1 && ageIndex != -1 && addressIndex != -1) {
                    // Retrieve data from cursor
                    String name = cursor.getString(nameIndex);
                    String mobile = cursor.getString(mobileIndex);
                    String age = cursor.getString(ageIndex);
                    String address = cursor.getString(addressIndex);

                    // Log the data to ensure it's fetched properly
                    Log.d("Labourdetails", "Name: " + name);
                    Log.d("Labourdetails", "Mobile: " + mobile);
                    Log.d("Labourdetails", "Age: " + age);
                    Log.d("Labourdetails", "Address: " + address);

                    // Set data to TextViews
                    tvname.setText(name);
                    tvnumber.setText(mobile);
                    tvage.setText(age);
                    tvaddress.setText(address);
                } else {
                    Log.e("Labourdetails", "One or more columns are missing in the database.");
                }
            } else {
                Log.e("Labourdetails", "No data found for the given ID.");
            }
            cursor.close();
        } else {
            Log.e("Labourdetails", "Cursor is null.");
        }
    }


    private void deleteuser(String id) {
        boolean isDeleted = labourREgistration.deleteUserById(id);

        if (isDeleted) {
            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Labourdetails.this, AdminHomepage.class);
            startActivity(i);
        } else {
            // Handle failure case
            Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show();
        }
    }
}
