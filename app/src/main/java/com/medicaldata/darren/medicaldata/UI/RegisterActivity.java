package com.medicaldata.darren.medicaldata.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.medicaldata.darren.medicaldata.Common.AsyncHttpClientUtils;
import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Model.LoginBean;
import com.medicaldata.darren.medicaldata.R;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    final int DATE_DIALOG = 1;
    private int mYear, mMonth, mDay;

    private AutoCompleteTextView tv_username;
    private EditText tv_password;
    private EditText et_basedata_hight;
    private EditText et_basedata_dateDisplay;
    private RadioButton rb_basedata_radioMale;
    private RadioButton rb_basedata_radioFemale;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Res.activityLists.add(RegisterActivity.this);

        setContentView(R.layout.activity_register);

        Button cancel_button = (Button) findViewById(R.id.register_cancel_button);
        Button submit_button = (Button) findViewById(R.id.register_submit_button);

        Button btn = (Button) findViewById(R.id.bt_basedata_submit);

        et_basedata_dateDisplay = (EditText) findViewById(R.id.et_basedata_dateDisplay);
        et_basedata_hight  = (EditText) findViewById(R.id.et_basedata_hight);
        rb_basedata_radioMale  = (RadioButton) findViewById(R.id.rb_basedata_radioMale);
        rb_basedata_radioFemale  = (RadioButton) findViewById(R.id.rb_basedata_radioFemale);
        rb_basedata_radioMale.setChecked(true);

        tv_username = (AutoCompleteTextView) findViewById(R.id.email);
        tv_password = (EditText) findViewById(R.id.password);

        gson = new Gson();

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = tv_username.getText().toString();
                String password = tv_password.getText().toString();
                String date = et_basedata_dateDisplay.getText().toString();
                String hight = et_basedata_hight.getText().toString();

                String gender = rb_basedata_radioMale.isChecked()?"M":"";

                if(TextUtils.isEmpty(gender)){
                    gender = rb_basedata_radioFemale.isChecked()?"F":"";
                }

                if(!Validate(username, password,date,hight,gender)){
                    return;
                };

                RequestParams requestParams = new RequestParams();
                requestParams.put("Username",username);
                requestParams.put("Password", password);
                requestParams.put("Gender", gender);
                requestParams.put("Height", hight);
                requestParams.put("Birthdate", date);
                // Simulate network access.
                AsyncHttpClientUtils.getInstance().post("Account/Register",requestParams, new TextHttpResponseHandler(){

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        LoginBean loginbean = gson.fromJson(s, LoginBean.class);
                        Res.loginBeanModel = loginbean;
                        Res.my_list_title.set(0,loginbean.getUsername());
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        Log.i("MainActivity","|"+s+"|");
                        Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });


        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    private boolean Validate(String username, String password,String date,String hight,String gender) {
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(username)) {
            tv_username.setError("用户名不能为空");
            return false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(password)) {
            tv_password.setError("密码不能为空");
            return false;
        }

        if (TextUtils.isEmpty(gender)) {
            rb_basedata_radioMale.setError("");
            return false;
        }

        if (TextUtils.isEmpty(hight)) {
            et_basedata_hight.setError("身高不能为空");
            return false;
        }


        if (TextUtils.isEmpty(date)) {
            et_basedata_dateDisplay.setError(getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        et_basedata_dateDisplay.setText(new StringBuffer().append(mYear).append("-").append(mMonth+ 1).append("-").append(mDay).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
}
