package com.example.inventorymaster.Modules.ItemInstances;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.inventorymaster.Config.InventoryMaster;
import com.example.inventorymaster.Modules.Boxes.Box;
import com.example.inventorymaster.Modules.Boxes.BoxDAO;
import com.example.inventorymaster.Modules.Boxes.BoxSelectActivity;
import com.example.inventorymaster.Modules.Items.Item;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.Stringer;

public class ItemInstanceManageActivity extends AppCompatActivity {
    private TextView itemInstanceManageCodeTv;
    private TextView itemInstanceBoxCodeTv;
    private CardView itemInstanceChangeBoxCv;
    private CardView itemInstanceDeleteCv;
    private CardView itemInstanceBackCv;

    private Item item;
    private ItemInstance itemInstance;
    private ItemInstanceDAO itemInstanceDAO;
    private BoxDAO boxDAO;

    private ActivityResultLauncher<Intent> boxSelectLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_instance_manage);

        item = (Item) getIntent().getSerializableExtra("item");
        itemInstance = (ItemInstance) getIntent().getSerializableExtra("instance");

        final var app = (InventoryMaster) getApplication();
        itemInstanceDAO = app.getItemInstanceDAO();
        boxDAO = app.getBoxDAO();

        boxSelectLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Box selectedBox = (Box) result.getData().getSerializableExtra(BoxSelectActivity.BOX_RESULT_LABEL);
                        if (selectedBox != null) {
                            handleMoveToBox(selectedBox);
                        }
                    }
                }
        );

        itemInstanceManageCodeTv = findViewById(R.id.itemInstanceManageCodeTv);
        itemInstanceBoxCodeTv = findViewById(R.id.itemInstanceBoxCodeTv);
        itemInstanceChangeBoxCv = findViewById(R.id.itemInstanceChangeBoxCv);
        itemInstanceDeleteCv = findViewById(R.id.itemInstanceDeleteCv);
        itemInstanceBackCv = findViewById(R.id.itemInstanceBackCv);

        setupData();

        itemInstanceBackCv.setOnClickListener(v -> finish());
        itemInstanceDeleteCv.setOnClickListener(this::handleDelete);
        itemInstanceChangeBoxCv.setOnClickListener(this::handleChangeBox);
    }

    private void setupData() {
        itemInstanceManageCodeTv.setText(item.getFeaturesCode() + "-" + Stringer.padLeft(itemInstance.getSequenceNumber(), 3));

        boxDAO.getBox(itemInstance.getBoxId(), box -> {
            if(box != null) {
                itemInstanceBoxCodeTv.setText(box.getCode());
            } else {
                itemInstanceBoxCodeTv.setText("BRAK");
            }
        });
    }

    private void handleDelete(View v) {
        itemInstanceDAO.deleteItemInstance(itemInstance.getId(), success -> {
            if(success) {
                Toast.makeText(this, "Usunięto instancję", Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            } else {
                Toast.makeText(this, "Błąd podczas usuwania", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleChangeBox(View v) {
        Intent intent = new Intent(this, BoxSelectActivity.class);
        boxSelectLauncher.launch(intent);
    }

    private void handleMoveToBox(Box selectedBox) {
        if (selectedBox.getId() == itemInstance.getBoxId()) {
            Toast.makeText(this, "Instancja już jest w tym pudełku", Toast.LENGTH_SHORT).show();
            return;
        }

        itemInstanceDAO.moveItemInstance(itemInstance.getId(), new ItemInstanceDTO.MoveItemInstanceRequest(selectedBox.getId()), success -> {
            if (success) {
                Toast.makeText(this, "Przeniesiono do " + selectedBox.getCode(), Toast.LENGTH_SHORT).show();
                itemInstance.setBoxId(selectedBox.getId());
                itemInstanceBoxCodeTv.setText(selectedBox.getCode());
                setResult(2); // Signal update
            } else {
                Toast.makeText(this, "Błąd podczas przenoszenia", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
