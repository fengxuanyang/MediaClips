package com.android.fxy.simplemediaclips.data.commom;

import android.util.Log;

public class LogUtils {
    private static final String MAIN_TAG = "Mediaclips";

    public static void d(String tag, String msg) {
        Log.d(MAIN_TAG, tag + ":  " + msg);
    }

    public static void d(String msg) {
        Log.d(MAIN_TAG, MAIN_TAG + ":  " + msg);
    }

    public static void i(String tag, String msg) {
        Log.i(MAIN_TAG, tag + ":  " + msg);
    }

    public static void e(String msg) {
        Log.d(MAIN_TAG, MAIN_TAG + ":  " + msg);
    }

    public static void e(String tag, String msg) {
        Log.e(MAIN_TAG, tag + ":  " + msg);
    }

    public static void i(String msg) {
        Log.d(MAIN_TAG, MAIN_TAG + ":  " + msg);
    }

}
