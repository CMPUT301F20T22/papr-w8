package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
import com.example.papr_w8.R;
import com.example.papr_w8.Search.Search;
import com.example.papr_w8.ShelfPack.BooksOwned;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a Fragment that displays the view of a book description for a book that has been
 * searched with the option to request the book.
 */
public class RequestBookView extends BookBase {

    private String name;
    // Get the current user's email
    private String userEmail = user.getEmail();

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * This override method will instantiate a BookBase view with the ability to request the book
     * currently being viewed.
     * @param baseView
     * @param container
     */

    @Override
    public void provideYourFragmentView(final View baseView, ViewGroup container){
        setRetainInstance(true);

        // Get the id of the ViewStub from the BookBase view
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);

        // Set the layout resource to the book request view
        stub.setLayoutResource(R.layout.fragment_book_request_view);

        // Inflate the layout provided at the location in BookBase view
        stub.inflate();



        // Get the book selected attributes
        final String owner = book.getOwner();
        final String bookId = book.getId();

        fbDB.collection("Users")
                .document(userEmail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        name = documentSnapshot.getString("name");
                    }
                });

        // Set the button Id from the XML file
        Button requestBookButton = (Button) baseView.findViewById(R.id.request_book_button);

        // Check if the current user is also the owner of the book
        if (userEmail.equals(owner)){
            requestBookButton.setEnabled(false);
            Toast.makeText(getContext(), "Cannot request a book you own!", Toast.LENGTH_SHORT).show();
        }

        // Instantiate an onClickListener for the request book button
        requestBookButton.setOnClickListener(new View.OnClickListener() {
            // onClickListener for when the user clicks on the confirm button to add a book
            @Override
            public void onClick(View view) {

                // Create a hashmap for the book to be requested
                Map<String, Object> bookRequested = new HashMap<>();
                bookRequested.put("Title", book.getTitle());
                bookRequested.put("Author", book.getAuthor());
                bookRequested.put("ISBN", book.getISBN());
                bookRequested.put("Status", "Requested");
                bookRequested.put("Book Cover", book.getCover());
                bookRequested.put("Owner", book.getOwner());

                // Create a hashmap for the user requesting the book
                Map<String, Object> userRequesting = new HashMap<>();
                userRequesting.put("email", userEmail);

                // Add the book to the user's awaiting approval collection
                fbDB.collection("Users")
                        .document(userEmail)
                        .collection("Awaiting Approval")
                        .document(bookId)
                        .set(bookRequested);
                // Add the user's name to the collection of users requesting this book
                fbDB.collection("Users")
                        .document(owner)
                        .collection("Books Owned")
                        .document(bookId)
                        .collection("Requested")
                        .document(userEmail)
                        .set(userRequesting);
                // Add the book to the owner's collection of books requested
                fbDB.collection("Users")
                        .document(owner)
                        .collection("Books_Requested")
                        .document(bookId)
                        .set(bookRequested);
                // Update the book in the owner's owned books collection
                fbDB.collection("Users")
                        .document(owner)
                        .collection("Books Owned")
                        .document(bookId)
                        .update("Status", "Requested");

                Toast.makeText(getContext(), "Book requested!", Toast.LENGTH_SHORT).show();

                notifyOwner(bookId, owner, name, book.getTitle());

                // Return the user to the Shelves page

                Intent intent = new Intent(getActivity(), Host.class);
                startActivity(intent);
                }


        });
    };

    /**
     * This method notifies the owner of the book that it has been requested
     * @param bookId
     * @param owner_email
     * @param user_name
     * @param book_title
     */

    public void notifyOwner(String bookId, String owner_email, String user_name, String book_title){
        Map<String, Object> notification = new HashMap<>();
        notification.put("Sender", userEmail);
        notification.put("Name", user_name);
        notification.put("Type", "request");
        notification.put("Book Title", book_title);
        notification.put("Book Id", bookId);

        fbDB.collection("Users")
                .document(owner_email)
                .collection("Notifications")
                .document()
                .set(notification);
    }
}