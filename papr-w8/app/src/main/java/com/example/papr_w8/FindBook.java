package com.example.papr_w8;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Placeholder: May not make it to final version due to redundancy
public class FindBook extends Fragment {
    public FindBook(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_find_book, container, false);

        return view;
    }
}