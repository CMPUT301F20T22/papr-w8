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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

                                    final FirebaseUser user_instance = FirebaseAuth.getInstance().getCurrentUser();
                                    final Task<QuerySnapshot> owned_books = FirebaseFirestore.getInstance()
                                            .collection("Users").document(email).collection("Books Owned").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        for (QueryDocumentSnapshot document: task.getResult()){
                                                            booklist.add(new Book(document.getString("Title"),document.getString("Author"),
                                                                    document.getString("ISBN"),document.getString("Status"),
                                                                    document.getString("Book Cover")));
                                                        }
                                                    }
                                                }
                                            });
                                    final Task<QuerySnapshot> requested_books = FirebaseFirestore.getInstance()
                                            .collection("Users").document(email).collection("Requested").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        for (QueryDocumentSnapshot document: task.getResult()){
                                                            booklist.add(new Book(document.getString("Title"),document.getString("Author"),
                                                                    document.getString("ISBN"),document.getString("Status"),
                                                                    document.getString("Book Cover")));
                                                        }
                                                    }
                                                }
                                            });
                                    final Task<QuerySnapshot> borrowed_books = FirebaseFirestore.getInstance()
                                            .collection("Users").document(email).collection("Borrowed").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        for (QueryDocumentSnapshot document: task.getResult()){
                                                            booklist.add(new Book(document.getString("Title"),document.getString("Author"),
                                                                    document.getString("ISBN"),document.getString("Status"),
                                                                    document.getString("Book Cover")));
                                                        }
                                                    }
                                                }
                                            });
                                    final Task<QuerySnapshot> accepted = FirebaseFirestore.getInstance()
                                            .collection("Users").document(email).collection("Borrowed").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        for (QueryDocumentSnapshot document: task.getResult()){
                                                            booklist.add(new Book(document.getString("Title"),document.getString("Author"),
                                                                    document.getString("ISBN"),document.getString("Status"),
                                                                    document.getString("Book Cover")));
                                                        }
                                                    }
                                                }
                                            });
                                    final Task<DocumentSnapshot> user_data = FirebaseFirestore.getInstance()
                                            .collection("Users").document(email).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult();
                                                        user = new User(document.getString("username"), document.getString("password"),
                                                                    document.getString("email"), document.getString("address"), booklist);
                                                    }

                                                }
                                            });

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("active_user", (Serializable) user);
                                    Intent intent = new Intent(MainActivity.this, Host.class);
                                    intent.putExtras(bundle);
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