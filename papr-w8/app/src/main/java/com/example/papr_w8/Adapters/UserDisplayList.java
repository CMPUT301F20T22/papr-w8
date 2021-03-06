package com.example.papr_w8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.papr_w8.R;
import com.example.papr_w8.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Adapter which sets the array of Book items
 *
 * @param context context
 * @param users ArrayList of Users
 * @return void
 */
public class UserDisplayList extends ArrayAdapter<User> {
    private Context context;
    private ArrayList<User> users;

    public UserDisplayList(Context context, ArrayList<User> users) { // items is an array of all the default items
        super(context,0,users);
        this.context = context;
        this.users = users; // UserDataList, or ArrayList of User objects
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.user_display_list_item,parent,false);
        }

        User user = users.get(position);

        TextView userName = (TextView) view.findViewById(R.id.user_name_text);
        TextView userEmail = (TextView) view.findViewById(R.id.user_email_text);

        userName.setText(user.getName());
        userEmail.setText(user.getEmail());

        return view;
    }
}
