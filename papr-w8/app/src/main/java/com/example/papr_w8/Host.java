package com.example.papr_w8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Host extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host);

        BottomNavigationView navBar = findViewById(R.id.bottomNavigationView);
        NavController controller = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(navBar, controller);
    }
}