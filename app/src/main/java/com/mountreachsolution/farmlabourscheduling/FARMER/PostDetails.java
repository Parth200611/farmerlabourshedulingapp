package com.mountreachsolution.farmlabourscheduling.FARMER;

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
import com.mountreachsolution.farmlabourscheduling.DATABASE.Postwork;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Workrequestdatabse;
import com.mountreachsolution.farmlabourscheduling.LABOUR.WorkDetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.io.File;

public class PostDetails extends AppCompatActivity {
    ImageView ivimage;
    TextView tvwork,tvname,tvlabour,tvaddress,tvstarttime,tvendtime,tvdate,tvwages,tvcrop;
    String id;
    Button btndelete;
    String id1, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image,labour;
    String labourname,labournumber,labouraddress,labourskill,labouradhar,labourage;

    Workrequestdatabse workrequestdatabse;
    Postwork postwork;
    AppCompatButton btnupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_details);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(PostDetails.this,R.color.lightbrown));
        getWindow().setNavigationBarColor(ContextCompat.getColor(PostDetails.this,R.color.white));
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
        btndelete=findViewById(R.id.btnremovepost);
        btnupdate=findViewById(R.id.btnUpdate);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostDetails.this,updatePost.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        postwork=new Postwork(PostDetails.this);
        
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost(id);
            }
        });

        fetchWorkPostingById(id);
      

    }

    private void deletePost(String id) {
        postwork.deleteWorkPostingById(id);
        Intent i = new Intent(PostDetails.this,AllPostwork.class);
        startActivity(i);
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