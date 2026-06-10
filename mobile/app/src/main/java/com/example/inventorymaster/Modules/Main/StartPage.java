package com.example.inventorymaster.Modules.Main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventorymaster.Common.Page;
import com.example.inventorymaster.Modules.Boxes.BoxDAO;
import com.example.inventorymaster.Modules.Inventory.InventoryDAO;
import com.example.inventorymaster.Modules.Items.ItemAddActivity;
import com.example.inventorymaster.R;

public class StartPage extends Page {
    private final BoxDAO boxDAO;
    private final InventoryDAO inventoryDAO;
    
    private TextView mainSummaryInstancesTv;
    private TextView mainSummaryItemsTv;
    private TextView mainSummaryBoxesTv;
    private TextView mainSummaryValueTv;

    private boolean isLoading = false;
    private int requestToken = 0;

    public StartPage(LayoutInflater layoutInflater, FrameLayout frameView, BoxDAO boxDAO, InventoryDAO inventoryDAO) {
        super(layoutInflater, frameView, R.layout.page_start);
        this.boxDAO = boxDAO;
        this.inventoryDAO = inventoryDAO;

        setupButtons();
        setupSummary();
        pullSummary();
    }

    private void setupButtons() {
        final var view = getPageView();

        view.findViewById(R.id.mainAddItemsCv).setOnClickListener(this::handleAddItem);
        view.findViewById(R.id.mainAddBoxCv).setOnClickListener(this::handleAddBox);
    }

    private void setupSummary() {
        final var view = getPageView();
        mainSummaryInstancesTv = view.findViewById(R.id.mainSummaryInstancesTv);
        mainSummaryItemsTv = view.findViewById(R.id.mainSummaryItemsTv);
        mainSummaryBoxesTv = view.findViewById(R.id.mainSummaryBoxesTv);
        mainSummaryValueTv = view.findViewById(R.id.mainSummaryValueTv);
    }

    private void pullSummary() {
        if (isLoading) return;
        isLoading = true;
        final int token = ++requestToken;

        inventoryDAO.getSummary((boxes, items, instances, value) -> {
            if (token != requestToken) return;
            mainSummaryInstancesTv.setText(String.valueOf(instances));
            mainSummaryItemsTv.setText(String.valueOf(items));
            mainSummaryBoxesTv.setText(String.valueOf(boxes));
            mainSummaryValueTv.setText(value + " PLN");
            isLoading = false;
        });
    }

    private void handleAddItem(View v) {
        final var context = v.getContext();
        final var intent = new Intent(context, ItemAddActivity.class);
        context.startActivity(intent);
    }

    private void handleAddBox(View v) {
        boxDAO.addBox(result -> {
            final var message = result
                    ? "Dodano nowe pudełko"
                    : "Nie udało się dodać nowego pudełka";
            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            pullSummary();
        });
    }

    @Override
    public void notifyOfNavigationSelection() {
        pullSummary();
    }

    @Override
    public void notifyOfActivityResume() {
        pullSummary();
    }
}
