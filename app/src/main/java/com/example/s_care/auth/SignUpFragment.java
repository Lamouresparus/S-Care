package com.example.s_care.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.s_care.Constants;
import com.example.s_care.R;
import com.example.s_care.custom.SetDatePicker;
import com.example.s_care.model.User;


public class SignUpFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String gender;

    public SignUpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner genderSpinner = view.findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        genderSpinner.setOnItemSelectedListener(this);

        Button signUpButton = view.findViewById(R.id.signup_button);

        final EditText emailEditText = view.findViewById(R.id.email);

        final EditText firstNameEditText = view.findViewById(R.id.first_name);

        final EditText lastNameEditText = view.findViewById(R.id.last_name);

        final EditText passwordEditText  = view.findViewById(R.id.password);

        final EditText confirmPasswordEditText  = view.findViewById(R.id.confirm_password);

        final EditText dobEditText = view.findViewById(R.id.date_of_birth);
        dobEditText.setFocusable(false);
        dobEditText.setClickable(true);
        final DialogFragment datePicker = new SetDatePicker(dobEditText,getContext());
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getParentFragmentManager(),"datePicker");

            }
        });



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String dateOfBirth = dobEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();


                Log.v("DAte of birth is: ", dateOfBirth);


                if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() || gender.isEmpty() || gender.equals(Constants.GENDER_DEFAULT)){
                    Toast.makeText(getContext(), "Please fill in required fields", Toast.LENGTH_SHORT).show();
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

                User user = new User(firstName, lastName, email, dateOfBirth, gender);

                FirebaseUtil.signUp(user,password);


            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();

        Log.v("gender selected is: ",gender);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
