package com.mountreachsolution.farmlabourscheduling.LABOUR;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Acceptedworkd;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Workrequestdatabse;
import com.mountreachsolution.farmlabourscheduling.FARMER.DetailsRequest;
import com.mountreachsolution.farmlabourscheduling.FARMER.FarmerHomepage;
import com.mountreachsolution.farmlabourscheduling.R;

import java.io.File;

public class LabourWorksheduldetails extends AppCompatActivity {
    ImageView ivimage;

    TextView tvname,tvaddress,tvstrat,tvend,tvdate,tvlabourrequire,tvwages,tvcrop,tvskill,tvworkbname,tvage;
    AppCompatButton btdonewithwork;


    String id;
    Workrequestdatabse workrequestdatabse;
    String id1, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image,labour;
    String labourname,labournumber,labouraddress,labourskill,labouradhar,labourage;
    Acceptedworkd acceptedworkd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_labour_worksheduldetails);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setNavigationBarColor(ContextCompat.getColor(LabourWorksheduldetails.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(LabourWorksheduldetails.this,R.color.lightbrown));
        id=getIntent().getStringExtra("requestid");

        acceptedworkd=new Acceptedworkd(LabourWorksheduldetails.this);

        tvworkbname=findViewById(R.id.tvworkd);
        tvname=findViewById(R.id.tvname);
        tvaddress=findViewById(R.id.tvaddress);
        tvstrat=findViewById(R.id.tvstartTime);
        tvend=findViewById(R.id.tvendtime);
        tvlabourrequire=findViewById(R.id.tvlabourrequired);
        tvwages=findViewById(R.id.tvwasgae);
        tvskill=findViewById(R.id.tvskill);
        tvdate=findViewById(R.id.tvDate);
        tvcrop=findViewById(R.id.tvcrop);
        tvage=findViewById(R.id.tvage);
        ivimage=findViewById(R.id.ivworkimage);


        btdonewithwork=findViewById(R.id.btndonewithwork);
        acceptedworkd =new Acceptedworkd(LabourWorksheduldetails.this);

        btdonewithwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRequest(id);
            }
        });


        loaddata(id);

    }

    private void deleteRequest(String id) {

        int deletedRows = acceptedworkd.deleteDataById(id);

        if (deletedRows > 0) {
            Toast.makeText(LabourWorksheduldetails.this, "Work Done!!", Toast.LENGTH_SHORT).show();
            Intent i= new Intent(LabourWorksheduldetails.this, LabourHomepage.class);
            startActivity(i);
        } else {
            Toast.makeText(LabourWorksheduldetails.this, "No record found with this ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddata(String id) {
        Cursor cursor = acceptedworkd.getRequestALL(id);
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            mobileno = cursor.getString(cursor.getColumnIndexOrThrow("mobileno"));
            address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            work = cursor.getString(cursor.getColumnIndexOrThrow("work"));
            crop = cursor.getString(cursor.getColumnIndexOrThrow("crop"));
            wages = cursor.getString(cursor.getColumnIndexOrThrow("wages"));
            image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
            starttime = cursor.getString(cursor.getColumnIndexOrThrow("starttime"));
            endtime = cursor.getString(cursor.getColumnIndexOrThrow("endtime"));
            workdate = cursor.getString(cursor.getColumnIndexOrThrow("workdate"));
            labourname = cursor.getString(cursor.getColumnIndexOrThrow("labourname"));
            labournumber = cursor.getString(cursor.getColumnIndexOrThrow("labournumber"));
            labouraddress = cursor.getString(cursor.getColumnIndexOrThrow("labouraddress"));
            labourskill = cursor.getString(cursor.getColumnIndexOrThrow("labourskill"));
            labourage = cursor.getString(cursor.getColumnIndexOrThrow("labourage"));
            labouradhar = cursor.getString(cursor.getColumnIndexOrThrow("labouradhar"));
            labour = cursor.getString(cursor.getColumnIndexOrThrow("labour"));

            // Log the values (Optional)

            tvworkbname.setText(work);
            tvname.setText(labourname);
            tvaddress.setText(labouraddress);
            tvstrat.setText(starttime);
            tvend.setText(endtime);
            tvdate.setText(workdate);
            tvlabourrequire.setText(labour);
            tvwages.setText(wages);
            tvcrop.setText(crop);
            tvskill.setText(labourskill);
            tvage.setText(labourage);
            loadImage();


            cursor.close();
        } else {
            Log.e("WorkDetails", "No data found for ID: " + id);
        }
    }

    private void loadImage() {
        // Get the saved image path for the logged-in user
        SharedPreferences sharedPreferences = getSharedPreferences("workImages", Context.MODE_PRIVATE);
        String savedImagePath = sharedPreferences.getString("image_uri_" + work, null);

        if (savedImagePath != null) {
            // Image exists, load it
            File imgFile = new File(savedImagePath);
            if (imgFile.exists()) {
                Glide.with(this)
                        .load(imgFile)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivimage);
                Log.d("ImagePicker", "Image loaded from internal storage: " + savedImagePath);
            }
        } else {
            // No image selected yet, set a placeholder or show nothing
            Glide.with(this)
                    .load(R.drawable.baseline_person_24)  // Replace with your placeholder image
                    .into(ivimage);
            Log.d("ImagePicker", "No saved image for user " + work);
        }
    }
}