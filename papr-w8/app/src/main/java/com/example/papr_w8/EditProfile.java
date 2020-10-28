package com.example.papr_w8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {

    private User user;

    public EditProfile(User user){
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        EditText username = (EditText) findViewById(R.id.usernameEdit);
        EditText phone = (EditText) findViewById(R.id.phoneEdit);
        EditText email = (EditText) findViewById(R.id.emailEdit);
        EditText address = (EditText) findViewById(R.id.addressEdit);

        username.setText(user.getName());
        phone.setText(user.getPassword());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        // checking what user entered

    }
}