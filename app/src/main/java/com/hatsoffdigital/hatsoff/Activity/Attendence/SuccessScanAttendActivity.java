package com.hatsoffdigital.hatsoff.Activity.Attendence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.InsertInOutTime;
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

public class SuccessScanAttendActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button btn_in_time, btn_out_time;
    ImageView scan_toggel_img, scan_close_img;
    TextView success_date;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_scan_attend);

        context = SuccessScanAttendActivity.this;

        getSupportActionBar().hide();
        dialog = new CustomProgressDialog(context);
        spManager = new SPManager(context);


//        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//

        text_box_hid = (RelativeLayout) findViewById(R.id.text_box_hid);

        scan_toggel_img = (ImageView) findViewById(R.id.scan_toggel_img);
        scan_toggel_img.setOnClickListener(this);
        scan_close_img = (ImageView) findViewById(R.id.scan_close_img);
        scan_close_img.setOnClickListener(this);
        success_date = (TextView) findViewById(R.id.success_date);


        attend_bahana = (EditText) findViewById(R.id.attend_bahana);

        bahana_dalo_text = (TextView) findViewById(R.id.bahana_dalo_text);


        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        btn_in_time = (Button) findViewById(R.id.btn_in_time);
        btn_out_time = (Button) findViewById(R.id.btn_out_time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String formattedDate = dateFormat.format(new Date()).toString();
        System.out.println("saggidate" + formattedDate);

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
        success_date.setText(date);


        in_time = getIntent().getStringExtra("IN");
        out_time = getIntent().getStringExtra("OUT");
        time = getIntent().getStringExtra("TIME");
        hours = getIntent().getStringExtra("Hours");

        late = getIntent().getStringExtra("Late");

        //Toast.makeText(context,hours,Toast.LENGTH_SHORT).show();

        if (in_time.equals("")) {

            //Toast.makeText(context,"late calculated",Toast.LENGTH_SHORT).show();
            //Toast.makeText(context,late,Toast.LENGTH_SHORT).show();

            if (late.equals("no")) {
                text_box_hid.setVisibility(View.GONE);

            } else {
                text_box_hid.setVisibility(View.VISIBLE);

                bahana_dalo_text.setText("O Oh! You're Late");

                //Toast.makeText(context,"you are Late",Toast.LENGTH_SHORT).show();
            }
        }


        if (hours != null) {
            Hours = Integer.parseInt(hours);
            int hors = Hours * -1;
            String hr = String.valueOf(hors);
            Toast.makeText(context, hr, Toast.LENGTH_SHORT).show();

            if (hors < 9) {
                text_box_hid.setVisibility(View.VISIBLE);
                bahana_dalo_text.setText("Why So Early?");
            } else {
                text_box_hid.setVisibility(View.GONE);
            }
        }


        if (in_time.equals("")) {
            btn_in_time.setText(time);
            btn_out_time.setText("00:00");
        } else if (!in_time.equals("")) {
            btn_in_time.setText(in_time);
            btn_out_time.setText(time);

        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == scan_toggel_img.getId()) {
            Intent intent = new Intent(context, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (id == scan_close_img.getId()) {
            finish();
        } else if (id == btn_submit.getId()) {

            if (in_time.equals("")) {

                out_time = "";

//                late_bahana=attend_bahana.getText().toString().trim();
//                InsertInOutTime();

                if (late.equals("no")) {

                    in_time = time;
                    InsertInOutTime();

                } else {

                    late_bahana = attend_bahana.getText().toString().trim();

                    if (late_bahana.equals("")) {
                        attend_bahana.requestFocus();
                        attend_bahana.setError("late ka bahana dalo");
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
                }

                //InsertInOutTime();
            }

        }


    }

    private void InsertInOutTime() {

        String emp_id = spManager.getEmployee_id();
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

                            Toast.makeText(context, "Successfully Done", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss(" ");
                        NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..and ReScanning!!",Toast.LENGTH_SHORT).show();
                        // in_time="";
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
