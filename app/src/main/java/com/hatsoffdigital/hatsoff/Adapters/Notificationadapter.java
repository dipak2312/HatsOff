package com.hatsoffdigital.hatsoff.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.Announcement_list;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

public class Notificationadapter extends RecyclerView.Adapter<Notificationadapter.NotiViewHolder> {

    Context context;
    ArrayList<Announcement_list> AnnoucementList;

    public Notificationadapter(Context context, ArrayList<Announcement_list> annoucementList) {
        this.context = context;
        AnnoucementList = annoucementList;
    }

    @Override
    public NotiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view,parent,false);
        NotiViewHolder holder=new NotiViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotiViewHolder holder, int position) {

        holder.notification_desc.setText(AnnoucementList.get(position).getAnnouncement_desc());
        holder.noti_date1.setText(AnnoucementList.get(position).getAnnouncement_date());

    }

    @Override
    public int getItemCount() {
        return AnnoucementList.size();
    }

    public class NotiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView notification_desc,noti_date1,read_more;
        public NotiViewHolder(View itemView) {
            super(itemView);
            notification_desc=(TextView)itemView.findViewById(R.id.notification_desc);
            noti_date1=(TextView)itemView.findViewById(R.id.noti_date1);
            read_more=(TextView)itemView.findViewById(R.id.read_more);
            read_more.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);


            builder1.setMessage(AnnoucementList.get(getAdapterPosition()).getAnnouncement_desc());

            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

            Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
            LinearLayout parent = (LinearLayout) positiveButton.getParent();
            parent.setGravity(Gravity.CENTER_HORIZONTAL);
            View leftSpacer = parent.getChildAt(1);
            leftSpacer.setVisibility(View.GONE);

        }
    }
}
