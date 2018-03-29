package com.medicaldata.darren.medicaldata.Bong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ginshell.ble.ParserUtils;
import com.ginshell.ble.x.request.XPerReadRequest;
import com.ginshell.ble.x.request.XPerReadResponse;
import com.ginshell.ble.x.request.XReadRequest;
import com.ginshell.ble.x.request.XReadResponse;
import com.ginshell.ble.x.request.XResponse;
import com.ginshell.ble.x.request.XWriteRequest;
import com.ginshell.sdk.BongManager;
import com.ginshell.sdk.ResultCallback;
import com.ginshell.sdk.command.BatteryCallback;
import com.ginshell.sdk.command.HeartCallback;
import com.ginshell.sdk.command.VersionCallback;
import com.ginshell.sdk.util.AlarmSettings;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.medicaldata.darren.medicaldata.Bong.util.DateUtil;
import com.medicaldata.darren.medicaldata.R;

import cn.ginshell.sdk.BongSdk;
import cn.ginshell.sdk.CommandApi;
import cn.ginshell.sdk.db.DBCurve;
import cn.ginshell.sdk.db.DBHeart;
import cn.ginshell.sdk.model.BongBlock;
import cn.ginshell.sdk.model.Gender;
import cn.ginshell.sdk.model.IntervalSum;
import cn.ginshell.sdk.model.SportType;
import cn.ginshell.sdk.model.Sum;

/**
 * @author hackill
 * @date 2017/2/14
 * bongSDK demo
 * github https://github.com/Ginshell/bongSDK-android
 */
public class BongActivity extends AppCompatActivity {
    private static final String TAG = "BongActivity";


    private BongManager mBongManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bong);
        ButterKnife.bind(this);

        mBongManager = MemoryCache.mBong.fetchBongManager();

        sport_start_time = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()); //(int) (System.currentTimeMillis() / 1000);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 点亮/震动手环的命令
     */
    @OnClick(R.id.vibrate)
    public void vibrate() {

        /**
         * 命令 bong 系列产品 通用
         */
        mBongManager.vibrateBong(new ResultCallbackX());
    }

//    @OnClick(R.id.show_bind)
//    public void show_bind() {
//        /**
//         * bong 3 以及新版支持
//         */
//        mBongManager.showBindStart(new ResultCallbackX());
//    }
//
//    @OnClick(R.id.show_success)
//    public void show_success() {
//        /**
//         * bong 3 以及新版支持
//         */
//        mBongManager.showBindSuccess(new ResultCallbackX());
//    }
//
//    @OnClick(R.id.recovery)
//    public void recovery() {
//        /**
//         * 命令 bong 系列产品 通用
//         */
//        mBongManager.recovery(new ResultCallbackX());
//    }
//
//    @OnClick(R.id.sync_time)
//    public void sync_time() {
//        /**
//         * 命令 bong 系列产品 通用
//         */
//        mBongManager.syncBongTime(new ResultCallbackX());
//    }
//
//
//    @OnClick(R.id.read_battery)
//    public void read_battery(final View view) {
//        /**
//         * 命令 bong 系列产品 通用
//         */
//        mBongManager.readBattery(new BatteryCallback() {
//            @Override
//            public void onReadBatter(int remain) {
//                Log.d(TAG, "onReadBatter() called with: remain = [" + remain + "]");
//                if (view instanceof TextView) {
//                    ((TextView) view).setText("读取电量：" + remain);
//                }
//            }
//
//            @Override
//            public void finished() {
//                Log.d(TAG, "finished() called");
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.d(TAG, "onError() called with: t = [" + t + "]");
//            }
//        });
//    }
//
//    @OnClick(R.id.read_version)
//    public void read_version(final View view) {
//        /**
//         * 命令 bong 系列产品 通用
//         */
//        mBongManager.readVersion(new VersionCallback() {
//            @Override
//            public void onReadVersion(String version) {
//                Log.d(TAG, "onReadVersion() called with: version = [" + version + "]");
//                if (view instanceof TextView) {
//                    ((TextView) view).setText("读取版本：" + version);
//                }
//            }
//
//            @Override
//            public void finished() {
//                Log.d(TAG, "finished() called");
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.d(TAG, "onError() called with: t = [" + t + "]");
//            }
//        });
//    }
//
//    @OnClick(R.id.set_alarm)
//    public void set_alarm() {
//
//        /**
//         * 命令 bong 系列产品 通用
//         */
//        List<AlarmSettings> alarmSettingses = new ArrayList<>();
//
//        AlarmSettings as = new AlarmSettings();
//
//
////     * AlarmSettings 属性如下
////     * private boolean isOn; 是否开启闹钟
////                * private int index; 闹钟id 1-5
////                * private int remindBefore; 浅睡眠提醒 (分钟)
////                * private Integer time;  闹钟时间 (分钟)
////                * private boolean day1On; 周一到周日 是否开 true - > 开 false -> 关
////                * private boolean day2On;
////                * private boolean day3On;
////                * private boolean day4On;
////                * private boolean day5On;
////                * private boolean day6On;
////                * private boolean day7On;
////                * private int lazyMode;//0：关闭 1:开启 懒人模式 (2s 设置无意义,但这个字段需要)
//
//        as.setIndex(0);
//        as.setOn(true);
//        as.setRemindBefore(0);
//        as.setTime((int) TimeUnit.HOURS.toMinutes(9)); // 早上九点
//
//        as.setDay1On(true);
//        as.setDay2On(true);
//        as.setDay3On(true);
//        as.setDay4On(true);
//        as.setDay5On(true);
//        as.setDay6On(true);
//        as.setDay7On(true);
//        as.setLazyMode(0);
//
//        alarmSettingses.add(as);
//        /**
//         * 闹钟设置 全量更新，修改任何一个闹钟，都需要把所有的闹钟信息发过去
//         */
//        mBongManager.setAlarms(alarmSettingses, new ResultCallbackX());
//    }

    /**
     * setUserInfo 结构
     * gender   MALE, FEMALE
     * cmHeight 180cm
     * weight   65.0 kg
     * birthday 1991
     */
//    @OnClick(R.id.set_user_info)
//    public void set_user_info() {
//        /**
//         * 命令 bong 系列产品 通用
//         */
//        mBongManager.setUserInfo(Gender.MALE, 180, 75f, 1991, new ResultCallbackX());
//    }
//
//    @OnClick(R.id.set_more)
//    public void set_more() {
//        startActivity(new Intent(this, CommandActivity.class));
//    }
//
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
//
//    @OnClick(R.id.sync2)
//    public void sync2(final View view) {
//
//        if (view instanceof TextView) {
//            ((TextView) view).setText("正在同步...请稍后");
//        }
//
//        /**
//         * 支持设定同步开始时间的同步
//         * 1、同步的起始时间 可设定 非智能同步的首次同步三个小时
//         * 2、若同步的时间 早于增量同步的时间 扔使用增量同步的时间 比如 上次同步到12：00  若 你设定从9：00开始同步 则扔从12：00开始
//         */
//
//        long startTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(6);
//
//        /**
//         * 获取最近6个小时的同步
//         */
//        mBongManager.sync(startTime / 1000, new ResultCallback() {
//            @Override
//            public void finished() {
//                Log.d(TAG, "syncAndLogBlock finished() called");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast("同步完毕，可以获取数据了");
//                        if (view instanceof TextView) {
//                            ((TextView) view).setText("同步成功");
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.e(TAG, "onError: ", t);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (view instanceof TextView) {
//                            ((TextView) view).setText("同步失败");
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//
    @OnClick(R.id.sync)
    public void sync(final View view) {

        if (view instanceof TextView) {
            ((TextView) view).setText("正在同步...请稍后");
        }

        /**
         * 智能同步，
         * 1、无数据或者许久未同步数据，则同步最近3天的数据 同步时间在1分钟左右
         * 2、后续同步 接着上次同步的点开始同步 时间约2-10秒钟，与距离上次同步时间成正比
         */
        mBongManager.syncAuto(new ResultCallback() {
            @Override
            public void finished() {
                Log.d(TAG, "syncAndLogBlock finished() called");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("同步完毕，可以获取数据了");
                        if (view instanceof TextView) {
                            ((TextView) view).setText("同步成功");
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ", t);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (view instanceof TextView) {
                            ((TextView) view).setText("同步失败");
                        }
                    }
                });
            }
        });
    }
//
//    /**
//     * BongBlock结构说明
//     * private long start_time = 0L; //时间戳 单位（秒）
//     * private long end_time = 0L; // 时间戳 单位（秒）
//     * private int present_type = -1; //类型
//     * private float energy = 0f; // 能量 单位（大卡）
//     * private int steps = 0; // 步数 单位（步）
//     * private int distance = 0; //距离 单位 (米)
//     * private int swings = 0; //  (单位：划臂次数)
//     * private int cycles = 0; // (单位：绕泳池圈数)
//     * private SportType sportType;  具体sporttype说明 见github
//     */
//    @OnClick(R.id.fetch_activity)
//    public void fetch_activity() {
//
//        //获取最近6个小时的活动数据
//        long end = System.currentTimeMillis() / 1000;
//        long start = end - TimeUnit.HOURS.toSeconds(6);
//
//
//        List<BongBlock> bongBlockListByTime = mBongManager.fetchActivity(start, end);
//
//        if (bongBlockListByTime != null) {
//            showToast("获取到 【" + bongBlockListByTime.size() + " 】条活动数据");
//
//            for (BongBlock block : bongBlockListByTime) {
//                Log.i(TAG, "fetch_activity: block " + block);
//            }
//        } else {
//            showToast("无数据，请检查是否同步过");
//        }
//    }
//
//    /**
//     * DBHeart结构说明
//     * private Long timestamp; //秒
//     * private Short heart; //心率值
//     * private Boolean manual; //是否自动测量r
//     */
//    @OnClick(R.id.fetch_heart_rate)
//    public void fetch_heart_rate() {
//        //获取最近6个小时的心率数据
//        long end = System.currentTimeMillis() / 1000;
//        long start = end - TimeUnit.HOURS.toSeconds(6);
//
//        List<DBHeart> dbHeartList = mBongManager.fetchHeart(start, end);
//
//        showToast("获取到 【" + dbHeartList.size() + " 】条心率数据");
//
//        for (DBHeart heart : dbHeartList) {
//            Log.i(TAG, "fetch_heart_rate: heart " + heart);
//        }
//    }
//
//    /**
//     * DBCurve数据说明
//     * <p>
//     * private Long id;
//     * private Float energy; // 大卡
//     * private Integer swings; // (单位：游泳挥臂次数) 无意义
//     * private Integer steps;// number of steps  (单位：步数)
//     * private Long time; //秒
//     */
//    @OnClick(R.id.fetch_detail)
//    public void fetch_fetch_detail() {
//
//        //获取最近6个小时的detail数据
//        long end = System.currentTimeMillis() / 1000;
//        long start = end - TimeUnit.HOURS.toSeconds(36);
//        List<DBCurve> dbCurveList = mBongManager.fetchCurve(start, end);
//
//        showToast("获取到 【" + dbCurveList.size() + " 】条detail数据");
//        for (DBCurve curve : dbCurveList) {
//            Log.i(TAG, "fetch_fetch_detail: curve " + curve);
//        }
//
//    }

    /**
     * sum 结构说明
     * private float calories; //千焦
     * private int step; // 步
     * private float distance; //米
     * private long sleepNum; //分钟
     * private int sleepTimes; //次
     * private float sleepQuality = 0; //质量 3.5 4 4.5 等
     */
    @OnClick(R.id.fetch_summary)
    public void fetch_summary() {

        /**
         * 方式1
         */

        long today = System.currentTimeMillis();




//        Sum sum = mBongManager.fetchSum(today);
//        showToast("sum  = " + sum);
//        Log.i(TAG, "fetch_summary: 一天的总结 ： sum =  " + sum);

        /**
         * 方式2
         */
        List<BongBlock> bongBlockListOneDay = mBongManager.fetchActivityOneDay(today);
        if (bongBlockListOneDay != null) {
            for (BongBlock block : bongBlockListOneDay) {
                Log.d(TAG, "fetch_summary: block " + block);
                IntervalSum intervalSum = mBongManager.fetchIntervalSum(block.getStart_time(), block.getEnd_time());
                Log.i(TAG, "fetch_summary: intervalSum  " + intervalSum);
            }
        }
        // 把上述获取到block 进行累加
        Sum sum2 = mBongManager.fetchSum(bongBlockListOneDay);
        Log.i(TAG, "fetch_summary: 一天的总结 ： sum2 =  " + sum2);

        LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ln = new LinearLayout(BongActivity.this    );
        ln.setLayoutParams(lnParams);
        ln.setOrientation(LinearLayout.VERTICAL);


        final TextView tv = new TextView(BongActivity.this);
        LinearLayout.LayoutParams TextParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        tv.setText("  当前卡路里消耗量为："+sum2.getCalories()+"\r\n  行走步数为："+sum2.getStep()+"\r\n  行走距离为："+sum2.getDistance()+"\r\n  睡眠时长为(分钟)："+sum2.getSleepTimeLength());

        ln.addView(tv);


        AlertDialog.Builder builder = new AlertDialog.Builder(BongActivity.this);

        builder.setTitle("数据信息").setView(ln)
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {//下面三句控制弹框的关闭
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);//true表示要关闭

//                            textView.setText(et.getText().toString());
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
        /**
         * 注意 Sum中 calories 单位为千焦
         * BongBlock中 calories 单位为大卡
         */
    }

//    @OnClick(R.id.fetch_day_activity)
//    public void fetch_day_activity() {
//
//        long today = System.currentTimeMillis(); //只要是今天所在的时间
//        long yesterday = today - TimeUnit.DAYS.toMillis(1);
//
//        List<BongBlock> bongBlockListToday = mBongManager.fetchActivityOneDay(today);
//        List<BongBlock> bongBlockListYesterday = mBongManager.fetchActivityOneDay(yesterday);
//
//        if (bongBlockListToday != null && bongBlockListYesterday != null) {
//            showToast("获取到 【" + sdf.format(new Date(today)) + bongBlockListToday.size() + "条】 运动数据,【" + sdf.format(new Date(yesterday)) + bongBlockListYesterday.size() + "条 】运动数据");
//        } else {
//            Log.i(TAG, "fetch_day_activity: bongBlockListToday =  " + bongBlockListToday + ", bongBlockListYesterday = " + bongBlockListYesterday);
//        }
//
//        if (bongBlockListToday != null) {
//            for (BongBlock block : bongBlockListToday) {
//                Log.i(TAG, "today  " + block);
//            }
//        }
//
//        if (bongBlockListYesterday != null) {
//            for (BongBlock block : bongBlockListYesterday) {
//                Log.i(TAG, "Yesterday " + block);
//            }
//        }
//    }
//
//    @OnClick(R.id.clear)
//    public void clear() {
//        //更换设备时，需要清空一下上个设备的数据
//        BongSdk.clearDatabase();
//    }
//
//    @OnClick(R.id.dfu)
//    public void dfu() {
//        showToast("观看 github上 固件升级的说明");
//    }


    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd日,", Locale.getDefault());


//    @OnClick(R.id.start_sport)
//    public void start_sport() {
//
//        Log.d(TAG, "start_sport() called");
//        //app 主动测量心率  需要开始运动状态
//        mBongManager.setStartSportModel(new ResultCallbackX());
//    }
//
//    @OnClick(R.id.read_heart)
//    public void readHeart() {
//        /**
//         * interval 测量间隔 单位 秒
//         *
//         * onReadBatter(int) 返回出0 代表还未测量出结果 该回调持续输出 直到 执行stop_sport
//         */
//        mBongManager.readHeartValue(30, new HeartCallback() {
//            @Override
//            public void onReadBatter(final int value) {
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast("当前读取心率值：" + value);
//                    }
//                });
//
//            }
//
//            @Override
//            public void finished() {
//                Log.d(TAG, "finished() called");
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.d(TAG, "onError() called with: t = [" + t + "]");
//            }
//        });
//
//    }
//
//    @OnClick(R.id.stop_sport)
//    public void stop_sport() {
//        Log.d(TAG, "stop_sport() called");
//        //app 主动测量心率结束  需要关闭运动状态
//        mBongManager.setStopSportModel(new ResultCallbackX());
//    }
//
//
      private int sport_start_time;
//
//    @OnClick(R.id.start_sport_model)
//    public void start_sport_model() {
//        Log.i(TAG, "start_sport_model: 开启运动模式 ....start2 = " + sport_start_time + "， time = " + DateUtil.formatDateDefault(System.currentTimeMillis()));
//        /**
//         *
//         *  bong 3 以及 bong 4 支持 该命令 。早期产品不支持。
//         *         该模式下，可以测量心率
//         *
//         * 参数1： 运动类型
//         *        Sport.GpsRun   开启运动 跑步
//         *        Sport.GpsCycle  开启运动 骑行
//         *        Sport.Fitness 开启运动 健身
//         * 参数2： 运动开始时间
//         */
//        mBongManager.setStartSportModel(SportType.GpsRun, sport_start_time, new ResultCallbackX());
//    }
//
//
//    @OnClick(R.id.read_heart_model)
//    public void readHeartModel() {
//
//        /**
//         * 运动模式下 读取心率间隔 比较短 ，与无界面的心率不同
//         *
//         * interval 测量间隔 单位 秒
//         * onReadBatter(int) 返回出0 代表还未测量出结果 该回调持续输出 直到 执行stop_sport
//         */
//        Log.i(TAG, "readHeartModel: 开始读心率 " + DateUtil.formatDateDefault(System.currentTimeMillis()));
//        mBongManager.readHeartValue(20, new HeartCallback() {
//            @Override
//            public void onReadBatter(final int value) {
//
//                Log.i(TAG, "onReadBatter: " + DateUtil.formatDateDefault(System.currentTimeMillis()) + "， 心率值 " + value);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast("当前读取心率值：" + value);
//                    }
//                });
//            }
//
//            @Override
//            public void finished() {
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//                Log.e(TAG, "onError: ", t);
//            }
//        });
//
//
//    }
//
//    @OnClick(R.id.stop_sport_model)
//    public void stop_sport_model() {
//        Log.i(TAG, "stop_sport_model: ...sport_start_time = " + new Date(sport_start_time * 1000));
//        // 关闭运动
//        mBongManager.setStopSportModel(SportType.GpsRun, sport_start_time, new ResultCallbackX());
//    }
//
//    //单位 米
//    int distance = 100;
//    //单位 cm/s
//    int speed = 0;
//
//    @OnClick(R.id.send_sport_status)
//    public void send_sport_status() {
//
//        /**
//         * 此命令为 更新距离到手环显示，主要针对 需求 使用GPS 跑步，将GPS跑步的距离回传给手环
//         * 若不传这个命令，手环则默认使用步数计算出来的距离
//         */
//
//
//        //speed 为速度，bong 4需要传，bong 3 不需要传，可以为0.
//        mBongManager.sendSportModelStatus(SportType.GpsRun, sport_start_time, distance, speed, new ResultCallbackX());
//
//        distance += 10;
//
//        speed = new Random().nextInt(100);
//    }
//
//
//    /**
//     * 用于 以上功能不满足需求，需要使用自定义命令
//     * 自定义命令的指令 需要看具体的文档 来看如何解析和指令含义
//     * <p>
//     * 命令总共有三种类型：
//     * 类型一： 发送命令 无返回内容  如震动命令
//     * 类型二：发送命令 有一条返回结果  如读取电量
//     * 类型三：发送命令 有多条返回结果 如同步数据
//     * <p>
//     * 这里demo只展示如何使用，具体byte不做解析,需要看文档自行解析 文档请联系github联系 或者其他途径
//     * bongManager & bong 未提供的接口 可以自定义，如上面的类型一的案例 其实可以用 mBongManager.vibrateBong(new ResultCallbackX());来震动，并不需要自定义。毕竟自定义很麻烦
//     */
    public void demo() {

        byte[] cmd = CommandApi.encodeVibrate(2);
        //类型一  案例：震动两次
        MemoryCache.mBong.getBleManager().addRequest(new XWriteRequest(cmd, new XResponse() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onCommandSuccess() {

            }
        }));

        byte[] cmd2 = CommandApi.readBatteryCommand();
        //类型二 案例：读取电量
        MemoryCache.mBong.getBleManager().addRequest(new XPerReadRequest(cmd2, new XPerReadResponse() {
            @Override
            public void onReceive(byte[] bytes) {
                //解析回调结果 具体如何解析 请查看指令文档
                int bu;
                if (bytes != null && bytes.length > 10) {
                    bu = (bytes[10] & 0x000000ff);
                } else {
                    bu = -1;
                }

                Log.i(TAG, "电量值为：" + bu);
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onCommandSuccess() {

            }
        }));

        long endTime = System.currentTimeMillis();
        long startTime = endTime - TimeUnit.HOURS.toMillis(6);
        byte[] cmd3 = CommandApi.syncSportDataByTimeRead(startTime, endTime);
        //类型三  案例：同步最近6小时数据
        MemoryCache.mBong.getBleManager().addRequest(new XReadRequest(cmd3, new XReadResponse() {
            @Override
            public void onReceive(List<byte[]> list) {
                //总结果 byte list
            }

            @Override
            public void onReceivePerFrame(byte[] bytes) {
                //每条 byte

            }

            @Override
            public void onError(Exception e) {
                //出错

            }

            @Override
            public void onCommandSuccess() {

            }
        }));
    }


//    @OnClick(R.id.test)
//    public void onTest() {
//        String cmdCode = "FE01270017C8BFE54429175196E91E3B118A580F";
//        String cmdCode2 = "ACD9C07427B2E6830280";
//
//        byte[] cmd1 = hexStringToBytes(cmdCode);
//        byte[] cmd2 = hexStringToBytes(cmdCode2);
//
//        byte[][] cmd = {cmd1, cmd2};
//
//        Log.i(TAG, "onTest: cmd " + ParserUtils.parse(cmd[0]) + "\n , cmd 2= " + ParserUtils.parse(cmd[1]));
//
//        if (mBongManager != null) {
//
//            MemoryCache.mBong.getBleManager().addRequest(new XPerReadRequest(cmd, new XPerReadResponse() {
//                @Override
//                public void onReceive(byte[] rsp) {
//                    Log.i(TAG, "onReceive: ... " + ParserUtils.parse(rsp));
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    Log.e(TAG, "onError: ", e);
//
//                }
//
//                @Override
//                public void onCommandSuccess() {
//
//                    Log.d(TAG, "onCommandSuccess() called");
//                }
//            }));
//        }
//
//    }


    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((byte) "0123456789ABCDEF".indexOf(hexChars[pos]) << 4 | (byte) "0123456789ABCDEF"
                    .indexOf(hexChars[pos + 1]));
        }
        return d;
    }

//    /**
//     * 智能同步，可选择是否回传数据
//     * 第一个参数传入true代表回传数据，false代表不回传
//     *
//     * @param view
//     */
//    @OnClick(R.id.syncUpdate)
//    public void syncUpdate(final View view) {
//
//        if (view instanceof TextView) {
//            ((TextView) view).setText("正在同步...请稍后");
//        }
//
//        /**
//         * 智能同步，
//         * 1、无数据或者许久未同步数据，则同步最近3天的数据 同步时间在1分钟左右
//         * 2、后续同步 接着上次同步的点开始同步 时间约2-10秒钟，与距离上次同步时间成正比
//         */
//        mBongManager.syncAutoUpdate(false, new ResultCallback() {
//            @Override
//            public void finished() {
//                Log.d(TAG, "syncAndLogBlock finished() called");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast("同步完毕，可以获取数据了");
//                        if (view instanceof TextView) {
//                            ((TextView) view).setText("同步成功");
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.e(TAG, "onError: ", t);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (view instanceof TextView) {
//                            ((TextView) view).setText("同步失败");
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 支持设定同步开始时间的同步，可选择是否回传数据
//     * 第二个参数传入true代表回传数据，false代表不回传
//     *
//     * @param view
//     */
//    @OnClick(R.id.sync2Update)
//    public void sync2Update(final View view) {
//
//        if (view instanceof TextView) {
//            ((TextView) view).setText("正在同步...请稍后");
//        }
//
//        /**
//         * 支持设定同步开始时间的同步
//         * 1、同步的起始时间 可设定 非智能同步的首次同步三个小时
//         * 2、若同步的时间 早于增量同步的时间 扔使用增量同步的时间 比如 上次同步到12：00  若 你设定从9：00开始同步 则扔从12：00开始
//         */
//
//        long startTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(6);
//
//        /**
//         * 获取最近6个小时的同步
//         */
//        mBongManager.syncUpdate(startTime / 1000, false, new ResultCallback() {
//            @Override
//            public void finished() {
//                Log.d(TAG, "syncAndLogBlock finished() called");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast("同步完毕，可以获取数据了");
//                        if (view instanceof TextView) {
//                            ((TextView) view).setText("同步成功");
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.e(TAG, "onError: ", t);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (view instanceof TextView) {
//                            ((TextView) view).setText("同步失败");
//                        }
//                    }
//                });
//            }
//        });
//    }

}
