package com.example.inventorymaster.Modules.Boxes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymaster.Config.InventoryMaster;
import com.example.inventorymaster.R;
import com.example.inventorymaster.Utils.RecyclerHelper;

public class BoxSelectActivity extends AppCompatActivity {
    private RecyclerView boxSelectRv;
    private EditText boxSelectSearchTe;
    private CardView boxSelectBackCv;
    private BoxRecyclerAdapter boxRecyclerAdapter;
    private BoxDAO boxDAO;
    private boolean isLoading = false;
    private int requestToken = 0;

    public static final String BOX_RESULT_LABEL = "box";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_select);

        final var app = (InventoryMaster) getApplication();
        boxDAO = app.getBoxDAO();

        boxSelectRv = findViewById(R.id.boxSelectRv);
        boxSelectSearchTe = findViewById(R.id.boxSelectSearchTe);
        boxSelectBackCv = findViewById(R.id.boxSelectBackCv);

        boxRecyclerAdapter = new BoxRecyclerAdapter();
        boxRecyclerAdapter.setItemClickHandler(this::handleBoxClick);
        boxSelectRv.setLayoutManager(new LinearLayoutManager(this));
        boxSelectRv.setAdapter(boxRecyclerAdapter);
        boxSelectRv.addOnScrollListener(RecyclerHelper.pullNeedCheckHandler(1, this::pullDataPage));

        boxSelectSearchTe.addTextChangedListener(new SearchDataChangeHandler());
        boxSelectBackCv.setOnClickListener(v -> finish());

        pullDataPage();
    }

    private void handleBoxClick(Box box, View v) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(BOX_RESULT_LABEL, box);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void pullDataPage() {
        if (isLoading) return;
        isLoading = true;
        final int token = ++requestToken;

        final var sequenceNumber = boxSelectSearchTe.getText().toString().trim();
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
        }
        else {
            boxDAO.getBoxes(page, data -> {
                if (token != requestToken) return;
                boxRecyclerAdapter.appendPageData(data);
                isLoading = false;
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
}
