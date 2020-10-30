package com.example.papr_w8;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class AddBook extends AppCompatActivity {

    private Book book;
//    private EditText newBookTitle;
//    private EditText newISBNTitle;
//    private Button cancel;
//    private Button confirm_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);



        final EditText newBookTitle = findViewById(R.id.new_title_editText);
        final EditText newBookISBN = findViewById(R.id.new_isbn_editText);
        final EditText newBookAuthor = findViewById(R.id.new_author_editText);
        Button cancel = findViewById(R.id.cancel_addbook_button);
        Button confirm = findViewById(R.id.confirm_addbook_button);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = newBookTitle.getText().toString();
                String ISBN = newBookISBN.getText().toString();
                String author = newBookAuthor.getText().toString();

                if (title.isEmpty()) {
                    newBookTitle.setError("Please enter title");
                }

                if (ISBN.isEmpty()) {
                    newBookISBN.setError("Please enter valid ISBN");
                }

                if (author.isEmpty()) {
                    newBookAuthor.setError("Please enter author's name");
                }
            }
        });
    }
}
