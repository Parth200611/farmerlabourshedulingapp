package com.mountreachsolution.farmlabourscheduling.LABOUR;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Postwork;
import com.mountreachsolution.farmlabourscheduling.LABOUR.ADpter.AdpterWork;
import com.mountreachsolution.farmlabourscheduling.R;
import java.util.ArrayList;

public class labourhome extends Fragment {
    RecyclerView recyclerView;
    SearchView searchView;
    AdpterWork adapter;

    ArrayList<String> id, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image;
    ArrayList<String> filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages, filteredStarttime, filteredEndtime, filteredWorkdate, filteredCrop;
    Postwork dbHelper;
    private boolean isToastShown = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_labourhome, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);
        dbHelper = new Postwork(requireContext());

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
        adapter = new AdpterWork(filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages, filteredStarttime, filteredEndtime, filteredWorkdate, filteredCrop, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        displayData();
        setupSearchView();

        return view;
    }

    private void displayData() {
        if (!isToastShown) {
            Toast.makeText(getActivity(), "Network Low, Reload Page", Toast.LENGTH_SHORT).show();
            isToastShown = true;
        }

        Cursor cursor = dbHelper.getAllWorkPostings();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
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
