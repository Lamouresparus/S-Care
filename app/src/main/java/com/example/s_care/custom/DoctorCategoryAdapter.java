package com.example.s_care.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_care.R;
import com.example.s_care.home.HomeFragmentDirections;
import com.example.s_care.model.DoctorCategory;

import java.util.List;

public class DoctorCategoryAdapter extends RecyclerView.Adapter<DoctorCategoryAdapter.ViewHolder> {
    private List<DoctorCategory> categoryList;
    public DoctorCategoryAdapter(List<DoctorCategory> doctorCategoryList){
        categoryList = doctorCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_category_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DoctorCategory category = categoryList.get(position);
        holder.bind(category);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_name);
            imageView = itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToDoctorListFragment().setCategoryName(textView.getText().toString()));
                }
            });
        }

        public void bind(DoctorCategory category) {
           imageView.setImageResource(category.getImageResource());
           textView.setText(category.getCategoyName());

        }
    }
}
