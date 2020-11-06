package com.example.papr_w8.BookView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.papr_w8.R;


/**
 * This is a Fragment that displays the view of a Book Description that has been Accepted and needs
 * to be Confirmed by the Borrower with a Location.
 */
public class BookCheckoutView extends Fragment {

    public BookCheckoutView() {
    }

    private Button buttonConfirmBorrow;
    private Button buttonCancelBorrow;
    private Button buttonCancel;

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;

    private ImageView imageViewDefault;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_owned, container, false);

        buttonConfirmBorrow = (Button) view.findViewById(R.id.confirmButton);
        buttonCancelBorrow = (Button) view.findViewById(R.id.cancelborrowButton);
        buttonCancel = (Button) view.findViewById(R.id.deleteButton);

        textViewTitle = view.findViewById(R.id.titleEditText);
        textViewAuthor = view.findViewById(R.id.authorEditText);
        textViewISBN = view.findViewById(R.id.isbnEditText);
        textViewStatus = view.findViewById(R.id.statusEditText);

        // This onClickListener performs the action of updating the status of a Book to Borrowed
        // sending the user to the MainActivity
        buttonConfirmBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Confirm Borrow button
            }
        });
        // This onClickListener performs the action of updating the status of a Book to Canceled
        // sending the user to the MainActivity
        buttonCancelBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Cancel Borrow button
            }
        });

        // This onClickListener performs the action of taking the user back to the MainActivity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Cancel button
            }
        });

        return view;
    }
}