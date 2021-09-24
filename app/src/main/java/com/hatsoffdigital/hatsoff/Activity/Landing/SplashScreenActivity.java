package com.hatsoffdigital.hatsoff.Activity.Landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    Context context;
    SPManager SpManager;

    private static final int SPLASH_DURATION_MS = 2000;

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

        getSupportActionBar().hide();

        mHandler.postDelayed(mEndSplash, SPLASH_DURATION_MS);


    }


}
