package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
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

import java.util.HashMap;
import java.util.Map;

import static com.example.papr_w8.ProfilePack.EditProfile.EXTRA_TEXT;

/**
 * Activity that allows owner to set a location once a request on a book has been accepted
 * Updates all book collections accordingly
 */

public class RequestConfirmView extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MyTag";
    //GoogleMap.OnMarkerClickListener,

    GoogleMap map;
    private Marker bookLoc;
    private Button setLoc;
    private Book book;
    private LatLng pos;
    private FirebaseUser user;
    private FirebaseFirestore fbDB;
    private View.OnClickListener handleClick;
    private String borrower_email;
    private String name;

    public RequestConfirmView() {

    }

    /**
     * this onCreateView is where the RequestConfirmView functionality begins
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirm_view);

        // Initialize Firestore database
        fbDB = FirebaseFirestore.getInstance();
        // Get the current user ID
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Get the book seleected
        book = (Book) getIntent().getSerializableExtra("book");
        borrower_email = getIntent().getStringExtra("borrower");

        // Get the id of the Set Location button
        setLoc = (Button) findViewById(R.id.set_loc_button);

        // Update the map
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.confirmMap);
        supportMapFragment.getMapAsync(this);

        // Get the name of the user
        fbDB.collection("Users")
                .document(user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        name = documentSnapshot.getString("name");
                    }
                });

        Intent intent = getIntent();

        //borrower_email = intent.getStringExtra("borrower");
    }

    /**
     * Allows user to select location and add a marker
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Set on click listener to handle interactions with the map
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

                // Set on click listener to handle setting the location after a pin is dropped
                setLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (bookLoc != null) {
                            pos = bookLoc.getPosition();

                            if (book!=null) {
                                handleRequests(user.getEmail(), book.getId(), borrower_email, name, book.getTitle(), pos);
                                fbDB.collection("Users")
                                        .document(user.getEmail())
                                        .collection("Books_Requested")
                                        .document(book.getId())
                                        .delete();
                                deleteRequests(user.getEmail(), book.getId());

                                Intent intent = new Intent(RequestConfirmView.this, Host.class);
                                intent.putExtra(EXTRA_TEXT, "Shelves");
                                startActivity(intent);
                            }else{
                                Log.d("TAG", "Book object is null");
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * Class method that will notify the requester of their book status
     * @param bookId
     * @param borrower_email
     * @param user_name
     * @param book_title
     * @param type
     */
    public void notifyRequester(String bookId, String borrower_email, String user_name, String book_title, String type){
        Map<String, Object> notification = new HashMap<>();
        notification.put("Sender", user.getEmail());
        notification.put("Name", user_name);
        notification.put("Type", type);
        notification.put("Book Title", book_title);
        notification.put("Book Id", bookId);

        fbDB.collection("Users")
                .document(borrower_email)
                .collection("Notifications")
                .document()
                .set(notification);
    }

    /**
     * Class method that will update the Borrower's book lists according to the accepted request
     * @param borrower_email
     * @param book_id
     * @param location
     */
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

    /**
     * Declines all other requests and updates book Collections
     * @param user_email
     * @param bookId
     * @param borrower_email
     * @param user_name
     * @param title
     * @param location
     */
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
                                    fbDB.collection("Users")
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
                                FirebaseFirestore.getInstance()
                                        .collection("Users")
                                        .document(user_email)
                                        .collection("Books Owned")
                                        .document(bookId)
                                        .update("Status", "Accepted");

                            }
                        }
                    }
                });
    }

    /**
     * Deletes all requests from Firebase
     * @param user_email
     * @param book_id
     */
    public void deleteRequests(final String user_email, final String book_id){

        fbDB.collection("Users")
                .document(user_email)
                .collection("Books Owned")
                .document(book_id)
                .collection("Requested")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                //Log.d("Result: ", String.valueOf(task.getResult()));
                                QuerySnapshot documents = task.getResult();
                                for (DocumentSnapshot document : documents) {
                                    //Log.d("Document ID:", document.getId());
                                    fbDB.collection("Users")
                                            .document(user_email)
                                            .collection("Books Owned")
                                            .document(book_id)
                                            .collection("Requested")
                                            .document(document.getId())
                                            .delete();
                                }
                            }
                        }
                    }
                });
    }
}