package com.medicaldata.darren.medicaldata.Bong.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by hackill on 2017/11/7.
 */

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(final Activity activity, final String msg) {
        if (activity == null || activity.isFinishing()) return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
                }
                mToast.setText(msg);
                mToast.show();
            }
        });
    }
}
