package com.medicaldata.darren.medicaldata.Bong;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ginshell.ble.BLEInitCallback;
import com.ginshell.ble.GattState;
import com.ginshell.sdk.Bong;
import com.ginshell.sdk.Device;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ginshell.sdk.BongSdk;
import cn.ginshell.sdk.model.Gender;

import com.medicaldata.darren.medicaldata.Bong.util.ToastUtils;
import com.medicaldata.darren.medicaldata.R;

/**
 * Created by hackill on 2017/11/7.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @BindView(R.id.select_ble)
    Button selectBle;

    @BindView(R.id.connect)
    Button connectBle;


    @BindView(R.id.device_info)
    TextView deviceInfo;
    @BindView(R.id.ll_command)
    LinearLayout llCommand;


    private String mCurMac;
    private Bong mBong;
    private Device mDevice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_bong);

        if(!BongSdk.isIsInitialized()){
            BongSdk.enableDebug(true);
            // 设置用户身体信息
            BongSdk.setUser(180, 75f, Gender.MALE);
            //初始化sdk
            BongSdk.initSdk(getApplication());
        }


        ButterKnife.bind(this);

        checkBlePermission();

        mBong = new Bong(getApplication());

        Log.i(TAG, "onCreate: ");

        MemoryCache.mBong = mBong;

        enableCommandView(false);

        LocalBroadcastManager.getInstance(this).registerReceiver(mBleStateReceiver, new IntentFilter(GattState.BLE_STATE_CHANGE));
    }

    /**
     * 连接状态会以广播的形式通知出来
     * 蓝牙连接state
     */
    private BroadcastReceiver mBleStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra("state");
            Log.i(TAG, "onReceive: state = " + state);

            deviceInfo.setText("设备:[" + mDevice.toString() + "]Mac:[" + mCurMac + "] \n 状态:[" + state + "]");

            if (TextUtils.equals(state, "CONNECTED")) {
                enableCommandView(true);
            } else {
                enableCommandView(false);
            }
        }
    };

    @OnClick(R.id.select_ble)
    public void selectDevice() {
        if (mBong.isConnected()) {
            mBong.disconnect();
        }

        Intent intent = new Intent(this, SelectActivity.class);
        startActivityForResult(intent, 10);

    }

    @OnClick(R.id.connect)
    public void connectDevice() {
        if (TextUtils.isEmpty(mCurMac)) {
            ToastUtils.showToast(this, "先选择设备");
            return;
        }

        /**
         * 需要告知 Bong对象，接下来的即将连接的设备，可只设置一次
         */
        mBong.setDevice(mDevice);


        /**
         * 连接操作，连接成功后，这里会正常回调。还有一个广播 （mBleStateReceiver） 进行接收 蓝牙连接各种状态。demo里在监听中处理连接成功后的操作。实际操作中 可在这里进行处理
         *
         * 广播与此次回调的区别：
         * 1、广播可以接收任何时候的断开和连接 以及中间状态
         * 2、此次回调只会处理当前的connect请求
         */
        mBong.connect(mCurMac, new BLEInitCallback() {
            @Override
            public void onSuccess() {
                enableCommandView(true);
            }

            @Override
            public boolean onFailure(int error) {
                return false;
            }
        });
    }

    @OnClick(R.id.disconnect)
    public void disconnectDevice() {
        if (TextUtils.isEmpty(mCurMac)) {
            ToastUtils.showToast(this, "先选择设备");
            return;
        }

        if (mBong.isConnected()) {
            mBong.disconnect();
        }
    }

    @OnClick(R.id.go_to_bong)
    public void go_to_bong() {

        if (mDevice == null || TextUtils.isEmpty(mCurMac)) return;

        if (mDevice == Device.BONG3 || mDevice == Device.BONG4 || mDevice == Device.BONG_VOGUE) {
            Intent intent = new Intent(this, BongActivity.class);
            startActivity(intent);
        } else {
            ToastUtils.showToast(this, "bong 手环支持该功能,当前类型：" + mDevice);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK) {
            mCurMac = data.getStringExtra("ble_mac");
            String bleName = data.getStringExtra("ble_name");


            if (TextUtils.equals(bleName, Device.BONG3.toString())) {
                mDevice = Device.BONG3;
            } else if (TextUtils.equals(bleName, Device.BONG4.toString())) {
                mDevice = Device.BONG4;
            } else if (TextUtils.equals(bleName, Device.BONG_VOGUE.toString())) {
                mDevice = Device.BONG_VOGUE;
            } else if (TextUtils.equals(bleName, Device.FIT.toString())) {
                mDevice = Device.FIT;
            } else if (TextUtils.equals(bleName, Device.FIT2.toString())) {
                mDevice = Device.FIT2;
            } else {
                mDevice = Device.BONG3;
                ToastUtils.showToast(this, "该设备类型不支持");
            }

            deviceInfo.setText("设备:[" + mDevice.toString() + "]Mac:[" + mCurMac + "] \n 请进行连接");

            //去连接
            connectBle.performClick();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBong.isConnected()) {
            mBong.disconnect();
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBleStateReceiver);
    }

    private void enableCommandView(boolean enable) {
        int count = llCommand.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = llCommand.getChildAt(i);
            view.setEnabled(enable);
            enableChildView(view, enable);
        }
    }

    private void enableChildView(View view, boolean enable) {
        if (view instanceof LinearLayout) {
            LinearLayout layout = ((LinearLayout) view);

            int count = layout.getChildCount();
            for (int i = 0; i < count; i++) {
                layout.getChildAt(i).setEnabled(enable);
            }
        }
    }

    /**
     * ==============================检查权限操作start============================
     */
    /**
     * 6.0 判断权限
     */
    private void checkBlePermission() {
        Log.i(TAG, "checkBlePermission : checkAndRequestPermissions() = " + checkBlePermission(this));

        if (checkBlePermission(this)) {
            selectBle.setEnabled(true);
        } else {
            selectBle.setEnabled(false);
        }
    }

    /**
     * 检查权限问题
     *
     * @param context
     * @return
     */
    private boolean checkBlePermission(Context context) {
        int bluetooth = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        int bluetoothAdmin = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN);


        List<String> listPermissionsNeeded = new ArrayList<>();
        if (bluetooth != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.BLUETOOTH);
        }
        if (bluetoothAdmin != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ToastUtils.showToast(this, "请检查蓝牙权限");
            return false;
        }

        int accessFinLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        if (accessFinLocation != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "requestPermissions: ...");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (permissions.length == 1 && TextUtils.equals(permissions[0], Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectBle.setEnabled(true);
            } else {
                // Permission was denied. Display an error message.
                ToastUtils.showToast(this, "6.0后需要该权限");
            }
        }
    }
    /**
     * ==============================检查权限操作end============================
     */
}
