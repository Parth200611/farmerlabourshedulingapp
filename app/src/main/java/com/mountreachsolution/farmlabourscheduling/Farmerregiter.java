package com.mountreachsolution.farmlabourscheduling;

import android.app.ProgressDialog;
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

import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;

public class Farmerregiter extends AppCompatActivity {
    EditText etname, etmobile, etage, etadharno, etpassword, etAddress;
    ProgressDialog progressDialog;
    AppCompatButton btnRegister;
    String category;
    FarmerRegistration farmerregiter;

    String name, mobileno, age, adhar, password, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_farmerregiter);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));

        category = getIntent().getStringExtra("category");

        etname = findViewById(R.id.etREgisterName);
        etmobile = findViewById(R.id.etREgisterMobileno);
        etAddress = findViewById(R.id.etREgisterAddress);
        etage = findViewById(R.id.etREgisterAge);
        etadharno = findViewById(R.id.etREgisterAdharnumber);
        etpassword = findViewById(R.id.etREgisterPassword);

        farmerregiter = new FarmerRegistration(Farmerregiter.this);

        btnRegister = findViewById(R.id.btnRegisterButton);

        btnRegister.setOnClickListener(view -> {
            if (etname.getText().toString().isEmpty()) {
                etname.setError("Please Enter the name");
            } else if (etmobile.getText().toString().isEmpty()) {
                etmobile.setError("Please enter the mobile number");
            } else if (etmobile.getText().toString().length() != 10) {
                etmobile.setError("Please enter Proper Mobile number ");
            } else if (etAddress.getText().toString().isEmpty()) {
                etAddress.setError("Please enter the address");
            } else if (etage.getText().toString().isEmpty()) {
                etage.setError("Please enter the age");
            } else if (etadharno.getText().toString().isEmpty()) {
                etadharno.setError("Please enter adhar number for verification");
            } else if (etpassword.getText().toString().isEmpty()) {
                etpassword.setError("Please enter username");
            } else if (etpassword.getText().toString().length() < 8) {
                etpassword.setError("Username must be at least 8 characters");
            } else if (!etpassword.getText().toString().matches(".*[A-Z].*")) {
                etpassword.setError("Please enter at least one uppercase letter");
            } else if (!etpassword.getText().toString().matches(".*[a-z].*")) {
                etpassword.setError("Please enter at least one lowercase letter");
            } else if (!etpassword.getText().toString().matches(".*[0-9].*")) {
                etpassword.setError("Please enter at least one number");
            } else if (!etpassword.getText().toString().matches(".*[!,@,#,$,%,&,*].*")) {
                etpassword.setError("Please enter at least one special symbol");
            } else {
                progressDialog = new ProgressDialog(Farmerregiter.this);
                progressDialog.setTitle("Creating Account");
                progressDialog.setMessage("Wait for a while");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                name = etname.getText().toString();
                mobileno = etmobile.getText().toString();
                address = etAddress.getText().toString();
                age = etage.getText().toString();
                adhar = etadharno.getText().toString();
                password = etpassword.getText().toString();

                age = String.valueOf(Integer.parseInt(etage.getText().toString().trim()));

                // Check if the mobile number already exists
                if (farmerregiter.isUserExists(mobileno)) {
                    progressDialog.dismiss();
                    Toast.makeText(Farmerregiter.this, "Mobile number already exists. Please log in.", Toast.LENGTH_SHORT).show();
                } else {
                    REgisterUser(name, mobileno, address, age, adhar, password, category);
                }
            }
        });
    }
    private void REgisterUser(String name, String mobileno, String address, String age, String adhar, String password, String category) {
        farmerregiter.UserRegister(name, mobileno, address, age, adhar, password, category);
        progressDialog.dismiss();
        Intent i = new Intent(Farmerregiter.this,LoginActivity.class);
        startActivity(i);
    }


}
