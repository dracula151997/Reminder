package com.project.semicolon.reminder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.semicolon.reminder.database.dao.CategoryDao;
import com.project.semicolon.reminder.database.dao.NoteDao;
import com.project.semicolon.reminder.database.entity.Category;
import com.project.semicolon.reminder.database.entity.Note;

@Database(entities = {Category.class, Note.class}, exportSchema = true, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "reminder";
    private static DatabaseHelper instance = null;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract CategoryDao categoryDao();

    public abstract NoteDao noteDao();
}
