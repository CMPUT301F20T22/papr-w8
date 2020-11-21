package com.example.papr_w8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.papr_w8.BookView.BookOwnedView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;


import java.io.Serializable;
import java.util.List;

/**
 * Fragment that allows user to view book, borrow, or return a book by scanning an ISBN
 */
public class Scan extends Fragment {

    private String ISBN;

    private final int VIEW_DESCRIPTION_REQUEST_CODE = 100;
    private final int BORROW_REQUEST_CODE = 200;
    private final int RETURN_REQUEST_CODE = 300;

    private Button view_desc;
    private Button borrow;
    private Button return_book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scan, container, false);

        view_desc = view.findViewById(R.id.view_desc_button);

        borrow = view.findViewById(R.id.borrow_button);

        return_book = view.findViewById(R.id.return_button);

        view_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, VIEW_DESCRIPTION_REQUEST_CODE);
            }
        });

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, BORROW_REQUEST_CODE);
            }
        });

        return_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RETURN_REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap photo = (Bitmap)data.getExtras().get("data");

        InputImage image = InputImage.fromBitmap(photo, 0);

        scanBarcode(image, requestCode);

    }




    private void scanBarcode(InputImage image, final int requestCode){
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder().setBarcodeFormats(
                        Barcode.ISBN)
                        .build();

        BarcodeScanner scanner = BarcodeScanning.getClient(options);

        Task<List<com.google.mlkit.vision.barcode.Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<com.google.mlkit.vision.barcode.Barcode>>() {
                    @Override
                    public void onSuccess(List<com.google.mlkit.vision.barcode.Barcode> barcodes) {

                        for (com.google.mlkit.vision.barcode.Barcode barcode : barcodes) {

                            ISBN = barcode.getDisplayValue();
                            switch (requestCode) {
                                case VIEW_DESCRIPTION_REQUEST_CODE:
                                    getBookDetails();
                                    break;
                                case BORROW_REQUEST_CODE:
                                    //notify owner of borrow
                                    break;
                                case RETURN_REQUEST_CODE:
                                    //notify owner of return
                                    break;
                            }
                        }
                    }
                });

    }

    private void getBookDetails(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        final Task<QuerySnapshot> bookDoc = FirebaseFirestore.getInstance().collection("Users")
                .document(email).collection("Books Owned").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //add book items from database
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                if (ISBN == document.getString("ISBN")){
                                    String title = document.getString("Title");
                                    String author = document.getString("Author");
                                    String status = document.getString("Status");
                                    String book_cover = document.getString("Book Cover");
                                    Book book = new Book(title,author,ISBN,status,book_cover);

                                    BookOwnedView bookBasicView = new BookOwnedView();

                                    Bundle bundle = new Bundle();

                                    bundle.putSerializable("bookSelected", (Serializable) book);

                                    Intent intent = new Intent(getContext(), BookOwnedView.class);

                                    //transfer data

                                    bookBasicView.setArguments(bundle);
                                    startActivity(intent);


                                }
                                else{
                                    Toast.makeText(getContext(), "You do not own this book, unable to view.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

    }
}