package com.project.semicolon.reminder.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.Snackbar;
import com.project.semicolon.reminder.views.Fab;

public class FabBehavior extends CoordinatorLayout.Behavior<Fab> {
    public FabBehavior(Context context, AttributeSet attributeSet) {
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull Fab child, @NonNull View view) {
        return view instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull Fab child, @NonNull View view) {
        if (view instanceof Snackbar.SnackbarLayout) {
            float translationY = Math.min(0, view.getTranslationY() - view.getHeight());
            child.setTranslationY(translationY);
            return true;
        }

        return false;

    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Fab child,
                                       @NonNull View directTargetChild, @NonNull View target,
                                       int axes, int type) {
        return type == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Fab child,
                               @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed, int type, @NonNull int[] consumed) {
        if (dyConsumed > 0) {
            child.hideFab();
        } else if (dyConsumed < 0) {
            child.showFab();
        }
    }
}
