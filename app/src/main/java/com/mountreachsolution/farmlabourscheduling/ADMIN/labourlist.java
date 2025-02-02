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
    UserAdapter adapter;
    List<User> userList, filteredList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_labourlist, container, false);

        svserch = view.findViewById(R.id.searchView);
        rvlist = view.findViewById(R.id.recyclerView);
        userList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new UserAdapter(filteredList, getActivity());
        rvlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvlist.setAdapter(adapter);

        labourREgistration = new LabourREgistration(getActivity());
        loadUserData();

        svserch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Not needed
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        return view;
    }

    private void loadUserData() {
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
            filteredList.addAll(userList);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "No users found", Toast.LENGTH_SHORT).show();
        }
    }

    private void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(userList);
        } else {
            for (User user : userList) {
                if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
                        user.getMobile().contains(query)) {
                    filteredList.add(user);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        labourREgistration.close();
    }
}
