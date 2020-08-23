package com.example.s_care.custom;

import com.google.firebase.database.DataSnapshot;

public interface SortDoctorCallback {

    void onChildAdded(DataSnapshot data);
}
