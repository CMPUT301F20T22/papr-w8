package com.example.papr_w8.BookView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.papr_w8.Book;
import com.example.papr_w8.R;

/**
 * This is a Fragment that displays the view of a book description with Author, Title, ISBN, and
 * Status.
 */
public class BookBasicView extends BookBase {

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * This override method will instantiate a BookBase view with no additional functionality
     * @param baseView
     * @param container
     */
    @Override
    public void provideYourFragmentView(View baseView, ViewGroup container){
        setRetainInstance(true);

        // Get the id of the ViewStub from the BookBase view
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);

        // Set the layout resource to the book base XML file
        stub.setLayoutResource(R.layout.fragment_book_base);

        // Inflate the layout provided at the location specified in BookBase view
        stub.inflate();

    };

}