package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Toast;

import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
import com.example.papr_w8.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a Fragment that displays the view of a book description for a book that has been
 * searched with the option to request the book.
 */
public class RequestBookView extends BookBase {

    private String fileName;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore fbDB = FirebaseFirestore.getInstance();
    private String userEmail = user.getEmail();
    private String userId = user.getUid();

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void provideYourFragmentView(final View baseView, ViewGroup container){


        Button requestBookButton;
        Button cancelRequestButton;

        setRetainInstance(true);
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);
        stub.setLayoutResource(R.layout.fragment_book_request_view);
        stub.inflate();
        final Bundle bundle = getArguments();
        final Book book = (Book) bundle.getSerializable("bookSelected");
        assert book != null;
        final String title = book.getTitle();
        final String author = book.getAuthor();
        final String ISBN = book.getISBN();
        final String owner = book.getOwner();
        final String id = book.getId();

        requestBookButton = (Button) baseView.findViewById(R.id.request_book_button);

        // owner cannot request an available book that they own
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        if (email.equals(owner)){
            requestBookButton.setEnabled(false);
            Toast.makeText(getContext(), "Cannot request a book you own!", Toast.LENGTH_SHORT).show();
        }

        requestBookButton.setOnClickListener(new View.OnClickListener() {  // onClickListener for when the user clicks on the confirm button to add a book
            @Override
            public void onClick(View view) {

                Map<String, Object> book = new HashMap<>();
                book.put("Title", title);
                book.put("Author", author);
                book.put("ISBN", ISBN);
                book.put("Status", "Requested");
                book.put("Book Cover", fileName);
                book.put("Owner", owner);

                Map<String, Object> user = new HashMap<>();
                user.put("email", userEmail);

                // Add Book to users awaiting approval collection
                fbDB.collection("Users")
                        .document(userEmail)
                        .collection("Awaiting Approval")
                        .document(id)
                        .set(book);
                fbDB.collection("Users")
                        .document(owner)
                        .collection("Books Owned")
                        .document(id)
                        .collection("Requested")
                        .document(userEmail)
                        .set(user);
                fbDB.collection("Users")
                        .document(owner)
                        .collection("Books_Requested")
                        .document(id)
                        .set(book);
                fbDB.collection("Users")
                        .document(owner)
                        .collection("Books Owned")
                        .document(id)
                        .set(book);

                Intent intent = new Intent(getActivity(), Host.class);
                startActivity(intent);
                }


        });
    };

}