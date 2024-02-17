package com.hatsoffdigital.hatsoff.Utils;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.R;

import org.jsoup.Jsoup;

public class MyApplication extends Application {

    String currentVersion = "";

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {

            currentVersion = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new GetVersionCode().execute();
    }


    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return Jsoup.connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName() + "&hl=en")
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


                    Runnable runnable=new Runnable() {
                        @Override
                        public void run() {

                            AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                            builder.setTitle("Update");
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setCancelable(false);
                            builder.setMessage("New Update is available");

//                    builder.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            try {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
//                            } catch (android.content.ActivityNotFoundException anfe) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
//                            }
//                        }
//                    });

                            Dialog dialog = builder.create();
                            /** this required special permission but u can use aplication context */
//                    dialog.getWindow().setType(WindowManager.LayoutParams.TY);
                            /** show dialog */
                            dialog.show();




                        }
                    };





                }

                //Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);


            }
        }

    }
}
