package com.example.s_care.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.s_care.auth.FirebaseUtil;
import com.example.s_care.home.MainActivity;
import com.example.s_care.R;
import com.example.s_care.auth.AuthenticationActivity;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Handler splashHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                launchApp();
            }
        };

        splashHandler.postDelayed(runnable, 1000);

    }

    private void launchApp(){
        FirebaseUtil.initialiseFirebaseAuth(null);

        if (FirebaseUtil.userIsLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
        }

        finish();
    }
}
