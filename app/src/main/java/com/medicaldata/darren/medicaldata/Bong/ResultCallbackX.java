package com.medicaldata.darren.medicaldata.Bong;

import android.util.Log;
import android.widget.Toast;

import com.ginshell.sdk.ResultCallback;


public class ResultCallbackX implements ResultCallback {
    private static final String TAG = "ResultCallbackX";

    @Override
    public void finished() {
        Log.d(TAG, "finished() called");
        showToast("finished");
    }

    @Override
    public void onError(Throwable t) {
        Log.e(TAG, "onError: ", t);
        showToast("error:" + t.getMessage());
    }

    private void showToast(String msg) {
//       Toast.makeText(BongApp.getApplication(), msg, Toast.LENGTH_SHORT).show();
    }
}
