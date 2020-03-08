package com.project.semicolon.reminder.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.project.semicolon.reminder.R;
import com.project.semicolon.reminder.utils.AnimatorUtil;

public class Fab extends AppCompatImageView {
    private boolean isHidden = false;

    public Fab(Context context) {
        super(context);
    }

    public Fab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Fab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showFab() {
        //if fab is hidden
        if (isHidden) {
            //assign false to isHidden variable.
            isHidden = false;
            AnimatorUtil.create(getContext().getApplicationContext())
                    .on(this)
                    .startVisibility(VISIBLE)
                    .animate(R.anim.fab_scroll_in);


        }
    }

    public void hideFab() {
        if (!isHidden) {
            isHidden = true;
            AnimatorUtil.create(getContext().getApplicationContext())
                    .on(this)
                    .startVisibility(GONE)
                    .animate(R.anim.fab_scroll_out);

        }
    }
}
