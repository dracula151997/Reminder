package com.project.semicolon.reminder.fragments;


import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.semicolon.reminder.database.DatabaseHelper;
import com.project.semicolon.reminder.database.entity.Note;
import com.project.semicolon.reminder.databinding.DrawingFragmentBind;
import com.project.semicolon.reminder.listeners.OnDrawListener;
import com.project.semicolon.reminder.utils.AppExecutors;
import com.project.semicolon.reminder.utils.SharedHelper;
import com.project.semicolon.reminder.views.DrawingView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawingFragment extends Fragment implements OnDrawListener {
    private DrawingFragmentBind drawingFragmentBind;
    private int theme, categoryId;
    private DatabaseHelper db;


    public DrawingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = SharedHelper.get(getContext(), "theme");
        categoryId = SharedHelper.get(getContext(), "id");

        db = DatabaseHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        drawingFragmentBind = DrawingFragmentBind.inflate(inflater, container, false);
        drawingFragmentBind.setDrawListener(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                saveNote();

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return drawingFragmentBind.getRoot();
    }

    private void saveNote() {
        String title = drawingFragmentBind.title.getText().toString();
        if (title.isEmpty()) title = "Untitled";

        final Note note = new Note();
        note.setTitle(title);
        note.setDesc(convertPaintToString());
        note.setType(1);
        note.setCategoryId(categoryId);
        note.setTheme(theme);
        note.setCreatedAt(System.currentTimeMillis());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.noteDao().insert(note);
            }
        });
    }

    private String convertPaintToString() {
        return Base64.encodeToString(drawingFragmentBind.drawingView.getBitmapAsByteArray(),
                Base64.NO_WRAP);
    }

    @Override
    public void onDraw(View view) {
        drawingFragmentBind.drawingView.setMode(DrawingView.Mode.DRAW);
        drawingFragmentBind.drawingView.setPaintStrokeWidth(3F);


    }

    @Override
    public void onEraser(View view) {
        drawingFragmentBind.drawingView.setMode(DrawingView.Mode.ERASER);
        drawingFragmentBind.drawingView.setPaintStrokeWidth(40F);

    }
}
