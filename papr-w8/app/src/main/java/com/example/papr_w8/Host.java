package com.example.papr_w8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Host extends AppCompatActivity{
    private String startActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host);

        Intent intent = getIntent();
        this.startActivity = intent.getStringExtra(EditProfile.EXTRA_TEXT);

        BottomNavigationView navBar = findViewById(R.id.bottomNavigationView);
        NavController controller = Navigation.findNavController(this, R.id.fragment);
        NavGraph graph = controller.getGraph();
        if(startActivity!=null){
            if(startActivity.equals("Profile")) {
                System.out.println("startActivity is running");
                graph.setStartDestination(R.id.profile);
                controller.setGraph(graph);
            }
        }
        NavigationUI.setupWithNavController(navBar, controller);
    }
}