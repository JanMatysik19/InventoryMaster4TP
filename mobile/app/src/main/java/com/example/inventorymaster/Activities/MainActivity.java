package com.example.inventorymaster.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.inventorymaster.Controllers.NavigationController;
import com.example.inventorymaster.R;

public class MainActivity extends AppCompatActivity {
    private NavigationController navigationController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final var frameView = (FrameLayout) findViewById(R.id.frameFl);
        final var inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final var menuView = (ConstraintLayout) findViewById(R.id.menuCl);
        navigationController = new NavigationController(menuView, frameView, inflater);



        navigationController.navigateTo(NavigationController.Page.START);
    }
}