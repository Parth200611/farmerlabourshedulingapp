package com.mountreachsolution.farmlabourscheduling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.mountreachsolution.farmlabourscheduling.ADMIN.AdminHomepage;
import com.mountreachsolution.farmlabourscheduling.DATABASE.AdminRegistration;
import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;
import com.mountreachsolution.farmlabourscheduling.DATABASE.LabourREgistration;
import com.mountreachsolution.farmlabourscheduling.FARMER.FarmerHomepage;
import com.mountreachsolution.farmlabourscheduling.LABOUR.LabourHomepage;

public class LoginActivity extends AppCompatActivity {

    TextView tvnewUser;
    EditText etnumber, etpassword;
    ProgressDialog progressDialog;
    AppCompatButton btnlogin;
    String mobileno, password;
    CheckBox cbshowpassword;
    LabourREgistration labourREgistration;
    FarmerRegistration farmerRegistration;
    AdminRegistration adminRegistration;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);

        etnumber = findViewById(R.id.etLoginNumber);
        etpassword = findViewById(R.id.etLoginPassword);
        btnlogin = findViewById(R.id.btnLoginButton);
        cbshowpassword = findViewById(R.id.cbLoginShowpassword);
        labourREgistration = new LabourREgistration(LoginActivity.this);
        farmerRegistration = new FarmerRegistration(LoginActivity.this);
        adminRegistration = new AdminRegistration(LoginActivity.this);

        cbshowpassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etpassword.setSelection(etpassword.getText().length());
        });

        tvnewUser = findViewById(R.id.tvLginNewUser);
        tvnewUser.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, selectcategory.class);
            startActivity(i);
        });

        btnlogin.setOnClickListener(view -> {

            if (etnumber.getText().toString().isEmpty()) {
                etnumber.setError("Please enter number");
            } else if (etnumber.getText().toString().length() != 10) {
                etnumber.setError("Please enter number properly");
            } else if (etpassword.getText().toString().isEmpty()) {
                etpassword.setError("Please enter password");
            } else {

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("LOG IN");
                progressDialog.setMessage("Please wait for a while");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mobileno = etnumber.getText().toString();
                password = etpassword.getText().toString();

                logijn();
            }
        });

    }

    private void logijn() {
        // Check for hardcoded Admin first (optional for testing)
        if (mobileno.equals("1234567890") && password.equals("admin")) {
            saveUserSession(mobileno, "Admin");
            Intent intent = new Intent(LoginActivity.this, AdminHomepage.class);
            startActivity(intent);
            finish();
        } else {
            // Proceed with registration-based login
            loginAdmin();
        }
    }

    private void loginAdmin() {
        String role = adminRegistration.loginUser(mobileno, password);

        if (role != null) {
            if (role.equals("Admin")) {
                saveUserSession(mobileno, "Admin");
                Intent intent = new Intent(LoginActivity.this, AdminHomepage.class);
                startActivity(intent);
                finish();
            }
        } else {
            loginFarmer(); // Proceed to Farmer login if Admin login fails
        }
    }

    private void loginFarmer() {
        String role = farmerRegistration.loginUser(mobileno, password);

        if (role != null) {
            saveUserSession(mobileno, "Farmer");
            Intent intent = new Intent(LoginActivity.this, FarmerHomepage.class);
            startActivity(intent);
            finish();
        } else {
            loginLabour(); // Proceed to Labour login if Farmer login fails
        }
    }

    private void loginLabour() {
        String role = labourREgistration.loginUser(mobileno, password);

        if (role != null) {
            saveUserSession(mobileno, "Labour");
            Intent intent = new Intent(LoginActivity.this, LabourHomepage.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Invalid mobile or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserSession(String number, String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LoggedInNumber", number);
        editor.putString("LoggedInRole", role);  // Save role to help with navigation
        editor.putBoolean("isLoggedIn", true);  // Save isLoggedIn state as true
        editor.apply();
    }
}
