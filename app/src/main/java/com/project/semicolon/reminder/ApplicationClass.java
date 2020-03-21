package com.project.semicolon.reminder;

import android.app.Application;

import com.project.semicolon.reminder.utils.SharedHelper;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedHelper.getInstance().init(this);
    }
}
