package com.hatsoffdigital.hatsoff.Activity.Attendence;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.ScanDateTime;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanAttendenceActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ImageView scan_close_img, scan_toggel_img;
    ZXingScannerView mScannerView;
    TextView scan_date;
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

//        mScannerView = (ZXingScannerView) findViewById(R.id.zx_view);
//        mScannerView.setAspectTolerance(0.5f);


        scan_toggel_img = (ImageView) findViewById(R.id.scan_toggel_img);
        scan_toggel_img.setOnClickListener(this);

        scan_close_img = (ImageView) findViewById(R.id.scan_close_img);
        scan_close_img.setOnClickListener(this);

        scan_date = (TextView) findViewById(R.id.scan_date);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
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

        String date = dayOfWeek + "," + " " + day + " " + dayOfMonth + "," + " " + year;
        scan_date.setText(date);


        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        mCodeScanner = new CodeScanner(this, scannerView);
        //mCodeScanner.setCamera(1);

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
//        mScannerView.startCamera();
//        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
//            @Override
//            public void handleResult(Result result) {
//
//                if(result.getText().equals("Hats Off"))
//                {
//
//                     mScannerView.stopCamera();
//                     getScanData();
//
//                }
//                else
//                {
//
//                    Toast.makeText(context,"Wrong QR Code",Toast.LENGTH_SHORT).show();
//                }
//
//                mScannerView.resumeCameraPreview(this);
//            }
//        });

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

                            if (in_time.equals("")) {
                                Intent intent = new Intent(context, SuccessScanAttendActivity.class);
                                intent.putExtra("IN", in_time);
                                intent.putExtra("OUT", out_time);
                                intent.putExtra("TIME", time);
                                intent.putExtra("Hours", hours);
                                intent.putExtra("Late", late);
                                startActivity(intent);
                                finish();
                            } else if (out_time.equals("")) {
                                Intent intent = new Intent(context, SuccessScanAttendActivity.class);
                                intent.putExtra("IN", in_time);
                                intent.putExtra("OUT", out_time);
                                intent.putExtra("TIME", time);
                                intent.putExtra("Hours", hours);
                                intent.putExtra("Late", late);
                                startActivity(intent);
                                finish();
                            } else {
                                ScanAttendenceMessage();

                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss(" ");
                        NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onComplete() {

                    }
                });


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
                    }
                });

        builder1.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    public void onPause() {
        super.onPause();
        //mScannerView.stopCamera();
        // Stop camera on pause

        mCodeScanner.releaseResources();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == scan_toggel_img.getId()) {
            Intent intent = new Intent(context, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (id == scan_close_img.getId()) {
            finish();
        }

    }
}
