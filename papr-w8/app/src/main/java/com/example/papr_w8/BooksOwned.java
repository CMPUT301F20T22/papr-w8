package com.example.papr_w8;

//Displays all books owned by a user. Pulls book document from data base
//Clicking on a book takes you to the description/ actions page

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.papr_w8.Adapters.BookDisplayList;
import com.example.papr_w8.BookView.BookOwnedView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class BooksOwned extends Fragment {

    public static final String EXTRA_MESSAGE = "com.example.myapp.MESSAGE";

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

        //set up firebase to pull data
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        View view =  inflater.inflate(R.layout.fragment_books_owned, container, false);


        //Implementation to be added later to match storyboard, issues with layout
//        floating_add_book = view.findViewById(R.id.add_book_float);

//        floating_add_book.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AddBook.class);
//                startActivity(intent);
//            }
//        });
        //hold book objects
        ownedBookList =  view.findViewById(R.id.books_owned_list);
        ownedBookDataList = new ArrayList<>();

        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Books Owned").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //add book items from database
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ownedBookDataList.add(new Book(
                                        document.getString("Title"), document.getString("Author"),
                                        document.getString("ISBN"), document.getString("Status"),
                                        document.getString("Book Cover")));
                                //setup an array adapter for the books
                                ownedBookAdapter = new BookDisplayList(getContext(), ownedBookDataList);
                                ownedBookList.setAdapter(ownedBookAdapter);
                            }
                        }
                    }
                });

        ownedBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            //go to book description when a book is clicked on
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookOwnedView bookOwnedView = new BookOwnedView();

                //bundle data to transfer
                Bundle bundle = new Bundle();
                Book bookSelected = ownedBookAdapter.getItem(i);
                bundle.putSerializable("bookSelected", (Serializable) bookSelected);
                bookOwnedView.setArguments(bundle);

                Intent intent = new Intent(getActivity(), BookOwnedView.class);

                //transfer data
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.books_owned, bookOwnedView);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return view;
    }
}