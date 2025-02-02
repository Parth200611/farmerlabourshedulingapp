package com.mountreachsolution.farmlabourscheduling.ADMIN.Adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.ADMIN.Farmerdetails;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.ArrayList;

public class Adpterlabour extends RecyclerView.Adapter<Adpterlabour.ViewHolder> {
    private ArrayList<String> id, name, number, address, age, adharNo, skill;
    private Context context;

    public Adpterlabour(ArrayList<String> id, ArrayList<String> name, ArrayList<String> number,
                        ArrayList<String> address, ArrayList<String> age, ArrayList<String> adharNo,
                        ArrayList<String> skill, Context context) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.age = age;
        this.adharNo = adharNo;
        this.skill = skill;
        this.context = context;
    }

    @NonNull
    @Override
    public Adpterlabour.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.labourdesginview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adpterlabour.ViewHolder holder, int position) {
        holder.tvName.setText(name.get(position));
        holder.tvNumber.setText(number.get(position));
        holder.tvAddress.setText(address.get(position));
        holder.tvAge.setText(age.get(position));
        holder.tvAdharNo.setText(adharNo.get(position));
        holder.tvSkills.setText(skill.get(position));

        holder.cvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Farmerdetails.class);
                i.putExtra("Farmerid", id.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }
    public void updateList(ArrayList<String> id, ArrayList<String> name, ArrayList<String> number,
                           ArrayList<String> address, ArrayList<String> age, ArrayList<String> adharNo,
                           ArrayList<String> skill) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.age = age;
        this.adharNo = adharNo;
        this.skill = skill;
        // Notify the adapter that the data has changed and it should update the RecyclerView
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber, tvAddress, tvAge, tvAdharNo, tvSkills;
        CardView cvCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCard = itemView.findViewById(R.id.cardview4);
            tvName = itemView.findViewById(R.id.nameValue);
            tvNumber = itemView.findViewById(R.id.numberValue);
            tvAge = itemView.findViewById(R.id.ageValue);
            tvAddress = itemView.findViewById(R.id.addressValue);
            tvAdharNo = itemView.findViewById(R.id.adhrValue);
            tvSkills = itemView.findViewById(R.id.skillvalue);
        }
    }
}