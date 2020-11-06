package com.example.papr_w8.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.papr_w8.R;


public class Search extends Fragment implements AdapterView.OnItemSelectedListener {

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
     * @param search_user fragment which handles searching for users
     * @param search_books fragment which handles searching through available books
     * @param adapter ArrayAdapter handling the String options for the spinner options
     * @return Search view
     */
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

        // handle clicking on different options in spinner
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

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * sets the fragment to new fragment on selection of a spinner item
     * @return void
     */
    public void selectFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=this.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_search, fragment); // if it doesn't work, find out where the fragment_search is
        fragmentTransaction.commit();
    }

    /**
     * handles spinner item click
     * @return void
     */
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