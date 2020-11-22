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
 * This is a Fragment that displays the view of a basic Book Description
 */
public class BookBasicView extends BookBase {

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void provideYourFragmentView(View baseView, ViewGroup container){

        setRetainInstance(true);
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);
        stub.setLayoutResource(R.layout.fragment_book_base);
        stub.inflate();

    };

}