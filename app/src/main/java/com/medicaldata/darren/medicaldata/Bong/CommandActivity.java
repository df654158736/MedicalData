package com.medicaldata.darren.medicaldata.Bong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ginshell.sdk.BongManager;
import com.medicaldata.darren.medicaldata.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommandActivity extends AppCompatActivity {
    private static final String TAG = "CommandActivity";

    @BindView(R.id.cb_call)
    CheckBox cbCall;
    @BindView(R.id.cb_sms)
    CheckBox cbSms;
    @BindView(R.id.cb_qq)
    CheckBox cbQq;
    @BindView(R.id.cb_wechat)
    CheckBox cbWechat;


    private BongManager mBongManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        ButterKnife.bind(this);

        mBongManager = MemoryCache.mBong.fetchBongManager();

        initView();
    }


    private void initView() {
        cbCall.setOnCheckedChangeListener(mOnCheckedChange);
        cbSms.setOnCheckedChangeListener(mOnCheckedChange);
        cbQq.setOnCheckedChangeListener(mOnCheckedChange);
        cbWechat.setOnCheckedChangeListener(mOnCheckedChange);

        /**
         * 自动心率测量 开启 会定时 测试你的 心率 默认关闭
         * 1小时测量一次
         */
        ((Switch) findViewById(R.id.sb_smart_heart)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBongManager.setAutoMeasureHeart(isChecked, new ResultCallbackX());
            }
        });

        /**
         * 久坐提醒开关 默认关闭
         *
         *
         * @param enable
         * @param callback
         */
        ((Switch) findViewById(R.id.sb_sit_remind)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //方式一
                mBongManager.setSitReminder(isChecked, new ResultCallbackX());

                //方式二：支持设置提醒时间 以及提醒周期

                List<Boolean> dayon = new ArrayList<>(7);
                dayon.add(Boolean.FALSE);//周日
                dayon.add(Boolean.TRUE); //周一
                dayon.add(Boolean.TRUE);
                dayon.add(Boolean.TRUE);
                dayon.add(Boolean.TRUE);
                dayon.add(Boolean.TRUE);
                dayon.add(Boolean.FALSE); //周六
                /**
                 *
                 * 参数一： 是否开启
                 * 参数二：提醒日期
                 * param 3 : startHour
                 * param 4 : startMin
                 * param 5 : endHour
                 * param 6 : endMin
                 */
                mBongManager.setSitReminder(isChecked, dayon, 8, 30, 22, 45, new ResultCallbackX());

            }
        });


        /**
         * 设置勿扰模式  默认关闭
         *
         * @param mode     勿扰开关 true:开，false:关
         * @param sH       开始 小时
         * @param sM       开始 分钟
         * @param eH       结束 小时
         * @param eM       结束 分钟
         * @param callback callback
         */
        ((Switch) findViewById(R.id.no_disturb_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 22:00 - 8:00 勿扰 届时 抬腕 消息通知 等不会生效
                mBongManager.setNotDisturb(isChecked, 22, 0, 8, 0, new ResultCallbackX());
            }
        });


        findViewById(R.id.btn_bong3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CommandActivity.this, Bong3Activity.class));
            }
        });


        findViewById(R.id.btn_bong4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CommandActivity.this, Bong4Activity.class));
            }
        });

    }


    /**
     * 开启哪些提醒  电话、短信、qq、微信
     */
    CompoundButton.OnCheckedChangeListener mOnCheckedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // 需要开启之后， 后面才能发消息提醒，否则发了 手环也不显示的
            mBongManager.setMessageNotifyEnable(cbCall.isChecked(), cbSms.isChecked(), cbQq.isChecked(), cbWechat.isChecked(), new ResultCallbackX());
        }
    };

    /**
     * 来电提醒
     *
     * @param view
     */
    public void sendIncoming(View view) {
        //  //来电提醒  姓名 + 号码
        mBongManager.sendAddIncomingCallNotify("张三", "1313131231231", new ResultCallbackX());
    }

    /**
     * 取消来电提醒
     *
     * @param view
     */
    public void cancelIncoming(View view) {
        //来电提醒  姓名 + 号码
        mBongManager.sendDelIncomingCallNotify("张三", "1313131231231", new ResultCallbackX());
    }

    public void sendQQMsg(View view) {
        /**
         * @param appName 比如qq 微信
         * @param msg  消息内容
         * @param msgId  消息id
         * @param appId  默认0
         */
        mBongManager.sendAddAppMsg("QQ", "QQ Message From App Test", 31231211, 12312, new ResultCallbackX());
    }

    public void sendWechatMsg(View view) {

        /**
         * @param appName 比如qq 微信
         * @param msg  消息内容
         * @param msgId  消息id
         * @param appId  appId  默认0
         */
        mBongManager.sendAddAppMsg("微信"/*or WeChat*/, "WeChat Message From App Test", 31231211, 12312, new ResultCallbackX());
    }

    public void cancelWechatMsg(View view) {
        /**
         * @param appName 比如qq 微信
         * @param msg  消息内容
         * @param msgId  消息id
         * @param appId appId  默认0
         */

        /**
         * bong 4 不支持取消操作
         */
        mBongManager.sendDelAppMsg(31231211, 12312, new ResultCallbackX());
    }

    public void sendSMS(View view) {
        /**
         * @param appName NDY  表示短信
         * @param msg  消息
         * @param msgId 消息id
         */
        mBongManager.sendAddSms("NDY", "SMS for test", 123, new ResultCallbackX());
    }

    @OnClick(R.id.restart)
    public void restart() {
        //重启bong
        mBongManager.restartBong(new ResultCallbackX());
    }


    @OnClick(R.id.weather)
    public void setWeather() {
        /**
         * 设置天气信息
         * <p>
         * <p>
         * id   |   天气
         * -----|-----------
         * 1	|	晴
         * 2	|	晴
         * 3	|	晴
         * 4	|	晴
         * 5	|	晴
         * 6	|	大部晴朗
         * 7	|	大部晴朗
         * 8	|	多云
         * 9	|	多云
         * 10	|	多云
         * 11	|	多云
         * 12	|	少云
         * 13	|	阴
         * 14	|	阴
         * 15	|	阵雨
         * 16	|	阵雨
         * 17	|	阵雨
         * 18	|	阵雨
         * 19	|	阵雨
         * 20	|	局部阵雨
         * 21	|	局部阵雨
         * 22	|	小阵雨
         * 23	|	强阵雨
         * 24	|	阵雪
         * 25	|	小阵雪
         * 26	|	雾
         * 27	|	雾
         * 28	|	冻雾
         * 29	|	沙尘暴
         * 30	|	浮尘
         * 31	|	尘卷风
         * 32	|	扬沙
         * 33	|	强沙尘暴
         * 34	|	霾
         * 35	|	霾
         * 36	|	阴
         * 37	|	雷阵雨
         * 38	|	雷阵雨
         * 39	|	雷阵雨
         * 40	|	雷阵雨
         * 41	|	雷阵雨
         * 42	|	雷电
         * 43	|	雷暴
         * 44	|	雷阵雨伴有冰雹
         * 45	|	雷阵雨伴有冰雹
         * 46	|	冰雹
         * 47	|	冰针
         * 48	|	冰粒
         * 49	|	雨夹雪
         * 50	|	雨夹雪
         * 51	|	小雨
         * 52	|	小雨
         * 53	|	中雨
         * 54	|	大雨
         * 55	|	暴雨
         * 56	|	大暴雨
         * 57	|	特大暴雨
         * 58	|	小雪
         * 59	|	小雪
         * 60	|	中雪
         * 61	|	中雪
         * 62	|	大雪
         * 63	|	暴雪
         * 64	|	冻雨
         * 65	|	冻雨
         * 66	|	小雨
         * 67	|	中雨
         * 68	|	大雨
         * 69	|	大暴雨
         * 70	|	大暴雨
         * 71	|	小雪
         * 72	|	小雪
         * 73	|	小雪
         * 74	|	大雪
         * 75	|	大雪
         * 76	|	大雪
         * 77	|	雪
         * 78	|	雨
         * 79	|	霾
         * 80	|	多云
         * 82	|	多云
         * 81	|	多云
         * 83	|	雾
         * 84	|	雾
         * 85	|	阴
         * 86	|	阵雨
         * 87	|	雷阵雨
         * 88	|	雷阵雨
         * 89	|	雷阵雨
         * 90	|	雷阵雨
         * 91	|	小到中雨
         * 92	|	中到大雨
         * 93	|	大到暴雨
         * 94	|	小到中雪
         *
         * @param second      时间戳 单位 秒
         * @param weatherCode 天气代码
         * @param temp        温度 摄氏度
         * @param pm25        pm 2.5
         * @param callback    call back
         */
        mBongManager.sendWeatherInfo(System.currentTimeMillis() / 1000, 1, -12, 13, new ResultCallbackX());
    }

    private boolean mLanguage;

    /**
     * 设置语言模式
     * true 中文
     * false 英文
     */
    @OnClick(R.id.set_language)
    public void setLanguage() {
        mLanguage = !mLanguage;
        mBongManager.setLanguage(mLanguage, new ResultCallbackX());
    }

}
