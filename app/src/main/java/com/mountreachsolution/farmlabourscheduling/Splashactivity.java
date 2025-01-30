package com.mountreachsolution.farmlabourscheduling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.mountreachsolution.farmlabourscheduling.ADMIN.AdminHomepage;
import com.mountreachsolution.farmlabourscheduling.FARMER.FarmerHomepage;
import com.mountreachsolution.farmlabourscheduling.LABOUR.LabourHomepage;

public class Splashactivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String role;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(Splashactivity.this,R.color.lightbrown));

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Make the app fullscreen by hiding the status bar and navigation bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        // Initialize SharedPreferences to check for login state
        sharedPreferences = getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);

                // Check if user is logged in
                isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    // Retrieve the role of the logged-in user
                    role = sharedPreferences.getString("LoggedInRole", "");

                    // Navigate to the appropriate homepage based on role
                    navigateToHomePage(role);
                } else {
                    // If not logged in, navigate to the LoginActivity
                    Intent loginIntent = new Intent(Splashactivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
            }
        }  // 2-second delay for splash screen
    },2000);
    }

    // Navigate to the respective homepage based on the role
    private void navigateToHomePage(String role) {
        Intent intent = null;

        // Check role and navigate accordingly
        switch (role) {
            case "Admin":
                intent = new Intent(Splashactivity.this, AdminHomepage.class);
                break;
            case "Farmer":
                intent = new Intent(Splashactivity.this, FarmerHomepage.class);
                break;
            case "Labour":
                intent = new Intent(Splashactivity.this, LabourHomepage.class);
                break;
            default:
                intent = new Intent(Splashactivity.this, LoginActivity.class);
                // Default case if role is not recognized (optional)
                break;
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }

}
