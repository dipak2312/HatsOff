package com.hatsoffdigital.hatsoff.Activity.HoFacts;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.HoFactsAdapter;
import com.hatsoffdigital.hatsoff.Adapters.Notificationadapter;
import com.hatsoffdigital.hatsoff.Models.AnnouncementListResponse;
import com.hatsoffdigital.hatsoff.Models.FactName;
import com.hatsoffdigital.hatsoff.Models.FactsResponse;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
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

public class HoFactsActivity extends AppCompatActivity {

    RecyclerView recy_ho_facts;
    Context context;
    HoFactsAdapter adapter;
    RelativeLayout rel_hofacts_toggel;
    CustomProgressDialog dialog;
    ArrayList<FactName> hofactList;
    ImageView img_ho_fact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_facts);
        context=HoFactsActivity.this;

        getSupportActionBar().hide();

        dialog=new CustomProgressDialog(context);

        recy_ho_facts=(RecyclerView)findViewById(R.id.recy_ho_facts);
        rel_hofacts_toggel=(RelativeLayout)findViewById(R.id.rel_ho_toggle);
        hofactList=new ArrayList<>();

        rel_hofacts_toggel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToggleScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


        LinearLayoutManager lm=new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_ho_facts.setNestedScrollingEnabled(false);
        recy_ho_facts.setLayoutManager(lm);

        img_ho_fact=(ImageView)findViewById(R.id.img_ho_fact);
        img_ho_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });



        getHoFacts();


    }

    private void getHoFacts() {

        dialog.show(" ");

        WebServiceModel.getRestApi().getHoFactsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FactsResponse>() {
                    @Override
                    public void onNext(FactsResponse factList) {
                        dialog.dismiss(" ");

                        String msg = factList.getMsg();

                        if (msg.equals("success")) {

                            hofactList = factList.getFactName();
                            adapter=new HoFactsAdapter(context,hofactList);
                            recy_ho_facts.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dialog.dismiss(" ");
//                        NetworkPopup.ShowPopup(context);

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

                        //Toast.makeText(context,"Please Check Your Network..Unable to Connect Server!!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

          }
    }



