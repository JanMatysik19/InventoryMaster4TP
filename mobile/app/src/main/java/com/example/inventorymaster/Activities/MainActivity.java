package com.example.inventorymaster.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymaster.R;

public class MainActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private FrameLayout frameFl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameFl = findViewById(R.id.frameFl);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        navigateTo(R.layout.activity_start);
    }

    public void navigateTo(int id) {
        var view = inflater.inflate(id, frameFl, false);
        frameFl.removeAllViews();
        frameFl.addView(view);
    }
}