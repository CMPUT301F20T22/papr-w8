package com.example.papr_w8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.papr_w8.BookView.RequestConfirmView;
import com.example.papr_w8.R;
import com.example.papr_w8.User;

import java.util.ArrayList;

public class UserRequestsDisplayList extends ArrayAdapter<User> {
    private Context context;
    private ArrayList<User> users;

    public UserRequestsDisplayList(Context context, ArrayList<User> users) { // items is an array of all the default items
        super(context,0,users);
        this.context = context;
        this.users = users; // UserDataList, or ArrayList of User objects
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.user_requests_display_list_item,parent,false);
        }

        User user = users.get(position);

        TextView userName = (TextView) view.findViewById(R.id.user_name_text);
        Button acceptRequest = (Button) view.findViewById(R.id.accept_request);
        Button declineRequest = (Button) view.findViewById(R.id.decline_request);

        userName.setText(user.getName());

        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RequestConfirmView.class);
                context.startActivity(intent);
            }
        });

        declineRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users.remove(position);
            }
        });

        return view;
    }
}
