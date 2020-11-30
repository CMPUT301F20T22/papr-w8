package com.example.papr_w8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.papr_w8.Adapters.NotificationDisplayList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Notification fragment that displays a user's notifications
 * Allows an owner to lend or to accept a returned book by scanning by clicking on
 * a notification of a certain type
 */
public class NotificationFragment extends Fragment {

    private static final String TAG = "myTag";
    private ArrayList<Notification> notifications;
    private ListView notification_listview;
    private NotificationDisplayList notification_adapter;
    private Notification selected_notification;
    private String user_name;
    private String user_email;

    private final int SCAN_TO_CONFIRM_BORROW = 1;
    private final int SCAN_TO_CONFIRM_RETURN = 2;


    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Creates the fragment view and loads the ArrayAdapter with notifications from Firestore Database
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notification_fragment, container, false);
        notification_listview = view.findViewById(R.id.notification_listview);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user_email = user.getEmail();

        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(user_email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user_name = documentSnapshot.getString("name");
                    }
                });

        // Gets the user's notifications from Firestore and adds them to notifications list
        final Task<QuerySnapshot> user_notifications = FirebaseFirestore.getInstance().collection("Users")
                .document(user_email).collection("Notifications").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // add notifications from database
                        if (task.isSuccessful()){
                            notifications = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()){

                                Notification notification = new Notification(
                                        document.getString("Sender"), document.getString("Type"),
                                        document.getString("Book Title"), document.getString("Name"),
                                        document.getString("Book ID"));
                                notification.setNotification_id(document.getId());
                                notifications.add(notification);
                                notification_adapter = new NotificationDisplayList(getContext(), notifications);
                                notification_listview.setAdapter(notification_adapter);
                            }
                        }
                    }
                });
        // if selected notification has type "borrow_scan" or "return_scan", go to ScanActivity
        notification_listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected_notification = notifications.get(i);
                String type = selected_notification.getType();
                switch(type){
                    case "borrow_scan":
                        Intent confirm_borrow = new Intent(getContext(), ScanActivity.class);
                        startActivityForResult(confirm_borrow, SCAN_TO_CONFIRM_BORROW);
                        break;
                    case "return_scan":
                        Intent confirm_return = new Intent(getContext(), ScanActivity.class);
                        startActivityForResult(confirm_return, SCAN_TO_CONFIRM_RETURN);
                        break;
                }

            }
        });

        notification_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                notifications.remove(i);
                notification_adapter.notifyDataSetChanged();
                return true;
            }
        });



        return view;

    }

    /**
     * On successful scan, notify Borrower and make necessary updates to Borrower and Owner's book collections
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final String scanned_isbn = data.getStringExtra("ISBN");

        //If scanning to confirm a borrow, notify the borrower, update the borrower's book collections,
        // update status of selected book to borrowed
        if (requestCode == SCAN_TO_CONFIRM_BORROW) {
            if (resultCode == RESULT_OK) {
                //get the sender's email to update their books and notifications
                String sender = selected_notification.getSenderId();
                Task<DocumentSnapshot> accepted = FirebaseFirestore.getInstance().collection("Users")
                        .document(sender)
                        .collection("Books_Accepted")
                        .document(selected_notification.getBook_id())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult() != null){
                                    String isbn = task.getResult().getString("ISBN");
                                        if (isbn.matches(scanned_isbn)) {
                                            //notify the borrower
                                            notifyBorrower(user_email, selected_notification.getSenderId(), user_name, "confirm_borrow");
                                            //update their books
                                            updateBorrowerBooks(selected_notification.getSenderId(),
                                                    selected_notification.getBook_id());
                                            //change book status to "Borrowed"
                                            updateOwnerBooks(user_email, selected_notification.getBook_id(), "Borrowed");

                                        }
                                    }
                                }
                            }
                        });
            }
        } else if (requestCode == SCAN_TO_CONFIRM_RETURN) {
            if (resultCode == RESULT_OK) {
                    final String sender = selected_notification.getSenderId();
                    Task<DocumentSnapshot> accepted = FirebaseFirestore.getInstance().collection("Users")
                            .document(sender)
                            .collection("Books_Borrowed")
                            .document(selected_notification.getBook_id())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult() != null){
                                        String isbn = task.getResult().getString("ISBN");
                                            if (isbn.matches(scanned_isbn)) {
                                                //set the notification to viewed
                                                selected_notification.setViewed();
                                                //notify the borrower
                                                notifyBorrower(user_email, selected_notification.getSenderId(), user_name, "confirm_return");
                                                //delete book from Borrowed collection
                                                deleteBookFromCollection(sender, selected_notification.getBook_id());
                                                //change book status to "Available"
                                                updateOwnerBooks(user_email, selected_notification.getBook_id(), "Available");

                                            }
                                        }
                                    }
                                }
                            });


                }
            }

        }

    /**
     * Updates the book status
     * @param owner_email
     * @param book_id
     */
    private void updateOwnerBooks(String owner_email, String book_id, String status) {
        FirebaseFirestore.getInstance().collection("Users")
                .document(owner_email)
                .collection("Books Owned")
                .document(book_id)
                .update("Status", status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Status of book has been updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failure to update book status");
                    }
                });
    }

    /**
     * Notifies the borrower that owner of book has scanned to confirm borrowing
     * @param owner_email
     * @param borrower_email
     * @param user_name
     * @param type
     */
    public void notifyBorrower(final String owner_email, final String borrower_email, final String user_name, final String type) {
        Task<DocumentSnapshot> user = FirebaseFirestore.getInstance().collection("Users")
                .document(borrower_email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> notification = new HashMap<>();
                            notification.put("Sender", owner_email);
                            notification.put("Name", user_name);
                            notification.put("Type", type);
                            notification.put("Book Title", selected_notification.getBookTitle());
                            notification.put("Book ID", selected_notification.getBook_id());
                            //add notification to Notifications collection inside of borrower's user document

                            db.collection("Users")
                                    .document(borrower_email)
                                    .collection("Notifications")
                                    .add(notification)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "Borrower has been notified.");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Failure to notify borrower.");
                                        }
                                    });
                        }
                    }
                });
    }

    /**
     * Adds the book to the borrower's Borrowed Books Collection and deletes book from Accepted Collection
     * @param borrower_email
     * @param book_id
     */
    public void updateBorrowerBooks(final String borrower_email, final String book_id){
        //add book to borrowed books
        //delete book from accepted books
        Task<DocumentSnapshot> acc_book = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(borrower_email)
                .collection("Books_Accepted")
                .document(book_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            Map<String, Object> bor_book = new HashMap<>();
                            bor_book.put("Title", document.getString("Title"));
                            bor_book.put("Author", document.getString("Author"));
                            bor_book.put("ISBN", document.getString("ISBN"));
                            bor_book.put("Owner", document.getString("Owner"));
                            bor_book.put("Status", "Borrowed");
                            bor_book.put("Book Cover", document.getString("Book Cover"));
                            //Add to Borrowed Books collection
                            FirebaseFirestore.getInstance()
                                    .collection("Users")
                                    .document(borrower_email)
                                    .collection("Books_Borrowed")
                                    .document(book_id)
                                    .set(bor_book)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //delete from Accepted collection
                                            FirebaseFirestore.getInstance()
                                                    .collection("Users")
                                                    .document(borrower_email)
                                                    .collection("Books_Accepted")
                                                    .document(book_id)
                                                    .delete();
                                        }

                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Failure to add book to Books_Borrowed collection.");
                                        }
                                    });

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failure to find document.");
                    }
                });

    }

    public void deleteBookFromCollection(String borrower_email, String book_id){
        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(borrower_email)
                .collection("Books_Borrowed")
                .document(book_id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Successfully deleted document from Collection");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failure to delete document from Collection.");
                    }
                });
    }

}
