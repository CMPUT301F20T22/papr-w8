package com.example.papr_w8.BookView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
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

import static com.example.papr_w8.ProfilePack.EditProfile.EXTRA_TEXT;

public class RequestConfirmView extends AppCompatActivity implements OnMapReadyCallback {
    //GoogleMap.OnMarkerClickListener,

    GoogleMap map;
    private Marker bookLoc;
    private Button setLoc;
    Book book;
    LatLng pos;

    public RequestConfirmView() {

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirm_view);

        final Book book = (Book) getIntent().getSerializableExtra("book");

        Log.d("hello", book.getOwner());

        setLoc = (Button) findViewById(R.id.set_loc_button);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.confirmMap);
        supportMapFragment.getMapAsync(this);

//        pos = bookLoc.getPosition();
        if (bookLoc!=null) {
            pos = bookLoc.getPosition();
            book.setLocation(pos);
            Log.d("book loc", book.getLocation().toString());
        }
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
                    public boolean onMarkerClick(Marker marker) {
                        Integer data = (Integer) marker.getTag();
                        marker.setTitle("book_loc");

                        if (data != null) {
//                            pos = marker.getPosition();
//                            book.setLocation(pos);
                            Intent intent = new Intent(RequestConfirmView.this, Host.class);
                            intent.putExtra(EXTRA_TEXT, "Shelves");
                            startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });
    }
}
//    /** Called when the user clicks a marker. */
//    @Override
//    public boolean onMarkerClick(final Marker marker) {
//        Integer data = (Integer) marker.getTag();
//
//        if (data != null) {
//            pos = marker.getPosition();
//            book.setLocation(pos);
//            Intent intent = new Intent(RequestConfirmView.this, Host.class);
//            intent.putExtra(EXTRA_TEXT, "Shelves");
//            startActivity(intent);
//        }
//        return false;
//    }
//}