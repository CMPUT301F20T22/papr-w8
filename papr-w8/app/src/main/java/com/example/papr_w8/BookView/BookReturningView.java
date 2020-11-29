package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.papr_w8.R;
import com.example.papr_w8.ScanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * This is a Fragment that displays the view of a Book Description that is being returned allowing
 * the user to Return the Book by scanning or to Cancel this transaction.
 */
public class BookReturningView extends BookBase{
    private static final String TAG = "MyTag";
    private FirebaseAuth firebaseAuth;
    private final int SCAN_ISBN_FOR_RETURN = 1;
    private String book_id;

    private Button buttonReturn;
    private Button buttonCancel;


    public BookReturningView() {
    }


    /**
     * Sets the view of the fragment
     * @param rootView
     * @param container
     */
    @Override
    public void provideYourFragmentView(View rootView, ViewGroup container) {

        setRetainInstance(true);
        ViewStub stub = rootView.findViewById(R.id.child_fragment_here);
        stub.setLayoutResource(R.layout.fragment_book_returning);
        stub.inflate();


        buttonReturn = (Button) rootView.findViewById(R.id.returnButton);
        buttonCancel = (Button) rootView.findViewById(R.id.cancelButton);

        book_id = book.getId();


        // This onClickListener performs the action of updating the status of a Book to Returned
        // by taking the user to a barcode scanner where they will scan the book ISBN
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivityForResult(intent, SCAN_ISBN_FOR_RETURN);
            }
        });

        // This onClickListener performs the action of taking the user back to the previous activity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }

    /**
     * On a successful scan, notify the Owner of the book
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_ISBN_FOR_RETURN) {
            if (resultCode == RESULT_OK) {

                final String owner_email = book.getOwner();

                final String scanned_isbn = data.getStringExtra("ISBN");

                firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String user_email = user.getEmail();
                final String user_name = user.getDisplayName();

                final Task<DocumentSnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                        .document(owner_email)
                        .collection("Books Owned")
                        .document(book_id)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists() & document.getString("ISBN").matches(scanned_isbn)) {
                                        notifyOwner(owner_email, user_email, user_name, book_id);
                                    } else {
                                        Toast.makeText(getContext(), "This book does not belong to the listed owner.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

            }
        }
    }

    /**
     * Notifies the borrower by adding a notification of type "return_scan" to Notifications collection
     * @param owner_email
     * @param user_email
     * @param user_name
     * @param book_id
     */
    public void notifyOwner(final String owner_email, final String user_email, final String user_name, final String book_id) {
        Task<DocumentSnapshot> user = FirebaseFirestore.getInstance().collection("Users")
                .document(owner_email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> notification = new HashMap<>();
                            notification.put("Sender", user_email);
                            notification.put("Name", user_name);
                            notification.put("Type", "return_scan");
                            notification.put("Book Title", book.getTitle());
                            notification.put("Book ID", book_id);

                            db.collection("Users")
                                    .document(owner_email)
                                    .collection("Notifications")
                                    .add(notification)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(getContext(), "Owner has been notified.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Unable to notify owner.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
    }
}