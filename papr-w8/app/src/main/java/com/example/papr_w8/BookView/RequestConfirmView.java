package com.example.papr_w8.BookView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.papr_w8.Book;
import com.example.papr_w8.R;

public class RequestConfirmView extends AppCompatActivity {

    public RequestConfirmView(){

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirm_view);

        final Book book = (Book) getIntent().getSerializableExtra("book");

        Log.d("hello", book.getOwner());
    }
}