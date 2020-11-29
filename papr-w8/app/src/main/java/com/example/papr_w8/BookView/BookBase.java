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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

/**
 * This is an abstract class extending Fragment to perform the displaying the Title, Author, ISBN
 * Status, Owner and Book Cover onto the view.
 */

public abstract class BookBase extends Fragment{

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;
    private TextView textViewOwner;
    protected Book book;

    protected FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    protected FirebaseUser user = firebaseAuth.getCurrentUser();
    protected FirebaseStorage fbST = FirebaseStorage.getInstance();
    protected FirebaseFirestore fbDB = FirebaseFirestore.getInstance();

    public BookBase(){
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
;
        // Inflate the view of the fragment using the XML file provided
        View baseView = inflater.inflate(R.layout.fragment_book_base, container, false);

        // Get the TextViews from the XML file
        textViewTitle = baseView.findViewById(R.id.titleEditText);
        textViewAuthor = baseView.findViewById(R.id.authorEditText);
        textViewISBN = baseView.findViewById(R.id.isbnEditText);
        textViewStatus = baseView.findViewById(R.id.statusEditText);
        textViewOwner = baseView.findViewById(R.id.ownerEditText);

        // Get the ImageView from the XML file
        final ImageView bookBaseCover = baseView.findViewById(R.id.book_base_cover);

        // Get the bundle containing the Book object passed to the View
        Bundle bundle = this.getArguments();
        book = (Book) bundle.getSerializable("bookSelected");

        // Set the text for the corresponding TextView
        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewISBN.setText(book.getISBN());
        textViewStatus.setText(book.getStatus());
        textViewOwner.setText(book.getOwner());

        // Set book cover to ImageView from FirebaseStorage
        fbST.getReference("images/" + book.getCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(bookBaseCover);
            }
        });

        provideYourFragmentView(baseView, container);
        return baseView;
    }

    /**
     * This is an abstract method to provide functionality to views extending BookBase and allowing
     * for additional information or functionality to be displayed below.
     * @param rootView
     * @param container
     */
    public abstract void provideYourFragmentView(View rootView, ViewGroup container);
}
