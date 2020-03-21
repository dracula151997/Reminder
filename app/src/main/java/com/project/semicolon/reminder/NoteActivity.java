package com.project.semicolon.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.project.semicolon.reminder.database.entity.Category;
import com.project.semicolon.reminder.databinding.NoteActivityBind;
import com.project.semicolon.reminder.utils.Keys;
import com.project.semicolon.reminder.utils.SharedHelper;

public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "NoteActivity";
    public int theme;
    int categoryId;
    private NoteActivityBind noteActivityBind;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getIntent();
        Category category = data.getParcelableExtra(Keys.CATEGORY.getKey());
        theme = category.getTheme();
        setTheme(Category.getStyle(theme));

        noteActivityBind = DataBindingUtil.setContentView(this, R.layout.activity_note);
        noteActivityBind.setToolbarTitle(category.getTitle());
        setSupportActionBar(noteActivityBind.toolbar);

        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        startDefaultFragment();

        noteActivityBind.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });

        SharedHelper.getInstance().storeInt(Keys.CATEGORY_ID.getKey(), category.getId());
    }

    private void startDefaultFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.THEME.getKey(), theme);
        Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(R.id.noteFragment, bundle);
    }

}
