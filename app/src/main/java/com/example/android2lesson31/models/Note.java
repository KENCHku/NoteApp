package com.example.android2lesson31.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Note implements Serializable {

  //  @PrimaryKey(autoGenerate = true)
  @PrimaryKey
  @NonNull
    private String noteId;
    private String title;
    private String date;

    public Note() {
    }

    public Note(String title, String date) {
        this.title = title;
        this.date = date;
    }


    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
