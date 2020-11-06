package com.example.papr_w8.BookView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.papr_w8.R;

/**
 * This is a Fragment that displays the view of a Book Description that has been Requested and needs
 * to be Approved or Declined by a Book Owner.
 */
public class BookApproveRequestView extends Fragment {

    public BookApproveRequestView() {
    }

    private Button buttonApproveRequest;
    private Button buttonDeclineRequest;

    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewISBN;
    private TextView textViewStatus;

    private ImageView imageViewDefault;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_owned, container, false);

        buttonApproveRequest = (Button) view.findViewById(R.id.approverequestButton);
        buttonDeclineRequest = (Button) view.findViewById(R.id.declinerequestButton);

        textViewTitle = view.findViewById(R.id.titleEditText);
        textViewAuthor = view.findViewById(R.id.authorEditText);
        textViewISBN = view.findViewById(R.id.isbnEditText);
        textViewStatus = view.findViewById(R.id.statusEditText);

        // This onClickListener performs the action of updating the status of a Book to Approved
        // sending the user to a pop-up screen notifying them that the Borrower has been
        // notified.

        buttonApproveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Approve button
            }
        });

        // This onClickListener performs the action of updating the status of a Book to Declined
        // sending the user to a pop-up screen notifying them that the Borrower has been
        // notified.

        buttonDeclineRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the Decline button
            }
        });

        return view;
    }
}