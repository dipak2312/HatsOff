package com.hatsoffdigital.hatsoff.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Models.PendingInvoiceRequestList;
import com.hatsoffdigital.hatsoff.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminPendingRequestList extends RecyclerView.Adapter<AdminPendingRequestList.AdminViewHolder> {

    Context context;
    ArrayList<PendingInvoiceRequestList> pendingInvoiceRequestList;

    public AdminPendingRequestList(Context context, ArrayList<PendingInvoiceRequestList> pendingInvoiceRequestList) {
        this.context = context;
        this.pendingInvoiceRequestList = pendingInvoiceRequestList;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_pending_request,parent,false);
        AdminViewHolder holder=new AdminViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        holder.text_admin_date.setText(pendingInvoiceRequestList.get(position).getRequest_date());
        holder.text_admin_client_name.setText(pendingInvoiceRequestList.get(position).getClient_name());

        holder.text_admin_costing.setText(pendingInvoiceRequestList.get(position).getCosting());





    }

    @Override
    public int getItemCount() {
        return pendingInvoiceRequestList.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView text_view,text_admin_date,text_admin_client_name,text_admin_costing;
        LinearLayout lin_admin_view;
        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            text_view=(TextView)itemView.findViewById(R.id.text_view);
            text_view.setPaintFlags(text_view.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            text_admin_date=(TextView)itemView.findViewById(R.id.text_admin_date);
            text_admin_client_name=(TextView)itemView.findViewById(R.id.text_admin_client_name);
            text_admin_costing=(TextView)itemView.findViewById(R.id.text_admin_costing);
            lin_admin_view=(LinearLayout)itemView.findViewById(R.id.lin_admin_view);
            lin_admin_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog popupdialog=new Dialog(context);
                    popupdialog.setContentView(R.layout.open_noti_popup);
                    popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                    TextView txt_notification=(TextView)popupdialog.findViewById(R.id.txt_notification);
                    txt_notification.setText(pendingInvoiceRequestList.get(getAdapterPosition()).getSubject());

                    Button btn_noti_okay=(Button)popupdialog.findViewById(R.id.btn_noti_okay);
                    btn_noti_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupdialog.cancel();

                        }
                    });

                    popupdialog.show();



                }
            });

        }
    }
}
