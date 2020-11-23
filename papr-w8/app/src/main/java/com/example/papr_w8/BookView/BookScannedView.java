package com.example.papr_w8.BookView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.papr_w8.Book;
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


        textViewTitle = view.findViewById(R.id.titleEditText);
        textViewAuthor = view.findViewById(R.id.authorEditText);
        textViewISBN = view.findViewById(R.id.isbnEditText);
        textViewStatus = view.findViewById(R.id.statusEditText);

        Bundle bundle = this.getArguments();
        Book book = (Book) bundle.getSerializable("bookScanned");

        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewISBN.setText(book.getISBN());
        textViewStatus.setText(book.getStatus());



        return view;
    }
}