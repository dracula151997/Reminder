package com.project.semicolon.reminder.utils;

import android.util.Log;

public class Logger {

    private static final String TAG = Logger.class.getSimpleName();

    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }
}
