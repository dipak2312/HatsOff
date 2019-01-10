package com.hatsoffdigital.hatsoff.Activity.Landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.CheckEmployeeId;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button btn_login;
    EditText edit_emp_otp, edit_emp_id;
    String employee_id;
    String Otp;
    CustomProgressDialog dialog;
    SPManager spManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        context = LoginActivity.this;
        spManager = new SPManager(context);

        dialog = new CustomProgressDialog(context);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        edit_emp_id = (EditText) findViewById(R.id.edit_emp_id);
        edit_emp_otp = (EditText) findViewById(R.id.edit_emp_otp);

        employee_id = getIntent().getStringExtra("employeeid");
        Otp = getIntent().getStringExtra("Otp");
        spManager.setOtp(Otp);
        edit_emp_id.setText(employee_id);

    }


    @Override
    public void onClick(View view) {


        String user_otp = edit_emp_otp.getText().toString().trim();
        String Otp = spManager.getOtp();


        if (user_otp.equalsIgnoreCase("")) {

            edit_emp_otp.requestFocus();
            edit_emp_otp.setError("OTP can't be blank");
        } else if (!user_otp.equals(Otp)) {
            edit_emp_otp.requestFocus();
            edit_emp_otp.setError("Invalid Otp");
        } else {

            GetLoginRequest();
        }


    }

    private void GetLoginRequest() {

        dialog.show(" ");

        String Otp = spManager.getOtp();

        WebServiceModel.getRestApi().CheckEmployeeid(employee_id, Otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckEmployeeId>() {
                    @Override
                    public void onNext(CheckEmployeeId checkemployeeid) {
                        dialog.dismiss(" ");

                        String msg = checkemployeeid.getMsg();
                        String user_name = checkemployeeid.getEmployee_name();
                        String emp_id = checkemployeeid.getEmployee_id();

                        if (msg.equals("success")) {

                            spManager.setName(user_name);
                            spManager.setEmployee_id(emp_id);
                            spManager.setLoggedIn("Login");
                            Intent intent = new Intent(context, HomeScreenActivity.class);
                            startActivity(intent);
                            finish();
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
}
