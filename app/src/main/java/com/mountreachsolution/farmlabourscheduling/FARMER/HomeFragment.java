package com.mountreachsolution.farmlabourscheduling.FARMER;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.mountreachsolution.farmlabourscheduling.R;


public class HomeFragment extends Fragment {

   SearchView svserach;
   TextView tvnorequest;
   RecyclerView rvlistofrequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
}