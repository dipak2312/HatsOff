package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.Holidays_list;
import com.hatsoffdigital.hatsoff.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

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

        SimpleDateFormat format1=new SimpleDateFormat("dd MMM yyyy");
        Date dt1= null;
        try {
            dt1 = format1.parse(HolidayList.get(position).getHoliday_date());
            DateFormat format2=new SimpleDateFormat("EEE");
            String finalDay=format2.format(dt1);
            holder.hodiday_date_name.setText(finalDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(position==0)
        {
            holder.rel_ho_bag.setBackground(context.getResources().getDrawable(
                    R.drawable.corner_button));
            holder.img_active.setImageDrawable(context.getResources().getDrawable(R.drawable.holiday_inactive));

            holder.holiday_date.setTextColor(Color.WHITE);
        }
        else
        {
            holder.rel_ho_bag.setBackground(context.getResources().getDrawable(
                    R.drawable.corner_white));
            holder.img_active.setImageDrawable(context.getResources().getDrawable(R.drawable.holiday_active));

            holder.holiday_date.setTextColor(context.getResources().getColor(R.color.HOBlue));
        }



        }

    @Override
    public int getItemCount() {
        return HolidayList.size();
    }

    public class HolidayviewHolder extends RecyclerView.ViewHolder {

        TextView holiday_date,holiday_name;
        TextView  hodiday_date_name;
        RelativeLayout rel_ho_bag;
        ImageView img_active;


        public HolidayviewHolder(View itemView) {
            super(itemView);

            holiday_date=(TextView)itemView.findViewById(R.id.holiday_date);
            holiday_name=(TextView)itemView.findViewById(R.id.holiday_name);
            hodiday_date_name=(TextView)itemView.findViewById(R.id.hodiday_date_name);
            rel_ho_bag=(RelativeLayout)itemView.findViewById(R.id.rel_ho_bag);
            img_active=(ImageView)itemView.findViewById(R.id.img_active);

        }
    }
}
