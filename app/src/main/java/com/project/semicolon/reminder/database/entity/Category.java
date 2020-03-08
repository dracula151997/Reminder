package com.project.semicolon.reminder.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.project.semicolon.reminder.R;
import com.project.semicolon.reminder.adapters.ListItemViewModel;
import com.project.semicolon.reminder.utils.FormatterUtil;

import java.util.Locale;

import lombok.Data;


@Entity(tableName = "category")
@Data
public class Category extends ListItemViewModel implements Parcelable {
    public static final int THEME_RED = 1;
    public static final int THEME_PINK = 2;
    public static final int THEME_PURPLE = 3;
    public static final int THEME_BLUE = 4;
    public static final int THEME_CYAN = 5;
    public static final int THEME_TEAL = 6;
    public static final int THEME_GREEN = 7;
    public static final int THEME_AMBER = 8;
    public static final int THEME_ORANGE = 9;
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "category_title")
    @NonNull
    private String title;
    @ColumnInfo(name = "created_at")
    private long createdAt;
    @ColumnInfo(name = "updated_at")
    private long updatedAt;
    @ColumnInfo(name = "theme")
    private int theme;
    @Ignore
    private int count;

    public Category() {
    }

    protected Category(Parcel in) {
        id = in.readInt();
        title = in.readString();
        createdAt = in.readLong();
        updatedAt = in.readLong();
        theme = in.readInt();
        count = in.readInt();
    }

    public static int getStyle(int theme) {
        switch (theme) {
            case THEME_AMBER:
                //get amber style
                return R.style.AppThemeAmber;
            case THEME_BLUE:
                //get blue style
                return R.style.AppThemeBlue;
            case THEME_CYAN:
                //get cyan style
                return R.style.AppThemeCyan;
            case THEME_GREEN:
                //get green style
                return R.style.AppThemeGreen;
            case THEME_ORANGE:
                //get orange style
                return R.style.AppThemeOrange;
            case THEME_PINK:
                //get pink style
                return R.style.AppThemePink;
            case THEME_PURPLE:
                //get purple style
                return R.style.AppThemePurple;
            case THEME_RED:
                return R.style.AppThemeRed;
            //get red theme
            case THEME_TEAL:
                //get teal style
                return R.style.AppThemeTeal;
        }

        return R.style.AppTheme;
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

    public String counter() {
        if (count == 0) return "";
        else if (count == 1) return String.format(Locale.US, "%d note.", count);
        else return String.format(Locale.US, "%d notes.", count);
    }

    public CharSequence date() {
        return FormatterUtil.formatShortDate(createdAt);
    }

    public String badgeTitle() {
        return title.substring(0, 1).toUpperCase(Locale.US);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeLong(createdAt);
        parcel.writeLong(updatedAt);
        parcel.writeInt(theme);
        parcel.writeInt(count);
    }

}
