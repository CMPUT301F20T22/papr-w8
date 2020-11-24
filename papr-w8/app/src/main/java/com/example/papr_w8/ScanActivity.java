package com.example.papr_w8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.papr_w8.AddPack.AddBook;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;



import java.io.IOException;


/**
 * Activity to allow users to scan a book's ISBN
 */


public class ScanActivity extends AppCompatActivity {


    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private SurfaceView surfaceView;

    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    private String isbn;
    private static final String TAG = "ScanActivity";


    /**
     * Initialize UI elements, barcode detector, camera source and surface view.
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        surfaceView = findViewById(R.id.scan_camera_view);
        initializeDetectorsAndSources();

}

    /**
     * Initializes barcode detector, camera source, and surface view
     */
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
                    Intent intent = new Intent();
                    intent.putExtra("ISBN", isbn);  // isbn is passed back to calling activity in bundle
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });
    }

}