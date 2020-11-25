package com.example.papr_w8.Search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.papr_w8.Adapters.BookDisplayList;
import com.example.papr_w8.Adapters.UserDisplayList;
import com.example.papr_w8.Book;
import com.example.papr_w8.BookView.BookOwnedView;
import com.example.papr_w8.BookView.BookRequestedView;
import com.example.papr_w8.BookView.RequestBookView;
import com.example.papr_w8.ProfilePack.RetrivedProfile;
import com.example.papr_w8.R;
import com.example.papr_w8.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;


public class Search extends Fragment implements AdapterView.OnItemSelectedListener {

    ListView resultList;
    ArrayAdapter<Book> bookAdapter;
    ArrayList<Book> bookDataList;
    ArrayAdapter<User> userAdapter;
    ArrayList<User> userDataList;
    private EditText searchText;
    private Integer selection  = 0; // 0 or Available books, 1 for Users

    /**
     * Displays "Seach" page when user clicks on "Search" from the bottom navigation bar
     * Displays spinner in the top left-hand corner so the user can choose between searching for Users or Available books
     * When either "User" or "Available Books" is selected from spinner, a new fragment displaying the search results will be returned
     * Search bar will return results of search query when completed
     *
     * Progress:
     * clicking on different spinner items will take you to different fragments
     * User data is fetched from firebase. Available books is currently dummy data, and will be replaced with a firebase fetch when the Books class is complete.
     * Search functionality not yet implemented
     *
     * @param spinner spinner in which user can choose to search "Users" or "Available books"
     * @param adapter ArrayAdapter handling the String options for the spinner options
     * @return Search view
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // restore fragment state
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.searchoptions_spinner);
        searchText = view.findViewById(R.id.search_edit_text);

        resultList = view.findViewById(R.id.result_list);
        bookDataList = new ArrayList<>();
        userDataList = new ArrayList<>();
        searchText.addTextChangedListener(searchFilter);

        /* Citation:
         * Title: How to Create Spinner with Fragments | Android Studio - Quick and Easy Tutorial
         * Author: Learn with Deeksha
         * Date published: June 9, 2020
         * Date retrieved: Oct 30, 2020
         * Licence: CC
         * URL: https://www.youtube.com/watch?v=kVk4cEZwnMM&ab_channel=LearnWithDeeksha
         */
        // Spinner containing "Available books", "Users"
        String[] values = getResources().getStringArray(R.array.search_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        // handle clicking on different options in spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                switch(pos){
                    case 0: // "Available books"
                        selection = 0;
                        // get Book data from Firebase
                        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Books")
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
                                                resultList.setAdapter(bookAdapter);
                                                Log.d("TAG-B", document.getId() + " => " + document.getData());
                                            }
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                        break;

                    case 1: // "Users"
                        selection = 1;
                        // get User data from Firebase
                        final Task<QuerySnapshot> userDoc = FirebaseFirestore.getInstance().collection("Users")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                ArrayList<User> userDataList = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        userDataList.add(new User(document.getString("name"), document.getString("password"), document.getString("email"), document.getString("address")));
                                        userAdapter = new UserDisplayList(getContext(), userDataList); // userDataList is an array of users
                                        resultList.setAdapter(userAdapter);
                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                                }
                            });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // handle click on list item
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(resultList.getItemAtPosition(pos) instanceof User){
                    User user = userAdapter.getItem(pos);
                    new RetrivedProfile();
                    RetrivedProfile retrivedProfileFragment = RetrivedProfile.newInstance(user);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction()
                            .show(retrivedProfileFragment);
                    ft.replace(R.id.fragment_search, retrivedProfileFragment);
                    ft.commit();
                } else if (resultList.getItemAtPosition(pos) instanceof Book) {

                    Book book = bookAdapter.getItem(pos);

                    RequestBookView requestBookView = new RequestBookView();

                    //bundle data to transfer
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("bookSelected", (Serializable) book);
                    requestBookView.setArguments(bundle);

                    Intent intent = new Intent(getActivity(), BookOwnedView.class);

                    //transfer data
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_search, requestBookView);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();

                    
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }

    private TextWatcher searchFilter = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            final String searchContent = searchText.getText().toString().trim();

            if(selection==0){ // "Available books"
                final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Books")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<Book> bookDataList = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(!searchContent.equals("") && document.getString("Status").equals("Available")
                                            && !document.getString("Title").toLowerCase().contains(searchContent.toLowerCase())
                                            && !document.getString("Author").toLowerCase().contains(searchContent.toLowerCase())
                                            && !document.getString("ISBN").toLowerCase().contains(searchContent.toLowerCase())){
                                        // clear listview
                                        bookAdapter = new BookDisplayList(getContext(), bookDataList);
                                        resultList.setAdapter(bookAdapter);
                                    } else if(document.getString("Status").equals("Available")
                                            || document.getString("Title").toLowerCase().contains(searchContent.toLowerCase())
                                            || document.getString("Author").toLowerCase().contains(searchContent.toLowerCase())
                                            || document.getString("ISBN").toLowerCase().contains(searchContent.toLowerCase())){
                                        // display applicable search results
                                        bookDataList.add(new Book(document.getString("Title"), document.getString("Author"), document.getString("ISBN"), document.getString("Status")));
                                        bookAdapter = new BookDisplayList(getContext(), bookDataList); // userDataList is an array of users
                                        resultList.setAdapter(bookAdapter);
                                        Log.d("TAG-CB", document.getId() + " => " + document.getData());
                                    }
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });

            } else if (selection==1){ // "Users"
                final Task<QuerySnapshot> userDoc = FirebaseFirestore.getInstance().collection("Users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<User> userDataList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(!document.getString("name").toLowerCase().contains(searchContent.toLowerCase()) && !searchContent.equals("")){
                                    userAdapter = new UserDisplayList(getContext(), userDataList);
                                    resultList.setAdapter(userAdapter);
                                } else if(document.getString("name").toLowerCase().contains(searchContent.toLowerCase())){
                                    userDataList.add(new User(document.getString("name"), document.getString("password"), document.getString("email"), document.getString("address")));
                                    userAdapter = new UserDisplayList(getContext(), userDataList); // userDataList is an array of users
                                    resultList.setAdapter(userAdapter);
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        }
                    });
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /**
     * handles spinner item selection
     * @return void
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}