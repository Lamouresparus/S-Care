package com.example.s_care.auth;

interface AuthCallback {
    void authSuccessful();

    void authFailed(String error);

    void showProgressBar(Boolean show);
}
