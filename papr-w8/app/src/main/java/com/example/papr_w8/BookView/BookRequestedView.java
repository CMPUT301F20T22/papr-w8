package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.papr_w8.Adapters.BookDisplayWithOwnerList;
import com.example.papr_w8.Adapters.UserRequestsDisplayList;
import com.example.papr_w8.Book;
import com.example.papr_w8.ProfilePack.RetrivedProfile;
import com.example.papr_w8.R;
import com.example.papr_w8.User;
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
 * This is a Fragment that displays the view of a book description with a list of users who have
 * requested the book. Giving the ability for the owner to Accept or Decline their request.
 */
public class BookRequestedView extends BookBase {

    private ArrayList<User> requestsDataList;
    private ArrayAdapter<User> requestsAdapter;
    private ListView requestsList;


    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * This override method will provide a fragment that will be displayed under the book
     * description. Displaying a list of users that requested the current book.
     * @param baseView
     * @param container
     */

    @Override
    public void provideYourFragmentView(View baseView, ViewGroup container){
        setRetainInstance(true);

        // Get the email of the current user
        //String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String email = user.getEmail();

        // Get the id of the ViewStub from the BookBase view
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);

        // Set the layout resource to the book requested XML
        stub.setLayoutResource(R.layout.fragment_book_requested);

        // Inflate the layout provided at the location specified in BookBase view
        stub.inflate();

        // Get the id of the ListView to display our requests on
        requestsList = baseView.findViewById(R.id.user_requests_list);
        requestsDataList = new ArrayList<>();

        // Get the id of the book
        String id = book.getId();

        // Query Firestore for the instances of users who requested the book selected
        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Books Owned").document(id)
                .collection("Requested").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Display user emails from Firestore
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){

                                // Create a temporary user
                                User temp = new User();
                                // Set the temporary user email for display in the list
                                temp.setEmail(document.getString("email"));

                                // add the book to the data list
                                requestsDataList.add(temp);

                                //setup an array adapter for the books
                                requestsAdapter = new UserRequestsDisplayList(getContext(), requestsDataList, book);
                                requestsList.setAdapter(requestsAdapter);
                            }
                        }
                    }
                });
    };
}