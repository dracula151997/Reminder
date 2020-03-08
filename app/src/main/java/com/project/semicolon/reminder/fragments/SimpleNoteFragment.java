package com.project.semicolon.reminder.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.project.semicolon.reminder.R;
import com.project.semicolon.reminder.database.DatabaseHelper;
import com.project.semicolon.reminder.database.entity.Note;
import com.project.semicolon.reminder.databinding.SimpleNoteFragBind;
import com.project.semicolon.reminder.listeners.OnEditorClickedListener;
import com.project.semicolon.reminder.utils.AppExecutors;
import com.project.semicolon.reminder.utils.SharedHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleNoteFragment extends Fragment implements OnEditorClickedListener {
    private static final String TAG = "SimpleNoteFragment";
    private SimpleNoteFragBind noteFragBind;
    private DatabaseHelper db;
    private Note note;
    private int categoryId, theme;


    public SimpleNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseHelper.getInstance(getContext());
        categoryId = SharedHelper.get(getContext(), "id");
        theme = SharedHelper.get(getContext(), "theme");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        noteFragBind = SimpleNoteFragBind.inflate(inflater, container, false);
        noteFragBind.setEditorListener(this);
        noteFragBind.richEditor.setPlaceholder(getString(R.string.note));


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //save note
                saveNoteToDB();

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return noteFragBind.getRoot();
    }

    private void saveNoteToDB() {
        final Note note = new Note();
        note.setTitle(noteFragBind.titleEdit.getText().toString());
        note.setDesc(noteFragBind.richEditor.getHtml());
        note.setCreatedAt(System.currentTimeMillis());
        note.setCategoryId(categoryId);
        note.setType(2);
        note.setTheme(theme);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.noteDao().insert(note);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.noteFragment);


    }

    @Override
    public void onBold(View view) {
        noteFragBind.richEditor.setBold();


    }

    @Override
    public void onItalic(View view) {
        noteFragBind.richEditor.setItalic();

    }

    @Override
    public void onUnderline(View view) {
        noteFragBind.richEditor.setUnderline();

    }

}
