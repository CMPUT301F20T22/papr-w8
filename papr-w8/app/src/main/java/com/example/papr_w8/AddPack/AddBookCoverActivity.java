package com.example.papr_w8.AddPack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.papr_w8.R;
import com.squareup.picasso.Picasso;

/**
 * AddBookCoverActivity is for choosing and confirms a book cover
 * image is chosen from the user's phone storage
 */
public class AddBookCoverActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button confirmImage;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookcover);

        Button chooseFile = findViewById(R.id.choosefile_button);
        Button confirmImage = findViewById(R.id.confirm_coverbutton);
        imageView = findViewById(R.id.cover_imageview);


        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        }); // onClickListener for choosing a file

        confirmImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // onClickListener for confirming a file
                Intent intent = new Intent();
                intent.putExtra("coverUri", imageUri.toString());  // imageUri is passed back to calling activity in bundle
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * fileChooser() is how the user chooses an image from their phone's storage
     */
    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * image is set to the imageView so the user can see what image they selected
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
        }
    }
}
