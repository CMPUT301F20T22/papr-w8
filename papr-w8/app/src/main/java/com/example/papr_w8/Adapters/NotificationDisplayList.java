package com.example.papr_w8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.papr_w8.Book;
import com.example.papr_w8.Notification;
import com.example.papr_w8.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NotificationDisplayList extends ArrayAdapter<Notification> {
    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationDisplayList(Context context, ArrayList<Notification> notifications) { // items is an array of all the default items
        super(context,0,notifications);
        this.context = context;
        this.notifications = notifications; // UserDataList, or ArrayList of User objects
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.notification_list_item,parent,false);
        }

        Notification notification = notifications.get(position);

        TextView message = view.findViewById(R.id.notification_text);

        String status = notification.getType();
        String sender = notification.getSenderName();
        String book_title = notification.getBookTitle();

        switch (status){
            case "request":
                message.setText(sender + " is requesting to borrow " + book_title + ".");
                break;
            case "borrow_scan":
                message.setText(sender + " has scanned to checkout " + book_title +". Scan to confirm.");
                break;
            case "return_scan":
                message.setText(sender + " has scanned to return " + book_title + ". Scan to confirm.");
                break;
            case "accepted":
                message.setText(sender + " has accepted your request to borrow " + book_title + ".");
                break;
            case "declined":
                message.setText(sender + " has denied your request to borrow " + book_title + ".");
                break;
            case "confirm_borrow":
                message.setText(sender + " has scanned to confirm your borrowing of " + book_title + ".");
                break;
            case "confirm_return":
                message.setText(sender + " has scanned to confirm the return of " + book_title + ".");
                break;

        }
        return view;

    }


}
