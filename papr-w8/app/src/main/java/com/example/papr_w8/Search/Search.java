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