package com.example.papr_w8;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity {

    private Book book;
    private Bitmap newCover;
    public static final String TAG = "TAG";

//    private EditText newBookTitle;
//    private EditText newISBNTitle;
//    private Button cancel;
//    private Button confirm_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);



        final EditText newBookTitle = findViewById(R.id.new_title_editText);
        final EditText newBookISBN = findViewById(R.id.new_isbn_editText);
        final EditText newBookAuthor = findViewById(R.id.new_author_editText);
        Button cancel = findViewById(R.id.cancel_addbook_button);
        Button confirm = findViewById(R.id.confirm_addbook_button);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fbDB = FirebaseFirestore.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference db = fbDB.collection("Books").document(uid);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = newBookTitle.getText().toString();
                String author = newBookAuthor.getText().toString();
                String ISBN = newBookISBN.getText().toString();

                if (title.isEmpty()) { //checks for valid title entry
                    newBookTitle.setError("Please enter title");
                    newBookTitle.requestFocus();
                    return;
                }
                else if (author.isEmpty()) { //checks for valid author entry
                    newBookAuthor.setError("Please enter author's name");
                    newBookAuthor.requestFocus();
                    return;

                } else if (ISBN.isEmpty()) { //checks for valid ISBN entry
                    newBookISBN.setError("Please enter valid ISBN");
                    newBookISBN.requestFocus();
                    return;
                } else {
//                    new Book(title, author, ISBN);
                    Map<String, Object> book = new HashMap<>();
                    book.put("Title", title);
                    book.put("Author", author);
                    book.put("ISBN", ISBN);

                    // Firebase implementation does not work properly:
                    db.collection("Books")
                            .add(book)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
