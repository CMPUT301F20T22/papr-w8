package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

/**
 * Fragment that allows user to view an accepted book
 */
public class BookCheckoutView extends BookBase {
    private static final String TAG = "MyTag" ;
    private FirebaseAuth firebaseAuth;
    private final int SCAN_ISBN_FOR_BORROW = 1;


    private Button buttonConfirmBorrow;
    private Button buttonCancelBorrow;


    private ImageView imageViewDefault;


    public BookCheckoutView() {
        // Required empty public constructor
    }


    @Override
    public void provideYourFragmentView(View rootView, ViewGroup container) {


        setRetainInstance(true);
        ViewStub stub = rootView.findViewById(R.id.child_fragment_here);
        stub.setLayoutResource(R.layout.fragment_book_checkout);
        stub.inflate();

        buttonConfirmBorrow = (Button) rootView.findViewById(R.id.confirmButton);
        buttonCancelBorrow = (Button) rootView.findViewById(R.id.cancel_checkout);


        // This onClickListener goes to ScanActivity
        // sending the user to Shelves
        buttonConfirmBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivityForResult(intent, SCAN_ISBN_FOR_BORROW);
            }
        });

        // This onClickListener performs the action of taking the user back to Shelves
        buttonCancelBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Cancel button
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_ISBN_FOR_BORROW){
            final String isbn = data.getStringExtra("ISBN");
            Log.d(TAG, "ISBN from scan: " + isbn);

            firebaseAuth = FirebaseAuth.getInstance();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String email = user.getEmail();

            final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                    .document(email).collection("Books_Accepted").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //add book items from database
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    if (isbn.matches(document.getString("ISBN"))){
                                        String owner = (String) document.get("Owner");
                                        Log.d(TAG, "owner_email" + owner);
                                        notifyOwner(owner, email);
                                    }

                                }
                            }
                        }
                    });

        }
    }

    public void notifyOwner(final String owner_email, final String user_email) {
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
                            notification.put("type", "borrow_scan");
                            notification.put("Book Title", book.getTitle());

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