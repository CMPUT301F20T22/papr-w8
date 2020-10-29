package com.example.papr_w8;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameET;
    private EditText passwordET;
    private EditText emailET;
    private EditText addressET;
    private FirebaseAuth firebaseAuth;
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
                if (password.length() < 6){
                    passwordET.setError("Password must be at least 6 characters.");
                    passwordET.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailET.setError("Invalid email.");
                    emailET.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(username, password, email, address);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //redirect to success page
                                            } else {
                                                Toast.makeText(SignUpActivity.this, "Unable to sign up.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                }
                                else{
                                    Toast.makeText(SignUpActivity.this, "Failed to sign up.", Toast.LENGTH_LONG).show();
                                }
                            }


                        });


            }


        });
    }
}