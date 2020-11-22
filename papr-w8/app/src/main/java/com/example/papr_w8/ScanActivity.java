package com.example.papr_w8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.papr_w8.BookView.BookScannedView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.io.IOException;
import java.io.Serializable;


public class ScanActivity extends AppCompatActivity {


    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private SurfaceView surfaceView;

    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    private String isbn;
    private static final String TAG = "ScanActivity";


    /**
     * Initialize UI elements, barcode detector, camera source and camera view.
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        surfaceView = findViewById(R.id.scan_camera_view);
        initializeDetectorsAndSources();

}

    private void initializeDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.EAN_13)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanActivity.this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(surfaceView.getHolder());
                    }
                    else {
                        ActivityCompat.requestPermissions(ScanActivity.this,
                                new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {
                Toast.makeText(getApplicationContext(),
                        "Barcode scanner has been stopped to prevent memory leaks",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0 ){
                    isbn = barcodes.valueAt(0).displayValue;

                    // go back to the caller activity and return the ISBN with an intent.
                    Intent intent = getIntent();
                    String request = intent.getStringExtra("request");
                    switch(request){
                        case "view description":
                            getBookDetails();
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
                                Log.i(TAG, "ISBN: " + document.getString("ISBN"));
                                if (isbn == document.getString("ISBN")){
                                    String title = document.getString("Title");
                                    String author = document.getString("Author");
                                    String status = document.getString("Status");
                                    String book_cover = document.getString("Book Cover");
                                    Book book = new Book(title,author,isbn,status,book_cover);

                                    BookScannedView bookScannedView = new BookScannedView();

                                    //bundle data to transfer
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("bookScanned", (Serializable) book);
                                    bookScannedView.setArguments(bundle);

                                    FragmentManager fragmentmanager = getSupportFragmentManager();
                                    FragmentTransaction transaction = fragmentmanager.beginTransaction();
                                    transaction.replace(R.id.book_scanned, bookScannedView);
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                    transaction.commit();



                                }
                                else{
                                    Toast.makeText(ScanActivity.this, "You do not own this book, unable to view.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

    }
}