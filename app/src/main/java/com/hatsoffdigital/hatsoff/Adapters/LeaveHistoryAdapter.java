package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.Leave_list;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LeaveHistoryAdapter extends RecyclerView.Adapter<LeaveHistoryAdapter.LeaveViewHolder> {

    Context context;
    ArrayList<Leave_list> leaveList;

    public LeaveHistoryAdapter(Context context, ArrayList<Leave_list> leaveList) {
        this.context = context;
        this.leaveList = leaveList;
    }

    @NonNull
    @Override
    public LeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.check_leave_history_activity,parent,false);
        LeaveViewHolder holder=new LeaveViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveViewHolder holder, int position) {

        holder.txt_leave_from_date.setText(leaveList.get(position).getLeave_date_from());
        holder.txt_leave_to_date.setText(leaveList.get(position).getLeave_date_to());
        holder.text_resone.setText(leaveList.get(position).getReason());
        String leaveday=leaveList.get(position).getDays() +" " +"Day";
        holder.text_leave_day.setText(leaveday);
        holder.text_current_date.setText(leaveList.get(position).getApplied_date());
        holder.radio_button_text.setText(leaveList.get(position).getType());

        String status=leaveList.get(position).getLeave_status();


        if(status.equals("0"))
        {
            holder.text_leave_status.setText("Pending");
            holder.card_baground_color.setCardBackgroundColor(Color.parseColor("#FFA500"));
        }
        if(status.equals("1"))
        {
            holder.text_leave_status.setText("Approved");
            holder.card_baground_color.setCardBackgroundColor(Color.parseColor("#008000"));
        }

        if(status.equals("2"))
        {
            holder.text_leave_status.setText("Rejected");
            holder.card_baground_color.setCardBackgroundColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    public class LeaveViewHolder  extends RecyclerView.ViewHolder{

        TextView txt_leave_from_date,txt_leave_to_date,text_resone,text_leave_day,text_leave_status,text_current_date;
        RadioButton radio_button_text;
        CardView card_baground_color;

        public LeaveViewHolder(View itemView) {
            super(itemView);

            txt_leave_from_date=(TextView)itemView.findViewById(R.id.txt_leave_from_date);
            txt_leave_to_date=(TextView)itemView.findViewById(R.id.txt_leave_to_date);
            text_resone=(TextView)itemView.findViewById(R.id.text_resone);
            text_leave_day=(TextView)itemView.findViewById(R.id.text_leave_day);
            text_leave_status=(TextView)itemView.findViewById(R.id.text_leave_status);
            text_current_date=(TextView)itemView.findViewById(R.id.text_current_date);
            card_baground_color=(CardView)itemView.findViewById(R.id.card_baground_color);

            radio_button_text=(RadioButton)itemView.findViewById(R.id.radio_button_text);
        }
    }
}
