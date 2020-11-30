package com.example.papr_w8.BookView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.papr_w8.Book;
import com.example.papr_w8.R;
import com.example.papr_w8.ScanActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Fragment that allows user to view an accepted book and to borrow a book by scanning
 */
public class BookCheckoutView extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MyTag" ;
    private FirebaseAuth firebaseAuth;
    private final int SCAN_ISBN_FOR_BORROW = 1;
    private String book_id;
    private String user_name;
    private Book book;
    private String user_email;

    GoogleMap map;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_book_checkout, container, false);
        Button buttonConfirmBorrow = view.findViewById(R.id.confirmButton);
        Bundle bundle = this.getArguments();
        book = (Book) bundle.getSerializable("bookSelected");

        book_id = book.getId();

        // This onClickListener goes to ScanActivity
        buttonConfirmBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivityForResult(intent, SCAN_ISBN_FOR_BORROW);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_email = user.getEmail();

        TextView textViewTitle = view.findViewById(R.id.titleEditText);
        TextView textViewAuthor = view.findViewById(R.id.authorEditText);
        TextView textViewISBN = view.findViewById(R.id.isbnEditText);
        TextView textViewStatus = view.findViewById(R.id.statusEditText);
        final ImageView bookBaseCover = view.findViewById(R.id.frontImageView);


        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewISBN.setText(book.getISBN());
        textViewStatus.setText(book.getStatus());

        //set book cover to imageview
        FirebaseStorage.getInstance().getReference("images/" + book.getCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(bookBaseCover);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    return view;
    }

    /**
     * On successful scan, notify the Owner of the book
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_ISBN_FOR_BORROW) {
            if (resultCode == RESULT_OK) {

                final String owner_email = book.getOwner();
                final String scanned_isbn = data.getStringExtra("ISBN");

                FirebaseFirestore.getInstance()
                        .collection("Users")
                        .document(user_email)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                user_name = documentSnapshot.getString("name");
                            }
                        });

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
     * Notifies the owner by adding Notification of type "borrow_scan" to Notifications collection
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
                            notification.put("Type", "borrow_scan");
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


    /**
     * Adds marker to map
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng location = new LatLng(book.getLatitude(), book.getLongitude());
        map.addMarker(new MarkerOptions().position(location).title("book location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}