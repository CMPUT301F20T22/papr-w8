package com.example.papr_w8;

/**
 * may merge this fragment with shelves next sprint
 * page that displays user options
 * clicking on a button takes user to other fragment/activities
*/
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PageOne extends Fragment {
    public PageOne() {
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

        View view =  inflater.inflate(R.layout.fragment_page_one, container, false);

        //Buttons to take user to other pages
        owned =  (Button) view.findViewById(R.id.books_owned);
        borrowed =  (Button) view.findViewById(R.id.books_borrowed);
        requested =  (Button) view.findViewById(R.id.books_requested);
        add_book =  (Button) view.findViewById(R.id.add_new_book);
        //REMOVE FIND BOOK ???
        // find = (Button) view.findViewById(R.id.find_book);
        accepted = (Button) view.findViewById(R.id.accept_request);

        //go to BooksOwned fragment
        owned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BooksOwned booksOwned = new BooksOwned();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.my_books,booksOwned,booksOwned.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Go to BooksBorrowed fragment
        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BooksBorrowed booksBorrowed = new BooksBorrowed();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.my_books,booksBorrowed,booksBorrowed.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Go to BooksRequested fragment
        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BooksRequested booksRequested = new BooksRequested();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.my_books,booksRequested,booksRequested.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //GO to AddBook activity
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddBook.class);
                startActivity(intent);
            }
        });

        //got to FindBook fragment REMOVE ??????
//        find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FindBook findBook = new FindBook();
//                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                ft.replace(R.id.my_books,findBook,findBook.getTag());
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        });

        //go to books AcceptRequest Fragment
        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptRequest acceptRequest = new AcceptRequest();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.my_books,acceptRequest,acceptRequest.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}