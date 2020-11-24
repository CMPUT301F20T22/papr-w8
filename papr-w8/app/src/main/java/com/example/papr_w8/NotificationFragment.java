package com.example.papr_w8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.papr_w8.Adapters.NotificationDisplayList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    private ArrayList<Notification> notifications;
    private ListView notification_listview;
    private FirebaseAuth firebaseAuth;
    private NotificationDisplayList notification_adapter;




    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notification_fragment, container, false);
        notification_listview = view.findViewById(R.id.notification_listview);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = user.getEmail();

        final Task<QuerySnapshot> user_notifications = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Notifications").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //add book items from database
                        if (task.isSuccessful()){
                            notifications = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()){

                                Notification notification = new Notification(
                                        document.getString("Sender"), document.getString("Type"),
                                        document.getString("Book Title"), document.getString("Name"));

                                notifications.add(notification);
                                notification_adapter = new NotificationDisplayList(getContext(), notifications);
                                notification_listview.setAdapter(notification_adapter);
                                Log.d("MyTag", document.getString("Sender"));
                            }
                        }
                    }
                });
        return view;

    }
}
