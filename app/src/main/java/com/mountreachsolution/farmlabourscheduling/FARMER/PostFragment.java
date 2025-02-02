package com.mountreachsolution.farmlabourscheduling.FARMER;

import static android.app.Activity.RESULT_OK;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mountreachsolution.farmlabourscheduling.DATABASE.Postwork;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.Calendar;


public class PostFragment extends Fragment {
    EditText etworkname, etlabourrequired, etaddress, etwages, etsatrttime, etendtime, etworkingdate,etcropname;
    Button btnaddimage;
    ImageView ivwork;
    AppCompatButton btnPost;
    String strname, strmobileno, strworkname, strlabour, straddress, strwages, strstartimae, strendtime, strdate,cropname,imagepath;

    Postwork postwork;
    Uri imageUri;


    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Userdata", Context.MODE_PRIVATE);


        strname = sharedPreferences.getString("userName", "Default Name");
        strmobileno = sharedPreferences.getString("userMobile", "Default Mobile");
        Log.d("UserData", "Fetched Name: " + strname);
        Log.d("UserData", "Fetched Mobile: " + strmobileno);


        etlabourrequired = view.findViewById(R.id.etrequiredlabour);
        etaddress = view.findViewById(R.id.etworkaddress);
        etwages = view.findViewById(R.id.etwages);
        etsatrttime = view.findViewById(R.id.etstarrttime);
        etendtime = view.findViewById(R.id.etendtime);
        etworkingdate = view.findViewById(R.id.etworkingdate);
        ivwork = view.findViewById(R.id.ivworkimage);
        btnaddimage = view.findViewById(R.id.btnaddworkimage);
        btnPost = view.findViewById(R.id.btnPostwork);
        TextView textView=view.findViewById(R.id.tvnameo);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {

                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;


                        etworkingdate.setText(selectedDate);


                         strdate = selectedDate;
                    }
                }, year, month, day);


        etworkingdate.setOnClickListener(v -> datePickerDialog.show());





        Spinner spinnerWorkName = view.findViewById(R.id.spinnerWorkName);
        Spinner spinnerCropName = view.findViewById(R.id.spinnerCropName);


        String[] workNames = {"Harvesting", "Seeding", "Weeding", "Fertilization", "Planting", "Spraying"};
        ArrayAdapter<String> workAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, workNames);
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkName.setAdapter(workAdapter);


        String[] cropNames = {"Gram", "Peanuts", "Wheat", "Bajra", "Onion", "Garlic", "Chillies", "Corn", "Cotton"};
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cropNames);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropName.setAdapter(cropAdapter);


        spinnerWorkName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 strworkname = workNames[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spinnerCropName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               cropname= cropNames[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });








        postwork = new Postwork(getContext());
         btnaddimage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Opengallerey();
             }
         });




        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strlabour = etlabourrequired.getText().toString();
                straddress = etaddress.getText().toString();
                strwages = etwages.getText().toString();
                strstartimae = etsatrttime.getText().toString();
                strendtime = etendtime.getText().toString();

                if (strname.isEmpty() || strmobileno.isEmpty() || strworkname.isEmpty() || strlabour.isEmpty() || straddress.isEmpty() ||
                        strwages.isEmpty() || strstartimae.isEmpty() || strendtime.isEmpty() || strdate.isEmpty() || cropname.isEmpty()) {


                    Toast.makeText(getActivity(), "Please enter all data properly.", Toast.LENGTH_SHORT).show();
                } else {

                    executePostWorkMethod();
                }


            }
        });



        return view;
    }

    private void Opengallerey() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    private void executePostWorkMethod() {
        postwork.insertPostwork(strname,strmobileno,straddress,strworkname,cropname,strwages,imagepath,strstartimae,strendtime,strdate,strlabour);
        Toast.makeText(getActivity(), "Work Posted", Toast.LENGTH_SHORT).show();
        clearedittext();
        
    }

    private void clearedittext() {
        etaddress.setText("");
        etlabourrequired.setText("");
        etwages.setText("");
        etsatrttime.setText("");
        etendtime.setText("");
        etworkingdate.setText("");
        ivwork.setImageResource(R.drawable.baseline_camera_alt_24);
        imagepath = null;
    }







}