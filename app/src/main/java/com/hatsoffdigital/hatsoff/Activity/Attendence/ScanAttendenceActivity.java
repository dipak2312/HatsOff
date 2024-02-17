package com.hatsoffdigital.hatsoff.Activity.Attendence;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.ScanDateTime;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ScanAttendenceActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    RelativeLayout rel_scan_toggle;
    ImageView img_scan_home;


    CustomProgressDialog dialog;
    SPManager spManager;
    private CodeScanner mCodeScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_attendence);
        context = ScanAttendenceActivity.this;


        getSupportActionBar().hide();

        dialog = new CustomProgressDialog(context);
        spManager = new SPManager(context);

        rel_scan_toggle = (RelativeLayout) findViewById(R.id.rel_scan_toggle);
        rel_scan_toggle.setOnClickListener(this);

        img_scan_home=(ImageView)findViewById(R.id.img_scan_home);
        img_scan_home.setOnClickListener(this);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        mCodeScanner = new CodeScanner(this, scannerView);
        // mCodeScanner.setCamera(1);


        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setScanMode(ScanMode.SINGLE);
        mCodeScanner.setTouchFocusEnabled(true);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);

        mCodeScanner.startPreview();
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getText().equals("Hats Off")) {

                            getScanData();

                        } else {

                            Toast.makeText(context, "Wrong QR Code", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        mCodeScanner.startPreview();


    }


    private void getScanData() {

        dialog.show(" ");
        String emp_id = spManager.getEmployee_id();

        WebServiceModel.getRestApi().Scandatetime(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ScanDateTime>() {
                    @Override
                    public void onNext(ScanDateTime scanDateTime) {
                        dialog.dismiss(" ");

                        String msg = scanDateTime.getMsg();

                        if (msg.equals("success")) {
                            String in_time = scanDateTime.getIn();
                            String out_time = scanDateTime.getOut();
                            String time = scanDateTime.getTime();
                            String hours = scanDateTime.getHours();
                            String late = scanDateTime.getLate();
                            String early = scanDateTime.getEarly();
                            String half_day_metting_status = scanDateTime.getHalf_day_or_meeting();

                            if (in_time.equals("")) {

                                if (early.equals("yes")) {
                                    Earlypopup();

                                } else {
                                    Intent intent = new Intent(context, SuccessScanAttendActivity.class);
                                    intent.putExtra("IN", in_time);
                                    intent.putExtra("OUT", out_time);
                                    intent.putExtra("TIME", time);
                                    intent.putExtra("Hours", hours);
                                    intent.putExtra("Late", late);
                                    intent.putExtra("MettingHalfStatus", half_day_metting_status);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            } else if (out_time.equals("")) {

                                Intent intent = new Intent(context, SuccessScanAttendActivity.class);
                                intent.putExtra("IN", in_time);
                                intent.putExtra("OUT", out_time);
                                intent.putExtra("TIME", time);
                                intent.putExtra("Hours", hours);
                                intent.putExtra("Late", late);
                                intent.putExtra("MettingHalfStatus", half_day_metting_status);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                ScanAttendenceMessage();

                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);

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

                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void Earlypopup() {

        final Dialog popupdialog = new Dialog(context);
        popupdialog.setContentView(R.layout.early_popup);
        popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_okay = (Button) popupdialog.findViewById(R.id.early_btn_okay);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupdialog.cancel();
                finish();
            }
        });

        popupdialog.show();
    }

    private void ScanAttendenceMessage() {

        //Toast.makeText(context,"Already Insert In and Out Time",Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Already Insert In and Out Time.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    public void onPause() {
        super.onPause();

        mCodeScanner.releaseResources();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == rel_scan_toggle.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==img_scan_home.getId())
        {
            Intent intent = new Intent(context, HomeScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


    }
}
