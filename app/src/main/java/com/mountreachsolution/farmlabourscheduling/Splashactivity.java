package com.mountreachsolution.farmlabourscheduling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mountreachsolution.farmlabourscheduling.DATABASE.AdminRegistration;

public class Splashactivity extends AppCompatActivity {

    AdminRegistration adminRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        adminRegistration=new AdminRegistration(Splashactivity.this);
        String name="Admin User";
        String number="1234567890";
        String password="admin";
        String role="admin";

        //adminRegistration.insertAdminData(name,number,password,role);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Make the app fullscreen by hiding the status bar and navigation bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//                boolean isLoggedIn = sharedPreferences.getBoolean("IsLoggedIn", false);
//                String role = sharedPreferences.getString("Role", null);
//                if (isLoggedIn) {
//                    if ("Farmer".equals(role)) {
//                        startActivity(new Intent(SplashScreen.this, FarmerHomepage.class));
//                    } else if ("Labour".equals(role)) {
//                        startActivity(new Intent(SplashScreen.this, LabourHomepage.class));
//                    } else if ("admin".equals(role)) {
//                        startActivity(new Intent(SplashScreen.this, AdminHomepage.class));
//                    }
//                } else {
                    startActivity(new Intent(Splashactivity.this, LoginActivity.class));
//                }
//                finish();

            }
        },2000);

    }
}