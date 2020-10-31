package com.example.papr_w8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment implements AdapterView.OnItemSelectedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.searchoptions_spinner);
        final search_user frag_search_user = new search_user();
        final search_books frag_search_books = new search_books();

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                switch(pos){
                    case 0: // "Available books"
                        selectFragment(frag_search_books);
                        break;
                    case 1: // "Users"
                        selectFragment(frag_search_user);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        // get emails from firebase
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        final DocumentReference userDoc = FirebaseFirestore.getInstance().collection("Users").document(user.getEmail());
//
//        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//            if (task.isSuccessful()){
//                DocumentSnapshot doc = task.getResult();
//                if (doc.exists()) {
//                    // display user profiles
//
//                    Log.d("Sample", "DocumentSnapshot data: " + doc.getData());
//                } else { // if user profile does not exist
//                    // display empty listview
//                    Log.d("Sample", "No such user");
//                }
//            } else { // if unable to retrive from firebase
//                Log.d("Sample", "get failed with ", task.getException());
//            }
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }

    public void selectFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=this.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_search, fragment); // if it doesn't work, find out where the fragment_search is
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        System.out.println("item is selected");
        String text = adapterView.getItemAtPosition(pos).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}