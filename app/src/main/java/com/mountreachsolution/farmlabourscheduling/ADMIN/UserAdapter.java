package com.mountreachsolution.farmlabourscheduling.ADMIN;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mountreachsolution.farmlabourscheduling.DATABASE.LabourREgistration;
import com.mountreachsolution.farmlabourscheduling.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.mobile.setText(user.getMobile());
        holder.address.setText(user.getAddress());
        holder.age.setText(user.getAge());
        holder.skill.setText(user.getSkill());
        holder.adharno.setText(user.getAdharno());

        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getId() != null) {
                    Intent i = new Intent(context, Labourdetails.class);
                    i.putExtra("labourid", user.getId());
                    context.startActivity(i);
                } else {

                    Log.e("UserAdapter", "Labour ID is null for user: " + user.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, mobile, address, age, skill, adharno;
        CardView cvcard;

        public UserViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            address = itemView.findViewById(R.id.address);
            age = itemView.findViewById(R.id.age);
            skill = itemView.findViewById(R.id.skill);
            adharno = itemView.findViewById(R.id.adharno);
            cvcard=itemView.findViewById(R.id.cardview3cv);
        }
    }
}