package com.example.inventorymaster.Modules.Items;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.inventorymaster.Config.InventoryMaster;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.FieldValidator;

public class ItemAddActivity extends AppCompatActivity {
    private CardView itemAddBackCv;
    private CardView itemAddCv;
    private EditText itemAddDescriptionEt;
    private EditText itemAddFeaturesCodeEt;
    private EditText itemAddPriceEt;
    private ItemDAO itemDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);

        final var app = (InventoryMaster) getApplication();
        itemDAO = app.getItemModel();

        itemAddBackCv = findViewById(R.id.itemAddBackCv);
        itemAddBackCv.setOnClickListener(this::handleGoBack);

        itemAddCv = findViewById(R.id.itemAddCv);
        itemAddCv.setOnClickListener(this::handleAddItem);

        itemAddFeaturesCodeEt = findViewById(R.id.itemAddFeaturesCodeEt);
        itemAddPriceEt = findViewById(R.id.itemAddPriceEt);
        itemAddDescriptionEt = findViewById(R.id.itemAddDescriptionEt);
    }

    private void goBack() {
        finish();
    }

    private void handleGoBack(View v) {
        goBack();
    }

    private void handleAddItem(View v) {
        final var context = v.getContext();

        final var featuresCode = itemAddFeaturesCodeEt.getText().toString();
        if(!FieldValidator.Item.validateFeaturesCode(featuresCode)) {
            Toast.makeText(context, "Zły kod", Toast.LENGTH_SHORT).show();
            return;
        }
        final var parsedFeaturesCode = featuresCode.trim().toUpperCase();

        final var price = itemAddPriceEt.getText().toString();
        final var parsedPrice = price.trim();

        final var description = itemAddDescriptionEt.getText().toString();
        if(!FieldValidator.Item.validateDescription(description)) {
            Toast.makeText(context, "Zły opis", Toast.LENGTH_SHORT).show();
            return;
        }
        final var parsedDescription = description.trim();

        itemDAO.addItem(new ItemDTO.AddItemRequest(parsedFeaturesCode, parsedDescription, parsedPrice), data -> {
            Toast.makeText(context, "Pomyślnie dodano nowy artykił", Toast.LENGTH_LONG).show();
            goBack();
        });
    }
}
