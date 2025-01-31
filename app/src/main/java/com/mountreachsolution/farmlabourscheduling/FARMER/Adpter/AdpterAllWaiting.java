package com.mountreachsolution.farmlabourscheduling.FARMER.Adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.FARMER.DetailsRequest;
import com.mountreachsolution.farmlabourscheduling.FARMER.Waitingdetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;

public class AdpterAllWaiting extends RecyclerView.Adapter<AdpterAllWaiting.ViewHolder>
{
    ArrayList<String> id, name, mobileno, address, work, wages, starttime, endtime, workdate, crop, image,labour,labourname,labournumber,labouraddress,labourskill,labouradhar,labourage;
    Context context;

    public AdpterAllWaiting(ArrayList<String> id, ArrayList<String> name, ArrayList<String> mobileno, ArrayList<String> address, ArrayList<String> work, ArrayList<String> wages, ArrayList<String> starttime, ArrayList<String> endtime, ArrayList<String> workdate, ArrayList<String> crop, ArrayList<String> image, ArrayList<String> labour, ArrayList<String> labourname, ArrayList<String> labournumber, ArrayList<String> labouraddress, ArrayList<String> labourskill, ArrayList<String> labouradhar, ArrayList<String> labourage, Context context) {
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
    public AdpterAllWaiting.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.requesttdesgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterAllWaiting.ViewHolder holder, int position) {

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
                Intent i=new Intent(context, Waitingdetails.class);
                i.putExtra("requestid", id.get(position)); // Pass the correct work ID
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
