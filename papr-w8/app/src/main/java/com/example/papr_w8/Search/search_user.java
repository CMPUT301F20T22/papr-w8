package com.example.papr_w8.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.papr_w8.R;
import com.example.papr_w8.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class search_user extends Fragment {

    ListView userList;
    ArrayAdapter<User> userAdapter;
    ArrayList<User> userDataList;

    ArrayList<String> userNames = new ArrayList<String>();
    ArrayList<String> userEmails = new ArrayList<String>();
    ArrayList<String> userPasswords = new ArrayList<String>();
    ArrayList<String> userAddresses = new ArrayList<String>();

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

//        // Default sample data - replace with User data fetched from firebase
//        String[] sampleNames = {"name 1","name 2","name 3"};
//        String[] samplePasswords = {"pass1","pass2","pass3"};
//        String[] sampleEmails = {"email 1", "email 2", "email 3"};
//        String[] sampleAddresses = {"address 1", "address 2", "address 3"};
//
//        for (int i = 0; i < sampleNames.length; i++) {
//            userDataList.add((new User(sampleNames[i], samplePasswords[i], sampleEmails[i], sampleAddresses[i])));
//        }

        // get emails from firebase
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference userDoc = FirebaseFirestore.getInstance().collection("Users").document(user.getEmail());

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    // display user profiles
                    userNames.add(doc.getString("name"));
                    userEmails.add(doc.getString("email"));
                    userPasswords.add(doc.getString("password"));
                    userAddresses.add(doc.getString("address"));
                    Log.d("Sample", "DocumentSnapshot data: " + doc.getData());
                } else { // if user profile does not exist
                    // display empty listview
                    Log.d("Sample", "No such user");
                }
            } else {
                Log.d("Sample", "get failed with ", task.getException());
            }
            }
        });

        System.out.println(userNames.isEmpty());

        for (int i = 0; i < userNames.size(); i++) {
            userDataList.add(new User(userNames.get(i), userPasswords.get(i), userPasswords.get(i), userEmails.get(i)));
        }

        userAdapter = new UserDisplayList(this.getContext(), userDataList); // itemDataList is an array of existing items
        userList.setAdapter(userAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}