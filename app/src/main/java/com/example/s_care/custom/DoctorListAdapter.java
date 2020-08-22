package com.example.s_care.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.s_care.R;
import com.example.s_care.model.Doctor;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.VH> {
    private List<Doctor> doctors;

    public DoctorListAdapter(List<Doctor> doctors) {
        this.doctors = doctors;
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

    public class VH extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private TextView ratingTv;
        private TextView educationTv;
        private TextView numberOfPatientsTv;
        private ImageView doctorIv;
        public VH(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.categories_doctor_name);
            ratingTv = itemView.findViewById(R.id.category_doctor_rating);
            educationTv = itemView.findViewById(R.id.categories_doctor_educatio);
            numberOfPatientsTv = itemView.findViewById(R.id.category_number_of_patients);
            doctorIv = itemView.findViewById(R.id.doctor_image);

        }

        public void bind(Doctor doctor) {
            nameTv.setText(doctor.getName());
            ratingTv.setText(String.valueOf(doctor.getRating()));
            educationTv.setText(doctor.getEducation());
            numberOfPatientsTv.setText(String.valueOf(doctor.getNumberOfPatients()));
        }
    }
}
