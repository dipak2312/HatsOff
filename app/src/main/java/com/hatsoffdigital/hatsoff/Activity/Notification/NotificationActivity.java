package com.hatsoffdigital.hatsoff.Activity.Notification;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.Notificationadapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.AnnouncementListResponse;
import com.hatsoffdigital.hatsoff.Models.Announcement_list;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    RecyclerView rec_show_notification;
    //ImageView notification_close_img,notification_toggle_img;
    RelativeLayout rel_notification_toggel;
    Notificationadapter adapter;
    SPManager spManager;
    //ImageView noti_inst_img;
    CustomProgressDialog dialog;
    AlertDialog reportdialog;


    ArrayList<Announcement_list> AnnoucementList;
    ImageView img_noti_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        context = NotificationActivity.this;
        getSupportActionBar().hide();

        dialog = new CustomProgressDialog(context);

        spManager = new SPManager(context);

        AnnoucementList = new ArrayList<>();

        rel_notification_toggel = (RelativeLayout) findViewById(R.id.rel_notification_toggel);
        rel_notification_toggel.setOnClickListener(this);

        img_noti_back=(ImageView)findViewById(R.id.img_noti_back);
        img_noti_back.setOnClickListener(this);

        rec_show_notification = (RecyclerView) findViewById(R.id.rec_show_notification);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rec_show_notification.setNestedScrollingEnabled(false);
        rec_show_notification.setLayoutManager(lm);

        String notification = spManager.getAnnouncements();

//        if(notification.equals(" ")) {
//            showNotificatioPopup();
//            spManager.setAnnouncements("announcements");
//        }
//


        Intent intent = getIntent();
        String imagepath = intent.getStringExtra("imageString");

        if (imagepath != null) {
            {
                openBdyPopup(imagepath);
            }

        }
        //BdyPopup();

        Annoucement();
    }

    private void openBdyPopup(String image) {

      AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.show_notification_image, null);

        ImageView imageshow = (ImageView) view.findViewById(R.id.image_view);

        Glide.with(context)
                .load(image)
                .placeholder(R.drawable.loading)
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageshow);

        RelativeLayout rel_text = (RelativeLayout) view.findViewById(R.id.rel_text);
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


    private void Annoucement() {

        dialog.show(" ");

        WebServiceModel.getRestApi().AnnouncementList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AnnouncementListResponse>() {
                    @Override
                    public void onNext(AnnouncementListResponse NotificationList) {
                        dialog.dismiss(" ");

                        String msg = NotificationList.getMsg();

                        if (msg.equals("success")) {

                            AnnoucementList = NotificationList.getAnnouncement_list();
                            adapter = new Notificationadapter(context, AnnoucementList);
                            rec_show_notification.setAdapter(adapter);
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
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);
                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

//    private void showNotificatioPopup() {
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//        builder1.setTitle("Announcements");
//        builder1.setMessage("Hurray!! Finally the whiteboard will get some rest. All the important announcements shall be seen here & also you will get a notifications each time their is an important announcement. Chances of you not reading will be eliminated & there will be no spam we promise (dnt take this seriously, iss company mein kuch bhi ho sakta hai bhaiya!!)\n");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Yes I Have Read",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//
//        Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
//        LinearLayout parent = (LinearLayout) positiveButton.getParent();
//        parent.setGravity(Gravity.CENTER_HORIZONTAL);
//        View leftSpacer = parent.getChildAt(1);
//        leftSpacer.setVisibility(View.GONE);
//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == rel_notification_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==img_noti_back.getId())
        {
            finish();
        }

    }
}
