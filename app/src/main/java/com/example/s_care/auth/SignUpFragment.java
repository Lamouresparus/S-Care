package com.example.s_care.auth;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s_care.FirebaseUtil;
import com.example.s_care.R;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {


    public SignUpFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUtil.initialiseFirebaseAuth(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signUpButton = view.findViewById(R.id.signup_button);

        final EditText emailEditText = view.findViewById(R.id.email);

        final EditText passwordEditText  = view.findViewById(R.id.password);

        final EditText confirmPasswordEditText  = view.findViewById(R.id.confirm_password);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();


                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getContext(), "email and password can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!confirmPassword.equals(password)){
                    Toast.makeText(getContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6){
                    Toast.makeText(getContext(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUtil.signUp(email,password);


            }
        });


    }


    }
