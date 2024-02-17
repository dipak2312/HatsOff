package com.hatsoffdigital.hatsoff.Activity.Attendence;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Meeting.MettingcompanyDetailsActivity;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.InsertInOutTime;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class SuccessScanAttendActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button btn_in_time, btn_out_time;
    //ImageView scan_toggel_img, scan_close_img;
    RelativeLayout rel_scan_toggle;
    //TextView success_date;
    String in_time, out_time, time, hours;
    Button btn_submit;
    CustomProgressDialog dialog;
    SPManager spManager;
    String attendence_status;
    int Hours;
    String late;

    EditText attend_bahana;
    String late_bahana;
    String time_bahana;

    RelativeLayout text_box_hid;
    TextView bahana_dalo_text;
    RelativeLayout rel_coming_going;
    String in_current_time, out_current_time;
    String attendencestatus;
    String NineHourescompleted = "no";
    String MettingHalfStatus;
    ImageView img_sucess_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_scan_attend);

        context = SuccessScanAttendActivity.this;

        getSupportActionBar().hide();
        dialog = new CustomProgressDialog(context);
        spManager = new SPManager(context);

        text_box_hid = (RelativeLayout) findViewById(R.id.text_box_hid);

        rel_scan_toggle = (RelativeLayout) findViewById(R.id.rel_scan_toggle);
        rel_scan_toggle.setOnClickListener(this);


        rel_coming_going = (RelativeLayout) findViewById(R.id.rel_coming_going);
        rel_coming_going.setOnClickListener(this);

        attend_bahana = (EditText) findViewById(R.id.attend_bahana);

        bahana_dalo_text = (TextView) findViewById(R.id.bahana_dalo_text);

        img_sucess_scan=(ImageView)findViewById(R.id.img_sucess_scan);
        img_sucess_scan.setOnClickListener(this);


        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        btn_in_time = (Button) findViewById(R.id.btn_in_time);
        btn_out_time = (Button) findViewById(R.id.btn_out_time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String formattedDate = dateFormat.format(new Date()).toString();


        in_time = getIntent().getStringExtra("IN");
        out_time = getIntent().getStringExtra("OUT");
        time = getIntent().getStringExtra("TIME");
        hours = getIntent().getStringExtra("Hours");
        late = getIntent().getStringExtra("Late");
        MettingHalfStatus = getIntent().getStringExtra("MettingHalfStatus");

        if (in_time.equals("")) {

            if (late.equals("no")) {
                text_box_hid.setVisibility(View.GONE);

            } else {

                text_box_hid.setVisibility(View.VISIBLE);
                bahana_dalo_text.setText("O Oh! You're Late");

            }
        }


        if (hours != null) {
            Hours = Integer.parseInt(hours);
            int hors = Hours * -1;
            String hr = String.valueOf(hors);

            if (hors < 9) {

                text_box_hid.setVisibility(View.VISIBLE);
                bahana_dalo_text.setText("Why So Early?");


            } else {
                //workinghourspopup();
                NineHourescompleted = "yes";
                text_box_hid.setVisibility(View.GONE);
            }
        }


        if (MettingHalfStatus.equals("yes")) {
            text_box_hid.setVisibility(View.GONE);
        }


        if (in_time.equals("")) {
            btn_in_time.setText(time);
            in_current_time = time;
            btn_out_time.setText("00:00");
        } else if (!in_time.equals("")) {
            btn_in_time.setText(in_time);
            in_current_time = in_time;
            btn_out_time.setText(time);
            out_current_time = time;

        }

    }


    private void workinghourspopup() {

        final Dialog popupdialog = new Dialog(context);
        popupdialog.setContentView(R.layout.hour_completed_popup);
        popupdialog.setCanceledOnTouchOutside(false);
        popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        popupdialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });


        Button btn_okay = (Button) popupdialog.findViewById(R.id.hour_btn_okay1);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupdialog.cancel();
                finish();
                Toast.makeText(context, "Successfully Done", Toast.LENGTH_SHORT).show();
            }
        });

        popupdialog.show();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == rel_scan_toggle.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==img_sucess_scan.getId())
        {
            Intent intent = new Intent(context, HomeScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        else if (id == rel_coming_going.getId()) {
            int hors = Hours * -1;
            if (hors < 9) {
                attendencestatus = "HALF";
            } else {
                attendencestatus = "FULL";
            }
            Intent intent = new Intent(context, MettingcompanyDetailsActivity.class);
            intent.putExtra("inTime", in_current_time);
            intent.putExtra("outTime", out_current_time);
            intent.putExtra("Status", attendencestatus);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == btn_submit.getId()) {

            if (in_time.equals("")) {

                out_time = "";

                if (late.equals("no")) {

                    in_time = time;
                    InsertInOutTime();

                } else {

                    if (MettingHalfStatus.equals("no")) {

                        late_bahana = attend_bahana.getText().toString().trim();

                        if (late_bahana.equals("")) {
                            attend_bahana.requestFocus();
                            attend_bahana.setError("late ka bahana dalo");
                        } else {
                            in_time = time;
                            InsertInOutTime();
                        }

                    } else {
                        in_time = time;
                        InsertInOutTime();

                    }

                }

            } else {
                out_time = time;
                int hors = Hours * -1;
                if (hors < 9) {
                    attendence_status = "HALF";
                } else {
                    attendence_status = "FULL";
                }


                if (hours != null) {
                    Hours = Integer.parseInt(hours);
                    if (MettingHalfStatus.equals("no")) {

                        if (hors < 9) {
                            time_bahana = attend_bahana.getText().toString().trim();

                            if (time_bahana.equals("")) {
                                attend_bahana.requestFocus();
                                attend_bahana.setError("Bahana dalo");
                            } else {
                                InsertInOutTime();

                            }

                        } else {
                            InsertInOutTime();

                        }

                    } else {
                        InsertInOutTime();
                    }
                }


            }

        }


    }

    private void InsertInOutTime() {

        String emp_id = spManager.getEmployee_id();

        //Toast.makeText(context,in_time+out_time,Toast.LENGTH_SHORT).show();

        dialog.show(" ");

        WebServiceModel.getRestApi().InsertTime(emp_id, in_time, out_time, attendence_status, late_bahana, time_bahana)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InsertInOutTime>() {
                    @Override
                    public void onNext(InsertInOutTime inOutTime) {
                        dialog.dismiss(" ");

                        String msg = inOutTime.getMsg();

                        if (msg.equals("success")) {

                            if (NineHourescompleted.equals("yes")) {
                                workinghourspopup();

                            } else {
                                Toast.makeText(context, "Successfully Done", Toast.LENGTH_SHORT).show();
                                finish();
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


                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
