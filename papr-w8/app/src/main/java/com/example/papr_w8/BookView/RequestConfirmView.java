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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.papr_w8.ProfilePack.EditProfile.EXTRA_TEXT;

public class RequestConfirmView extends AppCompatActivity implements OnMapReadyCallback {
    //GoogleMap.OnMarkerClickListener,

    GoogleMap map;
    private Marker bookLoc;
    private Button setLoc;
    private Book book;
    private LatLng pos;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private View.OnClickListener handleClick;

    public RequestConfirmView(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirm_view);

        book = (Book) savedInstanceState.getSerializable("book");
        user = FirebaseAuth.getInstance().getCurrentUser();

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.confirmMap);
        supportMapFragment.getMapAsync(this);

        setLoc = (Button) findViewById(R.id.set_loc_button);
        setLoc.setOnClickListener(handleClick);

        Log.d("hello", book.getOwner());

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


                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        final Integer data = (Integer) marker.getTag();
                        marker.setTitle("book_loc");

                        handleClick = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setLoc.setVisibility(View.VISIBLE);

                                if (data != null) {
                                    pos = marker.getPosition();
//                            book.setLocation(pos);
                                    Toast.makeText(RequestConfirmView.this, "Please tap on marker to set your book location",
                                            Toast.LENGTH_SHORT).show();

                                    if ((book!=null)&(user!=null)) {
//                                HashMap<String, Object> book_info = new HashMap<>();
//                                book_info.put("Location", pos);
                                        firebaseFirestore.getInstance().collection("Users")
                                                .document(user.getEmail())
                                                .collection("Books_Requested")
                                                .document(book.getId())
                                                .update("Location", pos)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("change book location", "book location updated.");
                                                        Toast.makeText(RequestConfirmView.this, "Update successful!",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("change book location", "Data storing failed");
                                                    }
                                                });
                                        Intent intent = new Intent(RequestConfirmView.this, Host.class);
                                        intent.putExtra(EXTRA_TEXT, "Shelves");
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(RequestConfirmView.this, "book object is null",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        };

//                        if (data != null) {
//                            pos = marker.getPosition();
////                            book.setLocation(pos);
//                            Toast.makeText(RequestConfirmView.this, "Please tap on marker to set your book location",
//                                    Toast.LENGTH_SHORT).show();
//
//                            if ((book!=null)&(user!=null)) {
////                                HashMap<String, Object> book_info = new HashMap<>();
////                                book_info.put("Location", pos);
//                                firebaseFirestore.getInstance().collection("Users")
//                                        .document(user.getEmail())
//                                        .collection("Books_Requested")
//                                        .document(book.getId())
//                                        .update("Location", pos)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Log.d("change book location", "book location updated.");
//                                                Toast.makeText(RequestConfirmView.this, "Update successful!",
//                                                        Toast.LENGTH_SHORT).show();
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.d("change book location", "Data storing failed");
//                                            }
//                                        });
//                                Intent intent = new Intent(RequestConfirmView.this, Host.class);
//                                intent.putExtra(EXTRA_TEXT, "Shelves");
//                                startActivity(intent);
//                            }else{
//                                Toast.makeText(RequestConfirmView.this, "book object is null",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
                        return false;
                    }
                });
            }
        });
    }
}