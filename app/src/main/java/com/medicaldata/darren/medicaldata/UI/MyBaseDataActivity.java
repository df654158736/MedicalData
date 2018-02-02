package com.medicaldata.darren.medicaldata.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.medicaldata.darren.medicaldata.R;

public class MyBaseDataActivity extends AppCompatActivity {

    int mYear, mMonth, mDay;
    Button btn;
    EditText dateDisplay;
    final int DATE_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base_data);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
        getWindow().setAttributes(p);

        setTitle("请更新您的基础数据");
        btn = (Button) findViewById(R.id.bt_basedata_submit);

//        Button bt_hight = (Button) findViewById(R.id.bt_basedata_hight);
        final TextView et_hight = (EditText) findViewById(R.id.et_basedata_hight);
        dateDisplay = (EditText) findViewById(R.id.et_basedata_dateDisplay);

        Button bt_submit = (Button) findViewById(R.id.bt_submit);
//        bt_hight.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MyBaseDataActivity.this);
//
//                final EditText et = new EditText(MyBaseDataActivity.this);
//                et.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//                builder.setTitle("请输入您的身高").setView(et)
//                        .setNegativeButton("提交", new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                try {//下面三句控制弹框的关闭
//                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
//                                    field.setAccessible(true);
//                                    field.set(dialog, true);//true表示要关闭
//
//                                    tv_hight.setText(et.getText().toString()+" cm");
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        })
//                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                try {//下面三句控制弹框的关闭
//                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
//                                    field.setAccessible(true);
//                                    field.set(dialog, true);//true表示要关闭
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }).show();
//            }
//        });


        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MyBaseDataActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
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
        dateDisplay.setText(new StringBuffer().append(mYear).append("-").append(mMonth+ 1).append("-").append(mDay).append(" "));
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
