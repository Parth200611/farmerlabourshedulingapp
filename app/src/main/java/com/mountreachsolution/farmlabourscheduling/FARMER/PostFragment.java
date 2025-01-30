package com.mountreachsolution.farmlabourscheduling.FARMER;

import static android.app.Activity.RESULT_OK;


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


    private static final int PICK_IMAGE_REQUEST = 1; // Request code for picking image
    private static final int PICK_IMAGE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Userdata", Context.MODE_PRIVATE);

        // Fetch data using the keys
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
                        // Format the selected date as a String
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        // Set the selected date in the EditText
                        etworkingdate.setText(selectedDate);

                        // Store the selected date in a String variable
                         strdate = selectedDate; // Store date in String variable for further use
                    }
                }, year, month, day);

// Show the DatePickerDialog
        etworkingdate.setOnClickListener(v -> datePickerDialog.show());





        Spinner spinnerWorkName = view.findViewById(R.id.spinnerWorkName);
        Spinner spinnerCropName = view.findViewById(R.id.spinnerCropName);

// Work Name List
        String[] workNames = {"Harvesting", "Seeding", "Weeding", "Fertilization", "Planting", "Spraying"};
        ArrayAdapter<String> workAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, workNames);
        workAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkName.setAdapter(workAdapter);

// Crop Name List
        String[] cropNames = {"Gram", "Peanuts", "Wheat", "Bajra", "Onion", "Garlic", "Chillies", "Corn", "Cotton"};
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cropNames);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropName.setAdapter(cropAdapter);

// Get selected values
        spinnerWorkName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 strworkname = workNames[position];
                // Do something with selectedWork
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        spinnerCropName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               cropname= cropNames[position];
                // Do something with selectedCrop
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });








        postwork = new Postwork(getContext());
        btnaddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

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

                    // Show Toast if any field is empty
                    Toast.makeText(getActivity(), "Please enter all data properly.", Toast.LENGTH_SHORT).show();
                } else {
                    // Execute method for posting the work if all fields are filled
                    executePostWorkMethod();
                }


            }
        });



        return view;
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
        ivwork.setImageResource(R.drawable.baseline_camera_alt_24); // Set a placeholder image
        imagepath = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the result is OK and we have a valid data
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Display the selected image in ImageView
            ivwork.setImageURI(imageUri);

            // Convert the image URI to String and insert it into the database
            // Use the appropriate number here
            imagepath=imageUri.toString();

            // Optionally, show a toast or message indicating that the image is uploaded
            Toast.makeText(getActivity(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    // Getters for the selected values

}