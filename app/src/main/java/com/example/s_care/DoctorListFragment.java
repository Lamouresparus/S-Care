package com.example.s_care;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.s_care.custom.DoctorCategoryAdapter;
import com.example.s_care.custom.DoctorListAdapter;
import com.example.s_care.model.Doctor;
import com.example.s_care.model.DoctorCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 *
 */
public class DoctorListFragment extends Fragment {



    public DoctorListFragment() {
        // Required empty public constructor
    }

    private RecyclerView doctorRv;
    private RecyclerView.LayoutManager layoutManager;
    private List<Doctor> doctors;
    private DoctorListAdapter doctorListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doctorRv = view.findViewById(R.id.doctor_rv);
        layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        doctorRv.setLayoutManager(layoutManager);
        createDoctorList();
        doctorListAdapter = new DoctorListAdapter(doctors);
        doctorRv.setAdapter(doctorListAdapter);

    }

    private void createDoctorList() {
        doctors = new ArrayList<>();
        Doctor doctor = new Doctor("John Francis", "PHD Child care", 4.5, 780,"08052258867");
        doctors.add(doctor);
        doctors.add(doctor);
        doctors.add(doctor);
        doctors.add(doctor);
    }
}