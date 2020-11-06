package com.example.papr_w8;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Placeholder, implementation to be added next sprint
 * will show books  user has recieved requests for
*/
public class AcceptRequest extends Fragment {
    public AcceptRequest(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_accept_request, container, false);

        return view;
    }
}