package com.project.semicolon.reminder.adapters;

import androidx.room.Ignore;

import com.project.semicolon.reminder.listeners.OnListItemClickListener;

public class ListItemViewModel {
    @Ignore
    public int adapterPosition = -1;
    @Ignore
    public OnListItemClickListener onListItemClickListener = null;
}
