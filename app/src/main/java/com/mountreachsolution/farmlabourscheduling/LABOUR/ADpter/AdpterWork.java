package com.mountreachsolution.farmlabourscheduling.LABOUR.ADpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.LABOUR.WorkDetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;

public class AdpterWork extends RecyclerView.Adapter<AdpterWork.ViewHolder> {
     ArrayList<String> id, name, mobileno, address, workname, wages, starttime, endtime, workdate,crop;
     Context context;

    public AdpterWork(ArrayList<String> id, ArrayList<String> name, ArrayList<String> mobileno, ArrayList<String> address, ArrayList<String> workname, ArrayList<String> wages, ArrayList<String> starttime, ArrayList<String> endtime, ArrayList<String> workdate, ArrayList<String> crop, Context context) {
        this.id = id;
        this.name = name;
        this.mobileno = mobileno;
        this.address = address;
        this.workname = workname;
        this.wages = wages;
        this.starttime = starttime;
        this.endtime = endtime;
        this.workdate = workdate;
        this.crop = crop;
        this.context = context;
    }

    @NonNull
    @Override
    public AdpterWork.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.postwork,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterWork.ViewHolder holder, int position) {

        holder.tvwork.setText(workname.get(position));
        holder.tvname.setText(name.get(position));
        holder.tvnumber.setText(mobileno.get(position));
        holder.tvaddress.setText(address.get(position));
        holder.tvstarttime.setText(starttime.get(position));
        holder.tvendtime.setText(endtime.get(position));
        holder.tvdate.setText(workdate.get(position));
        holder.tvwasge.setText(wages.get(position));
        holder.tvcrop.setText(crop.get(position));

        // Set onClickListener for cardView
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, WorkDetails.class);
                i.putExtra("workid", id.get(position)); // Pass the correct work ID
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivimage;
        TextView tvwork, tvname, tvnumber, tvaddress, tvstarttime, tvendtime, tvdate, tvwasge,tvcrop;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvwork = itemView.findViewById(R.id.tv_work_description);
            tvname = itemView.findViewById(R.id.tvname);
            tvnumber = itemView.findViewById(R.id.tvMobileno);
            tvaddress = itemView.findViewById(R.id.tvaddress);
            tvstarttime = itemView.findViewById(R.id.tvstartTime);
            tvendtime = itemView.findViewById(R.id.tvendtime);
            tvdate = itemView.findViewById(R.id.tvDate);
            tvwasge = itemView.findViewById(R.id.tvwasgae);
            tvcrop = itemView.findViewById(R.id.tvCrop);
            cardView = itemView.findViewById(R.id.carsView);


        }
    }
}
