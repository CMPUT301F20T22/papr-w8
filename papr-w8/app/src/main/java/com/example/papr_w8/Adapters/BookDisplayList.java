package com.example.papr_w8.Adapters;

import android.content.Context;
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

import java.util.ArrayList;

/**
 * Adapter which sets the array of Book items
 *
 * @param context context
 * @param books ArrayList of Books
 * @return void
 */
public class BookDisplayList extends ArrayAdapter<Book> {
    private Context context;
    private ArrayList<Book> books;

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
//        ImageView bookCover = (ImageView) view.findViewById(R.id.book_cover_image);

        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookISBN.setText(book.getISBN());
        bookStatus.setText(book.getStatus());

        return view;
    }
}
