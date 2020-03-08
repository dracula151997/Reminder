package com.project.semicolon.reminder.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert
    long insert(T model);

    @Delete
    int delete(T model);

    @Update
    int update(T model);

}
