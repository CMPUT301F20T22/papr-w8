package com.example.papr_w8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.papr_w8.BookView.BookBasicView;
import com.example.papr_w8.BookView.BookOwnedView;
import com.example.papr_w8.BookView.BookScannedView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.io.Serializable;
import java.util.List;

/**
 * Fragment that allows user to view book, borrow, or return a book by scanning an ISBN
 */
public class Scan extends Fragment {

    private String ISBN;

    private final int VIEW_DESCRIPTION_REQUEST_CODE = 100;
    private final int BORROW_REQUEST_CODE = 200;
    private final int RETURN_REQUEST_CODE = 300;

    private Button view_desc;
    private Button borrow;
    private Button return_book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scan, container, false);

        view_desc = view.findViewById(R.id.view_desc_button);

        borrow = view.findViewById(R.id.borrow_button);

        return_book = view.findViewById(R.id.return_button);

        view_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                intent.putExtra("request", "view description");
                startActivity(intent);
            }
        });

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                intent.putExtra("request", "borrow");
                startActivity(intent);
            }
        });

        return_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                intent.putExtra("request", "return");
                startActivity(intent);
            }
        });

        return view;
    }


}