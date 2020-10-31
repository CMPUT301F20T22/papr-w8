package com.example.papr_w8.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.papr_w8.R;
import com.example.papr_w8.User;

import java.util.ArrayList;

public class search_user extends Fragment {

    ListView userList;
    ArrayAdapter<User> userAdapter;
    ArrayList<User> userDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);

        userList = view.findViewById(R.id.user_list);
        userDataList = new ArrayList<>();

        // Default sample data - replace with User data fetched from firebase
        String[] sampleNames = {"name 1","name 2","name 3"};
        String[] samplePasswords = {"pass1","pass2","pass3"};
        String[] sampleEmails = {"email 1", "email 2", "email 3"};
        String[] sampleAddresses = {"address 1", "address 2", "address 3"};

        for (int i = 0; i < sampleNames.length; i++) {
            userDataList.add((new User(sampleNames[i], samplePasswords[i], sampleEmails[i], sampleAddresses[i])));
        }

        userAdapter = new UserDisplayList(this.getContext(), userDataList); // itemDataList is an array of existing items
        userList.setAdapter(userAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}