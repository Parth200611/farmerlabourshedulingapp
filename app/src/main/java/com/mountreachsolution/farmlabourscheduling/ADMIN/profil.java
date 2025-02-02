package com.mountreachsolution.farmlabourscheduling.ADMIN;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mountreachsolution.farmlabourscheduling.LoginActivity;
import com.mountreachsolution.farmlabourscheduling.R;


public class profil extends Fragment {

    Button btnlogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profil2, container, false);

        btnlogout=view.findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        return view;
    }

    private void logout() {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserLoginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("LoggedInNumber");  // Remove saved login information
            editor.remove("LoggedInRole");  // Remove saved role information
            editor.apply();

            // Optionally, log to check if it's cleared
            Log.d("Logout", "User logged out, session cleared");

            // Redirect to the login screen
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

}