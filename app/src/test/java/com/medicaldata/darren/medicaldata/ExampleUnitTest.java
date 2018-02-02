package com.medicaldata.darren.medicaldata;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.medicaldata.darren.medicaldata.Common.AsyncHttpClientUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        AsyncHttpClientUtils.getInstance().get("values/get", new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                assertEquals(4, 2 + 2);
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                assertEquals(4, 2 + 2);
            }
        });
        assertEquals(4, 2 + 2);

    }
}