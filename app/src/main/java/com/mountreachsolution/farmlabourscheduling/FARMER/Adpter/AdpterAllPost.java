package com.mountreachsolution.farmlabourscheduling.FARMER.Adpter;

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

import com.mountreachsolution.farmlabourscheduling.FARMER.PostDetails;
import com.mountreachsolution.farmlabourscheduling.LABOUR.WorkDetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;

public class AdpterAllPost extends RecyclerView.Adapter<AdpterAllPost.Viewholder> {
    ArrayList<String> id, name, mobileno, address, workname, wages, starttime, endtime, workdate,crop;
    Context context;

    public AdpterAllPost(ArrayList<String> id, ArrayList<String> name, ArrayList<String> mobileno, ArrayList<String> address, ArrayList<String> workname, ArrayList<String> wages, ArrayList<String> starttime, ArrayList<String> endtime, ArrayList<String> workdate, ArrayList<String> crop, Context context) {
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
    public AdpterAllPost.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.postwork,parent,false);
        return new Viewholder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterAllPost.Viewholder holder, int position) {
        holder.tvwork.setText(workname.get(position));
        holder.tvname.setText(name.get(position));
//        holder.tvnumber.setText(mobileno.get(position));
        holder.tvaddress.setText(address.get(position));
        holder.tvstarttime.setText(starttime.get(position));
        holder.tvendtime.setText(endtime.get(position));
        holder.tvdate.setText(workdate.get(position));
        holder.tvwasge.setText(wages.get(position));
        holder.tvcrop.setText(crop.get(position));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PostDetails.class);
                i.putExtra("workid", id.get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivimage;
        TextView tvwork, tvname, tvnumber, tvaddress, tvstarttime, tvendtime, tvdate, tvwasge,tvcrop;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvwork = itemView.findViewById(R.id.tv_work_description);
            tvname = itemView.findViewById(R.id.tvname);
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
