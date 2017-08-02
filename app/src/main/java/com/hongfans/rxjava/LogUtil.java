package com.hongfans.rxjava;

import android.util.Log;

/**
 * TODO
 * Created by MEI on 2017/7/22.
 */

public class LogUtil {

    private static final String TAG = "elegant";

    public static void i(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        if (stackTrace.length > 3) {
            sb.append("：")
                    .append(stackTrace[3].getMethodName())
                    .append("(")
                    .append(stackTrace[3].getFileName())
                    .append(":")
                    .append(stackTrace[3].getLineNumber())
                    .append(")");

        }
        Log.i(TAG + sb.toString(), msg);
    }
}
