package com.hatsoffdigital.hatsoff.Activity.Notification;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hatsoffdigital.hatsoff.Activity.Profile.UpdateProfileActivity;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.HolidayViewAdapter;
import com.hatsoffdigital.hatsoff.Adapters.Notificationadapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.AnnouncementListResponse;
import com.hatsoffdigital.hatsoff.Models.Announcement_list;
import com.hatsoffdigital.hatsoff.Models.HolidaysListResponse;
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

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    RecyclerView rec_show_notification;
    ImageView notification_close_img,notification_toggle_img;
    Notificationadapter adapter;
    TextView noti_date;
    SPManager spManager;
    ImageView noti_inst_img;
    CustomProgressDialog dialog;
    android.support.v7.app.AlertDialog reportdialog;
    Dialog dialog1;
    ArrayList<Announcement_list>AnnoucementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        context=NotificationActivity.this;
        getSupportActionBar().hide();

        dialog=new CustomProgressDialog(context);

        spManager=new SPManager(context);

        AnnoucementList=new ArrayList<>();

        notification_toggle_img=(ImageView)findViewById(R.id.notification_toggle_img);
        notification_toggle_img.setOnClickListener(this);
        notification_close_img=(ImageView)findViewById(R.id.notification_close_img);
        notification_close_img.setOnClickListener(this);

        noti_date=(TextView)findViewById(R.id.noti_date);

        noti_inst_img=(ImageView)findViewById(R.id.noti_inst_img);
        noti_inst_img.setOnClickListener(this);

        rec_show_notification=(RecyclerView)findViewById(R.id.rec_show_notification);
        LinearLayoutManager lm=new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rec_show_notification.setLayoutManager(lm);

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
        noti_date.setText(date);

        String notification=spManager.getAnnouncements();

        if(notification.equals(" ")) {
            showNotificatioPopup();
            spManager.setAnnouncements("announcements");
        }



        Intent intent=getIntent();
        String imagepath=intent.getStringExtra("imageString");

        if(imagepath !=null) {
            {
                openBdyPopup(imagepath);
            }

        }
        BdyPopup();
        Annoucement();
    }

    private void openBdyPopup(String image) {


        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.show_notification_image, null);

        ImageView imageshow=(ImageView)view.findViewById(R.id.image_view);
        //imageshow.setImageResource(R.drawable.bday1);


        Glide.with(context)
                .load(image)
                .placeholder(R.drawable.loading)
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageshow);



        RelativeLayout rel_text=(RelativeLayout)view.findViewById(R.id.rel_text);
        rel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportdialog.dismiss();
            }
        });


        dialog.setView(view);
        reportdialog = dialog.create();

        reportdialog.show();






    }




    private void BdyPopup() {












    }

    private void Annoucement() {

        dialog.show(" ");

        WebServiceModel.getRestApi().AnnouncementList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AnnouncementListResponse>() {
                    @Override
                    public void onNext(AnnouncementListResponse NotificationList) {
                        dialog.dismiss(" ");

                        String msg=NotificationList.getMsg();

                        if(msg.equals("success")) {

                            AnnoucementList=NotificationList.getAnnouncement_list();
                            adapter=new Notificationadapter(context,AnnoucementList);
                            rec_show_notification.setAdapter(adapter);
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

    private void showNotificatioPopup() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Announcements");
        builder1.setMessage("Hurray!! Finally the whiteboard will get some rest. All the important announcements shall be seen here & also you will get a notifications each time their is an important announcement. Chances of you not reading will be eliminated & there will be no spam we promise (dnt take this seriously, iss company mein kuch bhi ho sakta hai bhaiya!!)\n");
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

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if(id==notification_close_img.getId())
        {
            finish();
        }
        else if(id==notification_toggle_img.getId())
        {
            Intent intent=new Intent(context, UpdateProfileActivity.class);
            startActivity(intent);
        }
        else if(id==noti_inst_img.getId())
        {
            showNotificatioPopup();

        }
    }
}
