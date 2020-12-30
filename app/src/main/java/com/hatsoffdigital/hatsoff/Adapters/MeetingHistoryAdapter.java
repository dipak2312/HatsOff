package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.MettingHistoryList;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MeetingHistoryAdapter extends RecyclerView.Adapter<MeetingHistoryAdapter.mHistoryViewHolder>{

    Context context;
    ArrayList<MettingHistoryList> mettingHistoryLists;

    public MeetingHistoryAdapter(Context context, ArrayList<MettingHistoryList> mettingHistoryLists) {
        this.context = context;
        this.mettingHistoryLists = mettingHistoryLists;
    }

    @NonNull
    @Override
    public MeetingHistoryAdapter.mHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_popup,parent,false);
        mHistoryViewHolder holder=new mHistoryViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHistoryAdapter.mHistoryViewHolder holder, int position) {
        holder.txt_date.setText(mettingHistoryLists.get(position).getDate_cur());
        holder.txt_time.setText(mettingHistoryLists.get(position).getTime());
        holder.txt_company_name.setText(mettingHistoryLists.get(position).getCompany_name());
        holder.txt_company_service.setText(mettingHistoryLists.get(position).getServices());
        holder.txt_company_address.setText(mettingHistoryLists.get(position).getAddress());
        holder.txt_company_team.setText(mettingHistoryLists.get(position).getTeam_member());


    }

    @Override
    public int getItemCount() {
        return mettingHistoryLists.size();
    }

    public  class mHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date,txt_time,txt_company_name,txt_company_address,txt_company_service,txt_company_team;
        public mHistoryViewHolder(View itemView) {
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

