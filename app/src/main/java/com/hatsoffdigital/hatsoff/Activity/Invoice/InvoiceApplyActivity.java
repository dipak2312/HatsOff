package com.hatsoffdigital.hatsoff.Activity.Invoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hatsoffdigital.hatsoff.Activity.Toggle.ToggleScreenActivity;
import com.hatsoffdigital.hatsoff.Activity.home.HomeScreenActivity;
import com.hatsoffdigital.hatsoff.Adapters.SelectCategoryAdapter;
import com.hatsoffdigital.hatsoff.Adapters.SelectClientListAdapter;
import com.hatsoffdigital.hatsoff.Adapters.SelectConcernedPersonListAdapter;
import com.hatsoffdigital.hatsoff.Helper.SPManager;
import com.hatsoffdigital.hatsoff.Models.InvoiceSpinnerListResponse;
import com.hatsoffdigital.hatsoff.Models.RequestInvoiceResponse;
import com.hatsoffdigital.hatsoff.Models.categoryName;
import com.hatsoffdigital.hatsoff.Models.concernPerson;
import com.hatsoffdigital.hatsoff.Models.projectName;
import com.hatsoffdigital.hatsoff.R;
import com.hatsoffdigital.hatsoff.Retrofit.WebServiceModel;
import com.hatsoffdigital.hatsoff.Utils.CheckInternetBroadcast;
import com.hatsoffdigital.hatsoff.Utils.CustomProgressDialog;
import com.hatsoffdigital.hatsoff.Utils.ItemClientClickListener;
import com.hatsoffdigital.hatsoff.Utils.ItemConcernedPerson;
import com.hatsoffdigital.hatsoff.Utils.NetworkPopup;
import com.hatsoffdigital.hatsoff.Utils.ServerPopup;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InvoiceApplyActivity extends AppCompatActivity implements View.OnClickListener, ItemClientClickListener,ItemConcernedPerson {

    RelativeLayout rel_invoice_toggle;
    ImageView img_apply_invoice_home;
    TextView text_total_requested,text_pending_req,text_approved_req;
    EditText edit_costing,edit_subject;
    Button btn_add;
    Context context;
    Spinner spinner_currency,spinner_project_name;
    SPManager spManager;
    CustomProgressDialog dialog;
    ArrayList<projectName>projectNameList;
    ArrayList<categoryName>categoryNameList;
    ArrayList<concernPerson>concernPersonList;
    RadioGroup radio_btn_gst_wgst;
    RadioButton radio_btn_gst;
    String gst_or_withoutgst;
    int selectedId;
    SelectClientListAdapter clientAdapter;
    SelectConcernedPersonListAdapter concernedPerson;
    RelativeLayout rel_enter_cat_name,rel_enter_bdm;
    ArrayList<String> selectedclient;
    ArrayList<String> seletConcernedPerson;
    TextView select_cat_name,select_concerned_name;
    String selectedcategory;
    String selectedConPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_apply);
        getSupportActionBar().hide();

        context=InvoiceApplyActivity.this;
        spManager=new SPManager(context);
        dialog=new CustomProgressDialog(context);

        projectNameList=new ArrayList<>();
        categoryNameList=new ArrayList<>();
        concernPersonList=new ArrayList<>();
        selectedclient=new ArrayList<>();
        seletConcernedPerson=new ArrayList<>();


        rel_invoice_toggle=(RelativeLayout)findViewById(R.id.rel_invoice_toggle);
        rel_invoice_toggle.setOnClickListener(this);
        img_apply_invoice_home=(ImageView)findViewById(R.id.img_apply_invoice_home);
        img_apply_invoice_home.setOnClickListener(this);
        text_total_requested=(TextView)findViewById(R.id.text_total_requested);
        text_pending_req=(TextView)findViewById(R.id.text_pending_req);
        edit_costing=(EditText)findViewById(R.id.edit_costing);
        edit_subject=(EditText)findViewById(R.id.edit_subject);
        select_cat_name=(TextView)findViewById(R.id.select_cat_name);

        btn_add=(Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        spinner_currency=(Spinner)findViewById(R.id.spinner_currency);
        spinner_project_name=(Spinner)findViewById(R.id.spinner_project_name);

       // spinner_user_name=(Spinner)findViewById(R.id.spinner_user_name);


        text_approved_req=(TextView)findViewById(R.id.text_approved_req);
        text_approved_req.setText(spManager.getInvoice_approved());

        rel_enter_cat_name=(RelativeLayout)findViewById(R.id.rel_enter_cat_name);
        rel_enter_cat_name.setOnClickListener(this);

        rel_enter_bdm=(RelativeLayout)findViewById(R.id.rel_enter_bdm);
        rel_enter_bdm.setOnClickListener(this);


        text_total_requested.setText(spManager.getInvoice_total());
        text_pending_req.setText(spManager.getInvoice_pending());
        select_concerned_name=(TextView)findViewById(R.id.select_concerned_name);



        radio_btn_gst_wgst=(RadioGroup)findViewById(R.id.radio_btn_gst_wgst);

        String[] arraySpinner = new String[] {"\u20B9 INR","\u20AA Israel","\u0024 USA","\u0024 SGD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinnerTarget, arraySpinner);
        spinner_currency.setAdapter(adapter);


        InvoiceList();

    }

    @Override
    public void onClick(View view) {

        int id=view.getId();

        if(id==rel_invoice_toggle.getId())
        {
            Intent intent = new Intent(context, ToggleScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else if(id==img_apply_invoice_home.getId())

        {
            Intent intent = new Intent(context, HomeScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if(id==rel_enter_cat_name.getId())
        {

            openClientList();

        }
        else if(id==rel_enter_bdm.getId())
        {
            openConcernedList();
        }
        else if(id==btn_add.getId())
        {

            selectedId = radio_btn_gst_wgst.getCheckedRadioButtonId();
            String project_name,category_name,bdm;
            project_name=spinner_project_name.getSelectedItem().toString();
            //category_name=spinner_category_name.getSelectedItem().toString();
           // bdm=spinner_user_name.getSelectedItem().toString();


            if(project_name.equals("Select here"))
            {
                Toast.makeText(context,"Select Project Name",Toast.LENGTH_SHORT).show();
            }

            else if(selectedcategory ==null)
            {
                Toast.makeText(context,"Select Category Name",Toast.LENGTH_SHORT).show();
            }

            else if (edit_subject.getText().toString().trim().equalsIgnoreCase(""))
            {

                edit_subject.requestFocus();
                edit_subject.setError("Subject can't be blank");
            }

            else if(selectedId ==-1) {

                Toast.makeText(context, "Please Select GST OR Without GST", Toast.LENGTH_SHORT).show();
            }
            else
            {
                AddInvoice();
            }

        }

    }

    private void openConcernedList() {

        final Dialog dialog1 = new Dialog(context);
        dialog1.setContentView(R.layout.all_concerned_person_list);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        RecyclerView recy_all_name1 = (RecyclerView) dialog1.findViewById(R.id.recy_all_name);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_all_name1.setLayoutManager(lm);

        if (concernPersonList != null) {

            seletConcernedPerson.clear();
            for (int i = 0; i < concernPersonList.size(); i++) {
                concernPersonList.get(i).setSelected(false);
            }

            concernedPerson = new SelectConcernedPersonListAdapter(context, concernPersonList, (ItemConcernedPerson) this);
            recy_all_name1.setAdapter(concernedPerson);

        }


        RelativeLayout rel_ok = (RelativeLayout) dialog1.findViewById(R.id.rel_ok);
        rel_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1.dismiss();


                if (seletConcernedPerson.size() != 0) {

                    selectedConPerson= TextUtils.join(",", seletConcernedPerson);
                    select_concerned_name.setText(selectedConPerson);
                }




            }
        });

        dialog1.show();
    }

    private void openClientList() {

        final Dialog dialog1 = new Dialog(context);
        dialog1.setContentView(R.layout.all_user_category_popup);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        RecyclerView recy_all_name = (RecyclerView) dialog1.findViewById(R.id.recy_all_name);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recy_all_name.setLayoutManager(lm);

        if (categoryNameList != null) {

           selectedclient.clear();
            for (int i = 0; i < categoryNameList.size(); i++) {
                categoryNameList.get(i).setSelected(false);
            }

            clientAdapter = new SelectClientListAdapter(context, categoryNameList, (ItemClientClickListener) this);
            recy_all_name.setAdapter(clientAdapter);

        }



        RelativeLayout rel_ok = (RelativeLayout) dialog1.findViewById(R.id.rel_ok);
        rel_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1.dismiss();


                if (selectedclient.size() != 0) {

                    selectedcategory= TextUtils.join(",", selectedclient);
                    select_cat_name.setText(selectedcategory);
                }




            }
        });

        dialog1.show();

    }


    private void  InvoiceList()
    {
        dialog.show(" ");
        WebServiceModel.getRestApi().getInvoiceSpinnerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<InvoiceSpinnerListResponse>() {
                    @Override
                    public void onNext(InvoiceSpinnerListResponse getinvoiceList) {
                        dialog.dismiss(" ");

                        String msg = getinvoiceList.getMsg();

                        if (msg.equals("success")) {

                            ArrayList<String> projectname=new ArrayList<>();
                            projectname.add("Select here");

                            projectNameList=getinvoiceList.getProjectName();
                            for(int i=0;i<projectNameList.size();i++)
                            {
                                projectname.add(projectNameList.get(i).getClient_company_name());
                            }
                            ArrayAdapter<String> projectAdapter = new ArrayAdapter<String>(context,R.layout.spinner_layout,R.id.spinnerTarget,projectname);
                            spinner_project_name.setAdapter(projectAdapter);




                            categoryNameList=getinvoiceList.getCategoryName();

                           // ArrayList<String>username=new ArrayList<>();
                            concernPersonList=getinvoiceList.getConcernPerson();
//                            username.add("Select here");
//                            for(int k=0;k<concernPersonList.size();k++)
//                            {
//                                username.add(concernPersonList.get(k).getUser_name())  ;
//                            }
//                            ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(context,R.layout.spinner_layout,R.id.spinnerTarget,username);
//                            spinner_user_name.setAdapter(userAdapter);


                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        //showPopup();
//                         NetworkPopup.ShowPopup(context);
//                         dialog.dismiss(" ");

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


    private void AddInvoice() {
      String project_name,category_name,selected_currency,costing,subject,bdm,employee_id,employee_name;

      radio_btn_gst = (RadioButton) findViewById(selectedId);

      if(radio_btn_gst.getText().toString().equals("+ GST"))
      {
          gst_or_withoutgst="gst";
      }
      else if(radio_btn_gst.getText().toString().equals("Without Gst"))
      {
          gst_or_withoutgst="without gst";
      }

      costing=edit_costing.getText().toString().trim();
      if(costing.equals(""))
      {
          costing="N/A";
      }
      subject=edit_subject.getText().toString().trim();

      employee_id=spManager.getEmployee_id();
      employee_name=spManager.getUser_full_name();
      selected_currency=spinner_currency.getSelectedItem().toString();
      project_name=spinner_project_name.getSelectedItem().toString();

      //bdm=spinner_user_name.getSelectedItem().toString();

      dialog.show(" ");
        WebServiceModel.getRestApi().AddInvoice(employee_id,employee_name,project_name, selectedcategory,costing,selected_currency,subject,selectedConPerson,gst_or_withoutgst)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RequestInvoiceResponse>() {
                    @Override
                    public void onNext(RequestInvoiceResponse getinvoice) {
                        dialog.dismiss(" ");

                        String msg = getinvoice.getMsg();

                        if (msg.equals("success")) {

                            Toast.makeText(context, "Successfully done", Toast.LENGTH_SHORT).show();
                            finish();
                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        //showPopup();
//                         NetworkPopup.ShowPopup(context);
//                         dialog.dismiss(" ");

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
    public void clientList(String client_name, String sattusclient) {


        if (sattusclient.equals("check")) {
            selectedclient.add(client_name);
        } else {
            for (int i = 0; i < selectedclient.size(); i++) {

                if (selectedclient.get(i).equals(client_name)) {

                    selectedclient.remove(i);
                }
            }
        }



   }

    @Override
    public void ConcernedList(String con_name, String Status) {


        if (Status.equals("check")) {
            seletConcernedPerson.add(con_name);
        } else {
            for (int i = 0; i < seletConcernedPerson.size(); i++) {

                if (seletConcernedPerson.get(i).equals(con_name)) {

                    seletConcernedPerson.remove(i);
                }
            }
        }

    }
}
