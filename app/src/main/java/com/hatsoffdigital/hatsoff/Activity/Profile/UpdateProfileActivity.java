package com.hatsoffdigital.hatsoff.Activity.Profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.EmployeeProfile;
import com.hatsoffdigital.hatsoff.Models.UpdateProfileResponse;
import com.hatsoffdigital.hatsoff.Models.ViewProfileResponse;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.ImagePickUtils;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    Context context;
    //ImageView profile_back_img,profile_close_img;
    RelativeLayout rel_profile_toggel;
    EditText edit_firstname, edit_lastname, edit_mobileno, edit_email, edit_bloodgroup, edit_address, edit_pan, edit_adhar;
    TextView text_emp_id, edit_bdydate, edit_joindate;

    SPManager spManager;

    String fist_name, last_name, mobile_no, email_id, blood_group, address, emp_id;

    Button btn_submit;
    int year, month, day;
    SimpleDateFormat simpleDateFormat;
    String dateformat = "";
    String birth_date, join_date = "";
    TextView text_plus;
    CustomProgressDialog dialog;

    CircleImageView circle_profile_img;
    public static final int REQUEST_CAMERA = 123;
    public static final int SELECT_FILE = 2754;

    String encodedImage, pan_card, aadhar_card;

    String[] permissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,

    };

    ArrayList<EmployeeProfile> empProfile;


    String loginStatus="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_profile);

        context = UpdateProfileActivity.this;
        getSupportActionBar().hide();


        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH); // Note: zero based!
        day = now.get(Calendar.DAY_OF_MONTH);

        dialog = new CustomProgressDialog(context);
        rel_profile_toggel = (RelativeLayout) findViewById(R.id.rel_profile_toggel);
        rel_profile_toggel.setOnClickListener(this);

        spManager = new SPManager(context);
        emp_id = spManager.getEmployee_id();
        text_plus = (TextView) findViewById(R.id.text_plus);

        text_emp_id = (TextView) findViewById(R.id.text_emp_id);
        text_emp_id.setText(emp_id);
        empProfile = new ArrayList<>();

        edit_firstname = (EditText) findViewById(R.id.edit_firstname);
        edit_lastname = (EditText) findViewById(R.id.edit_lastname);
        edit_mobileno = (EditText) findViewById(R.id.edit_mobileno);
        edit_bdydate = (TextView) findViewById(R.id.edit_bdydate);
        edit_bdydate.setOnClickListener(this);
        edit_joindate = (TextView) findViewById(R.id.edit_joindate);
        edit_joindate.setOnClickListener(this);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_bloodgroup = (EditText) findViewById(R.id.edit_bloodgroup);
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_pan = (EditText) findViewById(R.id.edit_pan);
        edit_adhar = (EditText) findViewById(R.id.edit_adhar);

        circle_profile_img = (CircleImageView) findViewById(R.id.circle_profile_img);
        circle_profile_img.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        Intent intent = getIntent();
        loginStatus= getIntent().getStringExtra("LoginScreen");

        if(loginStatus ==null)
        {
            edit_adhar.setEnabled(false);
            edit_pan.setEnabled(false);
            edit_address.setEnabled(false);
            edit_email.setEnabled(false);
            edit_mobileno.setEnabled(false);
        }

        checkPermissions();
        getAllProfileResponse();

    }

    private void getAllProfileResponse() {

        dialog.show("");
        WebServiceModel.getRestApi().ViewProfile(emp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ViewProfileResponse>() {
                    @Override
                    public void onNext(ViewProfileResponse getallprofile) {
                        dialog.dismiss(" ");

                        String msg = getallprofile.getMsg();
                        if (msg.equals("success")) {

                            empProfile = getallprofile.getEmployeeProfile();
                            edit_firstname.setText(empProfile.get(0).getFirstname());
                            edit_lastname.setText(empProfile.get(0).getSurname());
                            edit_mobileno.setText(empProfile.get(0).getCellphone_no());
                            edit_email.setText(empProfile.get(0).getEmail_address());
                            edit_bloodgroup.setText(empProfile.get(0).getBloodtype());
                            edit_address.setText(empProfile.get(0).getPermanent_address());
                            edit_bdydate.setText(empProfile.get(0).getDate_of_birth());
                            edit_joindate.setText(empProfile.get(0).getJoining_date());
                            edit_pan.setText(empProfile.get(0).getPan_card());
                            edit_adhar.setText(empProfile.get(0).getAadhar_card());

                            String dob = empProfile.get(0).getDate_of_birth();
                            String joing_date1 = empProfile.get(0).getJoining_date();
                            join_date = joing_date1;
                            birth_date = dob;

                            String image = empProfile.get(0).getImage();
                            String str = "https://hatsoffdigital.in/app/upload/";

                            if (!str.equals(image)) {
                                Glide.with(context)
                                        .load(image)
                                        .placeholder(R.drawable.user_info)
                                        .dontAnimate()
                                        .fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .skipMemoryCache(true)
                                        .into(circle_profile_img);
                            }

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //showPopup();
//                        NetworkPopup.ShowPopup(context);
//                        dialog.dismiss(" ");

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


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == rel_profile_toggel.getId()) {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == edit_bdydate.getId()) {
            ShowDatePopup();
            dateformat = "1";

        } else if (id == edit_joindate.getId()) {
            ShowDatePopup();
            dateformat = "2";
        } else if (id == circle_profile_img.getId()) {
            ImagePickUtils.selectImage(context);
        } else if (id == btn_submit.getId()) {

            if (edit_firstname.getText().toString().trim().equalsIgnoreCase("")) {

                edit_firstname.requestFocus();
                edit_firstname.setError("First Name can't be blank");
            } else if (edit_lastname.getText().toString().trim().equalsIgnoreCase("")) {

                edit_lastname.requestFocus();
                edit_lastname.setError("Last Name can't be blank");
            }

            else if (edit_mobileno.getText().toString().trim().equalsIgnoreCase("")) {

                edit_mobileno.requestFocus();
                edit_mobileno.setError("Mobile No can't be blank");
            }

            else if (join_date.equals("")) {
                Toast.makeText(context, "Joining date can't be blank", Toast.LENGTH_SHORT).show();
            }

            else if (edit_email.getText().toString().trim().equalsIgnoreCase("")) {

                edit_email.requestFocus();
                edit_email.setError("Email Id can't be blank");
            }

            else if (edit_address.getText().toString().trim().equalsIgnoreCase("")) {

                edit_address.requestFocus();
                edit_address.setError("Address can't be blank");
            }

            else if (edit_pan.getText().toString().trim().equalsIgnoreCase("")) {
                edit_pan.requestFocus();
                edit_pan.setError("Pan Card No can't be blank");
            } else if (edit_adhar.getText().toString().trim().equalsIgnoreCase("")) {
                edit_adhar.requestFocus();
                edit_adhar.setError("Aadhar Card No can't be blank");
            } else if (edit_adhar.getText().toString().length() != 12) {
                edit_adhar.requestFocus();
                edit_adhar.setError("Please Check Addhar Card No");
            } else {
                getAllProfiledata();
            }


        }


    }

    private void getAllProfiledata() {

        fist_name = edit_firstname.getText().toString().trim();
        last_name = edit_lastname.getText().toString().trim();
        mobile_no = edit_mobileno.getText().toString().trim();
        email_id = edit_email.getText().toString().trim();
        blood_group = edit_bloodgroup.getText().toString().trim();
        address = edit_address.getText().toString().trim();
        pan_card = edit_pan.getText().toString().trim();
        aadhar_card = edit_adhar.getText().toString().trim();


//        UpdateProfileAuthModel model=new UpdateProfileAuthModel();
//        model.setFirstname(fist_name);
//        model.setSurname(last_name);
//        model.setCellphone_no(mobile_no);
//        model.setEmail_address(email_id);
//        model.setBloodtype(blood_group);
//        model.setPermanent_address(address);
//        model.setFile(encodedImage);
//        model.setEmployee_id(emp_id);
//        model.setJoining_date(join_date);
//        model.setDate_of_birth(birth_date);

        dialog.show(" ");
        WebServiceModel.getRestApi().UpdateProfile(emp_id, birth_date, fist_name, last_name, blood_group, address, email_id, mobile_no, join_date, encodedImage, pan_card, aadhar_card)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpdateProfileResponse>() {
                    @Override
                    public void onNext(UpdateProfileResponse getProfile) {
                        dialog.dismiss(" ");

                        String msg = getProfile.getMsg();
                        if (msg.equals("success")) {


                            if(loginStatus !=null)
                            {
                                spManager.setName(getProfile.getFirstname());
                                Toast.makeText(context, "done Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, HomeScreenActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            }
                            else {

                                spManager.setName(getProfile.getFirstname());
                                Toast.makeText(context, "done Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        //showPopup();
                        // NetworkPopup.ShowPopup(context);
                        //dialog.dismiss(" ");
                        //System.out.println("saggi"+e.toString());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_FILE) {
            if (data != null) {

                Uri uri = data.getData();

                String path = String.valueOf(uri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    File choosedFile = ImagePickUtils.getPickedFile(context, data.getData());

                    Bitmap compressedImageBitmap = new Compressor(this).compressToBitmap(choosedFile);

                    //File compressedImageFile = new Compressor(this).compressToFile(choosedFile);
                    text_plus.setText("");
                    circle_profile_img.setImageBitmap(null);
                    circle_profile_img.setImageBitmap(compressedImageBitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    encodedImage = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
                    System.out.println(encodedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else if (requestCode == REQUEST_CAMERA) {
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap thePic = (Bitmap) extras.get("data");
                text_plus.setText("");
                circle_profile_img.setImageBitmap(null);
                circle_profile_img.setImageBitmap(thePic);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thePic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                encodedImage = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
                // System.out.println("encoded"+encodedImage);

            }


        }
    }

    private void ShowDatePopup() {

        new SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback(UpdateProfileActivity.this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(false)
                .defaultDate(year, month, day)
                .maxDate(2050, 0, 1)
                .minDate(1970, 0, 1)
                .build()
                .show();

    }


    @Override
    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {

        String month = String.valueOf(monthOfYear + 1);
        String date = dayOfMonth + "-" + month + "-" + year1;


        if (dateformat.equals("1")) {
            birth_date = date;
            edit_bdydate.setText(date);

        }

        if (dateformat.equals("2")) {
            join_date = date;
            edit_joindate.setText(date);
        }


    }
}
