package com.mountreachsolution.farmlabourscheduling.FARMER.Adpter;

import android.content.Context;
import android.content.Intent;
import android.nfc.cardemulation.CardEmulation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.FARMER.RequestDetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;

public class AdpterRequestWork extends RecyclerView.Adapter<AdpterRequestWork.ViewHolder> {
    ArrayList<String> id, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image,labour,labourname,labournumber,labouraddress,labourskill,labouradhar,labourage;
    Context context;

    public AdpterRequestWork(ArrayList id, ArrayList name, ArrayList mobileno, ArrayList address, ArrayList work, ArrayList wages, ArrayList starttime, ArrayList endtime, ArrayList workdate, ArrayList crop, ArrayList image, ArrayList labour, ArrayList labourname, ArrayList labournumber, ArrayList labouraddress, ArrayList labourskill, ArrayList labouradhar, ArrayList labourage, Context context) {
        this.id = id;
        this.name = name;
        this.mobileno = mobileno;
        this.address = address;
        this.work = work;
        this.wages = wages;
        this.starttime = starttime;
        this.endtime = endtime;
        this.workdate = workdate;
        this.crop = crop;
        this.image = image;
        this.labour = labour;
        this.labourname = labourname;
        this.labournumber = labournumber;
        this.labouraddress = labouraddress;
        this.labourskill = labourskill;
        this.labouradhar = labouradhar;
        this.labourage = labourage;
        this.context = context;
    }

    @NonNull
    @Override
    public AdpterRequestWork.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.requesttdesgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterRequestWork.ViewHolder holder, int position) {
        holder.tvworkname.setText(work.get(position));
        holder.tvlabourname.setText(labourname.get(position));
        holder.tvlabouraddress.setText(labouraddress.get(position));
        holder.tvstart.setText(starttime.get(position));
        holder.tvend.setText(endtime.get(position));
        holder.tvdate.setText(workdate.get(position));
        holder.tvwages.setText(wages.get(position));
        holder.tvcrop.setText(crop.get(position));
        holder.tvskill.setText(labourskill.get(position));
        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, RequestDetails.class);
                i.putExtra("requestid", id.get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvworkname,tvlabourname,tvlabouraddress,tvskill,tvcrop,tvstart,tvend,tvdate,tvwages;
        CardView cvcard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvworkname=itemView.findViewById(R.id.tv_work_description);
            tvlabourname=itemView.findViewById(R.id.tvname);
            tvlabouraddress=itemView.findViewById(R.id.tvaddress);
            tvstart=itemView.findViewById(R.id.tvstartTime);
            tvend=itemView.findViewById(R.id.tvendtime);
            tvdate=itemView.findViewById(R.id.tvDate);
            tvwages=itemView.findViewById(R.id.tvwasgae);
            tvcrop=itemView.findViewById(R.id.tvCrop);
            tvskill=itemView.findViewById(R.id.tvskill);
            cvcard=itemView.findViewById(R.id.cardview2);
        }
    }
}
