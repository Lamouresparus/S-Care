package com.example.s_care.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.s_care.Constants;
import com.example.s_care.R;
import com.example.s_care.auth.FirebaseUtil;
import com.example.s_care.auth.USerCallback;
import com.example.s_care.model.User;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements UploadPhotoCallback {

    public ProfileFragment() {
        // Required empty public constructor
    }
    private Dialog mDialog;
    private Button button;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUtil.initializeValueEventListener(new USerCallback() {
            @Override
            public void currentUserData(User user) {
                showImage(user.getImageUrl());
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(R.layout.loading_dialog);
        builder.setTitle("uploading image...");
        mDialog = builder.create();
        mDialog.setCancelable(false);
        button = view.findViewById(R.id.profile_photo_upload_button);
        imageView = view.findViewById(R.id.profile_photo_upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Photo Upload"), Constants.UPLOAD_PROFILE_PHOTO_REQUEST_CODE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void uploadSuccessful(String imageUrl) {
        Toast.makeText(requireContext(),"Upload successful", Toast.LENGTH_SHORT).show();
        showImage(imageUrl);
    }

    private void showImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl)
                    .into(imageView);
        }
    }

    @Override
    public void uploadFailed(String error) {
        Toast.makeText(requireContext(),"Upload failed "+error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgressBar(Boolean show) {
        if (show) mDialog.show();
        else mDialog.dismiss();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);

        Log.v("Upload Image : ", "OnActivityForResultOn");

        if (requestCode == Constants.UPLOAD_PROFILE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = Objects.requireNonNull(data).getData();
            Log.v("Upload Image : ", "ImageUriGotten: "+ Objects.requireNonNull(imageUri).toString());

            FirebaseUtil.uploadPhoto(this,imageUri);
        }
    }
}