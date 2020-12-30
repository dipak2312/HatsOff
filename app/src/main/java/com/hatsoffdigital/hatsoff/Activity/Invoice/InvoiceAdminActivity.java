package com.hatsoffdigital.hatsoff.Activity.Invoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.AdminPendingRequestList;
import com.hatsoffdigital.hatsoff.Models.DeleteInvoiceMessage;
import com.hatsoffdigital.hatsoff.Models.InvoicePendingRequestResponse;
import com.hatsoffdigital.hatsoff.Models.PendingInvoiceRequestList;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.util.ArrayList;

public class InvoiceAdminActivity extends AppCompatActivity implements View.OnClickListener {


    AdminPendingRequestList adapter;
    RecyclerView recy_admin_pend_req;
    Context context;
    RelativeLayout rel_invoice_toggle;
    ImageView img_invoice_home;
    TextView text_admin_pending_req;
    CustomProgressDialog dialog;
    ArrayList<PendingInvoiceRequestList> pendingInvoiceRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_admin);
        getSupportActionBar().hide();

        context=InvoiceAdminActivity.this;
        dialog=new CustomProgressDialog(context);
        pendingInvoiceRequestList=new ArrayList<>();

        rel_invoice_toggle=(RelativeLayout)findViewById(R.id.rel_invoice_toggle);
        rel_invoice_toggle.setOnClickListener(this);
        img_invoice_home=(ImageView)findViewById(R.id.img_invoice_home);
        img_invoice_home.setOnClickListener(this);
        text_admin_pending_req=(TextView)findViewById(R.id.text_admin_pending_req);


        recy_admin_pend_req=(RecyclerView)findViewById(R.id.recy_admin_pend_req);
        LinearLayoutManager lm=new LinearLayoutManager(context);
        lm.setOrientation(RecyclerView.VERTICAL);
        recy_admin_pend_req.setLayoutManager(lm);



        getPendingRequest();

    }

    private void getPendingRequest() {
        dialog.show(" ");

        WebServiceModel.getRestApi().getInvoicePendingRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InvoicePendingRequestResponse>() {
                    @Override
                    public void onNext(InvoicePendingRequestResponse invoicePendingRequest) {
                        dialog.dismiss(" ");

                        String msg = invoicePendingRequest.getMsg();

                        if (msg.equals("success")) {

                            pendingInvoiceRequestList=invoicePendingRequest.getPendingInvoiceRequestList();
                            text_admin_pending_req.setText(String.valueOf(pendingInvoiceRequestList.size()));
                            adapter=new AdminPendingRequestList(context,pendingInvoiceRequestList);
                            recy_admin_pend_req.setAdapter(adapter);
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

    @Override
    public void onClick(View view) {

        int id=view.getId();

        if(id==rel_invoice_toggle.getId())
        {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==img_invoice_home.getId())

        {

            finish();
        }


    }
}
