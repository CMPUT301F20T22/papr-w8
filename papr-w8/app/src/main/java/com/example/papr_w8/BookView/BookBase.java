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
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public abstract class BookBase extends Fragment{

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;
    private TextView textViewOwner;
    protected Book book;

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
        final ImageView bookBaseCover = baseView.findViewById(R.id.book_base_cover);

        // Get the bundle containing the Book object passed to the View
        Bundle bundle = this.getArguments();
        book = (Book) bundle.getSerializable("bookSelected");

        textViewTitle.setText(book.getTitle());
        textViewAuthor.setText(book.getAuthor());
        textViewISBN.setText(book.getISBN());
        textViewStatus.setText(book.getStatus());
        textViewOwner.setText(book.getOwner());

        //set book cover to imageview
        FirebaseStorage.getInstance().getReference("images/" + book.getCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

    public abstract void provideYourFragmentView(View rootView, ViewGroup container);
}
