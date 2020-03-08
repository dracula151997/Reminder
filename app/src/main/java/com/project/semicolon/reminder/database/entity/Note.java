package com.project.semicolon.reminder.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.project.semicolon.reminder.R;
import com.project.semicolon.reminder.adapters.ListItemViewModel;
import com.project.semicolon.reminder.utils.FormatterUtil;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "note", foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "categoryId",
        onDelete = CASCADE), indices = @Index("categoryId"))
public class Note extends ListItemViewModel {
    public static final int THEME_RED = 1;
    public static final int THEME_PINK = 2;
    public static final int THEME_PURPLE = 3;
    public static final int THEME_BLUE = 4;
    public static final int THEME_CYAN = 5;
    public static final int THEME_TEAL = 6;
    public static final int THEME_GREEN = 7;
    public static final int THEME_AMBER = 8;
    public static final int THEME_ORANGE = 9;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "note_title")
    @NonNull
    private String title;

    @ColumnInfo(name = "note_desc")
    @NonNull
    private String desc;

    @ColumnInfo(name = "created_at")
    private long createdAt;

    @ColumnInfo(name = "updated_at")
    private long updatedAt;

    private int categoryId;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "theme")
    private int theme;

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDesc() {
        return desc;
    }

    public void setDesc(@NonNull String desc) {
        this.desc = desc;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getTypeDrawable() {
        if (type == 1) {
            return R.drawable.ic_gesture_black_24dp;

        } else {
            return R.drawable.ic_simple_note;
        }

    }


    public int getDrawable() {
        switch (theme) {
            case THEME_RED:
                return R.drawable.circle_red;
            case THEME_BLUE:
                return R.drawable.circle_blue;
            case THEME_AMBER:
                return R.drawable.circle_amber;
            case THEME_CYAN:
                return R.drawable.circle_cyan;
            case THEME_ORANGE:
                return R.drawable.circle_orange;
            case THEME_GREEN:
                return R.drawable.circle_green;
            case THEME_TEAL:
                return R.drawable.circle_teal;


        }

        return R.drawable.circle_red;
    }

    public CharSequence date() {
        return FormatterUtil.formatShortDate(createdAt);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", categoryId=" + categoryId +
                ", type=" + type +
                ", theme=" + theme +
                '}';
    }
}
