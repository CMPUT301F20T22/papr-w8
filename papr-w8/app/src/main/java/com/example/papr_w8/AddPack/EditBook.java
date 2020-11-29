package com.example.papr_w8.AddPack;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
import com.example.papr_w8.R;
import com.example.papr_w8.ScanActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * Edit book handles editing book description functionality
 */
public class EditBook extends AppCompatActivity {

    public static final String TAG = "TAG";
    private Uri imageUri;
    private ImageButton editBookCover;
    private StorageReference storageReference;
    private String fileName;
    private EditText newBookISBN;

    private final int SCAN_ISBN_REQUEST_CODE = 2;

    /**
     * onCreate starts the code for adding a book functionality
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        final Book book = (Book) getIntent().getSerializableExtra("Book");

        final EditText newBookTitle = findViewById(R.id.edit_title_editText);
        newBookISBN = findViewById(R.id.edit_isbn_editText);
        final EditText newBookAuthor = findViewById(R.id.edit_author_editText);
        Button cancel = findViewById(R.id.cancel_editBook_button);
        Button confirm = findViewById(R.id.confirm_editBook_button);
        Button scan = findViewById(R.id.scan_edit_isbn);
        editBookCover = findViewById(R.id.editImageButton);
        ImageView deleteBookCover = findViewById(R.id.delete_image_eb);
        final int add_id = getResources().getIdentifier("@android:drawable/ic_menu_add", null, null);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fbDB = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("images");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();

        Log.d("Debug", book.getId());

        // set current book description
        newBookTitle.setText(book.getTitle());
        newBookISBN.setText(book.getISBN());
        newBookAuthor.setText(book.getAuthor());
        FirebaseStorage.getInstance().getReference("images/" + book.getCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(editBookCover);
                fileName = book.getCover();
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditBook.this, ScanActivity.class);
                startActivityForResult(intent, SCAN_ISBN_REQUEST_CODE);
            }
        });

        editBookCover.setOnClickListener(new View.OnClickListener() {  // onClickListener for when the user clicks on the add book cover image buttor
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditBook.this, AddBookCoverActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        deleteBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = "default_book.png";
                editBookCover.setImageResource(add_id);
                imageUri = null;
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
                    if (imageUri == null) {  // if no image cover uploaded then set to default
                        fileName = "default_book.png";
                    }
                    final Map<String, Object> e_book = new HashMap<>();
                    e_book.put("Title", title);
                    e_book.put("Author", author);
                    e_book.put("ISBN", ISBN);
                    e_book.put("Status", book.getStatus());
                    e_book.put("Book Cover", fileName);

                    // Implementation for editing book details to the firestore collection
                    fbDB.collection("Users").document(email).collection("Books Owned").document(book.getId())
                            .update(e_book)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    fbDB.collection("Books").document(book.getId()) //adds book to "Books" collections too
                                            .update(e_book);
                                    Toast.makeText(EditBook.this, "Book Edited", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditBook.this, "Book Edit Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
//                    uploadCover();
                    Intent intent = new Intent(EditBook.this, Host.class);
                    startActivity(intent);
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
                Picasso.get().load(imageUri).into(editBookCover);
                uploadCover();
            }
        } else if (requestCode == SCAN_ISBN_REQUEST_CODE) {
            String isbn = data.getStringExtra("ISBN");
            newBookISBN.setText(isbn);
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
//                            Toast.makeText(AddBook.this, "Cover Uploaded", Toast.LENGTH_SHORT).show();
                            Log.d("CoverDEBUG", "Cover Uploaded");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(AddBook.this, "Cover Upload Failed", Toast.LENGTH_SHORT).show();
                            Log.d("CoverDEBUG", "Cover Upload Failed");
                        }
                    });
        } else {
            fileName = "default_book.png";
//            Toast.makeText(this, "No book cover selected", Toast.LENGTH_SHORT).show();
            Log.d("CoverDEBUG", "No book cover selected");

        }
    }
}