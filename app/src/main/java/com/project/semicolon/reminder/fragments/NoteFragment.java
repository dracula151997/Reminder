package com.project.semicolon.reminder.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.RecyclerView;

import com.project.semicolon.reminder.NoteActivity;
import com.project.semicolon.reminder.R;
import com.project.semicolon.reminder.TakeNoteActivity;
import com.project.semicolon.reminder.adapters.GenericAdapter;
import com.project.semicolon.reminder.adapters.SwipeController;
import com.project.semicolon.reminder.database.DatabaseHelper;
import com.project.semicolon.reminder.database.entity.Note;
import com.project.semicolon.reminder.databinding.NoteFragmentBind;
import com.project.semicolon.reminder.listeners.OnViewClickedListener;
import com.project.semicolon.reminder.utils.AnimatorUtil;
import com.project.semicolon.reminder.utils.AppExecutors;
import com.project.semicolon.reminder.utils.Keys;
import com.project.semicolon.reminder.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment implements
        OnViewClickedListener, SwipeController.OnRecyclerViewSwipedListener {
    private static final String TAG = "NoteFragment";
    private int theme;
    private int categoryId;
    private NoteFragmentBind noteFragmentBind;
    private boolean isFabOpen = false;
    private List<Note> noteList;
    private DatabaseHelper db;
    private GenericAdapter<Note> noteAdapter;


    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();

        db = DatabaseHelper.getInstance(getContext());
        categoryId = SharedHelper.getInstance().getInt(Keys.CATEGORY_ID.getKey(), -1);
        noteList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        noteFragmentBind = NoteFragmentBind.inflate(inflater, container, false);
        noteFragmentBind.setListener(this);

        initRecyclerView();

        fetchAllNotes();
        return noteFragmentBind.getRoot();
    }

    private void fetchAllNotes() {

        db.noteDao().getNotesForCategory(categoryId)
                .observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        Log.d(TAG, "onChanged: notes: " + notes.toString());
                        if (!notes.isEmpty()) {
                            noteList.addAll(notes);
                            noteFragmentBind.emptyState.setVisibility(View.GONE);
                            noteAdapter.setItems(notes);
                            return;
                        }

                        noteFragmentBind.emptyState.setVisibility(View.VISIBLE);

                    }
                });
    }

    private void initRecyclerView() {
        noteAdapter = new GenericAdapter<>(R.layout.list_item_notes);
        SwipeController swipeController = new SwipeController(getContext());
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeController);
        swipeController.setOnRecyclerViewSwipedListener(this);
        touchHelper.attachToRecyclerView(noteFragmentBind.notesRecycler);
        noteFragmentBind.notesRecycler.setAdapter(noteAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onFabClicked(View view) {
        toggleFab(false);
    }

    @Override
    public void onDrawingFabClicked(View view) {
        Intent intent = new Intent(getContext(), TakeNoteActivity.class);
        intent.putExtra("type", 2);
        intent.putExtra("theme", theme);
        startActivity(intent);


    }

    @Override
    public void onSimpleNoteFabClicked(View view) {
        Intent intent = new Intent(getContext(), TakeNoteActivity.class);
        intent.putExtra("type", 1);
        intent.putExtra("theme", theme);
        startActivity(intent);

    }

    private void toggleFab(boolean close) {
        if (isFabOpen) {
            isFabOpen = false;

            AnimatorUtil.create(getContext())
                    .on(noteFragmentBind.addFab)
                    .animate(R.anim.anim_rotate_back);

            AnimatorUtil.create(getContext())
                    .on(noteFragmentBind.noteFab)
                    .endVisibility(View.GONE)
                    .animate(R.anim.fab_out);

            AnimatorUtil.create(getContext())
                    .on(noteFragmentBind.drawingFab)
                    .delay(50)
                    .endVisibility(View.GONE)
                    .animate(R.anim.fab_out);


        } else if (!close) {
            isFabOpen = true;


            AnimatorUtil.create(getContext())
                    .on(noteFragmentBind.addFab)
                    .animate(R.anim.fab_rotate);

            AnimatorUtil.create(getContext())
                    .on(noteFragmentBind.noteFab)
                    .delay(80)
                    .startVisibility(View.VISIBLE)
                    .animate(R.anim.fab_in);

            AnimatorUtil.create(getContext())
                    .on(noteFragmentBind.drawingFab)
                    .startVisibility(View.VISIBLE)
                    .animate(R.anim.fab_in);
        }

    }

    @Override
    public void onDeleteSwiped(final int position) {
        final Note note = noteList.get(position);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int deletedRaw = db.noteDao().deleteNote(note.getId());
                if (deletedRaw == 1){
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            noteAdapter.deleteItem(position);
                            Toast.makeText(getContext(), R.string.deleted_msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


    }

    @Override
    public void onEditSwiped(int position, RecyclerView.ViewHolder viewHolder) {

    }
}
