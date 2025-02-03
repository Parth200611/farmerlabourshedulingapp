package com.mountreachsolution.farmlabourscheduling.FARMER;

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
import com.mountreachsolution.farmlabourscheduling.DATABASE.REjectedRequest;
import com.mountreachsolution.farmlabourscheduling.DATABASE.WaitingRequest;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Workrequestdatabse;
import com.mountreachsolution.farmlabourscheduling.LABOUR.WorkDetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.io.File;

public class RequestDetails extends AppCompatActivity {

    ImageView ivimage;

    TextView tvname,tvaddress,tvstrat,tvend,tvdate,tvlabourrequire,tvwages,tvcrop,tvskill,tvworkbname,tvage;
    AppCompatButton btnacfcept,btnreject,btnkeepwaiting;

    String id;
    Workrequestdatabse workrequestdatabse;
    String id1, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image,labour;
    String labourname,labournumber,labouraddress,labourskill,labouradhar,labourage;
    Acceptedworkd acceptedworkd;
    REjectedRequest rEjectedRequest;
    WaitingRequest waitingRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_details);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(RequestDetails.this,R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(RequestDetails.this,R.color.white));
        id=getIntent().getStringExtra("requestid");
        
        acceptedworkd=new Acceptedworkd(RequestDetails.this);
        rEjectedRequest=new REjectedRequest(RequestDetails.this);
        waitingRequest = new WaitingRequest(RequestDetails.this);

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

        btnacfcept=findViewById(R.id.btnapply);
        btnreject=findViewById(R.id.btnreject);
        btnkeepwaiting=findViewById(R.id.btnkeppwaiting);
        workrequestdatabse =new Workrequestdatabse(RequestDetails.this);

        btnacfcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insertdata();

            }
        });
        btnreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reject(id);
            }
        });
        btnkeepwaiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keppwait(id);
            }
        });

        loaddata(id);

    }

    private void keppwait(String id) {
        long answer=waitingRequest.insertData(name,mobileno,address,work,wages,starttime,endtime,workdate,crop,image,labour,labourname,labournumber,labouraddress,labourskill,labouradhar,labourage);
        if (answer != -1) {
            Toast.makeText(RequestDetails.this, "Request Keep Waiting!", Toast.LENGTH_SHORT).show();
            int deleterow = workrequestdatabse.deleteDataById(id);

            // Show Toast message based on the result
            if (deleterow > 0) {
                Intent i = new Intent(RequestDetails.this, Allrequest.class);
                startActivity(i);
            } else {

            }

        } else {
            // Show a failure Toast
            Toast.makeText(RequestDetails.this, "Data insertion failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void reject(String id) {
        long answer=rEjectedRequest.insertData(name,mobileno,address,work,wages,starttime,endtime,workdate,crop,image,labour,labourname,labournumber,labouraddress,labourskill,labouradhar,labourage);
        if (answer != -1) {
            Toast.makeText(RequestDetails.this, "Request Rejected!", Toast.LENGTH_SHORT).show();
            deteletfromworkrequest(id);
        } else {
            // Show a failure Toast
            Toast.makeText(RequestDetails.this, "Data insertion failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void Insertdata() {
        long result = acceptedworkd.insertData(name,mobileno,address,work,wages,starttime,endtime,workdate,crop,image,labour,labourname,labournumber,labouraddress,labourskill,labouradhar,labourage);
        
        if (result != -1) {
            Toast.makeText(RequestDetails.this, "Request Accepted!", Toast.LENGTH_SHORT).show();
            deteletfromworkrequest(id);
        } else {
            // Show a failure Toast
            Toast.makeText(RequestDetails.this, "Data insertion failed.", Toast.LENGTH_SHORT).show();
        }
        
    }

    private void deteletfromworkrequest(String id) {
        int rowsDeleted = workrequestdatabse.deleteDataById(id);

        // Show Toast message based on the result
        if (rowsDeleted > 0) {
           Intent i = new Intent(RequestDetails.this, FarmerHomepage.class);
           startActivity(i);
        } else {

        }
    }

    private void loaddata(String id) {
        Cursor cursor = workrequestdatabse.getRequestDEtails(id);
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("farmername"));
            mobileno = cursor.getString(cursor.getColumnIndexOrThrow("farmermobileno"));
            address = cursor.getString(cursor.getColumnIndexOrThrow("farmeraddress"));
            work = cursor.getString(cursor.getColumnIndexOrThrow("workname"));
            crop = cursor.getString(cursor.getColumnIndexOrThrow("crop"));
            wages = cursor.getString(cursor.getColumnIndexOrThrow("wages"));
            image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
            starttime = cursor.getString(cursor.getColumnIndexOrThrow("starttime"));
            endtime = cursor.getString(cursor.getColumnIndexOrThrow("endtime"));
            workdate = cursor.getString(cursor.getColumnIndexOrThrow("workdate"));
            labourname = cursor.getString(cursor.getColumnIndexOrThrow("labourname"));
            labournumber = cursor.getString(cursor.getColumnIndexOrThrow("labourmobileno"));
            labouraddress = cursor.getString(cursor.getColumnIndexOrThrow("labouraddress"));
            labourskill = cursor.getString(cursor.getColumnIndexOrThrow("labourskill"));
            labourage = cursor.getString(cursor.getColumnIndexOrThrow("labourage"));
            labouradhar = cursor.getString(cursor.getColumnIndexOrThrow("labouradhrno"));
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
            Log.d("ImagePicker", "No saved image for user " + ivimage);
        }
    }
}