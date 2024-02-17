package com.hatsoffdigital.hatsoff.Activity.TermsConditions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.RelativeLayout;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.TermsAndConditionAdapter;
import com.hatsoffdigital.hatsoff.Models.TermsConditionResponse;
import com.hatsoffdigital.hatsoff.Models.Termsandcondition;
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


public class TermsAndConditionsActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView recy_term_and_condition;
    Context context;
    TermsAndConditionAdapter adapter;
    //ImageView termscon_toggle_img,termscon_close_img;
    RelativeLayout rel_termscon_toggel;
    CustomProgressDialog dialog;
    ArrayList<Termsandcondition> getTermsconditionList;
    String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        context = TermsAndConditionsActivity.this;
        getSupportActionBar().hide();

        dialog = new CustomProgressDialog(context);
        getTermsconditionList = new ArrayList<>();

        recy_term_and_condition = (RecyclerView) findViewById(R.id.recy_term_and_condition);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recy_term_and_condition.setNestedScrollingEnabled(false);
        recy_term_and_condition.setLayoutManager(manager);

        rel_termscon_toggel = (RelativeLayout) findViewById(R.id.rel_termscon_toggel);
        rel_termscon_toggel.setOnClickListener(this);

        status = getIntent().getStringExtra("Meeting");
        if (status == null) {
            status = getIntent().getStringExtra("Attendance");

            if (status == null) {
                status = getIntent().getStringExtra("Holiday");

                if (status == null) {
                    status = getIntent().getStringExtra("Leaves");

                    if (status == null) {
                        status = getIntent().getStringExtra("WorkFromHome");
                    }
                }
            }
        }


        getTermsAndConditiontList();
    }


    private void getTermsAndConditiontList() {

        dialog.show(" ");

        WebServiceModel.getRestApi().getTermsConditon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<TermsConditionResponse>() {
                    @Override
                    public void onNext(TermsConditionResponse getTermsAndCondition) {
                        dialog.dismiss(" ");

                        String msg = getTermsAndCondition.getMsg();

                        if (msg.equals("success")) {

                            getTermsconditionList = getTermsAndCondition.getTermsandcondition();
                            adapter = new TermsAndConditionAdapter(context, getTermsconditionList, status);
                            recy_term_and_condition.setAdapter(adapter);


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

        if (id == rel_termscon_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

    }
}
