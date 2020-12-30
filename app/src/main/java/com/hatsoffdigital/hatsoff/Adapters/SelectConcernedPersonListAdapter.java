package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.concernPerson;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Utils.ItemConcernedPerson;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectConcernedPersonListAdapter extends RecyclerView.Adapter<SelectConcernedPersonListAdapter.ConcernedPersonViewHolder> {

    Context context;
    ArrayList<concernPerson> concernPersonList;
    ItemConcernedPerson clickListener;

    public SelectConcernedPersonListAdapter(Context context, ArrayList<concernPerson> concernPersonList, ItemConcernedPerson clickListener) {
        this.context = context;
        this.concernPersonList = concernPersonList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SelectConcernedPersonListAdapter.ConcernedPersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_concerned_person,parent,false);
        ConcernedPersonViewHolder holder=new ConcernedPersonViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectConcernedPersonListAdapter.ConcernedPersonViewHolder holder, int position) {
        holder.txt_user_name.setText(concernPersonList.get(position).getUser_name());

        if (concernPersonList.get(position).isSelected()) {
            holder.employee_check_box.setChecked(true);


        } else {
            holder.employee_check_box.setChecked(false);


        }
    }

    @Override
    public int getItemCount() {
        return concernPersonList.size();
    }

    public class ConcernedPersonViewHolder extends RecyclerView.ViewHolder {
        TextView txt_user_name;
        CheckBox employee_check_box;
        public ConcernedPersonViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_user_name=(TextView)itemView.findViewById(R.id.txt_user_name);
            employee_check_box=(CheckBox)itemView.findViewById(R.id.employee_check_box);

            employee_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if(isChecked)
                    {
                        concernPersonList.get(getAdapterPosition()).setSelected(true);
                        clickListener.ConcernedList(concernPersonList.get(getAdapterPosition()).getUser_name(),"check");

                    }
                    else
                    {
                        concernPersonList.get(getAdapterPosition()).setSelected(false);
                        clickListener.ConcernedList(concernPersonList.get(getAdapterPosition()).getUser_name(),"uncheck");

                    }

                }
            });

        }
    }
}
