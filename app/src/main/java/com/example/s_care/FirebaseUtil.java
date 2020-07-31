package com.example.s_care;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.s_care.auth.AuthenticationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class FirebaseUtil {
    private static FirebaseAuth mAuth;
    private static FirebaseUtil firebaseUtil;
    private static FirebaseUser currentUser;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static Context mContext;

    private static Dialog mDialog;

    private FirebaseUtil(){}

    static String getCurrentUser() {
        return currentUser.getEmail();
    }

    public static void initialiseFirebaseAuth(Context context) {
        mContext = context;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(R.layout.loading_dialog);
        mDialog = builder.create();
        mDialog.setCancelable(false);

        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mAuth = FirebaseAuth.getInstance();

        }
        else {

            currentUser = mAuth.getCurrentUser();
    }
    }

    public static void signIn(final String email, final String password) {

        showProgressBar(true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("Sigining in complete", "Email ");
                        showProgressBar(false);
                        if (task.isSuccessful()) {
                            Log.v("Sigining in is successful", "Email ");
                            currentUser= mAuth.getCurrentUser();
                            launchMainActivity();
                        } else {

                            Log.v("Sigining in failed", "Email ");

                            Toast.makeText(mContext, "Authentication failed. "+ Objects.requireNonNull(task.getException()).getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }

    public static void signUp(final String email, final String password) {

        showProgressBar(true);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("Sigin up in complete", "Email ");
                        showProgressBar(false);
                        if (task.isSuccessful()) {
                            Log.v("Sigin up in is successful", "Email ");
                            currentUser= mAuth.getCurrentUser();
                            launchMainActivity();
                        } else {

                            Log.v("Sign up in failed", "Email ");

                            Toast.makeText(mContext, "Sign Up failed. "+ Objects.requireNonNull(task.getException()).getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    private static void launchMainActivity() {
        Toast.makeText(mContext,"Welcome!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
        ((Activity)mContext).finish();
    }

    public static boolean userIsLoggedIn(){
        return currentUser != null;
    }

    private static void showProgressBar(Boolean show){

        if(show){
            mDialog.show();
        }
        else mDialog.dismiss();

    }

    static void logOut() {
        mAuth.signOut();
        Toast.makeText(mContext, ("Goodbye! "+getCurrentUser()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, AuthenticationActivity.class);
        mContext.startActivity(intent);
        ((Activity)mContext).finish();
    }
}
