package com.project.semicolon.reminder.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.project.semicolon.reminder.database.entity.Note;

import java.util.List;

@Dao
public interface NoteDao extends BaseDao<Note> {

    @Query("SELECT * FROM note WHERE categoryId=:categoryId ORDER BY created_at ASC")
    LiveData<List<Note>> getNotesForCategory(int categoryId);


}
