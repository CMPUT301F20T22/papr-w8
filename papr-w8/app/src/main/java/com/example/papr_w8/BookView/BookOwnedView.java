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
 * This is a Fragment that displays the view of a Book Description that is Owned providing
 * options for the Owner to Edit, View Requests, or Delete Book.
 */
public class BookOwnedView extends Fragment {

    public BookOwnedView() {
    }

    private Button buttonViewApprove;
    private Button buttonEditDescription;
    private Button buttonDeleteBook;

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;

    private ImageView imageViewDefault;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_owned, container, false);

        buttonViewApprove = (Button) view.findViewById(R.id.viewapproveButton);
        buttonEditDescription = (Button) view.findViewById(R.id.editdescriptionButton);
        buttonDeleteBook = (Button) view.findViewById(R.id.deleteButton);

        textViewTitle = view.findViewById(R.id.titleEditText);
        textViewAuthor = view.findViewById(R.id.authorEditText);
        textViewISBN = view.findViewById(R.id.isbnEditText);
        textViewStatus = view.findViewById(R.id.statusEditText);

        // Get the bundle containing the Book object passed to the View
        Bundle bundle = this.getArguments();
        Book book = (Book) bundle.getSerializable("bookSelected");

        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewISBN.setText(book.getISBN());
        textViewStatus.setText(book.getStatus());

        // This onClickListener performs the action of taking the Owner to view requests activity
        buttonViewApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the ViewApprove button
            }
        });

        buttonEditDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the EditDescription button
            }
        });

        buttonDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the DeleteBook button
            }
        });

        return view;
    }
}