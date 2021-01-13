package com.example.android2lesson21.models;

import java.io.Serializable;

public class Note implements Serializable {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Note(String title) {
        this.title = title;
    }
}
