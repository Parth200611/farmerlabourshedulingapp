package com.mountreachsolution.farmlabourscheduling.LABOUR;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.mountreachsolution.farmlabourscheduling.DATABASE.Acceptedworkd;
import com.mountreachsolution.farmlabourscheduling.FARMER.Adpter.Adpterallrequest;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;


public class labourshedul extends Fragment {
    SearchView svserach;
    RecyclerView rvlist;
    TextView tvNorequest;

    ArrayList<String> id1, name1, mobileno1, address1, work1, wages1, starttime1, endtime1, workdate1, crop1, image1, labour1,
            labourname1, labournumber1, labouraddress1, labourskill1, labouradhar1, labourage1;

    String monilenumber;
    Adpterallrequest adpterRequestWork;
    Acceptedworkd acceptedworkd;

    ArrayList<String> filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages, filteredStarttime,
            filteredEndtime, filteredWorkdate, filteredCrop;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_labourshedul, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("labourdata", Context.MODE_PRIVATE);
        monilenumber = sharedPreferences.getString("labourMobile", "");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        svserach = view.findViewById(R.id.searchView);
        rvlist =view.findViewById(R.id.rvrequestworkall);
        tvNorequest = view.findViewById(R.id.tvnorequest);
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
        acceptedworkd = new Acceptedworkd(getContext());

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

        adpterRequestWork = new Adpterallrequest(filteredId, filteredName, filteredMobileno, filteredAddress, filteredWork, filteredWages,
                filteredStarttime, filteredEndtime, filteredWorkdate, filteredCrop, image1, labour1,
                labourname1, labournumber1, labouraddress1, labourskill1, labouradhar1, labourage1, getActivity());
        rvlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvlist.setAdapter(adpterRequestWork);

        getalldata(monilenumber);

        // Setup SearchView
        setupSearchView();
        return view;
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

        Cursor cursor = acceptedworkd.getRequestLa(monilenumber);
        // Change this to the actual farmer mobile number

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