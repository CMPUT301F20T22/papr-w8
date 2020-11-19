package com.example.papr_w8.BookView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.papr_w8.Book;
import com.example.papr_w8.R;

public abstract class BookBase extends Fragment{

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;
    private TextView textViewOwner;

    private ImageView imageViewDefault;
    private Uri ImageUri;

    public BookBase(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
;
        View baseView = inflater.inflate(R.layout.fragment_book_base, container, false);

        textViewTitle = baseView.findViewById(R.id.titleEditText);
        textViewAuthor = baseView.findViewById(R.id.authorEditText);
        textViewISBN = baseView.findViewById(R.id.isbnEditText);
        textViewStatus = baseView.findViewById(R.id.statusEditText);
        textViewOwner = baseView.findViewById(R.id.ownerEditText);

        // Get the bundle containing the Book object passed to the View
        Bundle bundle = this.getArguments();
        Book book = (Book) bundle.getSerializable("bookSelected");

        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewISBN.setText(book.getISBN());
        textViewStatus.setText(book.getStatus());
        textViewOwner.setText(book.getOwner());

        provideYourFragmentView(baseView, container);

        return baseView;
    }

    public abstract void provideYourFragmentView(View rootView, ViewGroup container);
}
