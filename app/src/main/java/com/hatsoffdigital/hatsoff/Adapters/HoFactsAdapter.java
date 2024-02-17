package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.FactName;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HoFactsAdapter extends RecyclerView.Adapter<HoFactsAdapter.HoFactViewHolder> {


    Context context;
    ArrayList<FactName> hofactList;

    public HoFactsAdapter(Context context, ArrayList<FactName> hofactList) {
        this.context = context;
        this.hofactList = hofactList;
    }

    @NonNull
    @Override
    public HoFactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ho_facts_view_popup,parent,false);
        HoFactViewHolder holder=new HoFactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HoFactViewHolder holder, int position) {
        holder.txt_ho_facts.setText(hofactList.get(position).getFact_name());

    }

    @Override
    public int getItemCount() {
        return hofactList.size();
    }

    public class HoFactViewHolder extends RecyclerView.ViewHolder {
        TextView txt_ho_facts;
        public HoFactViewHolder(View itemView) {
            super(itemView);

            txt_ho_facts=(TextView)itemView.findViewById(R.id.txt_ho_facts);
        }
    }
}
