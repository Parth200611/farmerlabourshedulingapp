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

        // Check if a user is already logged in
        if (sharedPreferences.contains("LoggedInNumber")) {
            navigateToHome(); // Navigate to respective homepage
            finish();
        }

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
        if (mobileno.equals("1234567890") && password.equals("admin")) {
            saveUserSession(mobileno);
            Intent intent = new Intent(LoginActivity.this, AdminHomepage.class);
            startActivity(intent);
            finish();
        } else {
            loginAdmin();
        }
    }

    private void loginAdmin() {
        String role = adminRegistration.loginUser(mobileno, password);

        if (role != null) {
            if (role.equals("Admin")) {
                saveUserSession(mobileno);
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
            saveUserSession(mobileno);
            if (role.equals("Admin")) {
                Intent intent = new Intent(LoginActivity.this, AdminHomepage.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, FarmerHomepage.class);
                startActivity(intent);
                finish();
            }
        } else {
            loginLabour(); // Proceed to Labour login if Farmer login fails
        }
    }

    private void loginLabour() {
        String role = labourREgistration.loginUser(mobileno, password);

        if (role != null) {
            saveUserSession(mobileno);
            if (role.equals("Admin")) {
                Intent intent = new Intent(LoginActivity.this, AdminHomepage.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, LabourHomepage.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Invalid mobile or password in database", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserSession(String number) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LoggedInNumber", number);
        editor.apply();
    }

    private void navigateToHome() {
        // Check user role from saved preferences (you can enhance this logic further if needed)
        String savedNumber = sharedPreferences.getString("LoggedInNumber", "");
        if (savedNumber.equals("1234567890")) {
            Intent intent = new Intent(LoginActivity.this, AdminHomepage.class);
            startActivity(intent);
        } else {
            // Default homepage for other users (you can customize this)
            Intent intent = new Intent(LoginActivity.this, FarmerHomepage.class);
            startActivity(intent);
        }
    }
}
