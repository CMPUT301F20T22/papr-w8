package com.example.papr_w8.ShelfPack;

/**
 * may merge this fragment with shelves next sprint
 * page that displays user options
 * clicking on a button takes user to other fragment/activities
*/
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.papr_w8.R;

public class Shelves extends Fragment {
    public Shelves() {
    }
    private Button owned_button ;

    private Button requested_button;
    private Button accepted_requests_button;
    private Button awaiting_approval_button;
    private Button borrowed_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_shelves, container, false);

        //Buttons to take user to other pages
        owned_button =  (Button) view.findViewById(R.id.books_owned);
        requested_button =  (Button) view.findViewById(R.id.books_requested);
        accepted_requests_button =  (Button) view.findViewById(R.id.accepted_requests);
        awaiting_approval_button = (Button) view.findViewById(R.id.awaiting_approval);
        borrowed_button =  (Button) view.findViewById(R.id.books_borrowed);

        //go to BooksOwned fragment
        owned_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BooksOwned booksOwned = new BooksOwned();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.shelves,booksOwned,booksOwned.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Go to BooksRequested fragment
        requested_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BooksRequested booksRequested = new BooksRequested();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.shelves,booksRequested,booksRequested.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //GO to AddBook activity
        accepted_requests_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptedRequests acceptedRequests = new AcceptedRequests();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.shelves, acceptedRequests, acceptedRequests.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //go to books AwaitingApproval Fragment
        awaiting_approval_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AwaitingApproval awaitingApproval = new AwaitingApproval();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.shelves, awaitingApproval, awaitingApproval.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Go to BooksBorrowed fragment
        borrowed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BooksBorrowed booksBorrowed = new BooksBorrowed();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.shelves,booksBorrowed,booksBorrowed.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}