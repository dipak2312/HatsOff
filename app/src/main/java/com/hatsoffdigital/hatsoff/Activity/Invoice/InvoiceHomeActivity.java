package com.hatsoffdigital.hatsoff.Activity.Invoice;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.RequestInvoiceAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.InvoiceRequest;
import com.hatsoffdigital.hatsoff.Models.InvoiceRequestResponse;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InvoiceHomeActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rel_request_invoice,rel_invoice_toggle;
    TextView text_total_requested,text_pending_req,text_approved_req;
    RecyclerView recy_request_invoice;
    Context context;
    RequestInvoiceAdapter adapter;
    ImageView img_invoice_home;
    CustomProgressDialog dialog;
    SPManager spManager;
    int pendingcount;
    int approvedCount;
    int rejectedCount;
    ArrayList<com.hatsoffdigital.hatsoff.Models.InvoiceRequest> invoiceRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_home);
        getSupportActionBar().hide();

        context=InvoiceHomeActivity.this;
        spManager=new SPManager(context);

        dialog=new CustomProgressDialog(context);

        invoiceRequestList=new ArrayList<>();

        rel_request_invoice=(RelativeLayout)findViewById(R.id.rel_request_invoice);
        rel_request_invoice.setOnClickListener(this);
        text_total_requested=(TextView)findViewById(R.id.text_total_requested);
        text_approved_req=(TextView)findViewById(R.id.text_approved_req);
        text_pending_req=(TextView)findViewById(R.id.text_pending_req);
        recy_request_invoice=(RecyclerView)findViewById(R.id.recy_request_invoice);

        img_invoice_home=(ImageView)findViewById(R.id.img_invoice_home);
        img_invoice_home.setOnClickListener(this);

        rel_invoice_toggle=(RelativeLayout)findViewById(R.id.rel_invoice_toggle);
        rel_invoice_toggle.setOnClickListener(this);

        LinearLayoutManager lm=new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_request_invoice.setLayoutManager(lm);

    }

    @Override
    protected void onResume() {
        super.onResume();


        pendingcount=0;
        approvedCount=0;
        rejectedCount=0;
        getInvoiceList();
        text_pending_req.setText(spManager.getInvoice_pending());
        text_approved_req.setText(spManager.getInvoice_approved());
        text_total_requested.setText(spManager.getInvoice_total());
    }

    private void getInvoiceList() {



        dialog.show(" ");

        WebServiceModel.getRestApi().getInvoiceHistroy(spManager.getEmployee_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InvoiceRequestResponse>() {
                    @Override
                    public void onNext(InvoiceRequestResponse invoicelist) {
                        dialog.dismiss(" ");

                        String msg = invoicelist.getMsg();

                        if (msg.equals("success")) {

                            invoiceRequestList=invoicelist.getInvoiceRequest();

                            adapter=new RequestInvoiceAdapter(context,invoiceRequestList,text_pending_req);

                            recy_request_invoice.setAdapter(adapter);

                            for (int i=0;i<invoiceRequestList.size();i++)
                            {
                                if(invoiceRequestList.get(i).getStatus().equals("0"))
                                {
                                    pendingcount++;
                                }
                                if(invoiceRequestList.get(i).getStatus().equals("1"))
                                {
                                    approvedCount++;
                                }
                                if(invoiceRequestList.get(i).getStatus().equals("2"))
                                {
                                    rejectedCount++;
                                }
                            }



                            spManager.setInvoice_pending(String.valueOf(pendingcount));
                            text_pending_req.setText(spManager.getInvoice_pending());

                            spManager.setInvoice_approved(String.valueOf(approvedCount));
                            text_approved_req.setText(spManager.getInvoice_approved());

                            spManager.setInvoice_total(String.valueOf(rejectedCount));
                            text_total_requested.setText(spManager.getInvoice_total());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

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

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==rel_request_invoice.getId())
        {
            Intent intent = new Intent(context, InvoiceApplyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==rel_invoice_toggle.getId())
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
