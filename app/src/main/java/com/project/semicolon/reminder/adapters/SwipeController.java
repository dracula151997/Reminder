package com.project.semicolon.reminder.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.project.semicolon.reminder.R;
import com.project.semicolon.reminder.utils.Logger;

public class SwipeController extends ItemTouchHelper.SimpleCallback {

    private final ColorDrawable deleteBackground;
    private final ColorDrawable editBackground;
    private GenericAdapter<com.project.semicolon.reminder.database.entity.Category> genericAdapter;
    private Drawable deleteIcon;
    private Drawable editIcon;
    private Paint paint;
    private OnRecyclerViewSwipedListener onRecyclerViewSwipedListener;


    public SwipeController(Context context, GenericAdapter<com.project.semicolon.reminder.database.entity.Category> genericAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.genericAdapter = genericAdapter;
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep_black_24dp);
        deleteIcon.setTint(Color.WHITE);
        paint = new Paint();
        editIcon = ContextCompat.getDrawable(context, R.drawable.ic_simple_note);
        deleteBackground = new ColorDrawable(Color.parseColor("#FF0800"));
        editBackground = new ColorDrawable(Color.parseColor("#00A572"));

    }

    public void setOnRecyclerViewSwipedListener(OnRecyclerViewSwipedListener onRecyclerViewSwipedListener) {
        this.onRecyclerViewSwipedListener = onRecyclerViewSwipedListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.RIGHT) {
            if (onRecyclerViewSwipedListener != null)
                onRecyclerViewSwipedListener.onDeleteSwiped(viewHolder.getAdapterPosition());
        } else if (direction == ItemTouchHelper.LEFT) {
            if (onRecyclerViewSwipedListener != null)
                onRecyclerViewSwipedListener.onEditSwiped(viewHolder.getAdapterPosition(), viewHolder);
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (viewHolder != null){
                viewHolder.itemView.setElevation(3.0f);
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }

        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float width = viewHolder.itemView.getWidth();
            float height = viewHolder.itemView.getHeight();
            float alpha = 1.0f - Math.abs(dX) / width;
            viewHolder.itemView.setTranslationX(dX);
            viewHolder.itemView.setAlpha(alpha);
        }
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight());
        int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();


        if (dX > 0) {
            //swiping to right
            paint.setColor(Color.parseColor("#FF0800"));
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = iconLeft + deleteIcon.getIntrinsicWidth();
            deleteBackground.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) +
                    backgroundCornerOffset, itemView.getBottom());
            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

        } else if (dX < 0) { //swiping to right
            editBackground.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
            int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            editIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

        } else {
            //view is unswiped
            editBackground.setBounds(0, 0, 0, 0);
            deleteBackground.setBounds(0, 0, 0, 0);
            editIcon.setBounds(0, 0, 0, 0);
            deleteIcon.setBounds(0, 0, 0, 0);
        }

        deleteBackground.draw(c);
        editBackground.draw(c);
        deleteIcon.draw(c);
    }


    @Override
    public void clearView(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(0);
        viewHolder.itemView.setElevation(0.0f);

        super.clearView(recyclerView, viewHolder);


    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    public interface OnRecyclerViewSwipedListener {
        void onDeleteSwiped(int position);

        void onEditSwiped(int position, RecyclerView.ViewHolder viewHolder);

    }

    public interface ItemTouchHelperViewHolder {

        void onClearView();

    }
}
