package com.example.android2lesson31;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }

    public boolean isShown(){
        return preferences.getBoolean("isShown", false);
    }

    public void saveBoardsStatus() {
        preferences.edit().putBoolean("isShown", true).apply();
    }




    public void clearSettings(){
        preferences.edit().clear().apply();
        // preferences.edit().remove("isShown").apply();
    }
}