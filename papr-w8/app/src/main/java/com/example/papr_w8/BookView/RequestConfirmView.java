package com.example.papr_w8.BookView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.util.Log;

import com.example.papr_w8.Adapters.NotificationDisplayList;
import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
import com.example.papr_w8.Notification;
import com.example.papr_w8.ProfilePack.EditProfile;
import com.example.papr_w8.Book;
import com.example.papr_w8.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.papr_w8.ProfilePack.EditProfile.EXTRA_TEXT;

public class RequestConfirmView extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MyTag";
    //GoogleMap.OnMarkerClickListener,

    GoogleMap map;
    private Marker bookLoc;
    private Button setLoc;
    private Book book;
    private LatLng pos;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private View.OnClickListener handleClick;
    private String borrower_email;
    private String name;

    public RequestConfirmView() {

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirm_view);

        book = (Book) getIntent().getSerializableExtra("book");

        Log.d("#####hello", book.getOwner());

        firebaseFirestore = FirebaseFirestore.getInstance();

        setLoc = (Button) findViewById(R.id.set_loc_button);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.confirmMap);
        supportMapFragment.getMapAsync(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseFirestore.collection("Users")
                .document(user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        name = documentSnapshot.getString("name");
                    }
                });

        Intent intent = getIntent();

        borrower_email = intent.getStringExtra("borrower");
        Log.d("Testing", "Testing in");
        Log.d("Testing", borrower_email);
        Log.d("Testing", "Testing out");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();  //create marker
                markerOptions.position(latLng); // set marker position
                markerOptions.title(latLng.latitude + ":" + latLng.longitude); //set lat and long on marker
                map.clear(); //if user decide to change marker, the previous marker will be cleared
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20)); //set zoom
                bookLoc = map.addMarker(markerOptions);
                bookLoc.setTag(0);

                setLoc.setVisibility(View.VISIBLE);

                setLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RequestConfirmView.this, "set location clicked",
                                Toast.LENGTH_SHORT).show();

                        if (bookLoc != null) {
                            pos = bookLoc.getPosition();
//                            book.setLocation(pos);
                            Toast.makeText(RequestConfirmView.this, pos.toString(),
                                    Toast.LENGTH_SHORT).show();

                            if (book!=null) {
//                                HashMap<String, Object> book_info = new HashMap<>();
//                                book_info.put("Location", pos);
                                handleRequests(user.getEmail(), book.getId(), borrower_email, name, book.getTitle(), pos);
                                firebaseFirestore.collection("Users")
                                        .document(user.getEmail())
                                        .collection("Books_Requested")
                                        .document(book.getId())
                                        .delete();

                                Intent intent = new Intent(RequestConfirmView.this, Host.class);
                                intent.putExtra(EXTRA_TEXT, "Shelves");
                                startActivity(intent);
                            }else{
                                Toast.makeText(RequestConfirmView.this, "book object is null",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


//                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(final Marker marker) {
//                        final Integer data = (Integer) marker.getTag();
//                        marker.setTitle("book_loc");
//
//                        Toast.makeText(RequestConfirmView.this, "Please tap on marker to set your book location",
//                                Toast.LENGTH_SHORT).show();
//
//                        setLoc.setVisibility(View.VISIBLE);
//
//                        setLoc.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(RequestConfirmView.this, "set location clicked",
//                                        Toast.LENGTH_SHORT).show();
//
//                                if (data != null) {
//                                    pos = marker.getPosition();
////                            book.setLocation(pos);
//                                    Toast.makeText(RequestConfirmView.this, "Please tap on marker to set your book location",
//                                            Toast.LENGTH_SHORT).show();
//
//                                    if ((book!=null)&(user!=null)) {
////                                HashMap<String, Object> book_info = new HashMap<>();
////                                book_info.put("Location", pos);
//                                        firebaseFirestore.getInstance().collection("Users")
//                                                .document(user.getEmail())
//                                                .collection("Books_Requested")
//                                                .document(book.getId())
//                                                .update("Location", pos)
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//                                                        Log.d("change book location", "book location updated.");
//                                                        Toast.makeText(RequestConfirmView.this, "Update successful!",
//                                                                Toast.LENGTH_SHORT).show();
//                                                    }
//                                                })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        Log.d("change book location", "Data storing failed");
//                                                    }
//                                                });
//                                        Intent intent = new Intent(RequestConfirmView.this, Host.class);
//                                        intent.putExtra(EXTRA_TEXT, "Shelves");
//                                        startActivity(intent);
//                                    }else{
//                                        Toast.makeText(RequestConfirmView.this, "book object is null",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        });
//
//                        return false;
//                    }

            }
        });
    }

    public void notifyRequester(String bookId, String borrower_email, String user_name, String book_title, String type){
        Map<String, Object> notification = new HashMap<>();
        notification.put("Sender", user.getEmail());
        notification.put("Name", user_name);
        notification.put("Type", type);
        notification.put("Book Title", book_title);
        notification.put("Book Id", bookId);

        Log.d("MyyyTag", borrower_email);

        firebaseFirestore.collection("Users")
                .document(borrower_email)
                .collection("Notifications")
                .document()
                .set(notification);
    }

    public void updateBorrowerBooks(final String borrower_email, final String book_id, LatLng location){
        //add book to Accepted Collection
        //delete book from Awaiting Approval
        final double latitude = location.latitude;
        final double longitude = location.longitude;
        final GeoPoint loc = new GeoPoint(latitude, longitude);
        Task<DocumentSnapshot> acc_book = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(borrower_email)
                .collection("Awaiting Approval")
                .document(book_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            Map<String, Object> book = new HashMap<>();
                            book.put("Title", document.getString("Title"));
                            book.put("Author", document.getString("Author"));
                            book.put("ISBN", document.getString("ISBN"));
                            book.put("Owner", document.getString("Owner"));
                            book.put("Status", "Accepted");
                            book.put("Book Cover", document.getString("Book Cover"));
                            book.put("Location", loc);
                            //Add to Borrowed Books collection
                            FirebaseFirestore.getInstance()
                                    .collection("Users")
                                    .document(borrower_email)
                                    .collection("Books_Accepted")
                                    .document(book_id)
                                    .set(book)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //delete from Accepted collection
                                            FirebaseFirestore.getInstance()
                                                    .collection("Users")
                                                    .document(borrower_email)
                                                    .collection("Awaiting Approval")
                                                    .document(book_id)
                                                    .delete();
                                        }

                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Failure to add book to Books_Borrowed collection.");
                                        }
                                    });

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failure to find document.");
                    }
                });

    }

    public void handleRequests(final String user_email, final String bookId, final String borrower_email,
                               final String user_name, final String title, final LatLng location){
        final Task<QuerySnapshot> requests = FirebaseFirestore.getInstance().collection("Users")
                .document(user_email).collection("Books Owned")
                .document(bookId)
                .collection("Requested")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String requestID = document.getId();
                                if (requestID.matches(borrower_email)){
                                    notifyRequester(bookId, requestID, user_name, title, "accepted" );
                                    updateBorrowerBooks(requestID, bookId, location);
                                }
                                else{
                                    notifyRequester(bookId, requestID, user_name, title, "declined" );
                                    firebaseFirestore.collection("Users")
                                            .document(requestID)
                                            .collection("Awaiting Approval")
                                            .document(bookId)
                                            .delete();
                                }
                                FirebaseFirestore.getInstance()
                                        .collection("Users")
                                        .document(user_email)
                                        .collection("Requested")
                                        .document(requestID)
                                        .delete();

                            }
                        }
                    }
                });

    }

}