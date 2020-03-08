package com.project.semicolon.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.project.semicolon.reminder.database.entity.Category;
import com.project.semicolon.reminder.databinding.TakeNoteActivityBind;
import com.project.semicolon.reminder.fragments.SimpleNoteFragment;
import com.project.semicolon.reminder.listeners.OnBackClickedListener;
import com.project.semicolon.reminder.utils.SharedHelper;

public class TakeNoteActivity extends AppCompatActivity implements
        OnBackClickedListener {
    private static final String TAG = "TakeNoteActivity";
    private TakeNoteActivityBind noteActivityBind;
    private SimpleNoteFragment simpleNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent data = getIntent();
        int type = data.getIntExtra("type", 0);
        int theme = SharedHelper.get(getApplicationContext(), "theme");
        setTheme(Category.getStyle(theme));
        noteActivityBind = DataBindingUtil.setContentView(this, R.layout.activity_take_note);
        noteActivityBind.setListener(this);

        if (type == 1) {
            //simple note fragment

            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.simpleNoteFragment);
        } else if (type == 2) {
            //drawing fragment
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.drawingFragment);
        }


    }

    @Override
    public void onBackClicked(View view) {
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
