package com.example.inventorymaster.Modules.Items;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymaster.Common.Page;
import com.example.inventorymaster.Modules.Inventory.InventoryDAO;
import com.example.inventorymaster.Modules.ItemInstances.ItemInstanceActivity;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.RecyclerHelper;

import java.util.List;
import java.util.function.Consumer;

public class ItemsPage extends Page {
    private final CardView itemsAddCv;
    private final EditText itemsSearchTe;
    private final TextView itemsNumberTv;
    private final RecyclerView itemsRv;
    private final ItemRecyclerAdapter itemRecyclerAdapter;
    private final ItemDAO itemDAO;
    private final InventoryDAO inventoryDAO;
    private boolean isLoading = false;
    private int requestToken = 0;

    public ItemsPage(LayoutInflater layoutInflater, FrameLayout frameView, ItemDAO itemDAO, InventoryDAO inventoryDAO) {
        super(layoutInflater, frameView, R.layout.page_items);
        this.itemDAO = itemDAO;
        this.inventoryDAO = inventoryDAO;

        itemRecyclerAdapter = new ItemRecyclerAdapter();
        itemRecyclerAdapter.setItemClickHandler(this::handleRecyclerItemClick);
        itemsRv = getPageView().findViewById(R.id.itemsRv);
        itemsRv.setLayoutManager(new LinearLayoutManager(getPageView().getContext()));
        itemsRv.setAdapter(itemRecyclerAdapter);
        itemsRv.addOnScrollListener(RecyclerHelper.pullNeedCheckHandler(1, this::pullDataPage));

        itemsAddCv = getPageView().findViewById(R.id.itemAddCv);
        itemsAddCv.setOnClickListener(this::handleAddItem);

        itemsNumberTv = getPageView().findViewById(R.id.itemsNumberTv);

        itemsSearchTe = getPageView().findViewById(R.id.itemsSearchTe);
        itemsSearchTe.addTextChangedListener(new SearchDataChangeHandler());
    }

    private void handleAddItem(View v) {
        final var context = v.getContext();
        final var intent = new Intent(context, ItemAddActivity.class);
        context.startActivity(intent);
    }

    private void handleRecyclerItemClick(Item item, View v) {
        final var context = v.getContext();
        final var intent = new Intent(context, ItemInstanceActivity.class);
        intent.putExtra(ItemInstanceActivity.ITEM_SERIALIZABLE_EXTRA_LABEL, item);
        context.startActivity(intent);
    }

    private void pullDataPage() {
        if (isLoading) return;
        isLoading = true;
        final int token = ++requestToken;

        final var page = itemRecyclerAdapter.getPageToPull();

        final var search = itemsSearchTe.getText().toString();
        if(search.isBlank()) {
            itemDAO.getItems(page, data -> {
                if (token != requestToken) return;
                itemRecyclerAdapter.appendPageData(data);
                isLoading = false;
            });
            inventoryDAO.getItems(data -> {
                if (token != requestToken) return;
                itemsNumberTv.setText("(" + data + ")");
            });
        }
        else {
            itemDAO.getItems(page, search, data -> {
                if (token != requestToken) return;
                itemRecyclerAdapter.appendPageData(data);
                isLoading = false;
            });
            inventoryDAO.getItems(search, data -> {
                if (token != requestToken) return;
                itemsNumberTv.setText("(" + data + ")");
            });
        }
    }

    private void clearData() {
        requestToken++;
        isLoading = false;
        itemRecyclerAdapter.clearData();
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
    public void notifyOfNavigationSelection() {
        clearData();
        pullDataPage();
    }

    @Override
    public void notifyOfActivityResume() {
        clearData();
        pullDataPage();
    }
}
