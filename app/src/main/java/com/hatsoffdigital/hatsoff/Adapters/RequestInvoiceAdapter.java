package com.hatsoffdigital.hatsoff.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.AnnouncementListResponse;
import com.hatsoffdigital.hatsoff.Models.DeleteInvoiceMessage;
import com.hatsoffdigital.hatsoff.Models.InvoiceRequest;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RequestInvoiceAdapter extends RecyclerView.Adapter<RequestInvoiceAdapter.ReqInvViewHolder> {

    Context context;
    ArrayList<InvoiceRequest> invoiceRequestList;
    CustomProgressDialog dialog;
    TextView text_pending_req;
    SPManager spManager;


    public RequestInvoiceAdapter(Context context, ArrayList<InvoiceRequest> invoiceRequestList, TextView text_pending_req) {
        this.context = context;
        this.invoiceRequestList = invoiceRequestList;
        this.text_pending_req=text_pending_req;
    }

    @NonNull
    @Override
    public ReqInvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.request_invoice_popup,parent,false);
        ReqInvViewHolder holder=new ReqInvViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReqInvViewHolder holder, int position) {
        holder.txt_project_name.setText(invoiceRequestList.get(position).getProject_name());
        holder.txt_category.setText(invoiceRequestList.get(position).getCategory_name());
        holder.txt_date.setText(invoiceRequestList.get(position).getRequest_date());
        String str= invoiceRequestList.get(position).getCurrency();
        String firstlater = str.substring( 0, str.indexOf(" "));
        String costing=invoiceRequestList.get(position).getCoasting();
        if(costing.equals("N/A"))
        {
            firstlater="";
        }
        String all=firstlater+invoiceRequestList.get(position).getCoasting();
        holder.txt_coast.setText(all);
        holder.txt_subject.setText(invoiceRequestList.get(position).getSubject());

        holder.txt_bdm.setText(invoiceRequestList.get(position).getBdm());


        String status=invoiceRequestList.get(position).getStatus();


        if(status.equals("0"))
        {
            holder.rel_delete_cancel_icon.setVisibility(View.VISIBLE);
            holder.img_delete_cancel_icon.setBackgroundResource(R.drawable.invoice_delete);
            holder.text_invoice_status.setText("Pending");
            holder.card_invoice_baground.setCardBackgroundColor(Color.parseColor("#FFA500"));
        }
        if(status.equals("1"))
        {
            holder.rel_delete_cancel_icon.setVisibility(View.GONE);
            holder.text_invoice_status.setText(invoiceRequestList.get(position).getRequest_update_date());
            holder.card_invoice_baground.setCardBackgroundColor(Color.parseColor("#008000"));

        }

        if(status.equals("2"))
        {
            holder.rel_delete_cancel_icon.setVisibility(View.VISIBLE);
            holder.img_delete_cancel_icon.setBackgroundResource(R.drawable.invoice_instruction);
            holder.text_invoice_status.setText("Cancelled");
            holder.card_invoice_baground.setCardBackgroundColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return invoiceRequestList.size();
    }

    public class ReqInvViewHolder extends RecyclerView.ViewHolder {
        TextView txt_project_name,txt_date,txt_category,txt_coast,txt_subject,txt_bdm,text_invoice_status;
        CardView card_invoice_baground;
        RelativeLayout rel_delete_cancel_icon;
        ImageView img_delete_cancel_icon;

        public ReqInvViewHolder(View itemView) {
            super(itemView);

            txt_project_name=(TextView) itemView.findViewById(R.id.txt_project_name);
            txt_date=(TextView) itemView.findViewById(R.id.txt_date);
            txt_category=(TextView) itemView.findViewById(R.id.txt_category);
            txt_coast=(TextView) itemView.findViewById(R.id.txt_coast);
            txt_subject=(TextView) itemView.findViewById(R.id.txt_subject);
            txt_bdm=(TextView) itemView.findViewById(R.id.txt_bdm);
            text_invoice_status=(TextView) itemView.findViewById(R.id.text_invoice_status);
            card_invoice_baground=(CardView) itemView.findViewById(R.id.card_invoice_baground);

            rel_delete_cancel_icon=(RelativeLayout)itemView.findViewById(R.id.rel_delete_cancel_icon);
            img_delete_cancel_icon=(ImageView) itemView.findViewById(R.id.img_delete_cancel_icon);

            rel_delete_cancel_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(invoiceRequestList.get(getAdapterPosition()).getStatus().equals("0"))
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setTitle("Delete Invoice");
                        builder.setMessage("Are you sure You want delete this?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                deleteInvoice(getAdapterPosition());
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        dialog=new CustomProgressDialog(context);

                    }
                    else if(invoiceRequestList.get(getAdapterPosition()).getStatus().equals("2"))
                    {

                        final Dialog popupdialog=new Dialog(context);
                        popupdialog.setContentView(R.layout.open_noti_popup);
                        popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                        TextView txt_notification=(TextView)popupdialog.findViewById(R.id.txt_notification);
                        txt_notification.setText(invoiceRequestList.get(getAdapterPosition()).getCancel_reason());

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
            });

        }
    }

    private void deleteInvoice(int adapterPosition) {

        spManager=new SPManager(context);
        dialog.show(" ");
        WebServiceModel.getRestApi().deleteInvoice(invoiceRequestList.get(adapterPosition).getInvoice_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DeleteInvoiceMessage>() {
                    @Override
                    public void onNext(DeleteInvoiceMessage invoiceMessage) {
                        dialog.dismiss(" ");

                        String msg = invoiceMessage.getMsg();

                        if (msg.equals("success")) {
                            invoiceRequestList.remove(adapterPosition);
                            notifyDataSetChanged();

                            String pending=spManager.getInvoice_pending();
                            int pendingcount= Integer.parseInt(pending);
                            pendingcount--;
                            spManager.setInvoice_pending(String.valueOf(pendingcount));
                            text_pending_req.setText(spManager.getInvoice_pending());

                            Toast.makeText(context,"Delete Successfully",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        if(CheckInternetBroadcast.isNetworkAvilable(context))
                        {
                            ServerPopup.showPopup(context);
                            dialog.dismiss(" ");
                        }
                        else
                        {
                            NetworkPopup.ShowPopup(context);
                            dialog.dismiss(" ");

                        }
//
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
