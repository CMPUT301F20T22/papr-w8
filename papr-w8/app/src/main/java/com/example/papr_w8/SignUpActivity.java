package com.example.papr_w8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is an activity that allows new users to create an account
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameET;
    private EditText passwordET;
    private EditText emailET;
    private EditText addressET;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Button sign_up_button;
    private final static String TAG = "my_message";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        usernameET = findViewById(R.id.editTextUserName);
        passwordET = findViewById(R.id.editTextPassword);
        emailET = findViewById(R.id.editTextEmailAddress);
        addressET = findViewById(R.id.editTextPostalAddress);

        sign_up_button = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.signup_progress_bar);
        progressBar.setVisibility(View.GONE);


        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameET.getText().toString();
                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();
                final String address = addressET.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameET.setError("Invalid username.");
                    usernameET.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordET.setError("Invalid password.");
                    passwordET.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    passwordET.setError("Password must be at least 6 characters.");
                    passwordET.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailET.setError("Invalid email.");
                    emailET.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("Please provide valid email.");
                    emailET.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    addressET.setError("Invalid address.");
                    addressET.requestFocus();
                    return;
                }
                // check username doesn't already exist
                Query usernames = firebaseFirestore.collection("Users")
                        .whereEqualTo("username",username);
                usernames.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            boolean b = task.getResult().isEmpty();
                            if (b == false){
                                usernameET.setError("Username already exists.");
                                usernameET.requestFocus();
                            }
                        }
                        else{
                            Log.d(TAG,"Error getting documents: ",task.getException());
                        }

                    }
                });
                // check if email exists before creating new user
                Query emails = firebaseFirestore.collection("Users")
                        .whereEqualTo("email",email);
                emails.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            boolean b = task.getResult().isEmpty();
                            if (b == false){
                                emailET.setError("There is already an account under that email.");
                                emailET.requestFocus();
                            }
                        }
                        else{
                            Log.d(TAG,"Error getting documents: ",task.getException());
                        }

                    }
                });
                // Create new user with email and password
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    HashMap<String, String> user_info = new HashMap<>();
                                    user_info.put("name", username);
                                    user_info.put("email", email);
                                    user_info.put("password", password);
                                    user_info.put("address", address);
                                    firebaseFirestore.collection("Users")
                                            .document(email)
                                            .set(user_info)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "Data has been added successfully");
                                                    Toast.makeText(SignUpActivity.this, "Sign up successful!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Data storing failed");
                                                }
                                            });
                                    startActivity(new Intent(SignUpActivity.this, Host.class));
                                    finish();
                                }
                            }


                        });

            }


        });



    }
}
