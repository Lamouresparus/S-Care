package com.example.s_care.model;

public class Doctor {
    private String name;
    private String category;
    private String education;
    private String imageUrl;
    private  double rating;
    private  int numberOfPatients;
    private String phoneNumber;

    public Doctor(){}

    public Doctor(String category, String name, String education, double rating, int numberOfPatients, String phoneNumber , String image) {
        this.category = category;
        this.name = name;
        this.education = education;
        this.rating = rating;
        this.numberOfPatients = numberOfPatients;
        this.phoneNumber = phoneNumber;
        imageUrl = image;
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

    public String getCategory() {
        return category;
    }
}
