package com.medicaldata.darren.medicaldata.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.medicaldata.darren.medicaldata.Common.AsyncHttpClientUtils;
import com.medicaldata.darren.medicaldata.Common.JsonResult;
import com.medicaldata.darren.medicaldata.Common.QuestionnaireUtiliy;
import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Common.mUtil;
import com.medicaldata.darren.medicaldata.Model.LoginBean;
import com.medicaldata.darren.medicaldata.Model.RequestDataBean;
import com.medicaldata.darren.medicaldata.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class QuestionnaireActivity extends AppCompatActivity {

    private Button BtnLeft, btn_right;
    private List<LinkedHashMap<String, Object>> list;
    private int pos = 0;
    private RadioButton singleRbtn[] = null;
    private RadioButton rbtn[] = null;
    private List<RadioButton[]> singleRbtnList = new ArrayList<RadioButton[]>();
    private List<TextView> textViewList = new ArrayList<TextView>();
    private List<EditText> editTextList = new ArrayList<EditText>();
    private List<CheckBox[]> checkBoList = new ArrayList<CheckBox[]>();
    private TextView tv_sum;
    private ScrollView scroll;
    private LinearLayout ll;
    private boolean bll = true;
    private boolean boo = true;
    private ArrayList<Map<String, String>> zhi = new ArrayList<Map<String, String>>();
    private TextView tv_sleep;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_questionnaire);

//        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
//        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.8
//        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
//        getWindow().setAttributes(p);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        setTitle(title);

        //实例化
        findView();
        //去加载数据
        loadData();

        gson = new Gson();
        //监听
//        addListener();
    }


    private void addListener() {
        // TODO Auto-generated method stub
        BtnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }


    private void findView() {
        // TODO Auto-generated method stub
        scroll = (ScrollView) findViewById(R.id.scroll);
        ll = (LinearLayout) findViewById(R.id.ll);

    }


    private void loadData() {
        // TODO Auto-generated method stub
        //用JSON去解析得到的数据
        JsonResult result = new JsonResult();
        try {
            Intent intent = getIntent();
            String questionJson = intent.getStringExtra("QuestionJson");

            if (questionJson != null) {

                JSONObject jsonObject = new JSONObject(questionJson);

                if ("success".equals(jsonObject.getString("flag"))) {

                    JSONArray array = jsonObject.getJSONArray("vote");
                    list = new ArrayList<LinkedHashMap<String, Object>>();
                    int len = array.length();


                    for (int i = 0; i < len; i++) {
                        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                        map.put("Unit", array.getJSONObject(i)
                                .getString("Unit"));
                        map.put("Title",
                                array.getJSONObject(i).getString("Title"));
                        map.put("id", array.getJSONObject(i)
                                .getString("id"));
                        map.put("SelCount", array.getJSONObject(i)
                                .getString("SelCount"));
                        map.put("ctype",
                                array.getJSONObject(i).getString("ctype"));
                        String sel = array.getJSONObject(i).getString(
                                "SelCount");
                        int l = Integer.valueOf(sel);
                        for (int j = 0; j < l; j++) {
                            map.put("sel" + j,
                                    array.getJSONObject(i).getString(
                                            "Sel" + String.valueOf(j + 1)));
                        }
                        //用list封装进去自己解析好的数据
                        list.add(map);
                        Log.i("values", "len" + list);
                    }

                    //在主布局添加控件

                    addViews();


                } else {
                    Toast.makeText(QuestionnaireActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();

                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void addViews() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");

        LinearLayout mailayout = QuestionnaireUtiliy.MainLayout(QuestionnaireActivity.this, title);

        for (int i = 1; i <= list.size(); i++) {


            LinkedHashMap<String, Object> map = list.get(i - 1);

            String cty = (String) map.get("ctype");
            Log.i("values", "ctype" + cty);

            if (cty.equals("1")) {
                RadioButton[] singleRbtn = null;
                singleRbtn = QuestionnaireUtiliy.SingleAnswer(QuestionnaireActivity.this, mailayout, singleRbtn, map);
                singleRbtnList.add(singleRbtn);
            } else if (cty.equals("2")) {
                CheckBox[] checkBo = null;
                checkBo = QuestionnaireUtiliy.MultipleAnswer(QuestionnaireActivity.this, mailayout, checkBo, map);
                checkBoList.add(checkBo);
            } else if (cty.equals("3")) {
                EditText editText = new EditText(QuestionnaireActivity.this);
                editTextList.add(editText);
                QuestionnaireUtiliy.TextAnswer1(QuestionnaireActivity.this, mailayout, editText, map);
            } else if (cty.equals("4")) {
                QuestionnaireUtiliy.SingleTextAnswer(QuestionnaireActivity.this, mailayout, rbtn, map);
            } else if (cty.equals("5")) {
                CheckBox[] checkBo = null;
                QuestionnaireUtiliy.MultipleTextAnswer(QuestionnaireActivity.this, mailayout, checkBo, map);
                checkBoList.add(checkBo);
            } else if (cty.equals("6")) {
                QuestionnaireUtiliy.TextDialogAnswer(QuestionnaireActivity.this, mailayout, map);
            } else if (cty.equals("7")) {
                TextView textView = new TextView(QuestionnaireActivity.this);
                textViewList.add(textView);
                QuestionnaireUtiliy.TimeDialogAnswer(QuestionnaireActivity.this, mailayout, textView, map);
          }


        }


        if (title.equals(Res.question_list_title.get(1))){

            tv_sleep= QuestionnaireUtiliy.TimeDialogAnswerText(QuestionnaireActivity.this, mailayout);
        }

        Button bt = QuestionnaireUtiliy.Submit(QuestionnaireActivity.this, mailayout);

        //region 1
        if (title.equals(Res.question_list_title.get(0))) {

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String report1 = "";
                    String report2 = "";

                    String report1_yes1 = "请在运动前咨询内科医生或专业健康管理者，你恐怕需要在有认证医务人员监护的场地开展健身。";
                    String report2_yes2 = "建议咨询有资质的医生或健康管理人士，最好在有认证资质的职业教练指导下，循序渐进地推展你的运动计划。";

                    String report_no = "你可开始在安全场所进行自我指导的运动。";

                    String report2_yes1 = "你在心血管危险因素的反馈中有一个风险因素，介于可以或不用咨询医生或专业人士之间，建议谨慎行事。";
                    int count_report1 = 0;
                    int count_report2 = 0;

                    try {
                        for (int i = 0; i < checkBoList.size(); i++) {

                            CheckBox[] cb = checkBoList.get(i);

                            for (int j = 0; j < cb.length; j++) {

                                CheckBox item = cb[j];
                                if (item.isChecked()) {

                                    if (i == checkBoList.size() - 1) {
                                        count_report2 += 1;
                                    } else {

                                        count_report1 += 1;
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {

                        Log.i("Exception", e.getMessage());
                    }

                    if (count_report1 >= 1) {

                        report1 = report1_yes1;
                    }

                    if (count_report2 >= 2) {

                        report2 = report2_yes2;
                    }

                    if (count_report1 == 0 && count_report2 == 0) {

                        report1 = report_no;
                    }

                    if (count_report2 == 1) {

                        report2 = report2_yes1;
                    }

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","Risk1Report");
                    requestParams.put("Value1", report1);
                    requestParams.put("Value2", report2);
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/UpdateUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                            Res.loginBeanModel=loginBean;

                            Intent sportIntent = new Intent(QuestionnaireActivity.this, QuestionReportActivity.class);
                            Bundle sportBundle = new Bundle();
                            sportBundle.putString("ActivityName", "RiskReport");
                            sportIntent.putExtras(sportBundle);
                            startActivity(sportIntent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Toast.makeText(QuestionnaireActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });
        }
        //endregion
        //region    2
        else if (title.equals(Res.question_list_title.get(1))) {


            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String result = "";

                    String str1 =textViewList.get(0).getText().toString();
                    String str2 =textViewList.get(1).getText().toString();

                    if(str1.equals("无")||str2.equals("无")){
                        Toast.makeText(QuestionnaireActivity.this,"请选择时间", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String[] sleepArry1 = str1.split("：");
                    String[] sleepArry2 = str2.split("：");


                    String deepSleepTime = editTextList.get(0).getText().toString();
                    String lightSleepTime = editTextList.get(1).getText().toString();
                    String awakeTime = editTextList.get(2).getText().toString();

                    int sleephour1 = Integer.valueOf(sleepArry1[0]);
                    int sleepMin1 = Integer.valueOf(sleepArry1[1]);
                    int sleephour2 = Integer.valueOf(sleepArry2[0]);
                    int sleepMin2 = Integer.valueOf(sleepArry2[1]);

                    int Time1 = sleephour1 * 60 + sleepMin1;
                    int Time2 = sleephour2 * 60 + sleepMin2;

                    if (Time2 < Time1) {
                        Time2 = (sleephour2 + 24) * 60 + sleepMin2;
//                        tv_sleep.setText("入睡时间不能小于清醒时间");
                    }

                    double f = Math.round((Time2 - Time1) * 100 / 60) * 0.01d;
                    BigDecimal b = new BigDecimal(f);
                    double sleepValue = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                    final String sleepReport = "您的睡眠时长为" + sleepValue + "个小时。";


                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

                    Map<String, String> requestParams = new HashMap<String, String>();
                    requestParams.put("FieldName", "Sleep");
                    requestParams.put("Value1", textViewList.get(0).getText().toString());
                    requestParams.put("Value2", textViewList.get(1).getText().toString());
                    requestParams.put("Value3", sleepValue + "");
                    requestParams.put("Value4", deepSleepTime + "");
                    requestParams.put("Value5", lightSleepTime + "");
                    requestParams.put("Value6", awakeTime + "");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    list.add(requestParams);


                    RequestParams requestParamsList = new RequestParams();
                    requestParamsList.put("RequestList", list);
                    requestParamsList.put("Id", Res.loginBeanModel.getId());


                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/CreateUserData", requestParamsList, new TextHttpResponseHandler() {

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            LoginBean loginBean = gson.fromJson(s, LoginBean.class);

                            Res.loginBeanModel = loginBean;
                            tv_sleep.setText(sleepReport);
                            Toast.makeText(QuestionnaireActivity.this,"提交成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            tv_sleep.setText(s);
                        }
                    });

                }
            });



        }
        //endregion
        //region3
        else if (title.equals(Res.question_list_title.get(2))) {

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String report = "";
                    String report_Hight = "根据您的情况您的心血管疾病风险属于高度危险，推荐您在中等或较大强度运动前进行医学检查和运动测试，并且有在运动测试中进行医务监督的必要。";
                    String report_Mid = "根据您的情况您的心血管疾病风险属于中度危险，建议您在较大强度的运动前进行医学检查，您并无在运动前进行运动测试必要，在运动测试中也没有进行医学监督的必要。";
                    String report_Low = "根据您的情况您的运动风险属于低危，您不必要在运动前进行医学检查和运动测试，也不必要在运动时进行医学监督。";



                        for (int i = 0; i < singleRbtnList.size(); i++) {

                            RadioButton[] rb = singleRbtnList.get(i);

                            for (int j = 0; j < rb.length; j++) {

                                RadioButton item = rb[j];
                                if (item.isChecked() && "是".equals(item.getText().toString())) {
                                    report = report_Hight;
                                }
                            }

                        }

                        if (report == "") {

                            int count_report = 0;

                            for (int i = 0; i < checkBoList.size(); i++) {

                                CheckBox[] cb = checkBoList.get(i);

                                for (int j = 0; j < cb.length; j++) {

                                    CheckBox item = cb[j];
                                    if (item.isChecked()) {
                                        count_report += 1;
                                    }
                                }

                            }

                            if (count_report >= 2) {

                                report = report_Mid;
                            } else {
                                report = report_Low;
                            }

                        }

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","CardiovascularReport");
                    requestParams.put("Value1", report);
                    requestParams.put("Value2", "");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/UpdateUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {
                            LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                            Res.loginBeanModel=loginBean;
                            Intent sportIntent = new Intent(QuestionnaireActivity.this, QuestionReportActivity.class);
                            Bundle sportBundle = new Bundle();
                            sportBundle.putString("ActivityName", "CardiovascularReport");
                            sportIntent.putExtras(sportBundle);
                            startActivity(sportIntent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Toast.makeText(QuestionnaireActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
        //endregion
        //region 4
        else if (title.equals(Res.question_list_title.get(3)))
        {
            final int age = Res.loginBeanModel.getAge();

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String report1 = "";
                    String report2 = "";
                    String report3 = "";
                    String report4 = "";
                    String report5 = "";
                    String report51 = "";
                    String report6 = "";
                    String report61 = "";
                    String report62 = "";
                    String report7 = "";
                    String report71 = "";

                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

                    try {

                        for (int i = 0; i < editTextList.size(); i++) {

                            String inputValue = editTextList.get(i).getText().toString();

                            if(inputValue ==null || "".equals(inputValue)) {
                                continue;
                            }

                            float value =  Float.parseFloat(inputValue);

                                switch (i){
                                    case 0:
                                        float height = Integer.parseInt(Res.loginBeanModel.getHeight())*0.01f;
                                        float bmi = Math.round((value*100) / (height * height)) * 0.01f;
                                        if(bmi<18.5){
                                            report1 = "您的BMI值为："+bmi +"，属于低体重";
                                        }else if(bmi>=18.5 && bmi <24.9){
                                            report1 = "您的BMI值为："+bmi +"，属于正常";
                                        }else if(bmi>=24.9 && bmi <29.9){
                                            report1 = "您的BMI值为："+bmi +"，属于超重";
                                        }else if(bmi>=29.9 && bmi <34.9){
                                            report1 = "您的BMI值为："+bmi +"，属于肥胖Ⅰ";
                                        }else if(bmi>=34.9 && bmi <39.9){
                                            report1 = "您的BMI值为："+bmi +"，属于肥胖Ⅱ";
                                        }else{
                                            report1 = "您的BMI值为："+bmi +"，属于肥胖Ⅲ";
                                        }

                                        Map<String, String> requestParams1 = new HashMap<String, String>();
                                        requestParams1.put("FieldName","Weight");
                                        requestParams1.put("Value1", value+"");
                                        requestParams1.put("Value2", bmi+"");
                                        requestParams1.put("Value3", report1);
                                        list.add(requestParams1);
                                        continue;
                                    case 1:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            report2 = Report2Function(report2, value,80,100,120);
                                        }else{
                                            report2 = Report2Function(report2, value,70,90,110);
                                        }

                                        Map<String, String> requestParams2 = new HashMap<String, String>();
                                        requestParams2.put("FieldName","Waist");
                                        requestParams2.put("Value1", value+"");
                                        requestParams2.put("Value2", report2);
                                        list.add(requestParams2);
                                        continue;
                                    case 2:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age <=29) {
                                                report3 = Report3ManFunction(report3, value, 11.5f, 12.6f, 13.8f, 14.8f, 15.8f, 16.6f, 17.5f, 18.6f, 19.7f, 20.7f, 22.0f, 23.3f, 24.9f, 26.6f, 29.2f, 33.4f);
                                            }else if (age>=30 && age <=39) {
                                                report3 = Report3ManFunction(report3, value, 15.9f, 16.8f, 17.7f, 18.4f, 19.2f, 20.0f, 20.7f, 21.6f, 22.4f, 23.2f, 24.1f, 25.1f, 26.4f, 27.8f, 30.2f, 34.4f);
                                            }else if (age>=40 && age <=49) {
                                                report3 = Report3ManFunction(report3, value, 18.5f, 19.3f, 20.1f, 20.8f, 21.4f, 22.4f, 22.8f, 23.5f, 24.2f, 24.9f, 25.7f, 26.6f, 27.8f, 29.2f, 31.3f, 35.2f);
                                            }else if (age>=50 && age <=59) {
                                                report3 = Report3ManFunction(report3, value, 20.2f, 21.0f, 21.7f, 22.3f, 23.0f, 23.6f, 24.2f, 24.9f, 25.6f, 26.3f, 27.1f, 28.1f, 29.2f, 30.6f, 32.7f, 36.4f);
                                            }else if (age>=60 && age <=69) {
                                                report3 = Report3ManFunction(report3, value, 21.0f, 21.7f, 22.4f, 23.0f, 23.6f, 24.2f, 24.9f, 25.6f, 26.4f, 27.0f, 27.9f, 28.8f, 29.8f, 31.2f, 33.3f, 36.8f);
                                            }else{
                                                report3 = Report3ManFunction(report3, value, 21.0f, 21.6f, 22.3f, 22.9f, 23.7f, 24.1f, 24.7f, 25.3f, 25.8f, 26.5f, 27.1f, 28.4f, 29.4f, 30.7f, 32.9f, 37.2f);
                                            }
                                        }else{
                                            if(age <=29) {
                                                report3 = Report3WomanFunction(report3, value, 11.4f, 14.0f, 15.1f, 16.1f, 16.8f, 17.6f, 18.4f, 19.0f, 19.8f, 20.6f, 21.5f, 22.2f, 23.4f, 24.2f, 25.5f, 26.7f, 28.2f, 30.5f, 33.5f, 36.6f, 38.6f);
                                            }else if (age>=30 && age <=39) {
                                                report3 = Report3WomanFunction(report3, value, 11.2f, 13.9f, 15.5f, 16.5f, 17.5f, 18.3f, 19.2f, 20.1f, 21.0f, 22.0f, 22.8f, 23.7f, 24.8f, 25.8f, 26.9f, 28.1f, 29.6f, 31.5f, 33.6f, 36.2f, 39.0f);
                                            }else if (age>=40 && age <=49) {
                                                report3 = Report3WomanFunction(report3, value, 12.1f, 15.2f, 16.8f, 18.3f, 19.5f, 20.6f, 21.7f, 22.7f, 23.7f, 24.6f, 25.5f, 26.4f, 27.5f, 28.4f, 29.5f, 30.7f, 31.9f, 33.4f, 35.1f, 37.1f, 39.1f);
                                            }else if (age>=50 && age <=59) {
                                                report3 = Report3WomanFunction(report3, value, 13.9f, 16.9f, 19.1f, 20.8f, 22.3f, 23.6f, 24.8f, 25.8f, 26.7f, 27.6f, 28.4f, 29.3f, 30.1f, 30.8f, 31.8f, 32.9f, 33.9f, 35.0f, 36.1f, 37.6f, 39.8f);
                                            }else if (age>=60 && age <=69) {
                                                report3 = Report3WomanFunction(report3, value, 13.9f, 17.7f, 20.2f, 22.0f, 23.3f, 24.6f, 25.7f, 26.7f, 27.5f, 28.3f, 29.2f, 30.1f, 30.8f, 31.5f, 32.6f, 33.3f, 34.4f, 35.6f, 36.6f, 38.2f, 40.3f);
                                            }else{
                                                report3 = Report3WomanFunction(report3, value, 11.7f, 16.4f, 18.3f, 21.2f, 22.5f, 23.7f, 24.8f, 25.7f, 26.6f, 27.6f, 28.2f, 28.9f, 30.5f, 31.0f, 31.9f, 32.9f, 34.0f, 35.3f, 36.4f, 38.1f, 40.2f);
                                            }
                                        }

                                        Map<String, String> requestParams3 = new HashMap<String, String>();
                                        requestParams3.put("FieldName","BodyFat");
                                        requestParams3.put("Value1", value+"");
                                        requestParams3.put("Value2", report3);
                                        list.add(requestParams3);
                                        continue;
                                    case 3:
                                        value = Math.round((value*100) / 1.6f) * 0.01f;
                                        BigDecimal b = new  BigDecimal(value);
                                        value = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age <=29) {
                                                report4 = Report4Function(report4, value, 1.05f, 1.20f, 1.28f, 1.33f, 1.37f, 1.40f, 1.42f, 1.45f, 1.47f, 1.50f, 1.53f, 1.56f, 1.58f, 1.61f, 1.63f, 1.66f, 1.73f, 1.75f, 1.81f, 1.86f, 2.00f);
                                            }else if (age>=30 && age <=39) {
                                                report4 = Report4Function(report4, value, 1.05f, 1.18f, 1.25f, 1.29f, 1.33f, 1.36f, 1.39f, 1.41f, 1.44f, 1.46f, 1.49f, 1.53f, 1.54f, 1.57f, 1.59f, 1.62f, 1.66f, 1.70f, 1.75f, 1.82f, 1.94f);
                                            }else if (age>=40 && age <=49) {
                                                report4 = Report4Function(report4, value, 1.01f, 1.13f, 1.21f, 1.25f, 1.28f, 1.31f, 1.33f, 1.36f, 1.38f, 1.41f, 1.43f, 1.45f, 1.49f, 1.51f, 1.53f, 1.56f, 1.60f, 1.65f, 1.69f, 1.77f, 1.87f);
                                            }else if (age>=50 && age <=59) {
                                                report4 = Report4Function(report4, value, 0.95f, 1.06f, 1.13f, 1.17f, 1.20f, 1.23f, 1.25f, 1.28f, 1.30f, 1.33f, 1.34f, 1.37f, 1.40f, 1.42f, 1.45f, 1.48f, 1.52f, 1.55f, 1.61f, 1.68f, 1.81f);
                                            }else if (age>=60 && age <=69) {
                                                report4 = Report4Function(report4, value, 0.86f, 0.97f, 1.03f, 1.07f, 1.11f, 1.13f, 1.17f, 1.19f, 1.21f, 1.23f, 1.25f, 1.28f, 1.30f, 1.33f, 1.35f, 1.38f, 1.41f, 1.45f, 1.50f, 1.59f, 1.73f);
                                            }else{
                                                report4 = Report4Function(report4, value, 0.82f, 0.89f, 0.95f, 1.00f, 1.03f, 1.06f, 1.09f, 1.10f, 1.13f, 1.15f, 1.17f, 1.19f, 1.21f, 1.23f, 1.26f, 1.29f, 1.33f, 1.37f, 1.41f, 1.53f, 1.69f);
                                            }
                                        }else{
                                            if(age <=29) {
                                                report4 = Report4Function(report4, value, 0.97f, 1.08f, 1.13f, 1.17f, 1.21f, 1.23f, 1.26f, 1.29f, 1.32f, 1.33f, 1.37f, 1.38f, 1.41f, 1.45f, 1.46f, 1.49f, 1.53f, 1.57f, 1.61f, 1.69f, 1.83f);
                                            }else if (age>=30 && age <=39) {
                                                report4 = Report4Function(report4, value, 0.95f, 1.03f, 1.09f, 1.13f, 1.17f, 1.20f, 1.21f, 1.25f, 1.27f, 1.29f, 1.33f, 1.34f, 1.36f, 1.38f, 1.41f, 1.45f, 1.49f, 1.53f, 1.57f, 1.63f, 1.76f);
                                            }else if (age>=40 && age <=49) {
                                                report4 = Report4Function(report4, value, 0.93f, 1.01f, 1.05f, 1.10f, 1.13f, 1.15f, 1.18f, 1.21f, 1.22f, 1.25f, 1.27f, 1.29f, 1.31f, 1.33f, 1.37f, 1.39f, 1.41f, 1.45f, 1.51f, 1.57f, 1.73f);
                                            }else if (age>=50 && age <=59) {
                                                report4 = Report4Function(report4, value, 0.87f, 0.95f, 1.00f, 1.03f, 1.06f, 1.09f, 1.11f, 1.13f, 1.14f, 1.17f, 1.19f, 1.21f, 1.22f, 1.24f, 1.27f, 1.29f, 1.33f, 1.34f, 1.39f, 1.45f, 1.69f);
                                            }else if (age>=60 && age <=69) {
                                                report4 = Report4Function(report4, value, 0.86f, 0.92f, 0.95f, 0.98f, 1.00f, 1.01f, 1.03f, 1.05f, 1.07f, 1.10f, 1.12f, 1.13f, 1.14f, 1.17f, 1.18f, 1.21f, 1.22f, 1.26f, 1.31f, 1.36f, 1.49f);
                                            }else{
                                                report4 = Report4Function(report4, value, 0.78f, 0.86f, 0.91f, 0.93f, 0.97f, 0.99f, 1.00f, 1.02f, 1.03f, 1.05f, 1.08f, 1.09f, 1.10f, 1.13f, 1.13f, 1.14f, 1.16f, 1.21f, 1.22f, 1.35f, 1.49f);
                                            }
                                        }

                                        Map<String, String> requestParams4 = new HashMap<String, String>();
                                        requestParams4.put("FieldName","TwelveMinuteDistance");
                                        requestParams4.put("Value1", value+"");
                                        requestParams4.put("Value2", report4);
                                        list.add(requestParams4);
                                        continue;
                                        //卧推
                                    case 4:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age<20){
                                                report5 = Report5BenchPressFunction(value, 0.76f, 0.76f, 0.81f, 0.86f, 0.89f, 0.93f, 0.96f, 1.01f, 1.06f, 1.10f, 1.13f, 1.16f, 1.19f, 1.23f, 1.24f, 1.29f, 1.34f, 1.38f, 1.46f, 1.76f, 1.76f);
                                            } else if(age>=20 && age <=29) {
                                                report5 = Report5BenchPressFunction(value, 0.72f, 0.72f, 0.80f, 0.84f, 0.88f, 0.90f, 0.93f, 0.96f, 0.99f, 10.3f, 1.06f, 1.10f, 1.14f, 1.18f, 1.22f, 1.26f, 1.32f, 1.37f, 1.48f, 1.63f, 1.63f);
                                            }else if (age>=30 && age <=39) {
                                                report5 = Report5BenchPressFunction(value, 0.65f, 0.65f, 0.71f, 0.75f, 0.78f, 0.81f, 0.83f, 0.86f, 0.88f, 0.90f, 0.93f, 0.96f, 0.98f, 1.01f, 1.04f, 1.08f, 1.12f, 1.17f, 1.24f, 1.35f, 1.35f);
                                            }else if (age>=40 && age <=49) {
                                                report5 = Report5BenchPressFunction(value, 0.59f, 0.59f, 0.66f, 0.69f, 0.72f, 0.74f, 0.76f, 0.78f, 0.80f, 0.82f, 0.84f, 0.86f, 0.88f, 0.90f, 0.93f, 0.96f, 1.00f, 1.04f, 1.10f, 1.20f, 1.20f);
                                            }else if (age>=50 && age <=59) {
                                                report5 = Report5BenchPressFunction( value, 0.53f, 0.53f, 0.57f, 0.60f, 0.63f, 0.66f, 0.68f, 0.70f, 0.71f, 0.73f, 0.75f, 0.77f, 0.79f, 0.81f, 0.84f, 0.87f, 0.90f, 0.93f, 0.97f, 1.05f, 1.05f);
                                            }else{
                                                report5 = Report5BenchPressFunction(value, 0.49f, 0.49f, 0.53f, 0.56f, 0.57f, 0.60f, 0.63f, 0.65f, 0.66f, 0.67f, 0.68f, 0.70f, 0.72f, 0.74f, 0.77f, 0.79f, 0.82f, 0.84f, 0.89f, 0.94f, 0.94f);
                                            }
                                        }else{
                                            if(age<20){
                                                report5 = Report5BenchPressFunction(value, 0.41f, 0.41f, 0.50f, 0.52f, 0.53f, 0.55f, 0.56f, 0.57f, 0.58f, 0.60f, 0.63f, 0.64f, 0.65f, 0.70f, 0.74f, 0.76f, 0.77f, 0.81f, 0.83f, 0.88f, 0.88f);

                                            } else if(age>=20 && age <=29) {
                                                report5 = Report5BenchPressFunction(value, 0.44f, 0.44f ,0.48f, 0.50f, 0.51f, 0.53f, 0.56f, 0.58f, 0.59f, 0.63f, 0.65f, 0.68f, 0.70f, 0.72f, 0.74f, 0.77f, 0.80f, 0.83f, 0.90f, 1.01f, 1.01f);
                                            }else if (age>=30 && age <=39) {
                                                report5 = Report5BenchPressFunction(value, 0.39f, 0.39f, 0.42f, 0.45f, 0.47f, 0.49f, 0.51f, 0.52f, 0.53f, 0.55f, 0.57f, 0.58f, 0.60f, 0.62f, 0.63f, 0.65f, 0.70f, 0.72f, 0.76f, 0.82f, 0.82f);
                                            }else if (age>=40 && age <=49) {
                                                report5 = Report5BenchPressFunction(value, 0.35f, 0.35f, 0.38f, 0.42f, 0.43f, 0.45f, 0.47f, 0.48f, 0.50f, 0.51f, 0.52f, 0.53f, 0.54f, 0.55f, 0.57f, 0.60f, 0.62f, 0.66f, 0.71f, 0.77f, 0.77f);
                                            }else if (age>=50 && age <=59) {
                                                report5 = Report5BenchPressFunction(value, 0.31f, 0.31f, 0.37f, 0.38f, 0.39f, 0.41f, 0.42f, 0.43f, 0.44f, 0.45f, 0.46f, 0.47f, 0.48f, 0.50f, 0.52f, 0.53f, 0.55f, 0.57f, 0.61f, 0.68f, 0.68f);
                                            }else{
                                                report5 = Report5BenchPressFunction(value, 0.26f, 0.26f, 0.33f, 0.36f, 0.38f, 0.39f, 0.40f, 0.41f, 0.43f, 0.44f, 0.45f, 0.46f, 0.47f, 0.48f, 0.51f, 0.53f, 0.54f, 0.59f, 0.64f, 0.72f, 0.72f);
                                            }
                                        }

                                        Map<String, String> requestParams5 = new HashMap<String, String>();
                                        requestParams5.put("FieldName","BenchPress");
                                        requestParams5.put("Value1", value+"");
                                        requestParams5.put("Value2", report5);
                                        list.add(requestParams5);
                                        continue;
                                        //蹬腿，腿部力量
                                    case 5:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age<=29){
                                                report51 += Report5MuscleFunction(value, 1.51f, 1.63f, 1.74f, 1.83f, 1.91f, 1.97f, 2.05f, 2.13f, 2.27f);
                                            } else if(age>=30 && age <=39) {
                                                report51 += Report5MuscleFunction(value, 1.43f, 1.52f, 1.59f, 1.65f, 1.71f, 1.77f, 1.85f, 1.93f, 2.07f);
                                            }else if (age>=40 && age <=49) {
                                                report51 += Report5MuscleFunction(value, 1.35f, 1.44f, 1.51f, 1.57f, 1.62f, 1.68f, 1.74f, 1.82f, 1.92f);
                                            }else if (age>=50 && age <=59) {
                                                report51 += Report5MuscleFunction(value, 1.22f, 1.32f, 1.39f, 1.46f, 1.52f, 1.58f, 1.64f, 1.71f, 1.80f);
                                            }else{
                                                report51 += Report5MuscleFunction(value, 1.16f, 1.25f, 1.30f, 1.38f, 1.43f, 1.49f, 1.56f, 1.62f, 1.73f);
                                            }
                                        }else{
                                            if(age<=29){
                                                report51 += Report5MuscleFunction(value, 1.14f, 1.22f, 1.27f, 1.37f, 1.44f, 1.50f, 1.58f, 1.68f, 1.82f);
                                            } else if(age>=30 && age <=39) {
                                                report51 += Report5MuscleFunction(value, 1.00f, 1.09f, 1.15f, 1.21f, 1.27f, 1.33f, 1.39f, 1.47f, 1.61f);
                                            }else if (age>=40 && age <=49) {
                                                report51 += Report5MuscleFunction(value, 0.94f, 1.02f, 1.08f, 1.13f, 1.18f, 1.23f, 1.29f, 1.37f, 1.48f);
                                            }else if (age>=50 && age <=59) {
                                                report51 += Report5MuscleFunction(value, 0.78f, 0.88f, 0.95f, 0.99f, 1.05f, 1.10f, 1.17f, 1.25f, 1.37f);
                                            }else{
                                                report51 += Report5MuscleFunction(value, 0.72f, 0.85f, 0.88f, 0.93f, 0.99f, 1.04f, 1.13f, 1.18f, 1.32f);
                                            }
                                        }

                                        report5 +=report51;

                                        Map<String, String> requestParams6 = new HashMap<String, String>();
                                        requestParams6.put("FieldName","DriveLeg");
                                        requestParams6.put("Value1", value+"");
                                        requestParams6.put("Value2", report51);
                                        list.add(requestParams6);
                                        continue;
                                        //俯卧撑
                                    case 6:
                                        if (Res.loginBeanModel.getGender() =="M") {
                                            if(age<=29){
                                                report6 = Report6PushUpFunction(value, 16f, 17f, 21f, 22f, 28f, 29f, 35f, 36f);
                                            } else if(age>=30 && age <=39) {
                                                report6 = Report6PushUpFunction(value, 11f, 12f, 16f, 17f, 21f, 22f, 29f, 30f);
                                            }else if (age>=40 && age <=49) {
                                                report6 = Report6PushUpFunction(value, 9f, 10f, 12f, 13f, 16f, 17f, 24f, 25f);
                                            }else if (age>=50 && age <=59) {
                                                report6 = Report6PushUpFunction(value, 6f, 7f, 9f, 10f, 12f, 13f, 20f, 21f);
                                            }else{
                                                report6 = Report6PushUpFunction(value, 4f, 5f, 7f, 8f, 10f, 11f, 17f, 18f);
                                            }
                                        }else{
                                            if(age<=29){
                                                report6 = Report6PushUpFunction(value, 9f, 10f, 14f, 15f, 20f, 21f, 29f, 30f);
                                            } else if(age>=30 && age <=39) {
                                                report6 = Report6PushUpFunction(value, 7f, 8f, 12f, 13f, 19f, 20f, 26f, 27f);
                                            }else if (age>=40 && age <=49) {
                                                report6 = Report6PushUpFunction(value, 4f, 5f, 10f, 11f, 14f, 15f, 23f, 24f);
                                            }else if (age>=50 && age <=59) {
                                                report6 = Report6PushUpFunction(value, 1f, 2f, 6f, 7f, 10f, 11f, 20f, 21f);
                                            }else{
                                                report6 = Report6PushUpFunction(value, 1f, 2f, 4f, 5f, 11f, 12f, 16f, 17f);
                                            }
                                        }
                                        Map<String, String> requestParams7 = new HashMap<String, String>();
                                        requestParams7.put("FieldName","Pushup");
                                        requestParams7.put("Value1", value+"");
                                        requestParams7.put("Value2", report6);
                                        list.add(requestParams7);
                                        continue;
                                        // 屈膝抬肩
                                    case 7:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age<=29){
                                                report61 = Report6ShoulderFlexionFunction(value, 4f, 13f, 20f, 24f, 27f, 31f, 41f, 56f, 75f);
                                            } else if(age>=30 && age <=39) {
                                                report61 = Report6ShoulderFlexionFunction(value, 0f, 13f, 19f, 26f, 31f, 36f, 46f, 69f, 75f);
                                            }else if (age>=40 && age <=49) {
                                                report61 = Report6ShoulderFlexionFunction(value, 13f, 21f, 26f, 31f, 39f, 51f, 67f, 75f, 75f);
                                            }else if (age>=50 && age <=59) {
                                                report61 = Report6ShoulderFlexionFunction(value, 0f, 13f, 19f, 23f, 27f, 35f, 45f, 60f, 74f);
                                            }else{
                                                report61 = Report6ShoulderFlexionFunction(value, 0f, 0f, 6f, 9f, 16f, 19f, 26f, 33f, 53f);
                                            }
                                        }else{
                                            if(age<=29){
                                                report61 = Report6ShoulderFlexionFunction(value, 5f, 12f, 17f, 21f, 27f, 32f, 37f, 45f, 70f);
                                            } else if(age>=30 && age <=39) {
                                                report61 = Report6ShoulderFlexionFunction(value, 0f, 0f, 12f, 15f, 21f, 28f, 34f, 43f, 55f);
                                            }else if (age>=40 && age <=49) {
                                                report61 = Report6ShoulderFlexionFunction(value, 0f, 5f, 14f, 20f, 25f, 28f, 33f, 42f, 55f);
                                            }else if (age>=50 && age <=59) {
                                                report61 = Report6ShoulderFlexionFunction(value, 0f, 0f, 0f, 2f, 9f, 16f, 23f, 30f, 48f);
                                            }else{
                                                report61 = Report6ShoulderFlexionFunction(value, 0f, 0f, 3f, 9f, 13f, 19f, 24f, 30f, 50f);
                                            }
                                        }

                                        report6 += report61;

                                        Map<String, String> requestParams8 = new HashMap<String, String>();
                                        requestParams8.put("FieldName","ShoulderFlexion");
                                        requestParams8.put("Value1", value+"");
                                        requestParams8.put("Value2", report61);
                                        list.add(requestParams8);
                                       continue;
                                        // 卧推
                                    case 8:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age<=29){
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            } else if(age>=30 && age <=39) {
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }else if (age>=40 && age <=49) {
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }else if (age>=50 && age <=59) {
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }else{
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }
                                        }else{
                                            if(age<=29){
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            } else if(age>=30 && age <=39) {
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }else if (age>=40 && age <=49) {
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }else if (age>=50 && age <=59) {
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }else{
                                                report62 = Report6YMCAFunction(value, 10f, 13f, 17f, 20f, 22f, 24f, 28f, 29f, 33f, 34f, 41f, 44f, 64f);
                                            }
                                        }

                                        report6 += report62;

                                        Map<String, String> requestParams9 = new HashMap<String, String>();
                                        requestParams9.put("FieldName","YMCABenchPress");
                                        requestParams9.put("Value1", value+"");
                                        requestParams9.put("Value2", report62);
                                        list.add(requestParams9);
                                        continue;
                                        // 坐位体前屈
                                    case 9:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age<=29){
                                                report7 =   Report7SitAnteriorFunction(value, 24f, 25f, 29f, 30f, 33f, 34f, 39f, 40f);
                                            } else if(age>=30 && age <=39) {
                                                report7 =   Report7SitAnteriorFunction(value, 22f, 23f, 27f, 28f, 32f, 33f, 37f, 38f);
                                            }else if (age>=40 && age <=49) {
                                                report7 =   Report7SitAnteriorFunction(value, 17f, 18f, 23f, 24f, 28f, 29f, 34f, 35f);
                                            }else if (age>=50 && age <=59) {
                                                report7 =   Report7SitAnteriorFunction(value, 15f, 16f, 23f, 24f, 27f, 28f, 34f, 35f);
                                            }else{
                                                report7 =   Report7SitAnteriorFunction(value, 14f, 15f, 19f, 20f, 24f, 25f, 32f, 33f);
                                            }
                                        }else{
                                            if(age<=29){
                                                report7 =   Report7SitAnteriorFunction(value, 27f, 28f, 32f, 33f, 36f, 37f, 40f, 41f);
                                            } else if(age>=30 && age <=39) {
                                                report7 =   Report7SitAnteriorFunction(value, 26f, 27f, 31f, 32f, 35f, 36f, 40f, 41f);
                                            }else if (age>=40 && age <=49) {
                                                report7 =   Report7SitAnteriorFunction(value, 24f, 25f, 29f, 30f, 33f, 34f, 37f, 38f);
                                            }else if (age>=50 && age <=59) {
                                                report7 =   Report7SitAnteriorFunction(value, 24f, 25f, 29f, 30f, 32f, 33f, 38f, 39f);
                                            }else{
                                                report7 =   Report7SitAnteriorFunction(value, 22f, 23f, 26f, 27f, 30f, 31f, 34f, 35f);
                                            }
                                        }
                                        Map<String, String> requestParams10 = new HashMap<String, String>();
                                        requestParams10.put("FieldName","SitAnterior");
                                        requestParams10.put("Value1", value+"");
                                        requestParams10.put("Value2", report7);
                                        list.add(requestParams10);
                                        continue;
                                        // YMCA坐位体前屈
                                    case 10:
                                        if (Res.loginBeanModel.getGender() == "M") {
                                            if(age<=25){
                                                report71 =   Report7YMCASitAnteriorFunction(value, 11f, 13f, 14f, 15f, 17f, 18f, 19f, 20f, 22f);
                                            } else if(age>=26 && age <=35) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 9f, 11f, 13f, 14f, 15f, 17f, 17f, 19f, 21f);
                                            }else if (age>=36 && age <=45) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 7f, 11f, 13f, 13f, 15f, 16f, 17f, 19f, 21f);
                                            }else if (age>=46 && age <=55) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 6f, 9f, 10f, 11f, 13f, 14f, 15f, 17f, 19f);
                                            }else if (age>=56 && age <=65) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 5f, 7f, 9f, 9f, 11f, 13f, 13f, 15f, 17f);
                                            }else{
                                                report71 =   Report7YMCASitAnteriorFunction(value, 4f, 7f, 8f, 9f, 10f, 12f, 13f, 15f, 17f);
                                            }
                                        }else{
                                            if(age<=25){
                                                report71 =   Report7YMCASitAnteriorFunction(value, 14f, 16f, 17f, 18f, 19f, 20f, 21f, 22f, 24f);
                                            } else if(age>=26 && age <=35) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 13f, 15f, 16f, 17f, 19f, 20f, 20f, 21f, 23f);
                                            }else if (age>=36 && age <=45) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 12f, 14f, 15f, 16f, 17f, 18f, 19f, 21f, 22f);
                                            }else if (age>=46 && age <=55) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 10f, 12f, 14f, 14f, 16f, 17f, 18f, 20f, 21f);
                                            }else if (age>=56 && age <=65) {
                                                report71 =   Report7YMCASitAnteriorFunction(value, 9f, 11f, 13f, 14f, 15f, 16f, 17f, 19f, 20f);
                                            }else{
                                                report71 =   Report7YMCASitAnteriorFunction(value, 9f, 11f, 13f, 14f, 15f, 17f, 17f, 18f, 20f);
                                            }
                                        }

                                        report7 += report71;

                                        Map<String, String> requestParams11 = new HashMap<String, String>();
                                        requestParams11.put("FieldName","YMCASitAnterior");
                                        requestParams11.put("Value1", value+"");
                                        requestParams11.put("Value2", report71);
                                        list.add(requestParams11);
                                        continue;
                                    default:
                                        continue;
                                }
                        }

                    } catch (Exception e) {

                        Log.i("Exception", e.getMessage());
                    }

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("RequestList",list);
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/CreateUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                            Res.loginBeanModel = loginBean;
                            Intent sportIntent = new Intent(QuestionnaireActivity.this, QuestionReportActivity.class);
                            Bundle sportBundle = new Bundle();
                            sportBundle.putString("ActivityName", "PhysicalAgility");
                            sportIntent.putExtras(sportBundle);
                            startActivity(sportIntent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Toast.makeText(QuestionnaireActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
        //endregion

        scroll.addView(mailayout);
    }


    private String Report2Function(String report, float value,int num1,int num2,int num3) {
        if (value<num1) {
            report ="您的腰围是："+value+",健康风险分层为极低。";
        } else if (value>=num1 && value<num2) {
            report ="您的腰围是："+value+",健康风险分层为低。";
        } else if (value >= num2 && value < num3) {
            report ="您的腰围是："+value+",健康风险分层为高。";
        } else {
            report ="您的腰围是："+value+",健康风险分层为极高。";
        }
        return report;
    }

    private String Report3ManFunction(String report, float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9,
                                   float num10,float num11,float num12,float num13,float num14,float num15,float num16) {
        if (value<=num1) {
            report ="您的体脂为："+value+"%，百分位数为75,表现为良好。";
        } else if (value>num1 && value<=num2) {
            report ="您的体脂为："+value+"%，百分位数为70,表现为良好。";
        } else if (value > num2 && value <= num3) {
            report ="您的体脂为："+value+"%，百分位数为65,表现为良好。";
        } else if (value > num3 && value <= num4) {
            report ="您的体脂为："+value+"%，百分位数为60,表现为良好。";
        } else if (value > num4 && value <= num5) {
            report ="您的体脂为："+value+"%，百分位数为55,表现为一般。";
        } else if (value > num5 && value <= num6) {
            report ="您的体脂为："+value+"%，百分位数为50,表现为一般。";
        } else if (value > num6 && value <= num7) {
            report ="您的体脂为："+value+"%，百分位数为45,表现为一般。";
        } else if (value > num7 && value <= num8) {
            report ="您的体脂为："+value+"%，百分位数为40,表现为一般。";
        } else if (value > num8 && value <= num9) {
            report ="您的体脂为："+value+"%，百分位数为35,表现为较胖。";
        } else if (value > num9 && value <= num10) {
            report ="您的体脂为："+value+"%，百分位数为30,表现为较胖。";
        } else if (value > num10 && value <= num11) {
            report ="您的体脂为："+value+"%，百分位数为25,表现为较胖。";
        } else if (value > num11 && value <= num12) {
            report ="您的体脂为："+value+"%，百分位数为20,表现为较胖。";
        } else if (value > num12 && value <= num13) {
            report ="您的体脂为："+value+"%，百分位数为15,表现为很胖。";
        } else if (value > num13 && value <= num14) {
            report ="您的体脂为："+value+"%，百分位数为10,表现为很胖。";
        } else if (value > num14 && value <= num15) {
            report ="您的体脂为："+value+"%，百分位数为5,表现为很胖。";
        } else if (value > num15 && value <= num16) {
            report ="您的体脂为："+value+"%，百分位数为1,表现为很胖。";
        } else {
            report ="您的体脂为："+value+"%，百分位数为1,表现为很胖。";
        }
        return report;
    }

    private String Report3WomanFunction(String report, float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9,
                                      float num10,float num11,float num12,float num13,float num14,float num15,float num16,float num17,float num18,float num19,float num20,float num21) {
        if (value<=num1) {
            report ="您的体脂为："+value+"%，百分位数为99,表现为非常瘦。";
        } else if (value>num1 && value<=num2) {
            report ="您的体脂为："+value+"%，百分位数为95,表现为非常瘦。";
        } else if (value > num2 && value <= num3) {
            report ="您的体脂为："+value+"%，百分位数为90,表现为出色。";
        } else if (value > num3 && value <= num4) {
            report ="您的体脂为："+value+"%，百分位数为85,表现为出色。";
        } else if (value > num4 && value <= num5) {
            report ="您的体脂为："+value+"%，百分位数为80,表现为出色。";
        } else if (value > num5 && value <= num6) {
            report ="您的体脂为："+value+"%，百分位数为75,表现为良好。";
        } else if (value > num6 && value <= num7) {
            report ="您的体脂为："+value+"%，百分位数为70,表现为良好。";
        } else if (value > num7 && value <= num8) {
            report ="您的体脂为："+value+"%，百分位数为65,表现为良好。";
        } else if (value > num8 && value <= num9) {
            report ="您的体脂为："+value+"%，百分位数为60,表现为良好。";
        } else if (value > num9 && value <= num10) {
            report ="您的体脂为："+value+"%，百分位数为55,表现为一般。";
        } else if (value > num10 && value <= num11) {
            report ="您的体脂为："+value+"%，百分位数为50,表现为一般。";
        } else if (value > num11 && value <= num12) {
            report ="您的体脂为："+value+"%，百分位数为45,表现为一般。";
        } else if (value > num12 && value <= num13) {
            report ="您的体脂为："+value+"%，百分位数为40,表现为一般。";
        } else if (value > num13 && value <= num14) {
            report ="您的体脂为："+value+"%，百分位数为35,表现为较胖。";
        } else if (value > num14 && value <= num15) {
            report ="您的体脂为："+value+"%，百分位数为30,表现为较胖。";
        } else if (value > num15 && value <= num16) {
            report ="您的体脂为："+value+"%，百分位数为25,表现为较胖。";
        } else if (value > num16 && value <= num17) {
            report ="您的体脂为："+value+"%，百分位数为20,表现为较胖。";
        } else if (value > num17 && value <= num18) {
            report ="您的体脂为："+value+"%，百分位数为15,表现为很胖。";
        } else if (value > num18 && value <= num19) {
            report ="您的体脂为："+value+"%，百分位数为10,表现为很胖。";
        } else if (value > num19 && value <= num20) {
            report ="您的体脂为："+value+"%，百分位数为5,表现为很胖。";
        } else if (value > num20 && value <= num21) {
            report ="您的体脂为："+value+"%，百分位数为1,表现为很胖。";
        } else {
            report ="您的体脂为："+value+"%，百分位数为1,表现为很胖。";
        }
        return report;
    }

    private String Report4Function(String report, float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9,
                                        float num10,float num11,float num12,float num13,float num14,float num15,float num16,float num17,float num18,float num19,float num20,float num21) {
        if (value<=num1) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为99,表现为极弱。";
        } else if (value>num1 && value<=num2) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为95,表现为极弱。";
        } else if (value > num2 && value <= num3) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为90,表现为极弱。";
        } else if (value > num3 && value <= num4) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为85,表现为极弱。";
        } else if (value > num4 && value <= num5) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为80,表现为弱。";
        } else if (value > num5 && value <= num6) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为75,表现为弱。";
        } else if (value > num6 && value <= num7) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为70,表现为弱。";
        } else if (value > num7 && value <= num8) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为65,表现为弱。";
        } else if (value > num8 && value <= num9) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为60,表现为一般。";
        } else if (value > num9 && value <= num10) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为55,表现为一般。";
        } else if (value > num10 && value <= num11) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为50,表现为一般。";
        } else if (value > num11 && value <= num12) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为45,表现为一般。";
        } else if (value > num12 && value <= num13) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为40,表现为良好。";
        } else if (value > num13 && value <= num14) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为35,表现为良好。";
        } else if (value > num14 && value <= num15) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为30,表现为良好。";
        } else if (value > num15 && value <= num16) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为25,表现为良好。";
        } else if (value > num16 && value <= num17) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为20,表现为优秀。";
        } else if (value > num17 && value <= num18) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为15,表现为优秀。";
        } else if (value > num18 && value <= num19) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为10,表现为优秀。";
        } else if (value > num19 && value <= num20) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为5,表现为极好。";
        } else if (value > num20 && value <= num21) {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为1,表现为极好。";
        } else {
            report ="您的12分钟跑距离为："+value+"英里，百分位数为1,表现为极好。";
        }
        return report;
    }

    private String Report5BenchPressFunction(float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9,
                                   float num10,float num11,float num12,float num13,float num14,float num15,float num16,float num17,float num18,float num19,float num20,float num21) {
        String report ="";
        if (value<=num1) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为1,表现级别为VP。";
        } else if (value>num1 && value<=num2) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为5,表现级别为VP。";
        } else if (value > num2 && value <= num3) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为10,表现级别为VP。";
        } else if (value > num3 && value <= num4) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为15,表现级别为VP。";
        } else if (value > num4 && value <= num5) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为20,表现级别为P。";
        } else if (value > num5 && value <= num6) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为25,表现级别为P。";
        } else if (value > num6 && value <= num7) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为30,表现级别为P。";
        } else if (value > num7 && value <= num8) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为35,表现级别为P。";
        } else if (value > num8 && value <= num9) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为40,表现级别为F。";
        } else if (value > num9 && value <= num10) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为45,表现级别为F。";
        } else if (value > num10 && value <= num11) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为50,表现级别为F。";
        } else if (value > num11 && value <= num12) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为55,表现级别为F。";
        } else if (value > num12 && value <= num13) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为60,表现级别为G。";
        } else if (value > num13 && value <= num14) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为65,表现级别为G。";
        } else if (value > num14 && value <= num15) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为70,表现级别为G。";
        } else if (value > num15 && value <= num16) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为75,表现级别为G。";
        } else if (value > num16 && value <= num17) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为80,表现级别为E。";
        } else if (value > num17 && value <= num18) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为85,表现级别为E。";
        } else if (value > num18 && value <= num19) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为90,表现级别为E。";
        } else if (value > num19 && value <= num20) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为95,表现级别为S。";
        } else if (value > num20 && value <= num21) {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为99,表现级别为S。";
        } else {
            report ="您的卧推体重比为："+value+"，最大卧推重量百分位数为99,表现级别为S。";
        }
        return report;
    }

    private String Report5MuscleFunction(float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9) {
        String report = "";
        if (value<=num1) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为10,表现为显著低于平均水平。";
        } else if (value>num1 && value<=num2) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为20,表现为显著低于平均水平。";
        } else if (value > num2 && value <= num3) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为30,表现为低于平均水平。";
        } else if (value > num3 && value <= num4) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为40,表现为低于平均水平。";
        } else if (value > num4 && value <= num5) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为50,表现为平均水平。";
        } else if (value > num5 && value <= num6) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为60,表现为平均水平。";
        } else if (value > num6 && value <= num7) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为70,表现为高于平均水平。";
        } else if (value > num7 && value <= num8) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为80,表现为高于平均水平。";
        } else if (value > num8 && value <= num9) {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为80,表现为显著高于平均水平。";
        } else {
            report ="您的蹬腿最大重量为(kg)："+value+"，肌肉力量百分位数为90,表现为显著高于平均水平。";
        }
        return report;
    }

    private String Report6PushUpFunction(float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8) {
        String report = "";
        if (value<=num1) {
            report ="您的俯卧撑最大次数为："+value+"，表现为需要改进。";
        } else if (value>num1 && value<=num2) {
            report ="您的俯卧撑最大次数为："+value+"，表现为一般。";
        } else if (value > num2 && value <= num3) {
            report ="您的俯卧撑最大次数为："+value+"，表现为一般。";
        } else if (value > num3 && value <= num4) {
            report ="您的俯卧撑最大次数为："+value+"，表现为好。";
        } else if (value > num4 && value <= num5) {
            report ="您的俯卧撑最大次数为："+value+"，表现为好。";
        } else if (value > num5 && value <= num6) {
            report ="您的俯卧撑最大次数为："+value+"，表现为很好。";
        } else if (value > num6 && value <= num7) {
            report ="您的俯卧撑最大次数为："+value+"，表现为很好。";
        } else if (value > num7 && value <= num8) {
            report ="您的俯卧撑最大次数为："+value+"，表现为优秀。";
        } else {
            report ="您的俯卧撑最大次数为："+value+"，表现为优秀。";
        }
        return report;
    }

    private String Report6ShoulderFlexionFunction(float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9) {
        String report = "";
        if (value<=num1) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为显著低于平均水平。";
        } else if (value>num1 && value<=num2) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为显著低于平均水平。";
        } else if (value > num2 && value <= num3) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为显著低于平均水平。";
        } else if (value > num3 && value <= num4) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为低于平均水平。";
        } else if (value > num4 && value <= num5) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为低于平均水平。";
        } else if (value > num5 && value <= num6) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为平均水平。";
        } else if (value > num6 && value <= num7) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为平均水平。";
        } else if (value > num7 && value <= num8) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为高于平均水平。";
        } else if (value > num8 && value <= num9) {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为高于平均水平。";
        } else {
            report ="您的屈膝抬肩最大次数为："+value+"，表现为显著高于平均水平。";
        }
        return report;
    }

    private String Report6YMCAFunction(float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9,
                                      float num10,float num11,float num12,float num13) {
        String report = "";
        if (value<=num1) {
            report ="您的YMCA卧推次数为："+value+"，表现为很弱。";
        } else if (value>num1 && value<=num2) {
            report ="您的YMCA卧推次数为："+value+"，表现为很弱。";
        } else if (value > num2 && value <= num3) {
            report ="您的YMCA卧推次数为："+value+"，表现为弱。";
        } else if (value > num3 && value <= num4) {
            report ="您的YMCA卧推次数为："+value+"，表现为弱。";
        } else if (value > num4 && value <= num5) {
            report ="您的YMCA卧推次数为："+value+"，表现为低于平均水平。";
        } else if (value > num5 && value <= num6) {
            report ="您的YMCA卧推次数为："+value+"，表现为低于平均水平。";
        } else if (value > num6 && value <= num7) {
            report ="您的YMCA卧推次数为："+value+"，表现为平均水平。";
        } else if (value > num7 && value <= num8) {
            report ="您的YMCA卧推次数为："+value+"，表现为平均水平。";
        } else if (value > num8 && value <= num9) {
            report ="您的YMCA卧推次数为："+value+"，表现为高于平均水平。";
        } else if (value > num9 && value <= num10) {
            report ="您的YMCA卧推次数为："+value+"，表现为高于平均水平。";
        } else if (value > num10 && value <= num11) {
            report ="您的YMCA卧推次数为："+value+"，表现为好。";
        } else if (value > num11 && value <= num12) {
            report ="您的YMCA卧推次数为："+value+"，表现为好。";
        } else if (value > num12 && value <= num13) {
            report ="您的YMCA卧推次数为："+value+"，表现优秀。";
        } else {
            report ="您的YMCA卧推次数为："+value+"，表现优秀。";
        }
        return report;
    }

    private String Report7SitAnteriorFunction(float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8) {
        String report ="";
        if (value<=num1) {
            report ="您的坐位体前屈为："+value+"cm，表现为需要改进。";
        } else if (value>num1 && value<=num2) {
            report ="您的坐位体前屈为："+value+"cm，表现为需要改进。";
        } else if (value > num2 && value <= num3) {
            report ="您的坐位体前屈为："+value+"cm，表现为一般。";
        } else if (value > num3 && value <= num4) {
            report ="您的坐位体前屈为："+value+"cm，表现为一般。";
        } else if (value > num4 && value <= num5) {
            report ="您的坐位体前屈为："+value+"cm，表现为好。";
        } else if (value > num5 && value <= num6) {
            report ="您的坐位体前屈为："+value+"cm，表现为好。";
        } else if (value > num6 && value <= num7) {
            report ="您的坐位体前屈为："+value+"cm，表现为很好。";
        } else if (value > num7 && value <= num8) {
            report ="您的坐位体前屈为："+value+"cm，表现为很好。";
        } else {
            report ="您的坐位体前屈为："+value+"cm，表现为优秀。";
        }
        return report;
    }

    private String Report7YMCASitAnteriorFunction(float value,float num1,float num2,float num3,float num4,float num5,float num6,float num7,float num8,float num9) {
        String report = "";
        if (value<=num1) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为10，表现为远低于平均。";
        } else if (value>num1 && value<=num2) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为10，表现为远低于平均。";
        } else if (value > num2 && value <= num3) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为20，表现为远低于平均。";
        } else if (value > num3 && value <= num4) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为30，表现为低于平均。";
        } else if (value > num4 && value <= num5) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为40，表现为低于平均。";
        } else if (value > num5 && value <= num6) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为50，表现为平均水平。";
        } else if (value > num6 && value <= num7) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为60，表现为平均水平。";
        } else if (value > num7 && value <= num8) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为70，表现为高于平均水平。";
        } else if (value > num8 && value <= num9) {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为80，表现为高于平均水平。";
        } else {
            report ="您的YMCA坐位体前屈为："+value+"cm，定量百分比为90，表现为远高于平均水平。";
        }
        return report;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
