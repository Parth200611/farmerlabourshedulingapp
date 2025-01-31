package com.mountreachsolution.farmlabourscheduling.FARMER;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.DATABASE.Workrequestdatabse;
import com.mountreachsolution.farmlabourscheduling.FARMER.Adpter.AdpterRequestWork;
import com.mountreachsolution.farmlabourscheduling.LABOUR.WorkDetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;

public class Allrequest extends AppCompatActivity {

    SearchView svserach;
    RecyclerView rvlist;
    TextView tvNorequest;

    ArrayList<String> id1, name1, mobileno1, address1, work1, wages1, starttime1, endtime1, workdate1, crop1, image1, labour1,
            labourname1, labournumber1, labouraddress1, labourskill1, labouradhar1, labourage1;

    String monilenumber;
    AdpterRequestWork adpterRequestWork;
    Workrequestdatabse workrequestdatabse;

    ArrayList<String> filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages, filteredStarttime,
            filteredEndtime, filteredWorkdate, filteredCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_allrequest);
        SharedPreferences sharedPreferences = getSharedPreferences("Userdata", Context.MODE_PRIVATE);
        monilenumber = sharedPreferences.getString("userMobile", "");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(Allrequest.this, R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Allrequest.this, R.color.white));

        svserach = findViewById(R.id.searchView);
        rvlist = findViewById(R.id.rvrequestworkall);
        tvNorequest = findViewById(R.id.tvnorequest);
        id1 = new ArrayList<>();
        name1 = new ArrayList<>();
        mobileno1 = new ArrayList<>();
        address1 = new ArrayList<>();
        starttime1 = new ArrayList<>();
        endtime1 = new ArrayList<>();
        wages1 = new ArrayList<>();
        crop1 = new ArrayList<>();
        workdate1 = new ArrayList<>();
        work1 = new ArrayList<>();
        image1 = new ArrayList<>();
        labour1 = new ArrayList<>();
        labourname1 = new ArrayList<>();
        labournumber1 = new ArrayList<>();
        labouraddress1 = new ArrayList<>();
        labourage1 = new ArrayList<>();
        labourskill1 = new ArrayList<>();
        labouradhar1 = new ArrayList<>();
        workrequestdatabse = new Workrequestdatabse(Allrequest.this);

        // Create filtered lists
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

        adpterRequestWork = new AdpterRequestWork(filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages,
                filteredStarttime, filteredEndtime, filteredWorkdate, filteredCrop, image1, labour1,
                labourname1, labournumber1, labouraddress1, labourskill1, labouradhar1, labourage1, Allrequest.this);
        rvlist.setLayoutManager(new LinearLayoutManager(Allrequest.this));
        rvlist.setAdapter(adpterRequestWork);

        getalldata(monilenumber);

        // Setup SearchView
        setupSearchView();
    }

    private void setupSearchView() {
        svserach.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        for (int i = 0; i < work1.size(); i++) {
            if (work1.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    wages1.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    workdate1.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    starttime1.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    endtime1.get(i).toLowerCase().contains(text.toLowerCase()) ||
                    crop1.get(i).toLowerCase().contains(text.toLowerCase())) {

                filteredId.add(id1.get(i));
                filteredName.add(name1.get(i));
                filteredMobileno.add(mobileno1.get(i));
                filteredAddress.add(address1.get(i));
                filteredWork.add(work1.get(i));
                filteredWages.add(wages1.get(i));
                filteredStarttime.add(starttime1.get(i));
                filteredEndtime.add(endtime1.get(i));
                filteredWorkdate.add(workdate1.get(i));
                filteredCrop.add(crop1.get(i));
            }
        }

        adpterRequestWork.notifyDataSetChanged();
    }

    private void getalldata(String monilenumber) {

        Cursor cursor = workrequestdatabse.getRequest(monilenumber); // Change this to the actual farmer mobile number

        if (cursor.getCount() == 0) {
            rvlist.setVisibility(View.GONE);
            tvNorequest.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                id1.add(cursor.getString(0));
                name1.add(cursor.getString(1));
                mobileno1.add(cursor.getString(2));
                address1.add(cursor.getString(3));
                work1.add(cursor.getString(4));
                wages1.add(cursor.getString(5));
                starttime1.add(cursor.getString(6));
                endtime1.add(cursor.getString(7));
                workdate1.add(cursor.getString(8));
                crop1.add(cursor.getString(9));
                image1.add(cursor.getString(10));
                labour1.add(cursor.getString(11));
                labourname1.add(cursor.getString(12));
                labournumber1.add(cursor.getString(13));
                labouraddress1.add(cursor.getString(14));
                labourskill1.add(cursor.getString(15));
                labourage1.add(cursor.getString(16));
                labouradhar1.add(cursor.getString(17));
            }
            rvlist.setVisibility(View.VISIBLE);
            tvNorequest.setVisibility(View.GONE);
            filter("");  // Filter with empty text to show all data initially
        }
        cursor.close();
    }
}
