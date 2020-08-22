package com.example.s_care.model;

public class Doctor {
    private String name;
    private String education;
    private String imageUrl;
    private  double rating;
    private  int numberOfPatients;
    private String phoneNumber;

    public Doctor(String name, String education, double rating, int numberOfPatients, String phoneNumber) {
        this.name = name;
        this.education = education;
        this.rating = rating;
        this.numberOfPatients = numberOfPatients;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEducation() {
        return education;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
