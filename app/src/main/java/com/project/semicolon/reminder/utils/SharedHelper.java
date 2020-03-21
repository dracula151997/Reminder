package com.project.semicolon.reminder.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.security.Key;

public class SharedHelper {
    private static final String SHARED_NAME = "reminder_shared";
    private static SharedHelper instance = new SharedHelper();
    private SharedPreferences sharedPreferences;

    public static SharedHelper getInstance() {
        return instance;
    }

    public void init(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public void storeString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void getString(String key){
        sharedPreferences.getString(key, "");

    }

    public String getString(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

    public void storeInt(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultValue){
        return sharedPreferences.getInt(key, defaultValue);
    }

}