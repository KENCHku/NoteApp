package com.example.android2lesson31.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.android2lesson31.models.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
