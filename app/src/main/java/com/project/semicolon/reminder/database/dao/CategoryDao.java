package com.project.semicolon.reminder.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.project.semicolon.reminder.database.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao extends BaseDao<Category> {

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAll();

    @Query("UPDATE category SET category_title=:title, theme=:theme WHERE id=:id")
    int updateCategoryById(String title, int theme, int id);

    @Query("DELETE FROM category")
    int deleteAllCategory();

    @Query("DELETE FROM category WHERE id=:categoryId")
    int deleteCategory(int categoryId);

}
