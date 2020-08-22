package com.example.s_care.model;

public class DoctorCategory {
    private String categoyName;
    private int imageResource;

    public DoctorCategory(String categoyName, int imageResource) {
        this.categoyName = categoyName;
        this.imageResource = imageResource;
    }

    public String getCategoyName() {
        return categoyName;
    }

    public int getImageResource() {
        return imageResource;
    }
}
