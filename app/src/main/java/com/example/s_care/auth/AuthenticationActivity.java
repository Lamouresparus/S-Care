package com.example.s_care.auth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.s_care.home.MainActivity;
import com.example.s_care.R;
import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class AuthenticationActivity extends AppCompatActivity implements AuthCallback {

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FirebaseUtil.initialiseFirebaseAuth(this);

        ViewPager viewPager = findViewById(R.id.auth_viewpager);
        TabLayout tabLayout = findViewById(R.id.auth_tab_layout);

        viewPager.setAdapter(new AuthPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.loading_dialog);
        mDialog = builder.create();
        mDialog.setCancelable(false);
    }

    @Override
    public void authSuccessful() {
        launchMainActivity();
    }

    @Override
    public void authFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(Boolean show) {
        if (show) mDialog.show();
        else mDialog.dismiss();

    }

    private void launchMainActivity() {
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
