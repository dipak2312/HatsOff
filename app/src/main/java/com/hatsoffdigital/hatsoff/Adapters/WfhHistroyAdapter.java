package com.hatsoffdigital.hatsoff.Adapters;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.WFH_History;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class WfhHistroyAdapter extends RecyclerView.Adapter<WfhHistroyAdapter.WfhViewViewHolder> {

    Context context;
    ArrayList<WFH_History> WfhHistroyList;

    public WfhHistroyAdapter(Context context, ArrayList<WFH_History> wfhHistroyList) {
        this.context = context;
        WfhHistroyList = wfhHistroyList;
    }

    @NonNull
    @Override
    public WfhViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.check_wfh_histroy_view,parent,false);
        WfhViewViewHolder holder=new WfhViewViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WfhViewViewHolder holder, int position) {

        holder.txt_wfh_from_date.setText(WfhHistroyList.get(position).getFrom_date());
        holder.txt_wfh_to_date.setText(WfhHistroyList.get(position).getTo_date());
        holder.text_resone.setText(WfhHistroyList.get(position).getEmployee_reason());
        holder.text_current_date.setText(WfhHistroyList.get(position).getApplied_date());
        String status=WfhHistroyList.get(position).getLeave_status();
        if(status.equals("0"))
        {
            holder.text_wfh_status.setText("Pending");
            holder.card_wfh_baground_color.setCardBackgroundColor(Color.parseColor("#FFA500"));
        }
        if(status.equals("1"))
        {
            holder.text_wfh_status.setText("Approved");
            holder.card_wfh_baground_color.setCardBackgroundColor(Color.parseColor("#008000"));
            holder.card_approved_day.setVisibility(View.VISIBLE);

            String alldays=WfhHistroyList.get(position).getDays()+" "+"Day";
            holder.txt_approved_day.setText(alldays);


            String mntext=WfhHistroyList.get(position).getMgmt_reason();
            if(!mntext.equals("")) {
                holder.rel_mngmnt_txt.setVisibility(View.VISIBLE);
                holder.text_mngmnt.setText(mntext);
            }
        }

        if(status.equals("2"))
        {
            holder.text_wfh_status.setText("Rejected");
            holder.card_wfh_baground_color.setCardBackgroundColor(Color.parseColor("#FF0000"));

            String mntext=WfhHistroyList.get(position).getMgmt_reason();
            if(!mntext.equals("")) {
                holder.rel_mngmnt_txt.setVisibility(View.VISIBLE);
                holder.text_mngmnt.setText(mntext);
            }
        }


    }

    @Override
    public int getItemCount() {
        return WfhHistroyList.size();
    }

    public class WfhViewViewHolder extends RecyclerView.ViewHolder {
        TextView text_mngmnt,text_resone,text_current_date,txt_approved_day,txt_wfh_from_date,txt_wfh_to_date,text_wfh_status;
        CardView card_wfh_baground_color,card_approved_day;
        RelativeLayout rel_mngmnt_txt;
        public WfhViewViewHolder(View itemView) {
            super(itemView);
            txt_wfh_from_date=(TextView)itemView.findViewById(R.id.txt_wfh_from_date);
            txt_wfh_to_date=(TextView)itemView.findViewById(R.id.txt_wfh_to_date);
            text_resone=(TextView)itemView.findViewById(R.id.text_resone);
            text_mngmnt=(TextView)itemView.findViewById(R.id.text_mngmnt);
            text_current_date=(TextView)itemView.findViewById(R.id.text_current_date);
            txt_approved_day=(TextView)itemView.findViewById(R.id.txt_approved_day);
            text_wfh_status=(TextView)itemView.findViewById(R.id.text_wfh_status);
            card_wfh_baground_color=(CardView)itemView.findViewById(R.id.card_wfh_baground_color);
            card_approved_day=(CardView)itemView.findViewById(R.id.card_approved_day);
            rel_mngmnt_txt=(RelativeLayout)itemView.findViewById(R.id.rel_mngmnt_txt);



        }
    }
}
