package com.example.inventorymaster.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymaster.Models.BoxModel;
import com.example.inventorymaster.Models.CategoryModel;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.HttpClient;

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

        final var httpClient = new HttpClient();
        final var boxModel = new BoxModel(httpClient);
        final var categoryModel = new CategoryModel(httpClient);

        boxModel.getBoxes((boxes) -> {
            Log.println(Log.INFO, "BOX MODEL", "got " + boxes.size() + " boxes");
        });
        boxModel.getBox(5, (box) -> {
            if(box == null) return;
            Log.println(Log.INFO, "BOX MODEL", "got " + box.getCode() + " box");
        });

        boxModel.getBox(-1, (box) -> {
            if(box == null) return;
            Log.println(Log.INFO, "BOX MODEL", "got " + box.getCode() + " box");
        });
    }

    public void navigateTo(int id) {
        var view = inflater.inflate(id, frameFl, false);
        frameFl.removeAllViews();
        frameFl.addView(view);
    }
}