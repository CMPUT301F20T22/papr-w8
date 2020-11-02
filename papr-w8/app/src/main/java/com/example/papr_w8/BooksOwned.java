package com.example.papr_w8;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BooksOwned extends Fragment {
    public BooksOwned(){
    }
    private FloatingActionButton floating_add_book;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_books_owned, container, false);
        floating_add_book = view.findViewById(R.id.add_book_float);
        floating_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddBook.class);
                startActivity(intent);
            }
        });


        return view;
    }
}