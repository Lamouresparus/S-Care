package com.example.s_care.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.s_care.R;
import com.example.s_care.auth.FirebaseUtil;
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
    private String categoryName;
    private DoctorListAdapter.OpenDialerListener openDialerListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        DoctorListFragmentArgs doctorListFragmentArgs = DoctorListFragmentArgs.fromBundle(getArguments());
        categoryName = doctorListFragmentArgs.getCategoryName();
        Log.v("category name is ", categoryName);
//        Doctor doctor = new Doctor(categoryName, "John Francis", "PHD Child care", 4.5, 780,"08052258867", "");
//        FirebaseUtil.saveDoctorToDb(doctor);

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
        TextView textView = view.findViewById(R.id.findDocByCat);
        textView.setText(categoryName);
        layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        doctorRv.setLayoutManager(layoutManager);
        initalizeOpenDialerListener();
        doctorListAdapter = new DoctorListAdapter(categoryName, openDialerListener);
        doctorRv.setAdapter(doctorListAdapter);

    }

    private void initalizeOpenDialerListener() {
        openDialerListener = new DoctorListAdapter.OpenDialerListener() {
            @Override
            public void onBook(String phoneNumber) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));

                if(intent.resolveActivity(requireActivity().getPackageManager())!=null){
                    startActivity(intent);

                }
            }
        };
    }

}