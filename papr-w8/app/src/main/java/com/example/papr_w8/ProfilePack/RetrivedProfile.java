package com.example.papr_w8.ProfilePack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.papr_w8.R;
import com.example.papr_w8.User;

/**
 * Displays profile description of a selected user after it has been retrived from "search"
 * User can click "done" to go back to search page
 */
public class RetrivedProfile extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView address;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_retrived);

        // retrive the selected user
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User) bundle.getSerializable("userSelected");

        name = findViewById(R.id.nameVU);
        email = findViewById(R.id.emailVU);
        address = findViewById(R.id.addressVU);

        name.setText(user.getName());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        // Done button to return to search page
        Button doneButton = (Button) findViewById(R.id.doneVU_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
