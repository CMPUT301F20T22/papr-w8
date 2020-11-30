package com.example.papr_w8.ShelfPack;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.papr_w8.Adapters.BookDisplayWithOwnerList;
import com.example.papr_w8.Book;
import com.example.papr_w8.BookView.BookRequestedView;
import com.example.papr_w8.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Displays a view of books that have been requested by a user
 */
public class BooksRequested extends Fragment {
    public BooksRequested(){
    }

    private ArrayList<Book> requestedBookDataList;
    private ArrayAdapter<Book> requestedBookAdapter;
    private ListView requestedBookList;
    private FirebaseAuth firebaseAuth;
    public static final String EXTRA_TEXT = "com.example.papr_w8.EXTRA_TEXT";

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

        View view =  inflater.inflate(R.layout.fragment_books_requested, container, false);

        //hold book objects
        requestedBookList =  view.findViewById(R.id.books_requested_list);
        requestedBookDataList = new ArrayList<>();

        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Books_Requested").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //add book items from database
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){

                                Book temp = new Book(
                                        document.getString("Title"), document.getString("Author"),
                                        document.getString("ISBN"), document.getString("Status"),
                                        document.getString("Book Cover"), document.getString("Owner"));

                                temp.setId(document.getId());

                                Log.d("MyDebug", temp.getId());

                                // add the book to the data list
                                requestedBookDataList.add(temp);

                                //setup an array adapter for the books
                                requestedBookAdapter = new BookDisplayWithOwnerList(getContext(), requestedBookDataList);
                                requestedBookList.setAdapter(requestedBookAdapter);
                            }
                        }
                    }
                });

        requestedBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            //go to book description when a book is clicked on
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookRequestedView bookRequestedView = new BookRequestedView();

                //bundle data to transfer
                Bundle bundle = new Bundle();
                Book bookSelected = requestedBookAdapter.getItem(i);
                bundle.putSerializable("bookSelected", (Serializable) bookSelected);
                bookRequestedView.setArguments(bundle);



                Intent intent = new Intent(getActivity(), BookRequestedView.class);

                //transfer data
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_requested, bookRequestedView);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return view;
    }
}