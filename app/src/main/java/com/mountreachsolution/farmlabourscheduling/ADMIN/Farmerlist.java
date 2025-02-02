package com.mountreachsolution.farmlabourscheduling.ADMIN;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.ADMIN.Adpter.AdapterFarmer;
import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;
import com.mountreachsolution.farmlabourscheduling.R;
import java.util.ArrayList;

public class Farmerlist extends Fragment {
    SearchView svserch;
    RecyclerView rvlist;
    FarmerRegistration farmerRegistration;
    AdapterFarmer adapterFarmer;
    ArrayList<String> id, name, number, address, age, adhrno;
    ArrayList<String> filteredName, filteredNumber, filteredAddress, filteredAge, filteredAdhrno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_farmerlist, container, false);

        svserch = view.findViewById(R.id.searchView);
        rvlist = view.findViewById(R.id.rvrequestworkall);
        id = new ArrayList<>();
        name = new ArrayList<>();
        number = new ArrayList<>();
        address = new ArrayList<>();
        age = new ArrayList<>();
        adhrno = new ArrayList<>();
        filteredName = new ArrayList<>();
        filteredNumber = new ArrayList<>();
        filteredAddress = new ArrayList<>();
        filteredAge = new ArrayList<>();
        filteredAdhrno = new ArrayList<>();

        farmerRegistration = new FarmerRegistration(getActivity());
        rvlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter with filtered data
        adapterFarmer = new AdapterFarmer(id, filteredName, filteredNumber, filteredAddress, filteredAge, filteredAdhrno, getActivity());
        rvlist.setAdapter(adapterFarmer);

        displaydata();

        // Setting up SearchView listener
        svserch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });

        return view;
    }

    private void displaydata() {
        Cursor cursor = farmerRegistration.getAllUsers();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id.add(cursor.getString(cursor.getColumnIndex("id")));
                name.add(cursor.getString(cursor.getColumnIndex("name")));
                number.add(cursor.getString(cursor.getColumnIndex("mobileno")));
                address.add(cursor.getString(cursor.getColumnIndex("address")));
                age.add(cursor.getString(cursor.getColumnIndex("age")));
                adhrno.add(cursor.getString(cursor.getColumnIndex("adharno")));
            } while (cursor.moveToNext());
            cursor.close();
        }
        // Copy original data to filtered lists
        filteredName.addAll(name);
        filteredNumber.addAll(number);
        filteredAddress.addAll(address);
        filteredAge.addAll(age);
        filteredAdhrno.addAll(adhrno);

        // Notify the adapter that data has been loaded
        adapterFarmer.notifyDataSetChanged();
    }

    private void filterData(String query) {
        // Clear filtered lists to store the filtered results
        filteredName.clear();
        filteredNumber.clear();
        filteredAddress.clear();
        filteredAge.clear();
        filteredAdhrno.clear();

        if (query.isEmpty()) {
            // If the query is empty, show all data
            filteredName.addAll(name);
            filteredNumber.addAll(number);
            filteredAddress.addAll(address);
            filteredAge.addAll(age);
            filteredAdhrno.addAll(adhrno);
        } else {
            // Filter data based on the query
            for (int i = 0; i < name.size(); i++) {
                if (name.get(i).toLowerCase().contains(query.toLowerCase())) {
                    filteredName.add(name.get(i));
                    filteredNumber.add(number.get(i));
                    filteredAddress.add(address.get(i));
                    filteredAge.add(age.get(i));
                    filteredAdhrno.add(adhrno.get(i));
                }
            }
        }

        // Notify the adapter that the data has been filtered
        adapterFarmer.notifyDataSetChanged();
    }
}
