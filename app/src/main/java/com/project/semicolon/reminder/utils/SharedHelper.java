package com.project.semicolon.reminder.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class SharedHelper {

    public static void save(Context context, String key, int value) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(contextWeakReference.get());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static Integer get(Context context, String key) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(contextWeakReference.get());
        return preferences.getInt(key, 0);

    }
}