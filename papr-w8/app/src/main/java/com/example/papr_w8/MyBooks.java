package com.example.papr_w8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBooks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBooks extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String bookList; //PLACEHOLDER
    private Context context;

    public MyBooks(@NonNull Context context, String bookList) {
        this.bookList = bookList;
        this.context = context;

    }

    public MyBooks() {
    }

    @NonNull
//    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_my_books, parent, false);
        }
        TextView viewBar = (TextView)view.findViewById(R.id.view_bar);
        Button owned =  (Button) view.findViewById(R.id.books_owned);
        Button borrowed =  (Button) view.findViewById(R.id.books_borrowed);
        Button requested =  (Button) view.findViewById(R.id.books_requested);

        TextView actionBar = (TextView)view.findViewById(R.id.actions);
        Button add_book =  (Button) view.findViewById(R.id.add_book);
        Button find = (Button) view.findViewById(R.id.find_book);
        Button accepted = (Button) view.findViewById(R.id.accept_request);
        add_book.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddBook.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyBooks.
     */
    // TODO: Rename and change types and number of parameters
    public static MyBooks newInstance(String param1, String param2) {
        MyBooks fragment = new MyBooks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_books, container, false);
    }
}