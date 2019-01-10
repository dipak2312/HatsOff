package com.hatsoffdigital.hatsoff.Activity.Landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    Context context;
    SPManager SpManager;
    TextView text_name, current_date;
    private static final int SPLASH_DURATION_MS = 3000;

    private Handler mHandler = new Handler();
    private Runnable mEndSplash = new Runnable() {
        public void run() {
            if (!isFinishing()) {
                mHandler.removeCallbacks(this);

                if (SpManager.getLoggedIn().equals("Login")) {

                    getuserLogin();


                } else {
                    Intent intent = new Intent(context, GetOtpActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }


            }
        }

        ;
    };

    private void getuserLogin() {
        Intent intent = new Intent(context, HomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = SplashScreenActivity.this;
        SpManager = new SPManager(context);
        String str = SpManager.getName();
        String name = "Hey," + " " + str + "!!";

//        Hey, sagar!!

        getSupportActionBar().hide();

        mHandler.postDelayed(mEndSplash, SPLASH_DURATION_MS);

        text_name = (TextView) findViewById(R.id.name);
        text_name.setText(name);

        current_date = (TextView) findViewById(R.id.current_date);
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
        current_date.setText(date);


    }


}
