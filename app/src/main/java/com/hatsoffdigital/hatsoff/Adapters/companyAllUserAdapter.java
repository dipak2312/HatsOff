package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.employee_list;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Utils.ItemEmployeeClickListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class companyAllUserAdapter extends RecyclerView.Adapter<companyAllUserAdapter.AllUserViewHolder> {

    Context context;
    ArrayList<employee_list> employeeList;
    ItemEmployeeClickListener clicklistener;



    public companyAllUserAdapter(Context context, ArrayList<employee_list> employeeList, ItemEmployeeClickListener clicklistener) {
        this.context = context;
        this.employeeList = employeeList;
        this.clicklistener = clicklistener;
    }

    @NonNull
    @Override
    public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user,parent,false);
        AllUserViewHolder holder=new AllUserViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserViewHolder holder, int position) {

        holder.txt_user_name.setText(employeeList.get(position).getName());

        if (employeeList.get(position).isSelected()) {
            holder.employee_check_box.setChecked(true);


        } else {
            holder.employee_check_box.setChecked(false);


        }

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class AllUserViewHolder extends RecyclerView.ViewHolder {
        TextView txt_user_name;
        CheckBox employee_check_box;
        public AllUserViewHolder(View itemView) {
            super(itemView);
            txt_user_name=(TextView)itemView.findViewById(R.id.txt_user_name);
            employee_check_box=(CheckBox)itemView.findViewById(R.id.employee_check_box);

            employee_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if(isChecked)
                    {
                        employeeList.get(getAdapterPosition()).setSelected(true);
                       clicklistener.onemployeeClick(employeeList.get(getAdapterPosition()).getName(),"check");

                    }
                    else
                    {
                        employeeList.get(getAdapterPosition()).setSelected(false);
                        clicklistener.onemployeeClick(employeeList.get(getAdapterPosition()).getName(),"uncheck");

                    }

                }
            });

        }
    }
}
