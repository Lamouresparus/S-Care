package com.example.s_care.auth;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.s_care.Constants;
import com.example.s_care.custom.SortDoctorCallback;
import com.example.s_care.home.UploadPhotoCallback;
import com.example.s_care.model.Doctor;
import com.example.s_care.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private static FirebaseStorage mStorage;
    private static StorageReference mStorageReference;
    private static UploadPhotoCallback mUploadPhotoCallback;
    private static DatabaseReference doctorsDbRef;
    private static SortDoctorCallback sortDoctorCallback;

    private FirebaseUtil() {
    }

    public static void setLogOutAuthCallback(LogOutCallback logOutCallback) {
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
            mStorage = FirebaseStorage.getInstance();
            mStorageReference = mStorage.getReference().child(Constants.PROFILE_PHOTO_PATH);
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference().child(Constants.USERS_PATH);
            doctorsDbRef = mFirebaseDatabase.getReference().child(Constants.DOCTORS_PATH);
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
                            getUserFromDb(Objects.requireNonNull(mCurrentUser).getUid());

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

    static void saveUserToDb(final User user) {
        user.setId(mCurrentUser.getUid());
        Task<Void> task = mDatabaseReference.child(mCurrentUser.getUid()).setValue(user);

        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mCurrentUserDetails = user;
                    mAuthCallback.authSuccessful();
                } else {
                    mAuthCallback.authFailed(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                }
            }
        });
    }

    static void getUserFromDb(String id) {

        mDatabaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mCurrentUserDetails = snapshot.getValue(User.class);
                mAuthCallback.authSuccessful();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static User getUserData() {
        return mCurrentUserDetails;
    }

    public static void addValueEventListener() {
        mDatabaseReference.child(mCurrentUser.getUid()).addValueEventListener(mValueEventListener);
    }

    public static void removeValueEventListener() {
        if (mCurrentUser != null) {
            mDatabaseReference.child(mCurrentUser.getUid()).removeEventListener(mValueEventListener);
        }
    }

    public static void initializeValueEventListener(final USerCallback callback) {
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.v("currentUser is: ", Objects.requireNonNull(snapshot.getValue()).toString() );

                mCurrentUserDetails = snapshot.getValue(User.class);
                callback.currentUserData(mCurrentUserDetails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.w("load data cancelled: ", error.getMessage());

            }
        };

        addValueEventListener();
    }

    public static void uploadPhoto(UploadPhotoCallback callback, Uri file){
        mUploadPhotoCallback = callback;
        final StorageReference ref = mStorageReference.child("images/"+ file.getLastPathSegment());
        mUploadPhotoCallback.showProgressBar(true);
        Log.v("Upload Image : ", "storageRef: "+ref.toString());

        ref.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Log.v("Upload Image : ", "UploadCompleted: ");
                mUploadPhotoCallback.showProgressBar(false);
                if(task.isSuccessful()){
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final  String url = uri.toString();
                            //mCurrentUserDetails.setImageUrl(uri.toString());
                            mDatabaseReference.child(mCurrentUser.getUid()).child("imageUrl").setValue(url).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.v("Upload Image : ", "imageUrlGottenAndUploaded: "+getUserData().getImageUrl());
                                    mUploadPhotoCallback.uploadSuccessful(url);

                                }
                            });
                        }
                    });
                }

                else {
                    Log.v("Upload Image : ", "UploadFailed: ");

                    mUploadPhotoCallback.uploadFailed(Objects.requireNonNull(task.getException()).getLocalizedMessage());

                }

            }
        });

    }

    public static void saveDoctorToDb(Doctor doctor) {
        Task<Void> task = doctorsDbRef.push().setValue(doctor);

        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.v("doctor upload is ", "sucessful");
                } else {
                    Log.v("doctor upload is ", "failed");
                }
            }
        });
    }

    public static void  getDoctor(String sortType, SortDoctorCallback callback){
        sortDoctorCallback = callback;
        Query query = doctorsDbRef. orderByChild("category").equalTo(sortType);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.v("doctor details gotten", Objects.requireNonNull(snapshot.getValue(Doctor.class)).getName());
                sortDoctorCallback.onChildAdded(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
