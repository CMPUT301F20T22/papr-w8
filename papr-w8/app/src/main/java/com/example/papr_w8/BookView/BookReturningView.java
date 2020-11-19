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
 * This is a Fragment that displays the view of a Book Description that is being returned allowing
 * the user to Return the Book or to Cancel this transaction.
 */
public class BookReturningView extends Fragment {

    public BookReturningView() {
    }

    private Button buttonReturn;
    private Button buttonCancel;

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;
    private TextView textViewBorrower;

    private ImageView imageViewDefault;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_returning, container, false);

        buttonReturn = (Button) view.findViewById(R.id.returnButton);
        buttonCancel = (Button) view.findViewById(R.id.cancelButton);

        textViewTitle = view.findViewById(R.id.titleEditText);
        textViewAuthor = view.findViewById(R.id.authorEditText);
        textViewISBN = view.findViewById(R.id.isbnEditText);
        textViewStatus = view.findViewById(R.id.statusEditText);

        // This onClickListener performs the action of updating the status of a Book to Returned
        // by taking the user to a barcode scanner where they will scan the book ISBN
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the Return button
            }
        });

        // This onClickListener performs the action of taking the user back to the MainActivity
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the Cancel button
            }
        });

        return view;
    }
}