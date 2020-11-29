package com.example.papr_w8.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.papr_w8.Book;
import com.example.papr_w8.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * BoodDisplayList Adapted for displaying list of books
 */
public class BookDisplayList extends ArrayAdapter<Book> {
    private Context context;
    private ArrayList<Book> books;

    /**
     * Adapter which sets the array of Book items
     * @param context context
     * @param books ArrayList of Books
     * @return void
     */
    public BookDisplayList(Context context, ArrayList<Book> books) { // items is an array of all the default items
        super(context,0,books);
        this.context = context;
        this.books = books; // UserDataList, or ArrayList of User objects
    }

    /**
     * sets the fragment to new fragment on selection of a spinner item
     * @return void
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.book_display_list_item,parent,false);
        }

        Book book = books.get(position);

        TextView bookTitle = (TextView) view.findViewById(R.id.book_title_text);
        TextView bookAuthor = (TextView) view.findViewById(R.id.book_author_text);
        TextView bookISBN = (TextView) view.findViewById(R.id.book_isbn_text);
        TextView bookStatus = (TextView) view.findViewById(R.id.book_status_text);
        TextView bookOwner = (TextView) view.findViewById(R.id.book_owner_text);
        final ImageView bookCover = (ImageView) view.findViewById(R.id.book_cover_image);

        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookISBN.setText(book.getISBN());
        bookStatus.setText(book.getStatus());
        bookOwner.setText(book.getOwner());

        //Retrieves book covers here, uses picasso to load images
        FirebaseStorage.getInstance().getReference("images/" + book.getCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(bookCover);
            }
        });

        return view;
    }
}
