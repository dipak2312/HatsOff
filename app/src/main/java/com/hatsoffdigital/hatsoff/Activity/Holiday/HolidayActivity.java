package com.hatsoffdigital.hatsoff.Activity.Holiday;


import android.content.Context;

import android.content.Intent;

import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.RelativeLayout;


import com.hatsoffdigital.hatsoff.Activity.TermsConditions.TermsAndConditionsActivity;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.HolidayViewAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.HolidaysListResponse;
import com.hatsoffdigital.hatsoff.Models.Holidays_list;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CurrentDateTime;
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

public class HolidayActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    RecyclerView rec_show_Holiday;

    RelativeLayout rel_holiday_toggel;
    HolidayViewAdapter adapter;

    CustomProgressDialog dialog;

    ArrayList<Holidays_list> HolidayList;

    SPManager spManager;
    ImageView img_holiday;



    //ImageView holiday_inst_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        context = HolidayActivity.this;
        getSupportActionBar().hide();

        spManager = new SPManager(context);

        rec_show_Holiday = (RecyclerView) findViewById(R.id.rec_show_Holiday);

        rel_holiday_toggel = (RelativeLayout) findViewById(R.id.rel_holiday_toggel);
        rel_holiday_toggel.setOnClickListener(this);


        img_holiday=(ImageView)findViewById(R.id.img_holiday);
        img_holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//
//        holiday_inst_img = (ImageView) findViewById(R.id.holiday_inst_img);
//        holiday_inst_img.setOnClickListener(this);

        dialog = new CustomProgressDialog(context);



        HolidayList = new ArrayList<>();

        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rec_show_Holiday.setNestedScrollingEnabled(false);
        rec_show_Holiday.setLayoutManager(lm);


        //holiday_date.setText(CurrentDateTime.getDatetime());

        getHolidysList();

//        String holiday=spManager.getHoliday();
//
//         if(holiday.equals(" ")) {
//             HolidayPopup();
//             spManager.setHoliday("holiday");
//         }
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

                        String msg = holidayList.getMsg();

                        if (msg.equals("success")) {

                            HolidayList = holidayList.getHolidays_list();
                            adapter = new HolidayViewAdapter(context, HolidayList);
                            rec_show_Holiday.setAdapter(adapter);

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

        int id = view.getId();

      if (id == rel_holiday_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
//      else if (id == holiday_inst_img.getId()) {
//            //HolidayPopup();
//            Intent intent = new Intent(context, TermsAndConditionsActivity.class);
//            intent.putExtra("Holiday", "HOLIDAY:");
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//        }

    }
}
