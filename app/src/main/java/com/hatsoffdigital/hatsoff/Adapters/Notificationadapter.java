package com.hatsoffdigital.hatsoff.Adapters;
import android.app.Dialog;
import android.content.Context;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;


import com.hatsoffdigital.hatsoff.Models.Announcement_list;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

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

        String mystring="Read More";
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        holder.read_more.setText(content);

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


            final Dialog popupdialog=new Dialog(context);
            popupdialog.setContentView(R.layout.open_noti_popup);
            popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

           TextView txt_notification=(TextView)popupdialog.findViewById(R.id.txt_notification);
           txt_notification.setText(AnnoucementList.get(getAdapterPosition()).getAnnouncement_desc());

            Button btn_noti_okay=(Button)popupdialog.findViewById(R.id.btn_noti_okay);
            btn_noti_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupdialog.cancel();

                }
            });

            popupdialog.show();

        }
    }
}
