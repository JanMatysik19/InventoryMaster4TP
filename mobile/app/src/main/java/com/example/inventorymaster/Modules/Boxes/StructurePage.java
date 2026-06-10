package com.example.inventorymaster.Modules.Boxes;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymaster.Common.Page;
import com.example.inventorymaster.Modules.Inventory.InventoryDAO;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.RecyclerHelper;

public class StructurePage extends Page {
    private final TextView structureNumberTv;
    private final CardView structureAddCv;
    private final RecyclerView structureRv;
    private final EditText structureSearchTe;

    private final BoxRecyclerAdapter boxRecyclerAdapter;
    private final BoxDAO boxDAO;
    private final InventoryDAO inventoryDAO;
    private boolean isLoading = false;
    private int requestToken = 0;

    public StructurePage(LayoutInflater layoutInflater, FrameLayout frameView, BoxDAO boxDAO, InventoryDAO inventoryDAO) {
        super(layoutInflater, frameView, R.layout.page_structure);
        this.boxDAO = boxDAO;
        this.inventoryDAO = inventoryDAO;

        boxRecyclerAdapter = new BoxRecyclerAdapter();
        boxRecyclerAdapter.setItemClickHandler(this::handleRecyclerItemClick);
        structureRv = getPageView().findViewById(R.id.structureRv);
        structureRv.setLayoutManager(new LinearLayoutManager(getPageView().getContext()));
        structureRv.setAdapter(boxRecyclerAdapter);
        structureRv.addOnScrollListener(RecyclerHelper.pullNeedCheckHandler(1, this::pullDataPage));

        structureAddCv = getPageView().findViewById(R.id.structureAddCv);
        structureAddCv.setOnClickListener(this::handleAddBox);

        structureNumberTv = getPageView().findViewById(R.id.structureNumberTv);

        structureSearchTe = getPageView().findViewById(R.id.structureSearchTe);
        structureSearchTe.addTextChangedListener(new SearchDataChangeHandler());
    }

    private void handleAddBox(View v) {
        boxDAO.addBox(result -> {
            final var message = result
                    ? "Dodano nowe pudełko"
                    : "Nie udało się dodać nowego pudełka";
            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

            clearData();
            pullDataPage();
        });
    }

    private void handleRecyclerItemClick(Box box, View v) {
        final var context = v.getContext();
        final var intent = new Intent(context, BoxManageActivity.class);
        intent.putExtra(BoxManageActivity.BOX_SERIALIZABLE_EXTRA_LABEL, box);
        context.startActivity(intent);
    }

    private void pullDataPage() {
        if (isLoading) return;
        isLoading = true;
        final int token = ++requestToken;

        final var sequenceNumber = structureSearchTe.getText().toString().trim();
        int parsedSequenceNumber = -1;
        try {
            parsedSequenceNumber = Integer.parseInt(sequenceNumber);
        } catch (NumberFormatException ignored) { }

        final var page = boxRecyclerAdapter.getPageToPull();
        if(!sequenceNumber.isBlank() && parsedSequenceNumber != -1) {
            boxDAO.getBoxes(page, parsedSequenceNumber, data -> {
                if (token != requestToken) return;
                boxRecyclerAdapter.appendPageData(data);
                isLoading = false;
            });
            inventoryDAO.getBoxes(parsedSequenceNumber, data -> {
                if (token != requestToken) return;
                structureNumberTv.setText("(" + data + ")");
            });
        }
        else {
            boxDAO.getBoxes(page, data -> {
                if (token != requestToken) return;
                boxRecyclerAdapter.appendPageData(data);
                isLoading = false;
            });
            inventoryDAO.getBoxes(data -> {
                if (token != requestToken) return;
                structureNumberTv.setText("(" + data + ")");
            });
        }
    }

    private void clearData() {
        requestToken++;
        isLoading = false;
        boxRecyclerAdapter.clearData();
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
