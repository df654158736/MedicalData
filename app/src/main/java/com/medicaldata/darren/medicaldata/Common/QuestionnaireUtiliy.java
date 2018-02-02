package com.medicaldata.darren.medicaldata.Common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.medicaldata.darren.medicaldata.R;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * Created by Darren on 2017/8/28.
 */
public class QuestionnaireUtiliy {

    public static LinearLayout MainLayout(Activity activity,String text) {

        LinearLayout layout = new LinearLayout(
                activity);

        LinearLayout.LayoutParams raParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        int top = dip2px(activity, 10);
        int lef = dip2px(activity, 20);
        int rig = dip2px(activity, 20);
        int bot = dip2px(activity, 10);


        layout.setLayoutParams(raParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(lef, top, rig, bot);

        TextView tv = new TextView(activity);
        tv.setText(text);
        tv.setTextSize(26f);
        tv.setTextColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams tv_desParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        tv_desParams.topMargin = dip2px(activity, 10);
        tv_desParams.bottomMargin = dip2px(activity, 10);

        TextView tv_des = new TextView(activity);
        if(text.equals(Res.question_list_title.get(0))){
            tv_des.setText("请完成下列所有问题");
        }else if(text.equals(Res.question_list_title.get(1))){
            tv_des.setText("请录入您昨晚的睡眠时间");
        }else if(text.equals(Res.question_list_title.get(2))){
            tv_des.setText("请录入您体适能数据");
        }

        tv_des.setLayoutParams(tv_desParams);
        tv_des.setTextColor(Color.parseColor("#000000"));


        layout.addView(tv);
        layout.addView(tv_des);




        return layout;
    }


    public static RadioButton[] SingleAnswer(Activity activity, LinearLayout mainlayout, RadioButton rbtn[], LinkedHashMap<String, Object> list) {

        String s = (String) list.get("SelCount");

        RadioGroup radioGroup = new RadioGroup(
                activity);

        LinearLayout.LayoutParams raParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(raParams);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        int l = Integer.valueOf(s);
        rbtn = new RadioButton[l];

        for (int j = 0; j < l; j++) {

            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rbtn[j] = new RadioButton(
                    activity);
            rbtn[j].setLayoutParams(Params);
            String str = (String) list.get(
                    "sel" + String.valueOf(j));
            rbtn[j].setText(str);
            radioGroup.addView(rbtn[j]);
        }
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.weight = 1;

        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(""
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);
        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText("标题: "
                + (String) list.get("Title"));
        title.setPadding(20, 0, 0, 0);

        LinearLayout ln = new LinearLayout(activity);
        ln.setLayoutParams(titleParams);
        ln.setOrientation(LinearLayout.HORIZONTAL);


        danlayout.addView(titles);
        danlayout.addView(title);
        danlayout.addView(radioGroup);
        danlayout.addView(ln);

        mainlayout.addView(danlayout);

        return rbtn;
    }


    public static LinearLayout SingleTextAnswer(Activity activity, LinearLayout mainlayout, RadioButton rbtn[], LinkedHashMap<String, Object> list) {

        String s = (String) list.get("SelCount");
        RadioGroup radioGroup = new RadioGroup(
                activity);

        LinearLayout.LayoutParams raParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(raParams);
        radioGroup
                .setOrientation(LinearLayout.VERTICAL);
        final int l = Integer.valueOf(s);
        rbtn = new RadioButton[l];
        Log.i("values", "ctype" + l);


        final EditText et = new EditText(activity);
        et.setLayoutParams(titleParams);
        et.setVisibility(View.GONE);

        for (int j = 0; j < l; j++) {

            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rbtn[j] = new RadioButton(
                    activity);
            rbtn[j].setLayoutParams(Params);
            String str = (String) list.get(
                    "sel" + String.valueOf(j));
            rbtn[j].setText(str);
            rbtn[j].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    et.setVisibility(View.GONE);
                }
            });
            radioGroup.addView(rbtn[j]);

        }

        Button btn = new Button(activity);
        btn.setLayoutParams(btnParams);
        btn.setText("其他");

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et.setVisibility(View.VISIBLE);
                for (int n = 0; n < l; n++) {
//                    rbtn[n].setChecked(false);
                }
            }
        });
        btnParams.weight = 1;

        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText("单选综合题 "
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);
        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText("标题: "
                + (String) list.get("Title"));
        title.setPadding(20, 0, 0, 0);

        LinearLayout ln = new LinearLayout(activity);
        ln.setLayoutParams(titleParams);
        ln.setOrientation(LinearLayout.HORIZONTAL);


        danlayout.addView(titles);
        danlayout.addView(title);
        danlayout.addView(radioGroup);
        danlayout.addView(btn);
        danlayout.addView(et);
        danlayout.addView(ln);

        mainlayout.addView(danlayout);
        //layout.addView(scroll);
        return mainlayout;
    }


    public static CheckBox[] MultipleAnswer(final Activity activity, LinearLayout mainlayout, CheckBox checkBo[], LinkedHashMap<String, Object> item) {

        String s = (String) item.get("SelCount");
        String unit = (String) item.get("Unit");
        int l = Integer.valueOf(s);

        LinearLayout.LayoutParams raParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout radlayout = new LinearLayout(
                activity);
        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(raParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);
        radlayout.setLayoutParams(raParams);
        radlayout.setOrientation(LinearLayout.VERTICAL);

        checkBo = new CheckBox[l];
        for (int j = 0; j < l; j++) {

            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            Params.gravity = Gravity.CENTER_VERTICAL;
            checkBo[j] = new CheckBox(
                    activity);
            checkBo[j].setLayoutParams(Params);
            String str = (String) item.get(
                    "sel" + String.valueOf(j));
            Log.i("values", "msg" + str);
            checkBo[j].setText(str);

            final int count = j;

//            checkBo[j].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        Toast.makeText(activity, count+"+1", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(activity, count+"-1", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            radlayout.addView(checkBo[j]);

        }

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.weight = 1;
        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText("标题: "
                + (String) item.get("Title"));
        title.setPadding(20, 0, 0, 0);
        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(unit);
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);

        LinearLayout ln = new LinearLayout(activity);
        ln.setLayoutParams(titleParams);
        ln.setOrientation(LinearLayout.HORIZONTAL);


        danlayout.addView(titles);
        danlayout.addView(title);
        danlayout.addView(radlayout);

        danlayout.addView(ln);
        mainlayout.addView(danlayout);
        return checkBo;
    }


    public static LinearLayout MultipleTextAnswer(Activity activity, LinearLayout mainlayout, CheckBox checkBo[], LinkedHashMap<String, Object> list) {

        String s = (String) list.get("SelCount");
        int l = Integer.valueOf(s);
        LinearLayout.LayoutParams raParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout radlayout = new LinearLayout(
                activity);
        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(raParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);
        radlayout.setLayoutParams(raParams);
        radlayout.setOrientation(LinearLayout.VERTICAL);
        checkBo = new CheckBox[l];

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.weight = 1;
        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);

        Button btn = new Button(activity);
        btn.setLayoutParams(btnParams);
        btn.setText("其他");

        final EditText et = new EditText(activity);
        et.setLayoutParams(titleParams);
        et.setVisibility(View.GONE);

        for (int j = 0; j < l; j++) {

            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            checkBo[j] = new CheckBox(
                    activity);
            checkBo[j].setLayoutParams(Params);
            String str = (String) list.get(
                    "sel" + String.valueOf(j));
            Log.i("values", "msg" + str);
            checkBo[j].setText(str);

            radlayout.addView(checkBo[j]);


        }

        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText("标题: "
                + (String) list.get("Title"));
        title.setPadding(20, 0, 0, 0);
        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText("多选综合题 "
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);

        LinearLayout ln = new LinearLayout(activity);
        ln.setLayoutParams(titleParams);
        ln.setOrientation(LinearLayout.HORIZONTAL);

        Button btns = new Button(activity);
        final Button btnx = new Button(activity);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                et.setVisibility(View.VISIBLE);
            }
        });

        danlayout.addView(titles);
        danlayout.addView(title);
        danlayout.addView(radlayout);
        danlayout.addView(btn);
        danlayout.addView(et);
        danlayout.addView(ln);
        mainlayout.addView(danlayout);
        return mainlayout;
    }


    public static LinearLayout TextAnswer(final Activity activity, LinearLayout mainlayout, final TextView textView, LinkedHashMap<String, Object> list) {

        final String questionString = (String) list.get("Title");

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.weight = 1;
        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(titleParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(" "
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);

        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText(questionString);


        LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ln = new LinearLayout(activity);
        ln.setLayoutParams(lnParams);
        ln.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams showTextParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        showTextParams.weight = 2;


        textView.setLayoutParams(showTextParams);
        textView.setText("无");

        LinearLayout.LayoutParams btParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        btParams.weight = 1;

        Button bt = new Button(activity);
        bt.setLayoutParams(btParams);
        bt.setText("填写");

        ln.addView(textView);
        ln.addView(bt);

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout ln = new LinearLayout(activity);
                ln.setLayoutParams(lnParams);
                ln.setOrientation(LinearLayout.VERTICAL);

                final EditText et = new EditText(activity);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);

                final TextView tv = new TextView(activity);
                LinearLayout.LayoutParams TextParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                ln.addView(et);
                ln.addView(tv);


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(questionString).setView(ln)
                        .setNegativeButton("提交", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                try {//下面三句控制弹框的关闭
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);//true表示要关闭

                                    textView.setText(et.getText().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {//下面三句控制弹框的关闭
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);//true表示要关闭
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
            }
        });


        alllayout.addView(titles);
        alllayout.addView(title);
        alllayout.addView(ln);
        danlayout.addView(alllayout);

        mainlayout.addView(danlayout);

        return mainlayout;
    }


    public static LinearLayout TextAnswer1(final Activity activity, LinearLayout mainlayout, final EditText editText, LinkedHashMap<String, Object> list) {

        final String questionString = (String) list.get("Title");

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.weight = 1;
        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(titleParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(" "
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);

        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText(questionString);


        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        editText.setLayoutParams(editTextParams);

        if(questionString.equals("输入12min.跑距离(km)")){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.toString().isEmpty() == false){
                        String range = "此处不能超过10";
                         ;
                        try {
                            float value = Float.valueOf(charSequence.toString());
                            BigDecimal b = new  BigDecimal(value);
                            value = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

                            if(value>10f){
                                editText.setText(String.valueOf(10));
                                Toast toast = Toast.makeText(activity, range, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                            }
                        }catch (Exception e){


                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else{
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }



        alllayout.addView(titles);
        alllayout.addView(title);
        alllayout.addView(editText);
        danlayout.addView(alllayout);

        mainlayout.addView(danlayout);

        return mainlayout;
    }


    public static Button Submit(Activity activity, LinearLayout mainlayout) {

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        btnParams.gravity = Gravity.CENTER;

        Button bt = new Button(activity);
        bt.setLayoutParams(btnParams);
        bt.setText("提交");
        bt.setGravity(Gravity.CENTER);
        bt.setTextSize(26f);
        bt.setId(1000 + 1);


        LinearLayout.LayoutParams danlayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout danlayout = new LinearLayout(activity);

        int top = dip2px(activity, 10);
        int bot = dip2px(activity, 10);
        danlayoutParams.topMargin = top;
        danlayoutParams.bottomMargin = bot;
        danlayout.setLayoutParams(danlayoutParams);
        danlayout.setOrientation(LinearLayout.HORIZONTAL);

        danlayout.addView(bt);
        //layout.addView(scroll);
        mainlayout.addView(danlayout);

        return bt;
    }


    public static LinearLayout Description(Activity activity, LinearLayout mainlayout, String text) {


        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(titleParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(text);
        titles.setTextSize(16f);

        mainlayout.addView(alllayout);

        return mainlayout;
    }


    public static LinearLayout TextDialogAnswer(final Activity activity, LinearLayout mainlayout, LinkedHashMap<String, Object> list) {

        final String questionString = (String) list.get("Title");

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.weight = 1;
        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(titleParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(" "
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(26f);

        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);

        int top = dip2px(activity, 1);
        int lef = dip2px(activity, 0);
        int rig = dip2px(activity, 0);
        int bot = dip2px(activity, 25);

        danlayout.setPadding(lef, top, rig, bot);

        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText(questionString);
        title.setTextSize(16f);

        alllayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout ln = new LinearLayout(activity);
                ln.setLayoutParams(lnParams);
                ln.setOrientation(LinearLayout.VERTICAL);

                final EditText et = new EditText(activity);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);

                final TextView tv = new TextView(activity);
                LinearLayout.LayoutParams TextParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                tv.setLayoutParams(TextParams);

                int top = dip2px(activity, 1);
                int lef = dip2px(activity, 25);
                int rig = dip2px(activity, 20);
                int bot = dip2px(activity, 10);

                tv.setPadding(lef, top, rig, bot);

                ln.addView(et);
                ln.addView(tv);


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(questionString).setView(ln)
                        .setNegativeButton("提交", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                try {//下面三句控制弹框的关闭
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, false);//true表示要关闭

                                    tv.setText("结论：" + Compulate(questionString, et.getText().toString()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {//下面三句控制弹框的关闭
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);//true表示要关闭
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
            }
        });


        //alllayout.addView(titles);
        alllayout.addView(title);
        danlayout.addView(alllayout);

        mainlayout.addView(danlayout);

        return mainlayout;
    }

    @TargetApi(23)
    public static LinearLayout TimeDialogAnswer(final Activity activity, LinearLayout mainlayout, final TextView textView, LinkedHashMap<String, Object> list) {

        final String questionString = (String) list.get("Title");

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.weight = 1;
        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(titleParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(" "
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);

        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);
        title.setText(questionString);


        LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ln = new LinearLayout(activity);
        ln.setLayoutParams(lnParams);
        ln.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams showTextParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        showTextParams.weight = 2;


        textView.setLayoutParams(showTextParams);
        textView.setText("无");

        LinearLayout.LayoutParams btParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        btParams.weight = 1;

        Button bt = new Button(activity);
        bt.setLayoutParams(btParams);
        bt.setText("选择");

        ln.addView(textView);
        ln.addView(bt);

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout ln = new LinearLayout(activity);
                ln.setLayoutParams(lnParams);
                ln.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams TextParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                View view = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.item_timepiker,
                        null);
                final TimePicker mTimePicker = (TimePicker) view.findViewById(R.id.item_time_picker);
//                final TimePicker mTimePicker = new TimePicker(activity);
//                mTimePicker.setIs24HourView(true);
//                mTimePicker.setLayoutParams(TextParams1);
                mTimePicker.setIs24HourView(true);
                ln.addView(view);


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(questionString).setView(ln)
                        .setNegativeButton("提交", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                try {//下面三句控制弹框的关闭
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);//true表示要关闭


                                    String min = mTimePicker.getMinute()+"";

                                    if(min.length() == 1){

                                        min = "0"+min;
                                    }

                                    textView.setText(mTimePicker.getHour()+"："+min);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {//下面三句控制弹框的关闭
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);//true表示要关闭
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
            }
        });


        alllayout.addView(titles);
        alllayout.addView(title);
        alllayout.addView(ln);
        danlayout.addView(alllayout);

        mainlayout.addView(danlayout);

        return mainlayout;
    }


    public static TextView TimeDialogAnswerText(final Activity activity, LinearLayout mainlayout) {

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout alllayout = new LinearLayout(
                activity);
        alllayout.setLayoutParams(titleParams);
        alllayout.setOrientation(LinearLayout.VERTICAL);

        TextView titles = new TextView(activity);
        titles.setLayoutParams(titleParams);
        titles.setText(" "
        );
        titles.setGravity(Gravity.CENTER);
        titles.setTextSize(20f);

        LinearLayout danlayout = new LinearLayout(activity);
        danlayout.setLayoutParams(titleParams);
        danlayout.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(activity);
        title.setLayoutParams(titleParams);

        alllayout.addView(titles);
        alllayout.addView(title);
        danlayout.addView(alllayout);

        mainlayout.addView(danlayout);

        return title;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private static String Compulate(String text, String value) {
        int val = Integer.parseInt(value);
        switch (text) {
            case "题目1：输入体重（kg)":
                float bmi = Math.round((val * 100) / (1.88 * 1.88)) * 0.01f;
                String result = "您的BMI为：" + bmi;

                if (bmi < 18.5) {
                    result += "，属于低体重。";
                } else if (bmi >= 18.5 && bmi < 25) {
                    result += "，属于正常。";
                } else if (bmi >= 25 && bmi < 30) {
                    result += "，属于超重。";
                } else if (bmi >= 30 && bmi < 35) {
                    result += "，属于肥胖Ⅰ。";
                } else if (bmi >= 35 && bmi < 40) {
                    result += "，属于肥胖Ⅱ。";
                } else if (bmi > 40) {
                    result += "，属于肥胖Ⅲ。";
                }
                return result;
            case "题目2：输入腰围（cm)":
                return "";
            case "题目3：输入体脂百分比（%)":
                return "";
            case "题目4：输入12min.跑距离(km)":
                return "";
            case "题目5-1：最大卧推重量(kg)":
                return "";
            default:
                return "";
        }
    }

}
