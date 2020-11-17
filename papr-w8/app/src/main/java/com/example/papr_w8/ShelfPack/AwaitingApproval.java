package com.example.papr_w8.ShelfPack;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.papr_w8.R;

/**
 * Placeholder, implementation to be added next sprint
 * will show books  user has recieved requests for
*/
public class AwaitingApproval extends Fragment {
    public AwaitingApproval(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_awaiting_approval, container, false);

        return view;
    }
}