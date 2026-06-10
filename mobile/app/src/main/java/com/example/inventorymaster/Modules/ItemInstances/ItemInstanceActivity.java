package com.example.inventorymaster.Modules.ItemInstances;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymaster.Config.InventoryMaster;
import com.example.inventorymaster.Modules.Inventory.InventoryDAO;
import com.example.inventorymaster.Modules.Items.Item;
import com.example.inventorymaster.Modules.Items.ItemDAO;
import com.example.inventorymaster.Modules.Items.ItemManageActivity;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.RecyclerHelper;

public class ItemInstanceActivity extends AppCompatActivity {
    // Components
    private CardView itemInstancesAddCv;
    private CardView itemInstanceManageCv;
    private CardView itemInstancesBackCv;
    private TextView itemsNumberTv;
    private TextView itemInstancesInstanceTv;
    private EditText itemInstancesSearchTe;
    private RecyclerView itemInstancesRv;
    private ItemInstanceRecyclerAdapter itemInstanceRecyclerAdapter;
    private Item item;
    private ItemInstanceDAO itemInstanceDAO;
    private ItemDAO itemDAO;
    private InventoryDAO inventoryDAO;
    private ActivityResultLauncher<Intent> manageActivityLauncher;

    // Used
    private boolean isLoading = false;
    private int requestToken = 0;

    // Constants
    public static String ITEM_SERIALIZABLE_EXTRA_LABEL = "item";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (Item) getIntent().getSerializableExtra(ITEM_SERIALIZABLE_EXTRA_LABEL);
        setContentView(R.layout.activity_item_instances);

        manageActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == 1) goBack();
                    else if(result.getResultCode() == 2) pullItemData();
        });

        final var app = (InventoryMaster) getApplication();
        itemInstanceDAO = app.getItemInstanceDAO();
        itemDAO = app.getItemModel();
        inventoryDAO = app.getInventoryDAO();

        itemInstancesBackCv = findViewById(R.id.itemInstancesBackCv);
        itemInstancesBackCv.setOnClickListener(this::handleGoBack);

        itemsNumberTv = findViewById(R.id.itemsNumberTv);

        itemInstancesInstanceTv = findViewById(R.id.itemInstancesInstanceTv);
        itemInstancesInstanceTv.setText(item.getFeaturesCode());

        itemInstanceRecyclerAdapter = new ItemInstanceRecyclerAdapter(item);
        itemInstanceRecyclerAdapter.setItemClickHandler(this::handleRecyclerItemClick);
        itemInstancesRv = findViewById(R.id.itemInstancesRv);
        itemInstancesRv.setLayoutManager(new LinearLayoutManager(itemInstancesRv.getContext()));
        itemInstancesRv.setAdapter(itemInstanceRecyclerAdapter);
        itemInstancesRv.addOnScrollListener(RecyclerHelper.pullNeedCheckHandler(1, this::pullDataPage));

        itemInstancesSearchTe = findViewById(R.id.itemInstancesSearchTe);
        itemInstancesSearchTe.addTextChangedListener(new SearchDataChangeHandler());

        itemInstanceManageCv = findViewById(R.id.itemInstanceManageCv);
        itemInstanceManageCv.setOnClickListener(this::handleManageItem);

        itemInstancesAddCv = findViewById(R.id.itemInstancesAddCv);
        itemInstancesAddCv.setOnClickListener(this::handleAddItemInstance);

        pullDataPage();
    }


    private void goBack() {
        finish();
    }

    private void handleGoBack(View v) {
        goBack();
    }

    private void handleRecyclerItemClick(Item item, ItemInstance itemInstance, View v) {
        final var context = v.getContext();
        final var intent = new Intent(context, ItemInstanceManageActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("instance", itemInstance);
        manageActivityLauncher.launch(intent);
    }

    private void handleAddItemInstance(View v) {
        itemInstanceDAO.addItemInstance(item.getId(), result -> {
            final var message = result
                    ? "Dodano nową instancję artykułu"
                    : "Nie udało się dodać instancji";
            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

            clearData();
            pullDataPage();
        });
    }

    private void handleManageItem(View v) {
        final var context = v.getContext();
        final var intent = new Intent(context, ItemManageActivity.class);
        intent.putExtra("item", item);
        manageActivityLauncher.launch(intent);
    }

    private void pullDataPage() {
        if (isLoading) return;
        isLoading = true;
        final int token = ++requestToken;

        final var itemId = item.getId();
        final var page = itemInstanceRecyclerAdapter.getPageToPull();
        final var sequenceNumberStr = itemInstancesSearchTe.getText().toString().trim();

        if(sequenceNumberStr.isBlank()) {
            itemInstanceDAO.getItemInstances(page, itemId, data -> {
                if (token != requestToken) return;
                itemInstanceRecyclerAdapter.appendPageData(data);
                isLoading = false;
            });
            inventoryDAO.getItemInstances(itemId, data -> {
                if (token != requestToken) return;
                itemsNumberTv.setText("(" + data + ")");
            });
        }
        else {
            try {
                final var parsedSequenceNumber = Integer.parseInt(sequenceNumberStr);
                itemInstanceDAO.getItemInstances(page, itemId, parsedSequenceNumber, data -> {
                    if (token != requestToken) return;
                    itemInstanceRecyclerAdapter.appendPageData(data);
                    isLoading = false;
                });
                inventoryDAO.getItemInstances(itemId, parsedSequenceNumber, data -> {
                    if (token != requestToken) return;
                    itemsNumberTv.setText("(" + data + ")");
                });
            } catch (Exception e) {
                isLoading = false;
            }
        }
    }

    private void clearData() {
        requestToken++;
        isLoading = false;
        itemInstanceRecyclerAdapter.clearData();
    }

    private void pullItemData() {
        final var itemId = item.getId();
        itemDAO.getItem(itemId, newItem -> {
            if(newItem == null) return;
            item = newItem;
            itemInstancesInstanceTv.setText(item.getFeaturesCode());
        });
    }

    private class SearchDataChangeHandler implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            clearData();
            pullDataPage();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearData();
        pullDataPage();
    }
}
