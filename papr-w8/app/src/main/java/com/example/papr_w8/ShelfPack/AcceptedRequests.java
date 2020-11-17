package com.example.papr_w8.ShelfPack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.papr_w8.R;


public class AcceptedRequests extends Fragment {
    public AcceptedRequests(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_accepted_requests, container, false);

        return view;
    }
}