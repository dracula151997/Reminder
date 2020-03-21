package com.project.semicolon.reminder.utils;

public enum Keys {
    CATEGORY("category"),
    CATEGORY_ID("category_id"),
    THEME("theme");


    String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
