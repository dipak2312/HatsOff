package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.Holidays_list;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

public class HolidayViewAdapter extends RecyclerView.Adapter<HolidayViewAdapter.HolidayviewHolder> {

    Context context;
    ArrayList<Holidays_list> HolidayList;

    public HolidayViewAdapter(Context context, ArrayList<Holidays_list> holidayList) {
        this.context = context;
        HolidayList = holidayList;
    }

    @Override
    public HolidayviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_view,parent,false);
        HolidayviewHolder holder =new HolidayviewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(HolidayviewHolder holder, int position) {

        holder.holiday_name.setText(HolidayList.get(position).getHoliday_name());
        holder.holiday_date.setText(HolidayList.get(position).getHoliday_date());


        }

    @Override
    public int getItemCount() {
        return HolidayList.size();
    }

    public class HolidayviewHolder extends RecyclerView.ViewHolder {

        TextView holiday_date,holiday_name;
        RelativeLayout rel_baground;
        public HolidayviewHolder(View itemView) {
            super(itemView);

            holiday_date=(TextView)itemView.findViewById(R.id.holiday_date);
            holiday_name=(TextView)itemView.findViewById(R.id.holiday_name);
            rel_baground=(RelativeLayout)itemView.findViewById(R.id.rel_baground);
        }
    }
}
