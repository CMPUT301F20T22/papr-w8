package com.example.papr_w8;

import android.content.ContentResolver;
import android.content.Intent;
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

/**
 * AddBook class handles adding a book from the addbook activity
 */
public class AddBook extends AppCompatActivity {

    public static final String TAG = "TAG";
    private Uri imageUri;
    private ImageButton addBookCover;
    private StorageReference storageReference;
    private String fileName;

    /**
     * onCreate starts the code for adding a book functionality
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final EditText newBookTitle = findViewById(R.id.new_title_editText);
        final EditText newBookISBN = findViewById(R.id.new_isbn_editText);
        final EditText newBookAuthor = findViewById(R.id.new_author_editText);
        Button cancel = findViewById(R.id.cancel_addbook_button);
        Button confirm = findViewById(R.id.confirm_addbook_button);
        addBookCover = findViewById(R.id.imageButton);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseFirestore fbDB = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("images");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();

        addBookCover.setOnClickListener(new View.OnClickListener() {  // onClickListener for when the user clicks on the add book cover image buttor
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBook.this, AddBookCoverActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {  // onClickListener for when the user clicks on the confirm button to add a book
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
                    Map<String, Object> book = new HashMap<>();
                    book.put("Title", title);
                    book.put("Author", author);
                    book.put("ISBN", ISBN);
                    book.put("Status", "Available");
                    book.put("Book Cover", fileName);

                    // Implementation for adding book details to the firestore collection
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

        cancel.setOnClickListener(new View.OnClickListener() {  // onClickListener for cancel button if the user wants to cancel adding a book
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * Gets file extension from the image uri
     * @param uri
     * @return
     */
    private String getFileExt(Uri uri) {  // gets file extension of image
        ContentResolver cr = getContentResolver();
        MimeTypeMap mtp = MimeTypeMap.getSingleton();
        return mtp.getExtensionFromMimeType(cr.getType(uri));
    }

    /**
     * this gets the filename and uploads the image to firebase
     */
    private void uploadCover() {
        if (imageUri != null) {
            fileName = System.currentTimeMillis() + "." + getFileExt(imageUri);
            StorageReference fileRef = storageReference.child(fileName); //sets the filename of the image
            fileRef.putFile(imageUri)  //uploads the image to firebase using imageUri
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