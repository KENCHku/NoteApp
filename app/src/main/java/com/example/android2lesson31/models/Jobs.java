package com.example.android2lesson31.models;

import java.io.Serializable;

public class Jobs implements Serializable {
    private String profession;
    private String description;
    private int imageRaw;

    public Jobs(String profession, String description, int imageRaw) {
        this.profession = profession;
        this.description = description;
        this.imageRaw = imageRaw;
    }
    public int getImageRaw() {
        return imageRaw;
    }

    public void setImageRaw(int imageRaw) {
        this.imageRaw = imageRaw;
    }


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
