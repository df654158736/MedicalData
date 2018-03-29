package com.medicaldata.darren.medicaldata.Bong;

import android.app.Application;
import android.util.Log;

import cn.ginshell.sdk.BongSdk;
import cn.ginshell.sdk.model.Gender;

public class BongApp extends Application {

    public final static String TAG = BongApp.class.getSimpleName();

    private static BongApp application;


    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        // debug模式
        BongSdk.enableDebug(true);
        // 设置用户身体信息
        BongSdk.setUser(180, 75f, Gender.MALE);
        //初始化sdk
        BongSdk.initSdk(this);


        Log.i(TAG, "sdk version : " + BongSdk.getSdkVersion());

    }

    public static Application getApplication() {
        return application;
    }

}
