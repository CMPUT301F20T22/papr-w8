package com.example.papr_w8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameET;
    private EditText passwordET;
    private EditText emailET;
    private EditText addressET;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDatabase;
    private Button sign_up_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        usernameET = findViewById(R.id.editTextUserName);
        passwordET = findViewById(R.id.editTextPassword);
        emailET = findViewById(R.id.editTextEmailAddress);
        addressET = findViewById(R.id.editTextTextPostalAddress);

        sign_up_button = findViewById(R.id.sign_up_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseFirestore.getInstance();

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String address = addressET.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameET.setError("Invalid username.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordET.setError("Invalid password.");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailET.setError("Invalid email.");
                }
                if (TextUtils.isEmpty(address)) {
                    addressET.setError("Invalid address.");
                }
                Query usernameQuery = firebaseDatabase.


            }



        });
    }
}