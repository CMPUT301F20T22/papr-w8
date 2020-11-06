package com.example.papr_w8;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Placeholder, implementation to be added next sprint
 * Will show books user has requested to borrow
*/
public class BooksRequested extends Fragment {
    public BooksRequested(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_books_requested, container, false);

        return view;
    }
}