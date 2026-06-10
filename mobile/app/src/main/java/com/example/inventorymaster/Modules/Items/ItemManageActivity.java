package com.example.inventorymaster.Modules.Items;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.inventorymaster.Config.InventoryMaster;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.FieldValidator;

public class ItemManageActivity extends AppCompatActivity {
    private TextView itemInstanceManageInstanceTv;
    private CardView itemInstanceManageDeleteCv;
    private CardView itemInstanceManageBackCv;
    private EditText itemInstanceManageFeaturesCodeEt;
    private EditText itemInstanceManagePriceEt;
    private EditText itemInstanceManageDescriptionEt;
    private CardView itemInstanceManageConfirmCv;

    private Item item;
    private ItemDAO itemDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (Item) getIntent().getSerializableExtra("item");
        setContentView(R.layout.activity_item_manage);

        final var app = (InventoryMaster) getApplication();
        itemDAO = app.getItemModel();

        itemInstanceManageInstanceTv = findViewById(R.id.itemInstanceManageInstanceTv);
        itemInstanceManageInstanceTv.setText(item.getFeaturesCode());

        itemInstanceManageDeleteCv = findViewById(R.id.itemInstanceManageDeleteCv);
        itemInstanceManageDeleteCv.setOnClickListener(this::handleDeleteItem);

        itemInstanceManageBackCv = findViewById(R.id.itemInstanceManageBackCv);
        itemInstanceManageBackCv.setOnClickListener(this::handleGoBack);

        itemInstanceManageFeaturesCodeEt = findViewById(R.id.itemInstanceManageFeaturesCodeEt);
        itemInstanceManageFeaturesCodeEt.setText(item.getFeaturesCode());

        itemInstanceManagePriceEt = findViewById(R.id.itemInstanceManagePriceEt);
        itemInstanceManagePriceEt.setText(item.getPrice());

        itemInstanceManageDescriptionEt = findViewById(R.id.itemInstanceManageDescriptionEt);
        itemInstanceManageDescriptionEt.setText(item.getDescription());

        itemInstanceManageConfirmCv = findViewById(R.id.itemInstanceManageConfirmCv);
        itemInstanceManageConfirmCv.setOnClickListener(this::handleConfirmItemChanges);
    }

    private void goBack() {
        finish();
    }

    private void handleGoBack(View v) {
        goBack();
    }

    private void handleDeleteItem(View v) {
        itemDAO.deleteItem(item.getId(), ignore -> {
            Toast.makeText(v.getContext(), "Usunięto artykuł", Toast.LENGTH_LONG).show();
            setResult(1);
            goBack();
        });
    }

    private void handleConfirmItemChanges(View v) {
        final var context = v.getContext();

        final var featuresCode = itemInstanceManageFeaturesCodeEt.getText().toString();
        if(!FieldValidator.Item.validateFeaturesCode(featuresCode)) {
            Toast.makeText(context, "Zły kod", Toast.LENGTH_SHORT).show();
            return;
        }
        final var parsedFeaturesCode = featuresCode.trim().toUpperCase();

        final var price = itemInstanceManagePriceEt.getText().toString();
        final var parsedPrice = price.trim();

        final var description = itemInstanceManageDescriptionEt.getText().toString();
        if(!FieldValidator.Item.validateDescription(description)) {
            Toast.makeText(context, "Zły opis", Toast.LENGTH_SHORT).show();
            return;
        }
        final var parsedDescription = description.trim();

        if(featuresCode.equals(item.getFeaturesCode()) && description.equals(item.getDescription()) && price.equals(item.getPrice())) {
            Toast.makeText(context, "Takie same dane", Toast.LENGTH_SHORT).show();
            return;
        }

        itemDAO.updateItem(item.getId(), new ItemDTO.UpdateItemRequest(parsedFeaturesCode, parsedDescription, parsedPrice), data -> {
            Toast.makeText(context, "Zakutalizowano artykuł", Toast.LENGTH_LONG).show();
            setResult(2);
            goBack();
        });
    }
}
