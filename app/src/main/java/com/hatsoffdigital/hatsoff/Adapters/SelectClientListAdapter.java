package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.categoryName;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Utils.ItemClientClickListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectClientListAdapter extends RecyclerView.Adapter<SelectClientListAdapter.ClientListViewHolder> {

    Context context;
    ArrayList<categoryName> categoryNameList;
    ItemClientClickListener clickListener;

    public SelectClientListAdapter(Context context, ArrayList<categoryName> categoryNameList, ItemClientClickListener clickListener) {
        this.context = context;
        this.categoryNameList = categoryNameList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user,parent,false);
        ClientListViewHolder holder=new ClientListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientListViewHolder holder, int position) {

        holder.txt_user_name.setText(categoryNameList.get(position).getCategory_name());

        if (categoryNameList.get(position).isSelected()) {
            holder.employee_check_box.setChecked(true);


        } else {
            holder.employee_check_box.setChecked(false);


        }

    }

    @Override
    public int getItemCount() {
        return categoryNameList.size();
    }

    public class ClientListViewHolder extends RecyclerView.ViewHolder {
        TextView txt_user_name;
        CheckBox employee_check_box;
        public ClientListViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_user_name=(TextView)itemView.findViewById(R.id.txt_user_name);
            employee_check_box=(CheckBox)itemView.findViewById(R.id.employee_check_box);

            employee_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if(isChecked)
                    {
                        categoryNameList.get(getAdapterPosition()).setSelected(true);
                        clickListener.clientList(categoryNameList.get(getAdapterPosition()).getCategory_name(),"check");

                    }
                    else
                    {
                        categoryNameList.get(getAdapterPosition()).setSelected(false);
                        clickListener.clientList(categoryNameList.get(getAdapterPosition()).getCategory_name(),"uncheck");

                    }

                }
            });
        }
    }
}
