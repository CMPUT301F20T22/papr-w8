package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.papr_w8.AddPack.EditBook;
import com.example.papr_w8.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This is a Fragment that displays the view of a Book Description that is Owned providing
 * options for the Owner to Edit, View Requests, or Delete Book.
 */
public class BookOwnedView extends BookBase {

    private String fileName;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore fbDB = FirebaseFirestore.getInstance();
    private String email = user.getEmail();


    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void provideYourFragmentView(final View baseView, ViewGroup container){
        Button buttonEditDescription;
        Button buttonDeleteBook;

        setRetainInstance(true);
        ViewStub stub = baseView.findViewById(R.id.child_fragment_here);
        stub.setLayoutResource(R.layout.fragment_book_owned);
        stub.inflate();

        buttonEditDescription = (Button) baseView.findViewById(R.id.editdescriptionButton);
        buttonDeleteBook = (Button) baseView.findViewById(R.id.deleteButton);

        buttonEditDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO implement the action of clicking the EditDescription button
                Intent intent = new Intent(getContext(), EditBook.class);
                intent.putExtra("Book", book);
                startActivity(intent);
            }
        });

        buttonDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookID = book.getId();

                ConfirmDelete confirmDelete = new ConfirmDelete();

                Bundle bundle = new Bundle();
                //book = (Book) bundle.getSerializable("bookSelected");
                //bundle.putSerializable("bookSelected", (Serializable) book);
                bundle.putString("Book ID", bookID );
                confirmDelete.setArguments(bundle);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_book_base,confirmDelete,confirmDelete.getTag());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    };
}