package com.example.s_care.custom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_care.R;
import com.example.s_care.auth.FirebaseUtil;
import com.example.s_care.model.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.VH> implements SortDoctorCallback {
    private List<Doctor> doctors;
    private OpenDialerListener openDialerListener;

    public DoctorListAdapter(String sortType, OpenDialerListener listener) {
        openDialerListener = listener;
        doctors = new ArrayList<>();
        FirebaseUtil.getDoctor(sortType, this);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Doctor doctor = doctors.get(position);
        holder.bind(doctor);

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    @Override
    public void onChildAdded(DataSnapshot data) {
        doctors.add(data.getValue(Doctor.class));
        notifyItemInserted(doctors.size() - 1);
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private TextView ratingTv;
        private TextView educationTv;
        private TextView numberOfPatientsTv;
        private ImageView doctorIv;
        private Button book;

        public VH(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.categories_doctor_name);
            ratingTv = itemView.findViewById(R.id.category_doctor_rating);
            educationTv = itemView.findViewById(R.id.categories_doctor_educatio);
            numberOfPatientsTv = itemView.findViewById(R.id.category_number_of_patients);
            doctorIv = itemView.findViewById(R.id.doctor_image);
            book = itemView.findViewById(R.id.button);

        }

        public void bind(final Doctor doctor) {
            nameTv.setText(doctor.getName());
            ratingTv.setText(String.valueOf(doctor.getRating()));
            educationTv.setText(doctor.getEducation());
            numberOfPatientsTv.setText(String.valueOf(doctor.getNumberOfPatients()));
            showImage(doctor.getImageUrl());

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   openDialerListener.onBook(doctor.getPhoneNumber().trim());
                }
            });

        }

        private void showImage(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl)
                        .into(doctorIv);
            }
        }
    }
    public interface OpenDialerListener{
        void onBook(String phoneNumber);
    }
}
