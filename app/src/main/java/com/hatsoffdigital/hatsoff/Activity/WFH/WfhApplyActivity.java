package com.hatsoffdigital.hatsoff.Activity.WFH;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.LeaveDates;
import com.hatsoffdigital.hatsoff.Models.WfhSucessResponse;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WfhApplyActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    Context context;
    TextView text_wfh_from_date, text_wfh_to_date, wfh_total_days;
    EditText edit_wfh_bahana;
    Button btn_wfh_apply_leave;
    //ImageView wfh_apply_instruct;
    String selectdate;
    String WFHFromTODate;
    String WfhFromDate = "", WfhToDate = "";
    CustomProgressDialog dialog;
    String[] getDateList;
    int allshowdate = 0;
    String TotalCountDays;
    SPManager spManager;
    TextView text_applied_wfh_count;

    RelativeLayout rel_wfh_apply_toggel;
    ImageView img_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wfh_apply);
        getSupportActionBar().hide();
        context = WfhApplyActivity.this;

        dialog = new CustomProgressDialog(context);
        spManager = new SPManager(context);


        text_wfh_from_date = (TextView) findViewById(R.id.text_wfh_from_date);
        text_wfh_from_date.setOnClickListener(this);
        text_wfh_to_date = (TextView) findViewById(R.id.text_wfh_to_date);
        text_wfh_to_date.setOnClickListener(this);
        wfh_total_days = (TextView) findViewById(R.id.wfh_total_days);

        text_applied_wfh_count = (TextView) findViewById(R.id.text_applied_wfh_count);
        text_applied_wfh_count.setText(spManager.getWfh_applied_size());

        edit_wfh_bahana = (EditText) findViewById(R.id.edit_wfh_bahana);
        btn_wfh_apply_leave = (Button) findViewById(R.id.btn_wfh_apply_leave);
        btn_wfh_apply_leave.setOnClickListener(this);

        rel_wfh_apply_toggel = (RelativeLayout) findViewById(R.id.rel_wfh_apply_toggel);
        rel_wfh_apply_toggel.setOnClickListener(this);

        img_home=(ImageView)findViewById(R.id.img_home);
        img_home.setOnClickListener(this);

//        wfh_apply_instruct=(ImageView)findViewById(R.id.wfh_apply_instruct);
//        wfh_apply_instruct.setOnClickListener(this);
        getHolidaysLeave();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == text_wfh_from_date.getId()) {
            WFHFromTODate = "1";
            ShowDatePopup();

        } else if (id == text_wfh_to_date.getId()) {
            WFHFromTODate = "2";
            ShowDatePopup();

        } else if (id == btn_wfh_apply_leave.getId()) {

            sendWFHAppyList();

        } else if (id == rel_wfh_apply_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        else if(id==img_home.getId())
        {
            Intent intent = new Intent(context, HomeScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

//        else if(id==wfh_apply_instruct.getId())
//        {
//            Intent intent=new Intent(context, TermsAndConditionsActivity.class);
//            intent.putExtra("WorkFromHome","WORK FROM HOME:");
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//            //openWFHInstructPopup();
//        }
    }

    private void openWFHInstructPopup() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Work From Home :");
        builder1.setMessage("1. Monthly 1 WFH is allowed. It is solely on management’s decision whether to approve it or not depending upon the reason given.\n" +
                "\n" +
                "2. The daily report has to be shared for the work done without fail.\n" +
                "\n" +
                "3. More than 2 WFH will lead to one day pay for the 2 days worked. The daily report shared will be evaluated and if the work is done is satisfactory then the payment will be done according to the salary.\n");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes I have Read",
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

    private void sendWFHAppyList() {

        if (WfhFromDate.equals("")) {
            Toast.makeText(context, "Insert Work From Home From Date", Toast.LENGTH_SHORT).show();
        } else if (WfhToDate.equals("")) {
            Toast.makeText(context, "Insert Work From Home To Date", Toast.LENGTH_SHORT).show();
        } else if (edit_wfh_bahana.getText().toString().trim().equals("")) {
            edit_wfh_bahana.requestFocus();
            edit_wfh_bahana.setError("bahana dalo");
        } else {
            TotalCountDays = String.valueOf(allshowdate);
            sendWFHRestAPI();
        }

    }

    private void sendWFHRestAPI() {
        String employee_id = spManager.getEmployee_id();
        String full_name = spManager.getUser_full_name();
        String reson = edit_wfh_bahana.getText().toString();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        String currentdate = day + "-" + month + "-" + year;
        Date wfhfromdate, wfhtodate, currentDate1;

        SimpleDateFormat formats = new SimpleDateFormat("dd-MM-yyy");
        String WFHFromDate = "", WFHToDate = "", currentDate = "";
        try {
            wfhfromdate = formats.parse(WfhFromDate);
            wfhtodate = formats.parse(WfhToDate);
            currentDate1 = formats.parse(currentdate);

            WFHFromDate = formats.format(wfhfromdate);
            WFHToDate = formats.format(wfhtodate);
            currentDate = formats.format(currentDate1);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        dialog.show(" ");

        WebServiceModel.getRestApi().ApplyWFH(employee_id, full_name, WFHFromDate, WFHToDate, reson, currentDate, String.valueOf(allshowdate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WfhSucessResponse>() {
                    @Override
                    public void onNext(WfhSucessResponse wfhresponse) {
                        dialog.dismiss(" ");

                        String msg = wfhresponse.getMsg();

                        if (msg.equals("success")) {
                            ApplyLeavepopup();
                            dialog.dismiss(" ");

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


    private void ApplyLeavepopup() {

        final Dialog popupdialog = new Dialog(context);
        popupdialog.setContentView(R.layout.apply_work_from_home_popup);
        popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupdialog.setCanceledOnTouchOutside(false);

        popupdialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        Button btn_okay = (Button) popupdialog.findViewById(R.id.wfh_btn_okay);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupdialog.cancel();
                finish();
            }
        });

        popupdialog.show();
    }


    private void getHolidaysLeave() {

        dialog.show(" ");

        WebServiceModel.getRestApi().getLeaveDates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveDates>() {
                    @Override
                    public void onNext(LeaveDates leavedates) {
                        dialog.dismiss(" ");

                        String msg = leavedates.getMsg();

                        if (msg.equals("success")) {
                            getDateList = leavedates.getHolidays_leave_list();
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

    private void ShowDatePopup() {
        Calendar now1 = Calendar.getInstance();
        int year1 = now1.get(Calendar.YEAR);
        int month1 = now1.get(Calendar.MONTH); // Note: zero based!
        int day1 = now1.get(Calendar.DAY_OF_MONTH);

        new SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback(WfhApplyActivity.this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(false)
                .defaultDate(year1, month1, day1)
                .maxDate(2050, 0, 1)
                .minDate(1950, 0, 1)
                .build()
                .show();

    }

    @Override
    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
        String month = String.valueOf(monthOfYear + 1);
        selectdate = dayOfMonth + "-" + month + "-" + year1;

        if (WFHFromTODate.equals("1")) {
            text_wfh_from_date.setText(selectdate);
            WfhFromDate = selectdate;

            ShowDays();

            if (!WfhToDate.equals("")) {
                String checkstatus = checkBeforPlannedDate(WfhFromDate, WfhToDate);

                if (checkstatus.equals("yes")) {
                    WfhToDate = "";
                    text_wfh_to_date.setText("");
                    beforefromdate();
                }
                //Toast.makeText(context,checkstatus,Toast.LENGTH_SHORT).show();
            }
        } else if (WFHFromTODate.equals("2")) {

            if (!WfhFromDate.equals("")) {


                String wfhtodate = selectdate;

                String checkstatus = checkBeforPlannedDate(WfhFromDate, wfhtodate);

                if (checkstatus.equals("no")) {
                    WfhToDate = selectdate;
                    text_wfh_to_date.setText(selectdate);
                    ShowDays();
                } else {
                    //Toast.makeText(context,"dont small date to from date",Toast.LENGTH_SHORT).show();
                    beforefromdate();

                }

            } else {

                //Toast.makeText(context,"first select from date",Toast.LENGTH_SHORT).show();

                firstselectfromdate();
            }

        }

    }

    private void beforefromdate() {
        final Dialog popupdialog = new Dialog(context);
        popupdialog.setContentView(R.layout.before_from_date);
        popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupdialog.setCanceledOnTouchOutside(false);

        Button btn_okay = (Button) popupdialog.findViewById(R.id.wfh_btn_okay);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupdialog.cancel();

            }
        });

        popupdialog.show();
    }

    private void firstselectfromdate() {
        final Dialog popupdialog = new Dialog(context);
        popupdialog.setContentView(R.layout.select_from_date);
        popupdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupdialog.setCanceledOnTouchOutside(false);

        Button btn_okay = (Button) popupdialog.findViewById(R.id.wfh_btn_okay);
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupdialog.cancel();

            }
        });

        popupdialog.show();

    }

    private String checkBeforPlannedDate(String plannedfromdate1, String plannedtodate1) {


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String checkstatus = "no";

        try {
            Date date1 = sdf.parse(plannedfromdate1);
            Date date2 = sdf.parse(plannedtodate1);
            if (date1.compareTo(date2) > 0) {
                checkstatus = "yes";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return checkstatus;
    }

    private void ShowDays() {


        if (!WfhFromDate.equals("") && !WfhToDate.equals("")) {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
            String inputString1 = WfhFromDate;
            String inputString2 = WfhToDate;

            try {
                Date date1 = myFormat.parse(inputString1);
                Date date2 = myFormat.parse(inputString2);
                long diff = date2.getTime() - date1.getTime();
                String day1 = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                int day2 = Integer.parseInt(day1);
                int day3 = day2 + 1;

                int count = showsundaycount(WfhFromDate, WfhToDate);
                int showday = day3 - count;

                int holidaydate = getLeavedate(WfhFromDate, WfhToDate);
                allshowdate = showday - holidaydate;

                String day_append = allshowdate + " " + "Days";
                wfh_total_days.setText(day_append);

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }


    public static int showsundaycount(String wfhfromdate, String wfhtodate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        int count = 0;
        try {
            Date d1 = formatter.parse(wfhfromdate);
            Date d2 = formatter.parse(wfhtodate);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(d1);

            Calendar c2 = Calendar.getInstance();
            c2.setTime(d2);

            int sundays = 0;
            int saturday = 0;

            while (!c1.after(c2)) {
                if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    saturday++;
                }
                if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    sundays++;
                }

                c1.add(Calendar.DATE, 1);
            }
            count = sundays;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("sunday" + count);

        return count;
    }


    private int getLeavedate(String wfhomefromdate, String wfhometodate) {

        int count = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date d1 = formatter.parse(wfhomefromdate);
            Date d2 = formatter.parse(wfhometodate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(d1);

            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(d2);
            endCalendar.add(Calendar.DATE, 1);
            if (getDateList != null) {
                while (calendar.before(endCalendar)) {
                    Date result = calendar.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = dateFormat.format(result);

                    for (int i = 0; i < getDateList.length; i++) {
                        if (getDateList[i].equals(strDate)) {
                            count++;
                        }
                    }

                    calendar.add(Calendar.DATE, 1);
                }
            } else {
                getHolidaysLeave();
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return count;
    }
}
