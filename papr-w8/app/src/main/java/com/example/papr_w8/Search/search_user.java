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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(ArrayList<User> userDataList) {
                Log.d("TAG", "userDataList: " + userDataList.toString());
            }
        });

//        // Default sample data - replace with User data fetched from firebase
//        String[] sampleNames = {"name 1","name 2","name 3"};
//        String[] samplePasswords = {"pass1","pass2","pass3"};
//        String[] sampleEmails = {"email 1", "email 2", "email 3"};
//        String[] sampleAddresses = {"address 1", "address 2", "address 3"};

        Log.d("TAG", "userdatalist: " + userDataList.isEmpty());

        userAdapter = new UserDisplayList(this.getContext(), userDataList); // userDataList is an array of users
        userList.setAdapter(userAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void readData(final FirestoreCallback firestoreCallback) {
        // get emails from firebase
        final Task<QuerySnapshot> userDoc = FirebaseFirestore.getInstance().collection("Users")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            userDataList.add(new User(document.getString("name"), document.getString("password"), document.getString("email"), document.getString("address")));
                            Log.d("TAG", document.getId() + " => " + document.getData());
                        }
                        firestoreCallback.onCallback(userDataList);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                }
            });
    }

    private interface FirestoreCallback{
        void onCallback(ArrayList<User> userDataList);
    }

}