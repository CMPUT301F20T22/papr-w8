package com.example.papr_w8.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.papr_w8.Adapters.BookDisplayList;
import com.example.papr_w8.Book;
import com.example.papr_w8.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    ArrayList<String> userDataList = new ArrayList<>();

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

        // get User data from Firebase
        final Task<QuerySnapshot> userDoc = FirebaseFirestore.getInstance().collection("Books")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Book> bookDataList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getString("Status").equals("Available")){
                            bookDataList.add(new Book(document.getString("Title"), document.getString("Author"), document.getString("ISBN"), document.getString("Status")));
                            bookAdapter = new BookDisplayList(getContext(), bookDataList); // userDataList is an array of users
                            bookList.setAdapter(bookAdapter);
                            Log.d("TAGBook", document.getId() + " => " + document.getData());
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
                }
            });

        // Inflate the layout for this fragment
        return view;
    }
}