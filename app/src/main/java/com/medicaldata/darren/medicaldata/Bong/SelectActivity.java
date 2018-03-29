package com.medicaldata.darren.medicaldata.Bong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.ginshell.ble.scan.BleDevice;
import com.ginshell.ble.scan.BleScanCallback;
import com.ginshell.sdk.Bong;
import com.medicaldata.darren.medicaldata.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


/**
 * 搜索蓝牙设备
 * <p>
 * 支持同时搜索多种设备，只需要在搜索前设置filter即可，未设置则默认搜索所有蓝牙设备
 */
public class SelectActivity extends AppCompatActivity {
    private static final String TAG = "SelectActivity";

    private Set<BleDevice> mBleDeviceHashSet = Collections.synchronizedSet(new HashSet<BleDevice>(10));

    private SelectAdapter mSelectAdapter;

    private Bong mBong;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select);

        initView();

        /**
         * 这里用于搜索的 bong对象，用 getBong 或者 getFit都可以 。属于基础功能，每个对象都支持扫描操作
         */
        mBong = MemoryCache.mBong;
        /**
         * 可以设置蓝牙名称过滤
         *
         * bong3 蓝牙名称 -》bong3HR
         * bong4 蓝牙名称 -》bong4
         * bongFit 蓝牙名称 -》bongfit
         *
         * 若同时需要扫描 bong 3 ，bong 4则设置 mBong.filter("bong3HR","bong4"); 其他情况类推
         *
         * 不设置 默认搜索全部蓝牙设备
         */
        mBong.filter("bong4", "SWAN");
//        mBong.filter("bong4","bong3HR");
//        mBong.filter("bongfit");

        mBong.startLeScan(this, new BleScanCallback() {
            @Override
            public void onScanResult(BleDevice device) {
                mBleDeviceHashSet.add(device);
            }

            @Override
            public void onError(Exception e) {

                showToast("请检查蓝牙是否打开");

                Log.e(TAG, "onError: ", e);
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BleDevice[] bleDevices = new BleDevice[mBleDeviceHashSet.size()];
                bleDevices = mBleDeviceHashSet.toArray(bleDevices);
                Arrays.sort(bleDevices, new Comparator<BleDevice>() {
                    @Override
                    public int compare(BleDevice o1, BleDevice o2) {
                        return -o1.rssi + o2.rssi;
                    }
                });

                mSelectAdapter.setBleDevices(bleDevices);

                mHandler.postDelayed(this, 1000);
            }
        }, 500);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mBong != null) {
            mBong.stopLeScan();
        }
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSelectAdapter = new SelectAdapter(new SelectAdapter.OnBleClickListener() {
            @Override
            public void onBleClick(BleDevice device) {
                Intent intent = new Intent();

                intent.putExtra("ble_mac", device.mac);
                intent.putExtra("ble_name", device.name);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(mSelectAdapter);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
