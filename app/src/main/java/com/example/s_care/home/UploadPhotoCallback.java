package com.example.s_care.home;

public interface UploadPhotoCallback {

        void uploadSuccessful(String imageUrl);

        void uploadFailed(String error);

        void showProgressBar(Boolean show);

}
