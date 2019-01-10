package com.hatsoffdigital.hatsoff.Activity.Attendence;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Adapters.CheckYourAttendenceAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.Attendance_details;
import com.hatsoffdigital.hatsoff.Models.AttendenceDetailsResponse;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CheckAttendenceActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    ImageView attend_close_img, toggel_attend_img;

    RecyclerView rec_show_attend;
    CheckYourAttendenceAdapter adapter;
    TextView check_date;

    LinearLayout lin_attendence;

    CustomProgressDialog dialog;
    SPManager spManager;

    ArrayList<Attendance_details> attendanceDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendence);

        context = CheckAttendenceActivity.this;
        getSupportActionBar().hide();

        dialog = new CustomProgressDialog(context);
        spManager = new SPManager(context);

        attendanceDetails = new ArrayList<>();

        toggel_attend_img = (ImageView) findViewById(R.id.toggel_attend_img);
        toggel_attend_img.setOnClickListener(this);

        attend_close_img = (ImageView) findViewById(R.id.attend_close_img);
        attend_close_img.setOnClickListener(this);

        lin_attendence = (LinearLayout) findViewById(R.id.lin_attendence);
        lin_attendence.setOnClickListener(this);

        rec_show_attend = (RecyclerView) findViewById(R.id.rec_show_attend);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rec_show_attend.setLayoutManager(lm);


        check_date = (TextView) findViewById(R.id.check_date);


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

        String date = dayOfWeek + "," + day + " " + dayOfMonth + "," + year;
        check_date.setText(date);

        AttendanceDetailsCheck();
        checkAttendancePopup();


    }

    private void checkAttendancePopup() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Desclaimer");
        builder1.setMessage("1. One time not scanned => Penalty Rs. 120/-\n" +
                "2. Both time forgot to scan => Penalty Rs. 300/-\n" +
                "3. In case of meetings, network issues, App issues etc. kindly click the picture of the error & send it to the HR IMMEDIATELY, sending later or after the day ends may not be considered.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

        Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);

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
                        dialog.dismiss(" ");
                        NetworkPopup.ShowPopup(context);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == toggel_attend_img.getId()) {
            Intent intent = new Intent(context, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (id == attend_close_img.getId()) {
            finish();
        } else if (id == lin_attendence.getId()) {


//            int yearSelected;
//            int monthSelected;
//            //String customeTitle=" ";
//
//            //Set default values
//            Calendar calendar = Calendar.getInstance();
//            yearSelected = calendar.get(Calendar.YEAR);
//            monthSelected = calendar.get(Calendar.MONTH);
//
//            MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
//                    .getInstance(monthSelected, yearSelected);
//
//            dialogFragment.show(getSupportFragmentManager(), null);
//
//            dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(int year, int monthOfYear) {
//
//                    int month = monthOfYear + 1;
//                    String select = month + "/" + year;
//                    Toast.makeText(context, select, Toast.LENGTH_SHORT).show();
//
//                    // do something
//                }
//            });

        }

    }
}
