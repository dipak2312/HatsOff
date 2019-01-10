package com.hatsoffdigital.hatsoff.Activity.Holiday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Attendence.SuccessScanAttendActivity;
import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.HolidayViewAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.HolidaysListResponse;
import com.hatsoffdigital.hatsoff.Models.Holidays_list;
import com.hatsoffdigital.hatsoff.Models.ScanDateTime;
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

public class HolidayActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    RecyclerView rec_show_Holiday;
    ImageView holiday_toggle_img,holiday_close_img;
    HolidayViewAdapter adapter;
    TextView holiday_date;

    CustomProgressDialog dialog;

    ArrayList<Holidays_list>HolidayList;
    String holiday;
    SPManager spManager;

    ImageView holiday_inst_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        context=HolidayActivity.this;
        getSupportActionBar().hide();

        spManager=new SPManager(context);

        rec_show_Holiday=(RecyclerView)findViewById(R.id.rec_show_Holiday);
        holiday_toggle_img=(ImageView)findViewById(R.id.holiday_toggle_img);
        holiday_toggle_img.setOnClickListener(this);
        holiday_close_img=(ImageView)findViewById(R.id.holiday_close_img);
        holiday_close_img.setOnClickListener(this);

        holiday_inst_img=(ImageView)findViewById(R.id.holiday_inst_img);
        holiday_inst_img.setOnClickListener(this);

        dialog=new CustomProgressDialog(context);

        holiday_date=(TextView)findViewById(R.id.holiday_date);

        HolidayList=new ArrayList<>();

        LinearLayoutManager lm=new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rec_show_Holiday.setLayoutManager(lm);


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

        String date=dayOfWeek+","+" "+day +" "+dayOfMonth+","+ " "+year;
        holiday_date.setText(date);

        getHolidysList();

        String holiday=spManager.getHoliday();

         if(holiday.equals(" ")) {
             HolidayPopup();
             spManager.setHoliday("holiday");
         }
    }

    private void HolidayPopup() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Holiday");
        builder1.setMessage("Holiday Section consists a list of entire holidays entitled in a year. Isnt it great, Now no need to ask people around \"bhai kal leave hai kya?\" Also if there is any update in the holiday calendar, we shall notify via the app & the updates can be seen here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes I Have Read",
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

    private void getHolidysList() {

        dialog.show(" ");

        WebServiceModel.getRestApi().HolidaysList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<HolidaysListResponse>() {
                    @Override
                    public void onNext(HolidaysListResponse holidayList) {
                        dialog.dismiss(" ");

                        String msg=holidayList.getMsg();

                        if(msg.equals("success")) {

                            HolidayList = holidayList.getHolidays_list();
                             adapter=new HolidayViewAdapter(context,HolidayList);
                             rec_show_Holiday.setAdapter(adapter);

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

    @Override
    public void onClick(View view) {

        int id=view.getId();

        if(id==holiday_close_img.getId())
        {
            finish();
        }
        else if(id==holiday_toggle_img.getId())
        {
            Intent intent=new Intent(context, UpdateProfileActivity.class);
            startActivity(intent);
        }
        else if(id==holiday_inst_img.getId())
        {
            HolidayPopup();
        }

    }
}
