package com.example.papr_w8.BookView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.papr_w8.Book;
import com.example.papr_w8.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * This is a Fragment that displays the view of a basic Book Description
 */
public class BookBasicMapView extends BookBase implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_book_basic_map, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // Get the bundle containing the Book object passed to the View
        Bundle bundle = this.getArguments();
        Book book = (Book) bundle.getSerializable("bookSelected");

    }

    @Override
    public void provideYourFragmentView(View baseView, ViewGroup container){

        setRetainInstance(true);
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);
        stub.setLayoutResource(R.layout.fragment_book_basic_map);
        stub.inflate();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng bookLoc = book.getLocation();
        map.addMarker(new MarkerOptions().position(bookLoc).title("book location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(bookLoc));
    }
}