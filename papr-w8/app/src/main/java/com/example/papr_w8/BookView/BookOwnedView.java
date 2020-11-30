package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.papr_w8.AddPack.EditBook;
import com.example.papr_w8.R;

/**
 * This is a Fragment that displays the view of a book description for a book owned by a user
 * providing options for the owner to edit the book description or delete the book.
 */
public class BookOwnedView extends BookBase {

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * This override method will provide a fragment that will be display the book
     * description.
     * @param baseView
     * @param container
     */
    @Override
    public void provideYourFragmentView(final View baseView, ViewGroup container){
        setRetainInstance(true);

        // Get the id of the ViewStub from the BookBase view
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);

        // Set the layout resource to the book base XML file for Book Owned
        stub.setLayoutResource(R.layout.fragment_book_owned);

        // Inflate the layout provided at the location specified in BookBase
        stub.inflate();

        // Get the button id's from the layout
        Button buttonEditDescription = (Button) baseView.findViewById(R.id.editdescriptionButton);
        Button buttonDeleteBook = (Button) baseView.findViewById(R.id.deleteButton);

        // Initiate onClickListener for the Edit Button
        buttonEditDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditBook.class);
                intent.putExtra("Book", book);
                startActivity(intent);
            }
        });
        // Initiate onClickListener for the Delete Book Button
        buttonDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize confirmDelete class
                ConfirmDelete confirmDelete = new ConfirmDelete();

                // Pass the book that will be deleted in the bundle
                Bundle bundle = new Bundle();
                bundle.putSerializable("Book Del", book);
                confirmDelete.setArguments(bundle);

                // Start fragment transaction
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_book_base,confirmDelete,confirmDelete.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    };
}