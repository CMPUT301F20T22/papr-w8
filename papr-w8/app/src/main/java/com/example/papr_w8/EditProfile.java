package com.example.papr_w8;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public EditProfile(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference userDoc = FirebaseFirestore.getInstance().collection("Users").document(user.getEmail());

        final EditText username = (EditText) findViewById(R.id.usernameEdit);
//        EditText phone = (EditText) findViewById(R.id.phoneEdit);
        final EditText email = (EditText) findViewById(R.id.emailEdit);
        final EditText address = (EditText) findViewById(R.id.addressEdit);




        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        username.setText(doc.getString("name"));
                        address.setText(doc.getString("address"));
                        email.setText(user.getEmail());
                        Log.d("Sample", "DocumentSnapshot data: " + doc.getData());
                    } else {
                        Log.d("Sample", "No such document");
                    }
                } else {
                    Log.d("Sample", "get failed with ", task.getException());
                }
            }
        });

        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameET = username.getText().toString();
                final String emailET = email.getText().toString();
                final String addressET = address.getText().toString();

                if (TextUtils.isEmpty(usernameET)) {
                    username.setError("Invalid username.");
                    username.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(emailET)) {
                    email.setError("Invalid email.");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailET).matches()) {
                    email.setError("Please provide valid email.");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(addressET)) {
                    address.setError("Invalid address.");
                    address.requestFocus();
                    return;
                }


//                UserProfileChangeRequest updates = new UserProfileChangeRequest.Builder()
////                        .setPhotoUri(Uri.parse(""))
//                        .build();
//                // the above part is for user to update profile picture, unsure of how exactly we're going to do it so I'm commenting it for now
////                firebaseAuth.createUserWithEmailAndPassword(email, password)
////                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
////                            @Override
////                            public void onComplete(@NonNull Task<AuthResult> task) {
//                firebaseAuth.updateCurrentUser(user)
////                user.updateProfile(updates)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    HashMap<String, Object> user_info = new HashMap<>();
//                                    user_info.put("name", usernameET);
//                                    user_info.put("email", emailET);
//                                    user_info.put("address", addressET);
//                                    firebaseFirestore.collection("Users")
//                                            .document(user.getEmail())
//                                            .update(user_info)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Log.d("change profile", "User profile updated.");
//                                                    Toast.makeText(EditProfile.this, "Update successful!",
//                                                            Toast.LENGTH_SHORT).show();
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.d("change profile", "Data storing failed");
//                                                }
//                                            });
//                                    finish();
//
//                                }
//                            }
//                        });
                HashMap<String, Object> user_info = new HashMap<>();
                user_info.put("name", usernameET);
                user_info.put("email", emailET);
                user_info.put("address", addressET);
                firebaseFirestore.getInstance().collection("Users")
                        .document(user.getEmail())
                        .update(user_info)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("change profile", "User profile updated.");
                                Toast.makeText(EditProfile.this, "Update successful!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("change profile", "Data storing failed");
                            }
                        });
                finish();

            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}