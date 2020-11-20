package com.example.papr_w8.BookView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.papr_w8.Adapters.BookDisplayWithOwnerList;
import com.example.papr_w8.Adapters.UserRequestsDisplayList;
import com.example.papr_w8.Book;
import com.example.papr_w8.R;
import com.example.papr_w8.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This is a Fragment that displays the view of a Book Description that has been Requested and needs
 * to be Approved or Declined by a Book Owner.
 */
public class BookRequestedView extends BookBase {

    private ArrayList<User> requestsDataList;
    private ArrayAdapter<User> requestsAdapter;
    private ListView requestsList;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void provideYourFragmentView(View baseView, ViewGroup container){

        //set up firebase to pull data
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        setRetainInstance(true);
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);
        stub.setLayoutResource(R.layout.fragment_book_requested);
        stub.inflate();

        requestsList = baseView.findViewById(R.id.user_requests_list);
        requestsDataList = new ArrayList<>();

        String id = book.getId();

        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Books Owned").document(id)
                .collection("Requested").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //add book items from database
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){

                                User temp = new User(
                                        document.getString("name"), document.getString("email"));

                                Log.d("MyDebug", temp.getName());
                                // add the book to the data list
                                requestsDataList.add(temp);

                                //setup an array adapter for the books
                                requestsAdapter = new UserRequestsDisplayList(getContext(), requestsDataList);
                                requestsList.setAdapter(requestsAdapter);
                            }
                        }
                    }
                });
    };
}