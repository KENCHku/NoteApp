package com.example.android2lesson31;

import android.app.Application;

import androidx.room.Room;

import com.example.android2lesson31.room.AppDataBase;

public class App extends Application {

private static AppDataBase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
appDatabase= Room.databaseBuilder(
        this,
        AppDataBase.class,
        "database")
        .allowMainThreadQueries()
        .build();
    }
    public static AppDataBase getAppDatabase(){
        return appDatabase;
    }
}
