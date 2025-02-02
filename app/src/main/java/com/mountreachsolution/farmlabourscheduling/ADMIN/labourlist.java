package com.mountreachsolution.farmlabourscheduling.ADMIN;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.mountreachsolution.farmlabourscheduling.ADMIN.Adpter.AdapterFarmer;
import com.mountreachsolution.farmlabourscheduling.ADMIN.Adpter.Adpterlabour;
import com.mountreachsolution.farmlabourscheduling.DATABASE.FarmerRegistration;
import com.mountreachsolution.farmlabourscheduling.DATABASE.LabourREgistration;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;
import java.util.List;


public class labourlist extends Fragment {
    SearchView svserch;
    RecyclerView rvlist;
    LabourREgistration labourREgistration;
    Adpterlabour adpterlabour;

    private UserAdapter adapter;
    private List<User> userList;
    ArrayList<String> id, name, number, address, age, adhrno, skill;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_labourlist, container, false);

        svserch = view.findViewById(R.id.searchView);
        rvlist = view.findViewById(R.id.recyclerView);
        userList = new ArrayList<>();
        adapter = new UserAdapter(userList,getActivity());

        rvlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvlist.setAdapter(adapter);


        // Initialize the lists
        id = new ArrayList<>();
        name = new ArrayList<>();
        number = new ArrayList<>();
        address = new ArrayList<>();
        age = new ArrayList<>();
        adhrno = new ArrayList<>();
        skill = new ArrayList<>();

        // Initialize the database helper class
        labourREgistration = new LabourREgistration(getActivity());

        // Set up the RecyclerView
//        rvlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter with the data
//        adpterlabour = new Adpterlabour(id, name, number, address, age, adhrno, skill, getActivity());
//        rvlist.setAdapter(adpterlabour);

        // Load the data from the database


        // Set up SearchView to filter the list
        Cursor cursor = labourREgistration.getAllUsers();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String mobile = cursor.getString(cursor.getColumnIndex("mobileno"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String age = cursor.getString(cursor.getColumnIndex("age"));
                String skill = cursor.getString(cursor.getColumnIndex("skill"));
                String adharno = cursor.getString(cursor.getColumnIndex("adharno"));

                User user = new User(id, name, mobile, address, age, skill, adharno);
                userList.add(user);
            } while (cursor.moveToNext());

            cursor.close();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "No users found", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        labourREgistration.close();
    }



}
