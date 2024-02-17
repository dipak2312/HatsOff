package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.Attendance_details;
import com.hatsoffdigital.hatsoff.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;

public class CheckYourAttendenceAdapter extends RecyclerView.Adapter<CheckYourAttendenceAdapter.AttendenceViewHolder> {

    Context context;
    ArrayList<Attendance_details> attendanceDetails;


    public CheckYourAttendenceAdapter(Context context, ArrayList<Attendance_details> attendanceDetails) {
        this.context = context;
        this.attendanceDetails = attendanceDetails;
    }

    @Override
    public AttendenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.check_attendence,parent,false);

        AttendenceViewHolder holder=new AttendenceViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(AttendenceViewHolder holder, int position) {
        holder.btn_intime.setText(attendanceDetails.get(position).getAm_in());
        holder.btn_outtime.setText(attendanceDetails.get(position).getPm_out());
        String pm_out=attendanceDetails.get(position).getPm_out();
        if(pm_out.equals(""))
        {
            holder.btn_outtime.setText("--");
        }
        String date=attendanceDetails.get(position).getDate();
        holder.attend_status.setText(attendanceDetails.get(position).getTotal_hours());

        String status=attendanceDetails.get(position).getStatus();

        String count=attendanceDetails.get(position).getDate();

        if(status.equals("HALF"))
        {
            holder.attend_status.setBackground(context.getResources().getDrawable(
                    R.drawable.rounded_attend_read));
        }
        else if(status.equals("FULL"))
        {
            holder.attend_status.setBackground(context.getResources().getDrawable(
                    R.drawable.rounded_attend_green));
        }
        else if(status.equals(""))
        {
            holder.attend_status.setBackground(context.getResources().getDrawable(
                    R.drawable.rounded_attend_shadow));
            holder.attend_status.setText("--");
        }

        String str[] = date.split("-");
        int day = Integer.parseInt(str[0]);
        int month = Integer.parseInt(str[1]);
        int year = Integer.parseInt(str[2]);

        String inputDateStr = String.format("%s/%s/%s", day, month, year);
        Date inputDate = null;
        try {
            inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        String dayOfMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        String date1=dayOfWeek+","+" "+day +" "+dayOfMonth+","+ " "+year;
        holder.attend_date.setText(date1);

        }

    @Override
    public int getItemCount() {
       return  attendanceDetails.size();
    }

    public class AttendenceViewHolder extends RecyclerView.ViewHolder {

        Button btn_outtime,btn_intime;
        TextView attend_date,attend_status;
        public AttendenceViewHolder(View itemView) {
            super(itemView);

            btn_intime=(Button)itemView.findViewById(R.id.btn_intime);
            btn_outtime=(Button)itemView.findViewById(R.id.btn_outtime);

            attend_date=(TextView)itemView.findViewById(R.id.attend_date);
            attend_status=(TextView)itemView.findViewById(R.id.attend_status);
        }
    }
}
