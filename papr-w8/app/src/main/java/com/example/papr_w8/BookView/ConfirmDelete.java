package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
import com.example.papr_w8.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Allows a user to confirm the deletion of one of their owned books
 * Allows a user to cancel the deletion of one of their books and return to the shelves page
 */

public class ConfirmDelete extends Fragment {
    Button deleteConfirm;
    Button cancel;
    public ConfirmDelete (){}
    private String fileName;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore fbDB = FirebaseFirestore.getInstance();
    private String email = user.getEmail();

    /**
     * onCreate starts the code for deleting a book functionality
     * Contains the functionality for each of the buttons on the delete page
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final Book book = (Book) getArguments().getSerializable("Book Del");

        final View view = inflater.inflate(R.layout.fragment_confirm_delete, container, false);
        deleteConfirm = (Button) view.findViewById(R.id.confirm_deletion);
        cancel = (Button) view.findViewById(R.id.cancel_deletion);


        deleteConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (book.getStatus().matches("Available"))
                {

                    fbDB.collection("Users").document(email).collection("Books Owned").document(book.getId()).delete();
                    //fbDB.collection("Users").document(email).collection("Books_Requested").document(book.getId()).delete();
                    fbDB.collection("Books").document(book.getId()).delete();
                }
                else
                {
                    Toast.makeText(getContext(), "Book Unavailable", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getActivity(), Host.class);
                startActivity(intent);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Host.class);
                startActivity(intent);

            }
        });


        return view;


    }}
