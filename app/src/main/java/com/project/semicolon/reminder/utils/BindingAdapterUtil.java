package com.project.semicolon.reminder.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingAdapterUtil {

    @BindingAdapter(value = "backgroundRes")
    public static void setBackground(View view, int res) {
        view.setBackgroundResource(res);
    }

    @BindingAdapter(value = "imageRes")
    public static void setImageRes(ImageView view, int res) {
        view.setImageResource(res);
    }
}
