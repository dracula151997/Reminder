package com.project.semicolon.reminder.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;

public class AnimatorUtil {
    private Context context;
    private View view;
    private int delay = 0;
    private int startVisibility = View.VISIBLE;
    private int endVisibility = View.VISIBLE;
    private boolean clear = true;
    private AnimatorListener listener;

    private AnimatorUtil(Context context) {
        this.context = context;
    }

    public static AnimatorUtil create(Context context) {
        return new AnimatorUtil(context);
    }

    public <T extends View> AnimatorUtil on(T view) {
        this.view = view;
        return this;
    }

    public AnimatorUtil delay(int delay) {
        this.delay = delay;
        return this;
    }

    public AnimatorUtil clear(boolean clear) {
        this.clear = clear;
        return this;
    }

    public AnimatorUtil startVisibility(int startVisibility) {
        this.startVisibility = startVisibility;
        return this;
    }

    public AnimatorUtil endVisibility(int endVisibility) {
        this.endVisibility = endVisibility;
        return this;

    }

    public AnimatorUtil listener(AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public void animate(@AnimRes int animRes) {
        Animation animation = AnimationUtils.loadAnimation(context, animRes);
        if (delay > 0) animation.setStartOffset(delay);

        animation.setAnimationListener(new Animation.AnimationListener() {
            boolean endStatus = false;

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!endStatus) {
                    endStatus = true;
                    if (clear) view.clearAnimation();
                    view.setVisibility(endVisibility);
                    if (listener != null) listener.onAnimate();
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setVisibility(startVisibility);
        view.startAnimation(animation);
    }

    public interface AnimatorListener {
        void onAnimate();
    }


}
