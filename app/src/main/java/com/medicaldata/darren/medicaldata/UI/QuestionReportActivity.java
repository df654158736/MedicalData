package com.medicaldata.darren.medicaldata.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Model.LoginBean;
import com.medicaldata.darren.medicaldata.R;

public class QuestionReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_report);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
        getWindow().setAttributes(p);

        LinearLayout ll_report1 = (LinearLayout) findViewById(R.id.ll_report1);
        LinearLayout ll_report2 = (LinearLayout) findViewById(R.id.ll_report2);
        LinearLayout ll_report3 = (LinearLayout) findViewById(R.id.ll_report3);
        LinearLayout ll_report4 = (LinearLayout) findViewById(R.id.ll_report4);
        LinearLayout ll_report5 = (LinearLayout) findViewById(R.id.ll_report5);
        LinearLayout ll_report6 = (LinearLayout) findViewById(R.id.ll_report6);
        LinearLayout ll_report7 = (LinearLayout) findViewById(R.id.ll_report7);
        LinearLayout ll_report8 = (LinearLayout) findViewById(R.id.ll_report8);
        LinearLayout ll_report9 = (LinearLayout) findViewById(R.id.ll_report9);
        LinearLayout ll_report10 = (LinearLayout) findViewById(R.id.ll_report10);
        LinearLayout ll_report11 = (LinearLayout) findViewById(R.id.ll_report11);


        TextView report1 = (TextView) findViewById(R.id.tv_report1);
        TextView report2 = (TextView) findViewById(R.id.tv_report2);
        TextView report3 = (TextView) findViewById(R.id.tv_report3);
        TextView report4 = (TextView) findViewById(R.id.tv_report4);
        TextView report5 = (TextView) findViewById(R.id.tv_report5);
        TextView report6 = (TextView) findViewById(R.id.tv_report6);
        TextView report7 = (TextView) findViewById(R.id.tv_report7);
        TextView report8 = (TextView) findViewById(R.id.tv_report8);
        TextView report9 = (TextView) findViewById(R.id.tv_report9);
        TextView report10 = (TextView) findViewById(R.id.tv_report10);
        TextView report11 = (TextView) findViewById(R.id.tv_report11);

        LoginBean model = Res.loginBeanModel;

        Intent intent = getIntent();
        String activityName = intent.getStringExtra("ActivityName");

        if(activityName.equals("CardiovascularReport")){

            if (!TextUtils.isEmpty(model.getCardiovascularReport())) {
                ll_report1.setVisibility(View.VISIBLE);
                report1.setText(model.getCardiovascularReport());
            }
        }

        if(activityName.equals("RiskReport")) {

            if (!TextUtils.isEmpty(model.getRisk1Report())) {
                ll_report1.setVisibility(View.VISIBLE);
                report1.setText(model.getRisk1Report());
            }

            if (!TextUtils.isEmpty(model.getRisk2Report())) {
                ll_report2.setVisibility(View.VISIBLE);
                report2.setText(model.getRisk2Report());
            }
        }

        if(activityName.equals("PhysicalAgility")){

            if (!TextUtils.isEmpty(model.getWeightReport())) {
                ll_report1.setVisibility(View.VISIBLE);
                report1.setText(model.getWeightReport());
            }
            if (!TextUtils.isEmpty(model.getWaistReport())) {
                ll_report2.setVisibility(View.VISIBLE);
                report2.setText(model.getWaistReport());
            }
            if (!TextUtils.isEmpty(model.getBodyFatReport())) {
                ll_report3.setVisibility(View.VISIBLE);
                report3.setText(model.getBodyFatReport());
            }
            if (!TextUtils.isEmpty(model.getTwelveMinuteDistanceReport())) {
                ll_report4.setVisibility(View.VISIBLE);
                report4.setText(model.getTwelveMinuteDistanceReport());
            }
            if (!TextUtils.isEmpty(model.getBenchPressReport())) {
                ll_report5.setVisibility(View.VISIBLE);
                report5.setText(model.getBenchPressReport());
            }
            if (!TextUtils.isEmpty(model.getDriveLegReport())) {
                ll_report6.setVisibility(View.VISIBLE);
                report6.setText(model.getDriveLegReport());
            }

            if (!TextUtils.isEmpty(model.getPushupReport())) {
                ll_report7.setVisibility(View.VISIBLE);
                report7.setText(model.getPushupReport());
            }

            if (!TextUtils.isEmpty(model.getShoulderFlexionReport())) {
                ll_report8.setVisibility(View.VISIBLE);
                report8.setText(model.getShoulderFlexionReport());
            }

            if (!TextUtils.isEmpty(model.getYMCABenchPressReport())) {
                ll_report9.setVisibility(View.VISIBLE);
                report9.setText(model.getYMCABenchPressReport());
            }

            if (!TextUtils.isEmpty(model.getSitAnteriorReport())) {
                ll_report10.setVisibility(View.VISIBLE);
                report10.setText(model.getSitAnteriorReport());
            }

            if (!TextUtils.isEmpty(model.getYMCASitAnteriorReport())) {
                ll_report11.setVisibility(View.VISIBLE);
                report11.setText(model.getYMCASitAnteriorReport());
            }
        }







    }
}
