package com.mountreachsolution.farmlabourscheduling.FARMER;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.DATABASE.Postwork;
import com.mountreachsolution.farmlabourscheduling.FARMER.Adpter.AdpterAllPost;
import com.mountreachsolution.farmlabourscheduling.LABOUR.ADpter.AdpterWork;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;

public class AllPostwork extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchView;
    AdpterAllPost adapter;

    ArrayList<String> id, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image;
    ArrayList<String> filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages, filteredStarttime, filteredEndtime, filteredWorkdate, filteredCrop;
    Postwork dbHelper;
    private boolean isToastShown = false;
    String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_postwork);

        getWindow().setStatusBarColor(ContextCompat.getColor(AllPostwork.this, R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AllPostwork.this, R.color.white));

        SharedPreferences sharedPreferences = getSharedPreferences("Userdata", Context.MODE_PRIVATE);
        number = sharedPreferences.getString("userMobile", "");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        dbHelper = new Postwork(AllPostwork.this);

        // Initialize lists
        id = new ArrayList<>();
        name = new ArrayList<>();
        mobileno = new ArrayList<>();
        address = new ArrayList<>();
        work = new ArrayList<>();
        wages = new ArrayList<>();
        starttime = new ArrayList<>();
        endtime = new ArrayList<>();
        workdate = new ArrayList<>();
        crop = new ArrayList<>();
        image = new ArrayList<>();

        // Initialize filtered lists
        filteredId = new ArrayList<>();
        filteredName = new ArrayList<>();
        filteredMobileno = new ArrayList<>();
        filteredAddress = new ArrayList<>();
        filteredWork = new ArrayList<>();
        filteredWages = new ArrayList<>();
        filteredStarttime = new ArrayList<>();
        filteredEndtime = new ArrayList<>();
        filteredWorkdate = new ArrayList<>();
        filteredCrop = new ArrayList<>();

        // Set up adapter with filtered data
        adapter = new AdpterAllPost(filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages, filteredStarttime, filteredEndtime, filteredWorkdate, filteredCrop, AllPostwork.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllPostwork.this));

        displayData();
        setupSearchView();
    }

    private void displayData() {
        if (!isToastShown) {
            Toast.makeText(AllPostwork.this, "Network Low, Reload Page", Toast.LENGTH_SHORT).show();
            isToastShown = true;
        }

        Cursor cursor = dbHelper.getWorkPostingByMobile(number);
        if (cursor.getCount() == 0) {
            Toast.makeText(AllPostwork.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                mobileno.add(cursor.getString(2));
                address.add(cursor.getString(3));
                work.add(cursor.getString(4));
                crop.add(cursor.getString(5));
                wages.add(cursor.getString(6));
                starttime.add(cursor.getString(8));
                endtime.add(cursor.getString(9));
                workdate.add(cursor.getString(10));
            }
        }

        // Initially, show all data in filtered lists
        filteredId.addAll(id);
        filteredName.addAll(name);
        filteredMobileno.addAll(mobileno);
        filteredAddress.addAll(address);
        filteredWork.addAll(work);
        filteredWages.addAll(wages);
        filteredStarttime.addAll(starttime);
        filteredEndtime.addAll(endtime);
        filteredWorkdate.addAll(workdate);
        filteredCrop.addAll(crop);

        adapter.notifyDataSetChanged();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        filteredId.clear();
        filteredName.clear();
        filteredMobileno.clear();
        filteredAddress.clear();
        filteredWork.clear();
        filteredWages.clear();
        filteredStarttime.clear();
        filteredEndtime.clear();
        filteredWorkdate.clear();
        filteredCrop.clear();

        for (int i = 0; i < work.size(); i++) {
            if (work.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    wages.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    workdate.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    starttime.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    endtime.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    crop.get(i).toLowerCase().contains(text.toLowerCase())) {

                filteredId.add(id.get(i));
                filteredName.add(name.get(i));
                filteredMobileno.add(mobileno.get(i));
                filteredAddress.add(address.get(i));
                filteredWork.add(work.get(i));
                filteredWages.add(wages.get(i));
                filteredStarttime.add(starttime.get(i));
                filteredEndtime.add(endtime.get(i));
                filteredWorkdate.add(workdate.get(i));
                filteredCrop.add(crop.get(i));
            }
        }

        adapter.notifyDataSetChanged();
    }
}