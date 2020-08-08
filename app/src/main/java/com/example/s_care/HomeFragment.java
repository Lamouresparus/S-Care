package com.example.s_care;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.s_care.auth.AuthenticationActivity;
import com.example.s_care.auth.FirebaseUtil;
import com.example.s_care.auth.LogOutCallback;
import com.example.s_care.model.User;

import static com.example.s_care.auth.FirebaseUtil.getmCurrentUser;


public class HomeFragment extends Fragment implements LogOutCallback {

    int number = 0;

    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        FirebaseUtil.initializeValueEventListener();
        FirebaseUtil.setLogOutAuthCallback(this);
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

        User user = FirebaseUtil.getUserData();

        TextView textView = view.findViewById(R.id.fragment_home_tv);
//        textView.setText(String.format("Welcome! %s", user.getFirstName()));

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.log_out){
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
