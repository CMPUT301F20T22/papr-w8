package com.example.papr_w8.ProfilePack;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.papr_w8.MainActivity;
import com.example.papr_w8.ProfilePack.EditProfile;
import com.example.papr_w8.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * profile fragment after user click the profile bottom navigation button
 */
public class Profile extends Fragment {

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference userDoc = FirebaseFirestore.getInstance().collection("Users").document(user.getEmail());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView name = (TextView)view.findViewById(R.id.name);
//        TextView contact = (TextView)view.findViewById(R.id.number);

        // get all the TextView
        final TextView email = (TextView)view.findViewById(R.id.email);
        final TextView address = (TextView)view.findViewById(R.id.address);
//        final TextView phone = (TextView)view.findViewById(R.id.phone);
        Button edit = (Button)view.findViewById(R.id.edit_profile_button);
        Button logout = (Button)view.findViewById(R.id.logout_button);

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        name.setText(doc.getString("name"));
                        address.setText(doc.getString("address"));
                        email.setText(user.getEmail());
//                        phone.setText(doc.getString("address"));
                        Log.d("Sample", "DocumentSnapshot data: " + doc.getData());
                    } else {
                        Log.d("Sample", "No such document");
                    }
                } else {
                    Log.d("Sample", "get failed with ", task.getException());
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getActivity().finish();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}