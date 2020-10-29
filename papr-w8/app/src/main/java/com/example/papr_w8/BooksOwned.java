package com.example.papr_w8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BooksOwned extends AppCompatActivity {

    public BooksOwned (){

    }
   private FloatingActionButton floating_add_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_owned);

        floating_add_book = findViewById(R.id.add_book_float);
        floating_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddBook();
            }
        });
    }
    public void openAddBook(){
        Intent intent = new Intent(this, AddBook.class);
        startActivity(intent);
    }
}