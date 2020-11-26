package com.example.papr_w8.ProfilePack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.papr_w8.R;
import com.example.papr_w8.User;

public class RetrivedProfile extends Fragment {

    private TextView name;
    private TextView email;
    private TextView address;

    public static RetrivedProfile newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);

        RetrivedProfile fragment = new RetrivedProfile();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_retrived, container, false);

        final Bundle bundle = getArguments();
        final User user = (User) bundle.getSerializable("user");
        assert user != null;

        name = view.findViewById(R.id.nameVU);
        email = view.findViewById(R.id.emailVU);
        address = view.findViewById(R.id.addressVU);

        name.setText(user.getName());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        Button doneButton = (Button)view.findViewById(R.id.doneVU_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack(); // goes back to default fragment state
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                Fragment searchUserFrag = getActivity().getSupportFragmentManager().findFragmentByTag("retrivedProfileFragment_TAG");
//                if (searchUserFrag != null){
//                    ft.hide(searchUserFrag);
//                }
            }
        });

        return view;
    }
}
