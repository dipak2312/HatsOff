package com.hatsoffdigital.hatsoff.Activity.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hatsoffdigital.hatsoff.Activity.Attendence.AttendenceActivity;
import com.hatsoffdigital.hatsoff.Activity.HoFacts.HoFactsActivity;
import com.hatsoffdigital.hatsoff.Activity.Holiday.HolidayActivity;
import com.hatsoffdigital.hatsoff.Activity.Invoice.InvoiceAdminActivity;
import com.hatsoffdigital.hatsoff.Activity.Invoice.InvoiceHomeActivity;
import com.hatsoffdigital.hatsoff.Activity.Leave.LeaveHomeActivity;
import com.hatsoffdigital.hatsoff.Activity.Meeting.MeetingActivity;
import com.hatsoffdigital.hatsoff.Activity.Notification.NotificationActivity;
import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.WFH.WfhHomeActivity;
import com.hatsoffdigital.hatsoff.Adapters.LeaveCalander;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.AverageTime;
import com.hatsoffdigital.hatsoff.Models.EmployeeListResponse;
import com.hatsoffdigital.hatsoff.Models.GetExperience;
import com.hatsoffdigital.hatsoff.Models.HoFactsResponse;
import com.hatsoffdigital.hatsoff.Models.HolidayPopupResponse;
import com.hatsoffdigital.hatsoff.Models.InvoiceList;
import com.hatsoffdigital.hatsoff.Models.InvoiceListResponse;
import com.hatsoffdigital.hatsoff.Models.LeaveCount;
import com.hatsoffdigital.hatsoff.Models.LeaveDates;
import com.hatsoffdigital.hatsoff.Models.SendTokenResponse;
import com.hatsoffdigital.hatsoff.Models.employee_list;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CurrentDateTime;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import org.jsoup.Jsoup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rel_home_toggel;
    Context context;
    LinearLayout attendence, holiday, notification, leavetracker, lin_meeting, lin_wfh;
    CustomProgressDialog dialog;
    SPManager spManager;
    String user_name;
    TextView text_user_name;
    String date_time = " ";
    String reg_id;
    String emp_id;
    TextView text_date;
    int year, month, day;
    TextView text_experience;
    CircleImageView user_profile_img;
    TextView text_average;
    Dialog dialog1;
    Button btn_thanks;
    String birdth_date;
    String userNotFound = "present";
    String currentVersion = "";
    RelativeLayout rel_ho_facts;
    TextView txt_ho_facts;
    ImageView close_ho_facts;
    ArrayList<employee_list> employeeList;

    TextView text_pl_available, text_el_available;

    LinearLayout lin_ho_facts, lin_request_invoice;

    ArrayList<InvoiceList> invoiceList;
    String invoice = "no";

    String invoiceRole;
    LinearLayout lin_avgtime, lin_emergency_leaves, lin_planned_leaves;

    ArrayList<String> dateList;
    RecyclerView recy_all_day;

    LeaveCalander leavecalAdapter;
    String[] getDateList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();
        context = HomeScreenActivity.this;
        spManager = new SPManager(context);
        employeeList = new ArrayList<>();

        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        day = now.get(Calendar.DAY_OF_MONTH);

        dateList = new ArrayList<>();

        text_pl_available = (TextView) findViewById(R.id.text_pl_available);
        text_el_available = (TextView) findViewById(R.id.text_el_available);

        text_el_available.setText(spManager.getEL_Available());
        text_pl_available.setText(spManager.getPL_Available());
        invoiceList = new ArrayList<>();

        recy_all_day = (RecyclerView) findViewById(R.id.recy_all_day);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy_all_day.setLayoutManager(lm);

        lin_request_invoice = (LinearLayout) findViewById(R.id.lin_request_invoice);
        lin_request_invoice.setOnClickListener(this);

        lin_avgtime = (LinearLayout) findViewById(R.id.lin_avgtime);
        lin_emergency_leaves = (LinearLayout) findViewById(R.id.lin_emergency_leaves);
        lin_planned_leaves = (LinearLayout) findViewById(R.id.lin_planned_leaves);


        if (spManager.getHO_Fact_Date().equals(" ")) {
            getHoFactList();

        } else {

            String inputDateStr = String.format("%s-%s-%s", day, month, year);
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
            Date inputDate = null;
            String strDate = "";
            try {
                inputDate = formater.parse(inputDateStr);
                strDate = formater.format(inputDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!spManager.getHO_Fact_Date().equals(strDate)) {

                getHoFactList();
            }

        }

        if (spManager.getHoliday_reminder().equals(" ")) {
            getHolidayReminderResponse();

        } else {
            String inputDateStr1 = String.format("%s-%s-%s", day, month, year);
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
            Date inputDate1 = null;
            String strDate1 = "";
            try {
                inputDate1 = formater.parse(inputDateStr1);
                strDate1 = formater.format(inputDate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!spManager.getHoliday_reminder().equals(strDate1)) {

                getHolidayReminderResponse();
            }
        }


        if (spManager.getUser_full_name().equals(" ")) {
            getEmployeeList();
        }

        text_user_name = (TextView) findViewById(R.id.text_user_name);
        user_profile_img = (CircleImageView) findViewById(R.id.user_profile_img);

        user_profile_img.setOnClickListener(this);

        text_date = (TextView) findViewById(R.id.text_date);

        dialog = new CustomProgressDialog(context);

        text_experience = (TextView) findViewById(R.id.text_experience);
        text_experience.setText(spManager.getTotal_experience());

        rel_home_toggel = (RelativeLayout) findViewById(R.id.rel_home_toggel);
        rel_home_toggel.setOnClickListener(this);
        attendence = (LinearLayout) findViewById(R.id.attendence);
        attendence.setOnClickListener(this);

        holiday = (LinearLayout) findViewById(R.id.holiday);
        holiday.setOnClickListener(this);

        notification = (LinearLayout) findViewById(R.id.notification);
        notification.setOnClickListener(this);

        leavetracker = (LinearLayout) findViewById(R.id.leavetracker);
        leavetracker.setOnClickListener(this);

        lin_meeting = (LinearLayout) findViewById(R.id.lin_meeting);
        lin_meeting.setOnClickListener(this);

        lin_wfh = (LinearLayout) findViewById(R.id.lin_wfh);
        lin_wfh.setOnClickListener(this);

        text_average = (TextView) findViewById(R.id.text_average);
        text_average.setText(spManager.getAverage_time());

        rel_ho_facts = (RelativeLayout) findViewById(R.id.rel_ho_facts);
        txt_ho_facts = (TextView) findViewById(R.id.txt_ho_facts);

        close_ho_facts = (ImageView) findViewById(R.id.close_ho_facts);
        close_ho_facts.setOnClickListener(this);

        lin_ho_facts = (LinearLayout) findViewById(R.id.lin_ho_facts);
        lin_ho_facts.setOnClickListener(this);


        Glide.with(context)
                .load(spManager.getProfile_Image())
                .placeholder(R.drawable.user_info)
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(user_profile_img);


        text_date.setText(CurrentDateTime.getDatetime());


        emp_id = spManager.getEmployee_id();
        String fcm = spManager.getFcm_Token();


        try {

            currentVersion = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        new GetVersionCode().execute();


        List<Date> dates = new ArrayList<Date>();


        Calendar cal = Calendar.getInstance();
        //int firstDate = cal.getActualMinimum(Calendar.DATE);
        int firstDate = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);


        String str_date = String.valueOf(firstDate) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year);

        Calendar cal1 = Calendar.getInstance();
        int firstDate1 = cal1.getActualMaximum(Calendar.DATE);
        int month1 = cal1.get(Calendar.MONTH);
        int year1 = cal1.get(Calendar.YEAR);
        String end_date = String.valueOf(firstDate1) + "-" + String.valueOf(month1 + 1) + "-" + String.valueOf(year1);

        DateFormat formatter;

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate1 = null;
        Date endDate1 = null;
        try {
            startDate1 = (Date) formatter.parse(str_date);
            endDate1 = (Date) formatter.parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
        long endTime = endDate1.getTime(); // create your endtime here, possibly using Calendar or Date
        long curTime = startDate1.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            String ds = formatter.format(lDate);
            dateList.add(ds);
        }

        leavecalAdapter = new LeaveCalander(context, dateList, getDateList);
        recy_all_day.setAdapter(leavecalAdapter);


        getAverageList();

        sendRegisterId();
        getLeaveCount();
        getHolidaysLeave();



    }


    private void getHolidaysLeave() {

        WebServiceModel.getRestApi().getLeaveDates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveDates>() {
                    @Override
                    public void onNext(LeaveDates leavedates) {

                        String msg = leavedates.getMsg();

                        if (msg.equals("success")) {


                            getDateList = leavedates.getHolidays_leave_list();

                            leavecalAdapter = new LeaveCalander(context, dateList, getDateList);
                            recy_all_day.setAdapter(leavecalAdapter);

                            System.out.println(getDateList);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getLeaveCount() {

        WebServiceModel.getRestApi().getLeaveCount(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LeaveCount>() {
                    @Override
                    public void onNext(LeaveCount leavecount) {
                        dialog.dismiss(" ");

                        String el_date = leavecount.getEmergency_leave_count();
                        String pl_date = leavecount.getPlanned_leave_count();


                           double el=0, pl=0;

                           if(!el_date.equals(""))
                            {
                                el = Double.parseDouble(el_date);
                            }

                            if(!pl_date.equals(""))
                            {
                                pl = Double.parseDouble(pl_date);
                            }

                            if (el < 0) {
                                lin_emergency_leaves.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_below_avgtime));
                            } else if (el >= 0) {
                                lin_emergency_leaves.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_avgtime));
                            }

                            if (pl < 0) {
                                lin_planned_leaves.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_below_avgtime));
                            } else if (pl >= 0) {
                                lin_planned_leaves.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_avgtime));
                            }

                            if(el_date.equals(""))
                            {
                                spManager.setEL_Available("0");
                            }
                            else
                            {
                                spManager.setEL_Available(el_date);
                            }

                        if(pl_date.equals(""))
                        {
                            spManager.setPL_Available("0");
                        }
                        else
                        {
                            spManager.setPL_Available(pl_date);
                        }

                        text_el_available.setText(spManager.getEL_Available());
                         text_pl_available.setText(spManager.getPL_Available());

                        }



                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);

                        // Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getHolidayReminderResponse() {

//        dialog.show(" ");

        WebServiceModel.getRestApi().getHolidayPopup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<HolidayPopupResponse>() {
                    @Override
                    public void onNext(HolidayPopupResponse getHolidayResponse) {
//                        dialog.dismiss(" ");

                        String msg = getHolidayResponse.getMsg();

                        if (msg.equals("success")) {
                            spManager.setHoliday_reminder(getHolidayResponse.getDate_notification());
                            getHolidayReminder(getHolidayResponse.getHolidays_img());


                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getHolidayReminder(String holiday_image) {

        final Dialog holidayreminddialog = new Dialog(context, R.style.DialogCustomTheme);
        holidayreminddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        holidayreminddialog.setContentView(R.layout.remind_holiday_popup);

        btn_thanks = (Button) holidayreminddialog.findViewById(R.id.btn_thanks);
        ImageView holiday_popup = (ImageView) holidayreminddialog.findViewById(R.id.holiday_popup);
        final ProgressBar progress_bar = (ProgressBar) holidayreminddialog.findViewById(R.id.progress_bar);
        final TextView progress_bar_text = (TextView) holidayreminddialog.findViewById(R.id.progress_bar_text);

        Glide.with(context)
                .load(holiday_image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        //Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
                        btn_thanks.setVisibility(View.VISIBLE);
                        progress_bar.setVisibility(View.GONE);
                        progress_bar_text.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        btn_thanks.setVisibility(View.VISIBLE);
                        progress_bar.setVisibility(View.GONE);
                        progress_bar_text.setVisibility(View.GONE);
                        //Toast.makeText(context,"picture loaded",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .into(holiday_popup);

        btn_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holidayreminddialog.dismiss();
                Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();

            }
        });

        holidayreminddialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        holidayreminddialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        holidayreminddialog.getWindow().setGravity(Gravity.BOTTOM);
        holidayreminddialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        holidayreminddialog.show();
    }


    private void getEmployeeList() {

        WebServiceModel.getRestApi().employeeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<EmployeeListResponse>() {
                    @Override
                    public void onNext(EmployeeListResponse employeelist) {

                        employeeList = employeelist.getEmployee_list();

                        for (int i = 0; i < employeeList.size(); i++) {
                            if (spManager.getEmployee_id().equals(employeeList.get(i).getEmployee_id())) {

                                String current_name = employeeList.get(i).getName();
                                spManager.setUser_full_name(current_name);

                            }

                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getInvoiceList() {

        // dialog.show(" ");

        WebServiceModel.getRestApi().getInvoiceList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InvoiceListResponse>() {
                    @Override
                    public void onNext(InvoiceListResponse AllInvoiceList) {
                        // dialog.dismiss(" ");

                        String msg = AllInvoiceList.getMsg();

                        if (msg.equals("success")) {

                            invoiceList = AllInvoiceList.getInvoiceList();


                            if (invoiceList.size() != 0) {

                                for (int i = 0; i < invoiceList.size(); i++) {
                                    if (invoiceList.get(i).getEmployee_id().equals(spManager.getEmployee_id())) {

                                        invoice = "yes";
                                        invoiceRole=invoiceList.get(i).getRoleId();

                                    }

                                }


                            }


                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //dialog.dismiss(" ");
                        //NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getHoFactList() {

        // dialog.show(" ");

        WebServiceModel.getRestApi().getHoFacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<HoFactsResponse>() {
                    @Override
                    public void onNext(HoFactsResponse getHoFactResponse) {
                        // dialog.dismiss(" ");

                        String msg = getHoFactResponse.getMsg();

                        if (msg.equals("success")) {

                            spManager.setHO_Fact_Date(getHoFactResponse.getFactDate());
                            rel_ho_facts.setVisibility(View.VISIBLE);
                            txt_ho_facts.setText(getHoFactResponse.getFactName());

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //dialog.dismiss(" ");
                        //NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();

        user_name = spManager.getName();

        getTimeFromAndroid();
        String name = "Hey " + user_name + "!!  " + date_time;
        text_user_name.setText(name);
        text_average.setText(spManager.getAverage_time());
        getExperienceList();
        getInvoiceList();


        String el_date=spManager.getEL_Available();
        String pl_date=spManager.getPL_Available();



            double el=0, pl=0;

            if(!el_date.equals(" "))
            {
                el = Double.parseDouble(el_date);
            }

            if(!pl_date.equals(" "))
            {
                pl = Double.parseDouble(pl_date);
            }

            if (el < 0) {
                lin_emergency_leaves.setBackground(context.getResources().getDrawable(
                        R.drawable.corner_below_avgtime));
            } else if (el >= 0) {
                lin_emergency_leaves.setBackground(context.getResources().getDrawable(
                        R.drawable.corner_avgtime));
            }

            if (pl < 0) {
                lin_planned_leaves.setBackground(context.getResources().getDrawable(
                        R.drawable.corner_below_avgtime));
            } else if (pl >= 0) {
                lin_planned_leaves.setBackground(context.getResources().getDrawable(
                        R.drawable.corner_avgtime));
            }


            text_el_available.setText(spManager.getEL_Available());
            text_pl_available.setText(spManager.getPL_Available());





    }


    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=en")
                        .timeout(10000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                //Toast.makeText(context,onlineVersion,Toast.LENGTH_SHORT).show();

                if (onlineVersion.equals(currentVersion)) {

                } else {

                    LayoutInflater factory = LayoutInflater.from(context);
                    final View updatedigView = factory.inflate(R.layout.update_app_view, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.CustomAlertDialog).create();
                    alertDialog.setView(updatedigView);

                    alertDialog.setCanceledOnTouchOutside(false);


                    alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            // Prevent dialog close on back press button
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });


                    Button btn_update = (Button) updatedigView.findViewById(R.id.btn_update);
                    btn_update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
                            }

                            alertDialog.dismiss();

                        }
                    });


                    alertDialog.show();


                }

            }


        }
    }


    private void openBdyPopup() {


        dialog1 = new Dialog(context, R.style.DialogCustomTheme);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.bday_popup);

        btn_thanks = (Button) dialog1.findViewById(R.id.btn_thanks);
        btn_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();

            }
        });

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog1.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        dialog1.getWindow().setGravity(Gravity.BOTTOM);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.show();

    }


    private void getAverageList() {

        dialog.show(" ");
        WebServiceModel.getRestApi().getAverageTime(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AverageTime>() {
                    @Override
                    public void onNext(AverageTime getaverageTime) {

                        dialog.dismiss(" ");
                        String msg = getaverageTime.getMsg();

                        if (msg.equals("success")) {

                            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

                            String currentdate = day + "-" + month + "-" + year;

                            spManager.setAbove_nine_hrs(getaverageTime.getAboveNinehrsCount());
                            spManager.setBelow_nine_hrs(getaverageTime.getBelowNinehrsCount());
                            spManager.setAverage_status(getaverageTime.getAverageStatus());

                            if (spManager.getAverage_status().equals("yes")) {
                                lin_avgtime.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_avgtime));
                            } else {
                                lin_avgtime.setBackground(context.getResources().getDrawable(
                                        R.drawable.corner_below_avgtime));
                            }


                            birdth_date = getaverageTime.getDateOfBirth();

                            if (birdth_date != null) {


                                try {

                                    Date date1 = myFormat.parse(currentdate);
                                    Date date2 = myFormat.parse(birdth_date);

                                    Calendar cal1 = Calendar.getInstance();
                                    cal1.setTime(date1);
                                    Calendar cal2 = Calendar.getInstance();
                                    cal2.setTime(date2);

                                    int day1 = cal1.get(Calendar.DAY_OF_MONTH);
                                    int day2 = cal2.get(Calendar.DAY_OF_MONTH);
                                    int month1 = date1.getMonth() + 1;
                                    int month2 = date2.getMonth() + 1;

                                    if (day1 == day2 && month1 == month2) {
                                        String bdydate = spManager.getBday_Check();

                                        if (bdydate.equals(" ")) {
                                            spManager.setBday_Check(currentdate);
                                            openBdyPopup();

                                        } else {
                                            if (!spManager.getBday_Check().equals(currentdate)) {
                                                spManager.setBday_Check(currentdate);
                                                openBdyPopup();
                                            }


                                        }
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String average = getaverageTime.getAverageTime();
                                if(average.equals("NANhr NANm"))
                                {
                                    average="0hr 0m";
                                }

                                text_average.setText(average);
                                spManager.setAverage_time(average);


                            }

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


                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void getExperienceList() {

        // dialog.show(" ");

        WebServiceModel.getRestApi().GetAllExperience(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GetExperience>() {
                    @Override
                    public void onNext(GetExperience getexperience) {
                        //dialog.dismiss(" ");

                        String msg = getexperience.getMsg();
                        System.out.println("saggimsg" + msg);

                        if (msg.equals("success")) {

//                            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//                            String date1 = day + "-" + month + "-" + year;


                            //String Joining_date = getexperience.getJoiningDate();
                            String image = getexperience.getImage();
                            System.out.println("img" + image);
                            spManager.setProfile_Image(image);
                            Glide.with(getApplicationContext())
                                    .load(image)
                                    .placeholder(R.drawable.user_info)
                                    .dontAnimate()
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .skipMemoryCache(true)
                                    .into(user_profile_img);

                            String experience = getexperience.getExperience();

                            text_experience.setText(experience);
                            spManager.setTotal_experience(experience);

                        } else {
                            userNotFound = "absent";
                            userNotFound();

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        //dialog.dismiss(" ");

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    private void sendRegisterId() {

        //dialog.show(" ");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HomeScreenActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                reg_id = instanceIdResult.getToken();
                WebServiceModel.getRestApi().SendToken(emp_id, reg_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableObserver<SendTokenResponse>() {
                            @Override
                            public void onNext(SendTokenResponse NotificationList) {
                                //dialog.dismiss(" ");
                                String msg = NotificationList.getMsg();

                            }

                            @Override
                            public void onError(Throwable e) {
                                //dialog.dismiss(" ");

                                //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


            }
        });


    }


    private void getTimeFromAndroid() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            date_time = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            date_time = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            date_time = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            date_time = "Good Night";
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == rel_home_toggel.getId()) {

            if (userNotFound.equals("present")) {
                Intent intent = new Intent(context, ToggleScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                userNotFound();
            }


        } else if (id == attendence.getId()) {

            if (userNotFound.equals("present")) {
                Intent intent = new Intent(context, AttendenceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                this.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
                attendence.startAnimation(myAnim);
            } else {
                userNotFound();
            }
        } else if (id == holiday.getId()) {
            if (userNotFound.equals("present")) {
                Intent intent = new Intent(context, HolidayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                userNotFound();
            }

        } else if (id == notification.getId()) {
            if (userNotFound.equals("present")) {
                Intent intent = new Intent(context, NotificationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                userNotFound();
            }

        } else if (id == leavetracker.getId()) {
            if (userNotFound.equals("present")) {
                Intent intent = new Intent(context, LeaveHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                userNotFound();
            }

        } else if (id == lin_meeting.getId()) {
            if (userNotFound.equals("present")) {
                //attenddencepopup();
                Intent intent = new Intent(context, MeetingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                userNotFound();
            }
        } else if (id == lin_wfh.getId()) {
            if (userNotFound.equals("present")) {

                // attenddencepopup();

                Intent intent = new Intent(context, WfhHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                userNotFound();
            }

        } else if (id == user_profile_img.getId()) {
            if (userNotFound.equals("present")) {


                final Dialog nagDialog = new Dialog(context,R.style.AppBlackTheme);
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nagDialog.setCancelable(false);
                nagDialog.setContentView(R.layout.preview_image);
                RelativeLayout rel_back_icon = (RelativeLayout) nagDialog.findViewById(R.id.rel_back_icon);
                ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
                TextView txt_user_name=(TextView)nagDialog.findViewById(R.id.txt_user_name);
                txt_user_name.setText(spManager.getUser_full_name());
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.together);
                ivPreview.startAnimation(myAnim);
                String image=spManager.getProfile_Image();
                Glide.with(getApplicationContext())
                        .load(image)
                        .placeholder(R.drawable.user_info)
                        .dontAnimate()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .into(ivPreview);


                rel_back_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        nagDialog.dismiss();
                    }
                });
                nagDialog.show();



//                Intent intent = new Intent(context, UpdateProfileActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
            } else {
                userNotFound();
            }
        } else if (id == lin_ho_facts.getId()) {
            if (userNotFound.equals("present")) {

                Intent intent = new Intent(context, HoFactsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                userNotFound();
            }

        } else if (id == lin_request_invoice.getId()) {
            if (userNotFound.equals("present")) {


                if (invoice.equals("yes")) {


                    if(invoiceRole.equals("3")) {//invoice user role is 3

                        Intent intent = new Intent(context, InvoiceHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                    else if(invoiceRole.equals("2"))//invoice Admin role is 2
                    {
                        Intent intent = new Intent(context, InvoiceAdminActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                } else {
                    InvoicePopup();
                }

            } else {
                userNotFound();
            }
        } else if (id == close_ho_facts.getId()) {
            rel_ho_facts.setVisibility(View.GONE);
        }

    }

    private void attenddencepopup() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);

        builder1.setTitle("Coming soon");
        builder1.setMessage("Sabar marlo thoda. Woh kya kehte hai \" Shanti ki ****** pakdo \"");

        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
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


    private void userNotFound() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Important Notices");
        builder1.setMessage("Hey There!!\n" +
                "You are no long associated with Hats-Off. Hope you enjoyed your time here. Kindly uninstall the application.\n" +
                "Thank you!!");
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


    private void InvoicePopup() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("");
        builder1.setMessage("Currently, you don't have access to use this Module.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
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


}
