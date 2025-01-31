package com.mountreachsolution.farmlabourscheduling.FARMER;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
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

import com.mountreachsolution.farmlabourscheduling.DATABASE.Acceptedworkd;
import com.mountreachsolution.farmlabourscheduling.DATABASE.REjectedRequest;
import com.mountreachsolution.farmlabourscheduling.DATABASE.WaitingRequest;
import com.mountreachsolution.farmlabourscheduling.DATABASE.Workrequestdatabse;
import com.mountreachsolution.farmlabourscheduling.R;

public class DetailsRequest extends AppCompatActivity {
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
        setContentView(R.layout.activity_details_request);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setNavigationBarColor(ContextCompat.getColor(DetailsRequest.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(DetailsRequest.this,R.color.lightbrown));
        id=getIntent().getStringExtra("requestid");

        acceptedworkd=new Acceptedworkd(DetailsRequest.this);

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


        btdonewithwork=findViewById(R.id.btndonewithwork);
        acceptedworkd =new Acceptedworkd(DetailsRequest.this);

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
            Toast.makeText(DetailsRequest.this, "Work Done!!", Toast.LENGTH_SHORT).show();
            Intent i= new Intent(DetailsRequest.this,FarmerHomepage.class);
            startActivity(i);
        } else {
            Toast.makeText(DetailsRequest.this, "No record found with this ID", Toast.LENGTH_SHORT).show();
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


            cursor.close();
        } else {
            Log.e("WorkDetails", "No data found for ID: " + id);
        }
    }
}