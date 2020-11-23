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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Login Activity, the first page a user sees
 */

public class MainActivity extends AppCompatActivity {
    private Button login_button, sign_up_button;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth firebaseAuth;
    private final static String TAG = "my_message";
    private ArrayList<Book> booklist;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        login_button = findViewById(R.id.login_button);
        sign_up_button = findViewById(R.id.sign_up_button);

        firebaseAuth = FirebaseAuth.getInstance();

        booklist = new ArrayList();


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is required.");
                    emailEditText.requestFocus();
                    return;
                };
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEditText.setError("Invalid email.");
                    emailEditText.requestFocus();
                    return;
                };
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required.");
                    emailEditText.requestFocus();
                    return;
                };

                //Authenticate user
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent intent = new Intent(MainActivity.this, Host.class);
                                    startActivity(intent);

                                } else {
                                    Log.w(TAG,"signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_up = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(sign_up);
            }
        });

    }
}