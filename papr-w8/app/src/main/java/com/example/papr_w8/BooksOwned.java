package com.example.papr_w8;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.papr_w8.Adapters.BookDisplayList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;


public class BooksOwned extends Fragment {

    public BooksOwned(){
    }
    private FloatingActionButton floating_add_book;

    private ArrayList<Book> ownedBookDataList;
    private ArrayAdapter<Book> ownedBookAdapter;
    private ListView ownedBookList;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        View view =  inflater.inflate(R.layout.fragment_books_owned, container, false);

//        floating_add_book = view.findViewById(R.id.add_book_float);

//        floating_add_book.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AddBook.class);
//                startActivity(intent);
//            }
//        });
        ownedBookList =  view.findViewById(R.id.books_owned_list);
        ownedBookDataList = new ArrayList<>();

        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Books Owned").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ownedBookDataList.add(new Book(
                                        document.getString("Title"), document.getString("Author"),
                                        document.getString("ISBN"), document.getString("Status"),
                                        document.getString("Book Cover")));
                                ownedBookAdapter = new BookDisplayList(getContext(), ownedBookDataList);
                                ownedBookList.setAdapter(ownedBookAdapter);

                            }

                        }
                    }
                });

        return view;
    }
}