package com.example.s_care;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_care.auth.AuthenticationActivity;
import com.example.s_care.auth.FirebaseUtil;
import com.example.s_care.auth.LogOutCallback;
import com.example.s_care.auth.USerCallback;
import com.example.s_care.custom.DoctorCategoryAdapter;
import com.example.s_care.model.DoctorCategory;
import com.example.s_care.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.s_care.auth.FirebaseUtil.getmCurrentUser;


public class HomeFragment extends Fragment implements LogOutCallback {

    public HomeFragment() {

    }

    private ProgressBar progressBar;
    private TextView textView;
    private RecyclerView doctorCategoryRv;
    private RecyclerView.LayoutManager layoutManager;
    private List<DoctorCategory> doctorCategories;
    private DoctorCategoryAdapter doctorCategoryAdapter;
    private ImageView profileImage;
    private TextView findDocByCat;
    private CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        FirebaseUtil.initializeValueEventListener(new USerCallback() {
            @Override
            public void currentUserData(User user) {
                textView.setText(String.format("Hello %s", user.getFirstName()));
                progressBar.setVisibility(View.INVISIBLE);
                showViews();


            }
        });

        FirebaseUtil.setLogOutAuthCallback(this);
    }

    private void showViews() {
        profileImage.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        findDocByCat.setVisibility(View.VISIBLE);
        doctorCategoryRv.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        progressBar = view.findViewById(R.id.progress_bar);
        cardView = view.findViewById(R.id.materialCardView);
        textView = view.findViewById(R.id.profile_name);
        profileImage = view.findViewById(R.id.profile_photo);
        findDocByCat = view.findViewById(R.id.findDocByCat);
        doctorCategoryRv = view.findViewById(R.id.category_rv);
        layoutManager = new GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false);
        doctorCategoryRv.setLayoutManager(layoutManager);
        loadDoctorCategries();
        doctorCategoryAdapter = new DoctorCategoryAdapter(doctorCategories);
        doctorCategoryRv.setAdapter(doctorCategoryAdapter);

        User user = FirebaseUtil.getUserData();

        if (user != null) {
            textView.setText(String.format("Hello %s", user.getFirstName()));
            showViews();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            hideViews();
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment());
            }
        });


    }

    private void hideViews() {
        profileImage.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        findDocByCat.setVisibility(View.INVISIBLE);
        doctorCategoryRv.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.VISIBLE);



    }

    private void loadDoctorCategries() {
        doctorCategories = new ArrayList<>();
        DoctorCategory eyeCare = new DoctorCategory("Optician", R.drawable.ic_baseline_remove_red_eye_24);
        DoctorCategory childSpecialist = new DoctorCategory("Child Sp-ecialist", R.drawable.ic_baby_face);
        DoctorCategory dentist = new DoctorCategory("Dentist", R.drawable.ic_tooth);
        DoctorCategory lungsSpecialist = new DoctorCategory("Lungs S-pecialist", R.drawable.ic_lungs);
        DoctorCategory neurologist = new DoctorCategory("Neurolo-gist", R.drawable.ic_brain_line);
        DoctorCategory obstetrician = new DoctorCategory("Obstetri-cian", R.drawable.ic_pregnancy);
        DoctorCategory orthopaedicSurgeon = new DoctorCategory("Ortho-paedic Surgeon", R.drawable.ic_knee_joint_pain);





        doctorCategories.add(childSpecialist);
        doctorCategories.add(dentist);
        doctorCategories.add(lungsSpecialist);
        doctorCategories.add(eyeCare);
        doctorCategories.add(neurologist);
        doctorCategories.add(obstetrician);
        doctorCategories.add(orthopaedicSurgeon);

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            FirebaseUtil.logOut();
        }


        return true;
    }

    @Override
    public void logOutSuccessful() {
        Toast.makeText(getContext(), ("Goodbye! " + getmCurrentUser()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FirebaseUtil.removeValueEventListener();
    }
}
