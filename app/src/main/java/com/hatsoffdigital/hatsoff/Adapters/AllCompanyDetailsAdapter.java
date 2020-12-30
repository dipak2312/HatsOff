package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.CompanyDetails;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllCompanyDetailsAdapter extends RecyclerView.Adapter<AllCompanyDetailsAdapter.companyDetailsViewHolder> {

    Context context;
    ArrayList<CompanyDetails> companyDetails;

    public AllCompanyDetailsAdapter(Context context, ArrayList<CompanyDetails> companyDetails) {
        this.context = context;
        this.companyDetails = companyDetails;
    }

    @NonNull
    @Override
    public AllCompanyDetailsAdapter.companyDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_popup,parent,false);
        companyDetailsViewHolder holder=new companyDetailsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllCompanyDetailsAdapter.companyDetailsViewHolder holder, int position) {

        holder.txt_date.setText(companyDetails.get(position).getDate_cur());
        holder.txt_time.setText(companyDetails.get(position).getTime());
        holder.txt_company_name.setText(companyDetails.get(position).getCompany_name());
        holder.txt_company_service.setText(companyDetails.get(position).getServices());
        holder.txt_company_address.setText(companyDetails.get(position).getAddress());
        holder.txt_company_team.setText(companyDetails.get(position).getTeam_member());

    }

    @Override
    public int getItemCount() {
        return companyDetails.size();
    }


    public   class companyDetailsViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_date,txt_time,txt_company_name,txt_company_address,txt_company_service,txt_company_team;

        public companyDetailsViewHolder(View itemView) {
            super(itemView);

            txt_date=(TextView)itemView.findViewById(R.id.txt_date);
            txt_time=(TextView)itemView.findViewById(R.id.txt_time);
            txt_company_name=(TextView)itemView.findViewById(R.id.txt_company_name);
            txt_company_address=(TextView)itemView.findViewById(R.id.txt_company_address);
            txt_company_service=(TextView)itemView.findViewById(R.id.txt_company_service);
            txt_company_team=(TextView)itemView.findViewById(R.id.txt_company_team);

        }
    }

}
