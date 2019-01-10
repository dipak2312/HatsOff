package com.hatsoffdigital.hatsoff.Activity.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hatsoffdigital.hatsoff.Activity.Attendence.AttendenceActivity;
import com.hatsoffdigital.hatsoff.Activity.Holiday.HolidayActivity;
import com.hatsoffdigital.hatsoff.Activity.Leave.LeaveHomeActivity;
import com.hatsoffdigital.hatsoff.Activity.Notification.NotificationActivity;
import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.AverageTime;
import com.hatsoffdigital.hatsoff.Models.GetExperience;
import com.hatsoffdigital.hatsoff.Models.SendTokenResponse;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView toggel_img;
    Context context;
    LinearLayout attendence, holiday, notification, leavetracker, salary,lin_reimbur;
    CustomProgressDialog dialog;
    SPManager spManager;
    String user_name;
    TextView text_user_name;
    String date_time = " ";
    String PROJECT_NUMBER = "563241165970", regid;
    String reg_id;
    String emp_id;
    TextView text_date;
    int year, month, day;
    String inputDateStr;
    Date inputDate = null;
    TextView text_experience;
    CircleImageView user_profile_img;
    TextView text_average;
    Dialog dialog1;
    Button btn_thanks;
    String birdth_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();
        context = HomeScreenActivity.this;

        spManager = new SPManager(context);


        text_user_name = (TextView) findViewById(R.id.text_user_name);
        user_profile_img = (CircleImageView) findViewById(R.id.user_profile_img);
        text_date = (TextView) findViewById(R.id.text_date);

        dialog = new CustomProgressDialog(context);

        text_experience = (TextView) findViewById(R.id.text_experience);
        text_experience.setText(spManager.getTotal_experience());

        toggel_img = (ImageView) findViewById(R.id.toggel_img);
        toggel_img.setOnClickListener(this);
        attendence = (LinearLayout) findViewById(R.id.attendence);
        attendence.setOnClickListener(this);

        holiday = (LinearLayout) findViewById(R.id.holiday);
        holiday.setOnClickListener(this);

        notification = (LinearLayout) findViewById(R.id.notification);
        notification.setOnClickListener(this);

        leavetracker = (LinearLayout) findViewById(R.id.leavetracker);
        leavetracker.setOnClickListener(this);

        salary = (LinearLayout) findViewById(R.id.salary);
        salary.setOnClickListener(this);

        lin_reimbur=(LinearLayout)findViewById(R.id.lin_reimbur);
        lin_reimbur.setOnClickListener(this);

        text_average = (TextView) findViewById(R.id.text_average);
        text_average.setText(spManager.getAverage_time());


        Glide.with(context)
                .load(spManager.getProfile_Image())
                .placeholder(R.drawable.user_info)
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(user_profile_img);

        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        day = now.get(Calendar.DAY_OF_MONTH);
        inputDateStr = String.format("%s/%s/%s", day, month, year);

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
        text_date.setText(date);


        emp_id = spManager.getEmployee_id();
        String fcm = spManager.getFcm_Token();

//       if(fcm.equals(" ")) {
//            spManager.setFcm_Token("FCM");
//            sendRegisterId();
//
//        }


        getAverageList();

        sendRegisterId();


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


                        String msg = getaverageTime.getMsg();

                        if (msg.equals("success")) {
                            dialog.dismiss(" ");


                            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

                            String currentdate = day + "-" + month + "-" + year;


                            birdth_date = getaverageTime.getDateOfBirth();

                            try {

                                Date date1 = myFormat.parse(currentdate);
                                Date date2 = myFormat.parse(birdth_date);

                                Calendar cal1=Calendar.getInstance();
                                cal1.setTime(date1);
                                Calendar cal2=Calendar.getInstance();
                                cal2.setTime(date2);

                                int day1=cal1.get(Calendar.DAY_OF_MONTH);
                                int day2=cal2.get(Calendar.DAY_OF_MONTH);
                                int month1=date1.getMonth()+1;
                                int month2=date2.getMonth()+1;

                                 if(day1==day2 && month1==month2)
                                 {
                                     String bdydate = spManager.getBday_Check();

                                  if (bdydate.equals(" ")) {
                                        spManager.setBday_Check("Happy Bday");

                                        openBdyPopup();

                                   }
                                 }

                                 } catch (ParseException e) {
                                e.printStackTrace();
                            }




                            String average = getaverageTime.getAverageTime();
                            text_average.setText(average);
                            spManager.setAverage_time(average);


                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        NetworkPopup.ShowPopup(context);
                        dialog.dismiss(" ");

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

                        if (msg.equals("success")) {

//                            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//                            String date1 = day + "-" + month + "-" + year;


                            //String Joining_date = getexperience.getJoiningDate();
                            String image = getexperience.getImage();
                            System.out.println("img"+image);
                            spManager.setProfile_Image(image);
                            Glide.with(getApplicationContext())
                                    .load(image)
                                    .placeholder(R.drawable.user_info)
                                    .dontAnimate()
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .skipMemoryCache(true)
                                    .into(user_profile_img);

                            String experience=getexperience.getExperience();

                            text_experience.setText(experience);
                            spManager.setTotal_experience(experience);

//
//                            try {
//
//                                Date Date1 = myFormat.parse(date1);
//                                Date date2 = myFormat.parse(Joining_date);
//
//                                int year1 = Date1.getYear() - date2.getYear();
//                                int month1 = Date1.getMonth() - date2.getMonth();
//
//                                String joiningdate = year1 + "." + month1 + " " + "Yrs";


//
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }


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

                //Toast.makeText(context,emp_id,Toast.LENGTH_SHORT).show();


                //System.out.println("saggi  " + reg_id);

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

    @Override
    protected void onResume() {
        super.onResume();
        user_name = spManager.getName();
        getTimeFromAndroid();
        String name = "Hey " + user_name + "!!  " + date_time;
        text_user_name.setText(name);
        getExperienceList();
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
        if (id == toggel_img.getId()) {

            Intent intent = new Intent(context, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (id == attendence.getId()) {
            Intent intent = new Intent(context, AttendenceActivity.class);
            startActivity(intent);
        } else if (id == holiday.getId()) {
            Intent intent = new Intent(context, HolidayActivity.class);
            startActivity(intent);

        } else if (id == notification.getId()) {
            Intent intent = new Intent(context, NotificationActivity.class);
            startActivity(intent);

        } else if (id == leavetracker.getId()) {
            Intent intent = new Intent(context, LeaveHomeActivity.class);
            startActivity(intent);
            //attenddencepopup();
        } else if (id == salary.getId()) {
            attenddencepopup();
//            Intent intent=new Intent(context, SalaryActivity.class);
//            startActivity(intent);
        }

        else if(id==lin_reimbur.getId())
        {
            attenddencepopup();
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


}
