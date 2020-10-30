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
    private Button owned ;
    private Button borrowed;
    private Button requested;
    private Button add_book;
    private Button find ;
    private Button accepted;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_my_books, container, false);
        owned =  (Button) view.findViewById(R.id.books_owned);
        borrowed =  (Button) view.findViewById(R.id.books_borrowed);
        requested =  (Button) view.findViewById(R.id.books_requested);
        add_book =  (Button) view.findViewById(R.id.add_new_book);
        find = (Button) view.findViewById(R.id.find_book);
        accepted = (Button) view.findViewById(R.id.accept_request);


        owned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BooksOwned.class);
                startActivity(intent);
            }
        });
        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BooksBorrowed.class);
                startActivity(intent);
            }
        });
        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BooksRequested.class);
                startActivity(intent);
            }
        });
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddBook.class);
                startActivity(intent);
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindBook.class);
                startActivity(intent);
            }
        });
        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AcceptRequest.class);
                startActivity(intent);
            }
        });


        return view;
    }
}