package com.mountreachsolution.farmlabourscheduling.LABOUR;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Postwork;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Workrequestdatabse;
import com.mountreachsolution.farmlabourscheduling.FARMER.FarmerHomepage;
import com.mountreachsolution.farmlabourscheduling.R;

public class WorkDetails extends AppCompatActivity {
    ImageView ivimage;
    TextView tvwork,tvname,tvlabour,tvaddress,tvstarttime,tvendtime,tvdate,tvwages,tvcrop;
    String id;
    Button btnApply;
    String id1, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image,labour;
    String labourname,labournumber,labouraddress,labourskill,labouradhar,labourage;

    Workrequestdatabse workrequestdatabse;
    Postwork postwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_work_details);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(WorkDetails.this,R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(WorkDetails.this,R.color.white));
        id=getIntent().getStringExtra("workid");
        // Log the retrieved ID to check its value
        Log.d("MainActivity", "Received work ID: " + id);

        ivimage=findViewById(R.id.ivworkimage);
        tvwork=findViewById(R.id.tvworkd);
        tvname=findViewById(R.id.tvname);
        tvaddress=findViewById(R.id.tvaddress);
        tvstarttime=findViewById(R.id.tvstartTime);
        tvendtime=findViewById(R.id.tvendtime);
        tvdate=findViewById(R.id.tvDate);
        tvwages=findViewById(R.id.tvwasgae);
        tvlabour=findViewById(R.id.tvlabourrequired);
        tvcrop=findViewById(R.id.tvcrop);
        btnApply=findViewById(R.id.btnapply);

        postwork=new Postwork(WorkDetails.this);
      workrequestdatabse=new Workrequestdatabse(WorkDetails.this);

      btnApply.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              SharedPreferences sharedPreferences = getSharedPreferences("labourdata", Context.MODE_PRIVATE);

             labourname = sharedPreferences.getString("labourName", "N/A");
              labournumber = sharedPreferences.getString("labourMobile", "N/A");
              labourage = sharedPreferences.getString("age", "N/A");
             labouraddress = sharedPreferences.getString("labouraddress", "N/A");
              labouradhar = sharedPreferences.getString("labouradhsr", "N/A");
              labourskill = sharedPreferences.getString("labourskill", "N/A");
              Applyforwork();


          }
      });

      loadimage(id);

        fetchWorkPostingById(id);
        //loadProfileImage();

    }

    private void loadimage(String id) {
         image = postwork.getImageById(id); // Fetch image for ID = 1

        if (image != null) {
            Glide.with(this)
                    .load(image)
                    .into(ivimage); // Load image into ImageView using Glide
        } else {
            ivimage.setImageResource(R.drawable.baseline_person_24); // Set a default image if no image is found
        }
    }

    private void Applyforwork() {
        long result = workrequestdatabse.insertData(name,mobileno,address,work,wages,starttime,endtime,workdate,crop,image,labour,
                labourname,labournumber,labouraddress,labourskill,labourage,labouradhar);

        // Show a toast message
        if (result != -1) {
            Toast.makeText(this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(WorkDetails.this, LabourHomepage.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchWorkPostingById(String id) {

            Cursor cursor = postwork.getWorkPostingById(id);

            if (cursor != null && cursor.moveToFirst()) {
                 name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                 mobileno = cursor.getString(cursor.getColumnIndexOrThrow("number"));
                 address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                 work = cursor.getString(cursor.getColumnIndexOrThrow("working_short"));
                 crop = cursor.getString(cursor.getColumnIndexOrThrow("crop_name"));
                 wages = cursor.getString(cursor.getColumnIndexOrThrow("wages"));
                 image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                 starttime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
                 endtime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));
                 workdate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                 labour = cursor.getString(cursor.getColumnIndexOrThrow("labour_required"));

                // Log the values (Optional)

                tvwork.setText(work);  tvcrop.setText(crop);  tvname.setText(name);  tvaddress.setText(address);
                tvstarttime.setText(starttime);  tvendtime.setText(endtime);  tvwages.setText(wages);  tvlabour.setText(labour);
                tvdate.setText(workdate);


                cursor.close();
            } else {
                Log.e("WorkDetails", "No data found for ID: " + id);
            }
        }
    }
