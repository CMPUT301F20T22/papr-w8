package com.example.papr_w8;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyBooks extends Fragment {
    public MyBooks() {
    }
    public Button owned ;
    public Button borrowed;
    public Button requested;
    public Button add_book;
    public Button find ;
    public Button accepted;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_my_books, container, false);
        owned =  (Button) view.findViewById(R.id.books_owned);
        borrowed =  (Button) view.findViewById(R.id.books_borrowed);
        requested =  (Button) view.findViewById(R.id.books_requested);
        find = (Button) view.findViewById(R.id.find_book);
        accepted = (Button) view.findViewById(R.id.accept_request);
        add_book =  (Button) view.findViewById(R.id.add_new_book);
        //addbook button
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddBook.class);
                startActivity(intent);
            }
        });

        return view;
    }
}