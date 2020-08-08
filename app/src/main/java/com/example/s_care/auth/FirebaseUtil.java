package com.example.s_care.auth;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.s_care.Constants;
import com.example.s_care.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class FirebaseUtil {
    private static FirebaseAuth mAuth;

    private static FirebaseUtil firebaseUtil;
    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mDatabaseReference;
    private static FirebaseUser mCurrentUser;
    private static ValueEventListener mValueEventListener;
    private static User mCurrentUserDetails;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static AuthCallback mAuthCallback;
    private static LogOutCallback mLogOutCallback;

    private FirebaseUtil() {
    }

    public static void setLogOutAuthCallback(LogOutCallback logOutCallback){
        mLogOutCallback = logOutCallback;
    }


    public static String getmCurrentUser() {
        return mCurrentUser.getEmail();
    }


    public static void initialiseFirebaseAuth(AuthCallback authCallback) {
        mAuthCallback = authCallback;

        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference().child(Constants.USERS_PATH);

        } else {
            mCurrentUser = mAuth.getCurrentUser();
        }
    }

    public static void signIn(final String email, final String password) {

        mAuthCallback.showProgressBar(true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.v("Sigining in complete", "Email ");
                        mAuthCallback.showProgressBar(false);
                        if (task.isSuccessful()) {
                            Log.v("Sigining in is successful", "Email ");
                            mCurrentUser = mAuth.getCurrentUser();
                            mAuthCallback.authSuccessful();

                        } else {
                            String error = Objects.requireNonNull(task.getException()).getLocalizedMessage();
                            mAuthCallback.authFailed(error);
                            Log.v("Sigining in failed", "Email ");
                        }

                    }
                });


    }

    public static void signUp(final User user, final String password) {

        mAuthCallback.showProgressBar(true);
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("Sigin up in complete", "Email ");
                        mAuthCallback.showProgressBar(false);
                        if (task.isSuccessful()) {
                            mCurrentUser = mAuth.getCurrentUser();
                            saveUserToDb(user);
                        } else {

                            String error = Objects.requireNonNull(task.getException()).getLocalizedMessage();
                            mAuthCallback.authFailed(error);
                            Log.v("Sign up in failed", "Email ");
                        }

                    }
                });

    }


    public static boolean userIsLoggedIn() {
        return mCurrentUser != null;
    }

    public static void logOut() {
        mAuth.signOut();
        mLogOutCallback.logOutSuccessful();
    }

    static void saveUserToDb(User user){
        user.setId(mCurrentUser.getUid());
        Task<Void> task = mDatabaseReference.child(mCurrentUser.getUid()).setValue(user);

        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mAuthCallback.authSuccessful();
                }
                else {
                    mAuthCallback.authFailed(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                }
            }
        });
    }

    public static User getUserData(){
        return mCurrentUserDetails;
    }

    public static void addValueEventListener(){
        mDatabaseReference.child(mCurrentUser.getUid()).addValueEventListener(mValueEventListener);
    }

    public static void removeValueEventListener(){
        if (mCurrentUser == null ){

        }else {
            mDatabaseReference.child(mCurrentUser.getUid()).removeEventListener(mValueEventListener);
        }
    }

    public static void initializeValueEventListener(){
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.v("currentUser is: ", Objects.requireNonNull(snapshot.getValue()).toString() );

                mCurrentUserDetails = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.w("load data cancelled: ", error.getMessage());

            }
        };

        addValueEventListener();
    }
}
