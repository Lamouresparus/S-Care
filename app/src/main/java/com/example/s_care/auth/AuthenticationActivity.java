package com.example.s_care.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.s_care.R;
import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        ViewPager viewPager = findViewById(R.id.auth_viewpager);
        TabLayout tabLayout = findViewById(R.id.auth_tab_layout);

        viewPager.setAdapter(new AuthPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}
