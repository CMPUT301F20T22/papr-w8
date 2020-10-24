package com.example.papr_w8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User user;
    private Context context;

    public Profile(@NonNull Context context, User user){
        this.user = user;
        this.context = context;
    }

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Get log in information to get the correct profile information
     */
    @NonNull
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_profile, parent, false);
        }
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView contact = (TextView)view.findViewById(R.id.number);
        TextView email = (TextView)view.findViewById(R.id.email);
        TextView address = (TextView)view.findViewById(R.id.address);
        Button edit = (Button)view.findViewById(R.id.EditProfile);

        name.setText(user.getName());
        contact.setText(user.getContact());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditProfile(user);
                Intent intent = new Intent(view.getContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}