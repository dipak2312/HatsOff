package com.hatsoffdigital.hatsoff.Activity.Attendence;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.CheckYourAttendenceAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.Attendance_details;
import com.hatsoffdigital.hatsoff.Models.AttendenceDetailsResponse;
import com.hatsoffdigital.hatsoff.Models.AverageTime;
import com.hatsoffdigital.hatsoff.Models.ScanDateTime;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;

import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

//import static com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE;


public class AttendenceActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    LinearLayout lin_scan;
    SPManager spManager;
    RelativeLayout rel_attend_toggel;
    RecyclerView rec_show_attend;
    CheckYourAttendenceAdapter adapter;
    ArrayList<Attendance_details> attendanceDetails;
    TextView txt_average, txt_below_9hrs, txt_above_9hrs;
    LinearLayout lin_avgtime;

    CustomProgressDialog dialog;
    CustomProgressDialog dialog1;

    //EasyWayLocation easyWayLocation;
    double distance;


    String[] permissions = new String[]{

            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
           // Manifest.permission.ACCESS_COARSE_LOCATION,
           // Manifest.permission.ACCESS_FINE_LOCATION

    };

    ImageView img_home;
    String emp_id;
   private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        context = AttendenceActivity.this;
        getSupportActionBar().hide();

        spManager = new SPManager(context);

        emp_id=spManager.getEmployee_id();

        lin_avgtime = (LinearLayout) findViewById(R.id.lin_avgtime);

       //easyWayLocation=new EasyWayLocation(this,false,this);


        rec_show_attend = (RecyclerView) findViewById(R.id.rec_show_attend);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rec_show_attend.setLayoutManager(lm);

        dialog = new CustomProgressDialog(context);
        dialog1=new CustomProgressDialog(context);
        attendanceDetails = new ArrayList<>();

        lin_scan = (LinearLayout) findViewById(R.id.lin_scan);
        lin_scan.setOnClickListener(this);

        rel_attend_toggel = (RelativeLayout) findViewById(R.id.rel_attend_toggel);
        rel_attend_toggel.setOnClickListener(this);

        txt_average = (TextView) findViewById(R.id.txt_average);
        txt_below_9hrs = (TextView) findViewById(R.id.txt_below_9hrs);
        txt_below_9hrs.setText(spManager.getBelow_nine_hrs());
        txt_above_9hrs = (TextView) findViewById(R.id.txt_above_9hrs);
        txt_above_9hrs.setText(spManager.getAbove_nine_hrs());
        txt_average.setText(spManager.getAverage_time());

        img_home=(ImageView)findViewById(R.id.img_home);
        img_home.setOnClickListener(this);


        if (!spManager.getAverage_status().equals(" ")) {
            if (spManager.getAverage_status().equals("yes")) {
                lin_avgtime.setBackground(context.getResources().getDrawable(
                        R.drawable.corner_avgtime));
            } else {
                lin_avgtime.setBackground(context.getResources().getDrawable(
                        R.drawable.corner_below_avgtime));


            }

        }


//        img_inst = (ImageView) findViewById(R.id.img_inst);
//        img_inst.setOnClickListener(this);


        checkPermissions();

//        if (permissionIsGranted()) {
//            doLocationWork();
//        } else {
//            // Permission not granted, ask for it
//            //testLocationRequest.requestPermission(121);
//        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        AttendanceDetailsCheck();
        getAverageList();

       // easyWayLocation.startLocation();

       // Toast.makeText(context,"show result",Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        easyWayLocation.endUpdates();
//    }

//    private void doLocationWork() {
//        easyWayLocation.startLocation();
//    }


//    public boolean permissionIsGranted() {
//
//        int permissionState = androidx.core.app.ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//
//        return permissionState == PackageManager.PERMISSION_GRANTED;
//    }


    private void getAverageList() {

        WebServiceModel.getRestApi().getAverageTime(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AverageTime>() {
                    @Override
                    public void onNext(AverageTime getaverageTime) {


                        String msg = getaverageTime.getMsg();

                        if (msg.equals("success")) {


                            spManager.setAbove_nine_hrs(getaverageTime.getAboveNinehrsCount());
                            spManager.setBelow_nine_hrs(getaverageTime.getBelowNinehrsCount());
                            spManager.setAverage_status(getaverageTime.getAverageStatus());
                            spManager.setAverage_time(getaverageTime.getAverageTime());

                            txt_below_9hrs.setText(spManager.getBelow_nine_hrs());
                            txt_above_9hrs.setText(spManager.getAbove_nine_hrs());

                            if (spManager.getAverage_status().equals("yes")) {
                                lin_avgtime.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_avgtime));
                            } else {
                                lin_avgtime.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_below_avgtime));
                            }


                            if(getaverageTime.getAverageTime().equals("NANhr NANm"))
                            {
                                spManager.setAverage_time("0hr 0m");
                            }



                            txt_average.setText(spManager.getAverage_time());


                        }


                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }


    private void AttendanceDetailsCheck() {

        String emp_id = spManager.getEmployee_id();
        dialog.show(" ");

        WebServiceModel.getRestApi().AttendenceDetails(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AttendenceDetailsResponse>() {
                    @Override
                    public void onNext(AttendenceDetailsResponse attenddetailResponce) {
                        dialog.dismiss(" ");

                        String msg = attenddetailResponce.getMsg();

                        if (msg.equals("success")) {
                            attendanceDetails = attenddetailResponce.getAttendance_details();
                            adapter = new CheckYourAttendenceAdapter(context, attendanceDetails);
                            rec_show_attend.setAdapter(adapter);

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


                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//        if (requestCode == LOCATION_SETTING_REQUEST_CODE) {
//            easyWayLocation.onActivityResult(resultCode);
//        }


        if(resultCode != Activity.RESULT_OK)
        {
            //Log.d(LOGTAG,"COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");

            if (result.equals("Hats Off")) {

                getScanData();

            } else {

                Toast.makeText(context, "Wrong QR Code", Toast.LENGTH_SHORT).show();
            }
            //Log.d(LOGTAG,"Have scan result in your app activity :"+ result);
//            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//            alertDialog.setTitle("Success fully done scan");
//            alertDialog.setMessage(result);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();

        }
    }






    private void getScanData() {
        dialog1.show(" ");
        String emp_id = spManager.getEmployee_id();

        WebServiceModel.getRestApi().Scandatetime(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ScanDateTime>() {
                    @Override
                    public void onNext(ScanDateTime scanDateTime) {
                        dialog1.dismiss(" ");

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


                            } else {
                                ScanAttendenceMessage();

                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog1.dismiss(" ");
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

    public void onClick(View view) {

        int id = view.getId();


// if (id == img_inst.getId()) {
//            //attendencePopup();
//            Intent intent=new Intent(context, TermsAndConditionsActivity.class);
//            intent.putExtra("Attendance","ATTENDANCE:");
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//
//        }
        if (id == lin_scan.getId()) {
            //Toast.makeText(context,String.valueOf(distance),Toast.LENGTH_SHORT).show();
//            if(distance <=50) {

                Intent i = new Intent(context, QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
//            }
//            else
//            {
//                Toast.makeText(context,"Don't Correct Location Please Wait System get correct location",Toast.LENGTH_SHORT).show();
//            }

        } else if (id == rel_attend_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
        else if(id==img_home.getId())
        {
            finish();
        }


    }

//
//    @Override
//    public void locationOn() {
//        Toast.makeText(this, "Location On", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void currentLocation(Location location) {
//
//        distance=easyWayLocation.calculateDistance(18.48287939, 73.87369157, location.getLatitude(), location.getLongitude());
//
//        String lat= String.valueOf(location.getLatitude());
//        String lang= String.valueOf(location.getLongitude());
//        String latlang=lat+","+lang;
//        Toast.makeText(context,latlang,Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    @Override
//    public void locationCancelled() {
//        Toast.makeText(this, "Location Cancelled", Toast.LENGTH_SHORT).show();
//
//    }
//

}
