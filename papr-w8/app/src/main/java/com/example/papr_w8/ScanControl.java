package com.example.papr_w8;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Fragment that
 */
public class ScanControl extends Fragment {

    private String ISBN;

    private final int VIEW_DESCRIPTION_REQUEST_CODE = 100;
    private final int BORROW_REQUEST_CODE = 200;
    private final int RETURN_REQUEST_CODE = 300;

    private Button get_isbn;
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

        get_isbn = view.findViewById(R.id.get_isbn_button);

        borrow = view.findViewById(R.id.borrow_button);

        return_book = view.findViewById(R.id.return_button);

        get_isbn.setOnClickListener(new View.OnClickListener() {
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