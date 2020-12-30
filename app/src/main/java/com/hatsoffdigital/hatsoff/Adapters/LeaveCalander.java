package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeaveCalander extends RecyclerView.Adapter<LeaveCalander.LeaveCalenderViewHolder> {

    Context context;
    ArrayList<String> dateList;
    String[] getDateList;

    public LeaveCalander(Context context, ArrayList<String> dateList, String[] getDateList) {
        this.context = context;
        this.dateList = dateList;
        this.getDateList = getDateList;
    }

    @NonNull
    @Override
    public LeaveCalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.date_format_popup,parent,false);
        LeaveCalenderViewHolder holder=new LeaveCalenderViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveCalenderViewHolder holder, int position) {

        String str= dateList.get(position);
        String firstlater = str.substring( 0, str.indexOf("-"));
        holder.txt_day.setText(firstlater);


        SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
        Date dt1= null;
        String finalDay = null;
        try {
            dt1 = format1.parse(dateList.get(position));
            DateFormat format2=new SimpleDateFormat("EEE");
            finalDay=format2.format(dt1);
            holder.txt_day_name.setText(finalDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txt_day.setTextColor(context.getResources().getColor(R.color.medium_light_gray));
        holder.txt_day_name.setTextColor(context.getResources().getColor(R.color.medium_light_gray));

        if(finalDay.equals("Sun"))
        {
            holder.txt_day.setTextColor(context.getResources().getColor(R.color.HOBlue));
            holder.txt_day_name.setTextColor(context.getResources().getColor(R.color.HOBlue));
        }

        if(getDateList !=null) {

            for (int i = 0; i < getDateList.length; i++) {

                if (dateList.get(position).equals(getDateList[i])) {
                    holder.txt_day.setTextColor(context.getResources().getColor(R.color.HOBlue));
                    holder.txt_day_name.setTextColor(context.getResources().getColor(R.color.HOBlue));
                }

            }
        }


    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class LeaveCalenderViewHolder extends RecyclerView.ViewHolder {
        TextView txt_day,txt_day_name;
        public LeaveCalenderViewHolder(View itemView) {
            super(itemView);
            txt_day=(TextView)itemView.findViewById(R.id.txt_day);
            txt_day_name=(TextView)itemView.findViewById(R.id.txt_day_name);

        }
    }
}
