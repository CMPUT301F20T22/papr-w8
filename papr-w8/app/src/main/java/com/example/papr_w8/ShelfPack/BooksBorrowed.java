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
import com.example.papr_w8.BookView.BookBasicView;
import com.example.papr_w8.BookView.BookReturningView;
import com.example.papr_w8.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Displays a listview of all books currently borrowed by a user
 */

public class BooksBorrowed extends Fragment {
    public BooksBorrowed(){
    }

    private ArrayList<Book> borrowedBookDataList;
    private ArrayAdapter<Book> borrowedBookAdapter;
    private ListView borrowedBookList;
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

        View view =  inflater.inflate(R.layout.fragment_books_borrowed, container, false);

        //hold book objects
        borrowedBookList =  view.findViewById(R.id.books_borrowed_list);
        borrowedBookDataList = new ArrayList<>();

        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Books_Borrowed").get()
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
                                // add the book to the data list
                                borrowedBookDataList.add(temp);

                                //setup an array adapter for the books
                                borrowedBookAdapter = new BookDisplayWithOwnerList(getContext(), borrowedBookDataList);
                                borrowedBookList.setAdapter(borrowedBookAdapter);
                            }
                        }
                    }
                });

        borrowedBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            //go to book description when a book is clicked on
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookReturningView returningView = new BookReturningView();

                //bundle data to transfer
                Bundle bundle = new Bundle();
                Book bookSelected = borrowedBookAdapter.getItem(i);
                bundle.putSerializable("bookSelected", (Serializable) bookSelected);
                returningView.setArguments(bundle);

                Intent intent = new Intent(getActivity(), BookReturningView.class);

                //transfer data
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_borrowed, returningView);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return view;
    }
}