package com.example.jaden.medicoapp.patientrecord.uploadimage;

import android.graphics.Bitmap;

/**
 * Created by buste on 5/4/2017.
 */

public class Photo {
    Bitmap image;
    String name;
    String date;
    String doctor;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
