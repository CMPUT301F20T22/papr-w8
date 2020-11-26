package com.example.papr_w8.BookView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.papr_w8.AddPack.AddBook;
import com.example.papr_w8.Book;
import com.example.papr_w8.Host;
import com.example.papr_w8.MainActivity;
import com.example.papr_w8.R;
import com.example.papr_w8.ShelfPack.BooksOwned;
import com.example.papr_w8.ShelfPack.Shelves;
import com.example.papr_w8.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
            }
        });

        buttonDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookID = book.getId();

                ConfirmDelete confirmDelete = new ConfirmDelete();

                Bundle bundle = new Bundle();
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