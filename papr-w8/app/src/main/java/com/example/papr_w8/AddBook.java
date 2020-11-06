package com.example.papr_w8;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity {

    private Book book;
    private Bitmap newCover;
    public static final String TAG = "TAG";
    private Uri imageUri;
    private ImageButton addBookCover;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String fileName;
//    EditText newBookTitle = findViewById(R.id.new_title_editText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final EditText newBookTitle = findViewById(R.id.new_title_editText);
//        final EditText newBookTitle = findViewById(R.id.new_title_editText);
        final EditText newBookISBN = findViewById(R.id.new_isbn_editText);
        final EditText newBookAuthor = findViewById(R.id.new_author_editText);
        Button cancel = findViewById(R.id.cancel_addbook_button);
        Button confirm = findViewById(R.id.confirm_addbook_button);
        addBookCover = findViewById(R.id.imageButton);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final FirebaseFirestore fbDB = FirebaseFirestore.getInstance();
        final String uid = firebaseAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference("images");
        databaseReference = FirebaseDatabase.getInstance().getReference("images");
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();

        addBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBook.this, AddBookCoverActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

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
                } else if (author.isEmpty()) { //checks for valid author entry
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
                    book.put("Status", "Available");
                    book.put("Book Cover", fileName);
                    // Firebase implementation, might need to change later:

                    fbDB.collection("Users").document(email).collection("Books Owned")
                            .add(book)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddBook.this, "Book Added", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddBook.this, "Book Add Failed", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String imageUriString = data.getStringExtra("coverUri");
                imageUri = Uri.parse(imageUriString);
                Picasso.get().load(imageUri).into(addBookCover);
                uploadCover();
            }
        }
    }

    private String getFileExt(Uri uri) {  // gets file extension of image
        ContentResolver cr = getContentResolver();
        MimeTypeMap mtp = MimeTypeMap.getSingleton();
        return mtp.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadCover() {
        if (imageUri != null) {
            fileName = System.currentTimeMillis() + "." + getFileExt(imageUri);
            StorageReference fileRef = storageReference.child(fileName);
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddBook.this, "Cover Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBook.this, "Cover Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No book cover selected", Toast.LENGTH_SHORT).show();
        }
    }
}