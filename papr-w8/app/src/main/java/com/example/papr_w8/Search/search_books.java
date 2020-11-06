package com.example.papr_w8.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.papr_w8.Adapters.BookDisplayList;
import com.example.papr_w8.Book;
import com.example.papr_w8.R;

import java.util.ArrayList;

/**
 * Displays a list of books fetched from firebase in Listview format
 *
 * @param bookList Listview of Books
 * @param bookAdapter ArrayAdapter of Book items
 * @param bookDataList ArrayList of Book
 * @return void
 */
public class search_books extends Fragment {

    ListView bookList;
    ArrayAdapter<Book> bookAdapter;
    ArrayList<Book> bookDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_books, container, false);

        bookList = view.findViewById(R.id.book_list);
        bookDataList = new ArrayList<>();

        // Default sample data - replace with User data fetched from firebase
        String[] sampleTitles = {"The Great Gatsby","Sistering","Harry Potter"};
        String[] sampleAuthors = {"F. Scott Fitzgerald","Jennifer Quist","J. K. Rowling"};
        String[] sampleISBNs = {"9780333791035", "9781927535707", "9788867156016"};
        String[] sampleStatus = {"Available", "On Request", "Ready to checkout"};

        for (int i = 0; i < sampleTitles.length; i++) {
            bookDataList.add(new Book(sampleTitles[i], sampleAuthors[i], sampleISBNs[i], sampleStatus[i]));
        }

        // set Book adapter and refresh it to display
        bookAdapter = new BookDisplayList(this.getContext(), bookDataList); // itemDataList is an array of existing items
        bookList.setAdapter(bookAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}