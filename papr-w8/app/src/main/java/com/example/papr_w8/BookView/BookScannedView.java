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
public class BookScannedView extends Fragment {

    public BookScannedView() {
    }

    private Button buttonLend;
    private Button buttonRequest;
    private Button buttonCancel;

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;

    private ImageView imageViewDefault;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_scanned, container, false);

        buttonLend = (Button) view.findViewById(R.id.lendButton);
        buttonRequest = (Button) view.findViewById(R.id.requestButton);
        buttonCancel = (Button) view.findViewById(R.id.cancelButton);

        textViewTitle = view.findViewById(R.id.titleEditText);
        textViewAuthor = view.findViewById(R.id.authorEditText);
        textViewISBN = view.findViewById(R.id.isbnEditText);
        textViewStatus = view.findViewById(R.id.statusEditText);

        // This onClickListener performs the action of updating a Book to Lend it out
        // Sending the user back to the MainActivity
        buttonLend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Lend button
            }
        });

        // This onClickListener performs the action of updating a Book to being requested and
        // adding it to the Requested Books collection
        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Request button
            }
        });

        // This onClickListener performs the action of taking the user back to the MainActivity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Cancel Button
            }
        });

        return view;
    }
}