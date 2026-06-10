package com.example.inventorymaster.Modules.Boxes;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.inventorymaster.Config.InventoryMaster;
import com.example.inventorymaster.R;

public class BoxManageActivity extends AppCompatActivity {
    // Components
    private CardView boxInstanceBackCv;
    private TextView boxInstanceInstanceTv;
    private CardView boxInstanceDeleteCv;

    // Used
    private BoxDAO boxDAO;
    private Box box;

    // Constants
    public static String BOX_SERIALIZABLE_EXTRA_LABEL = "box";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = (Box) getIntent().getSerializableExtra(BOX_SERIALIZABLE_EXTRA_LABEL);
        setContentView(R.layout.activity_box_manage);

        final var app = (InventoryMaster) getApplication();
        boxDAO = app.getBoxDAO();

        boxInstanceInstanceTv = findViewById(R.id.boxInstanceInstanceTv);
        boxInstanceBackCv = findViewById(R.id.boxInstanceBackCv);
        boxInstanceDeleteCv = findViewById(R.id.boxInstanceDeleteCv);

        boxInstanceInstanceTv.setText(box.getCode());
        boxInstanceBackCv.setOnClickListener(this::handleBack);
        boxInstanceDeleteCv.setOnClickListener(this::handleOnDelete);
    }

    private void goBack() {
        finish();
    }

    private void handleBack(View v) {
        goBack();
    }

    private void handleOnDelete(View v) {
        boxDAO.deleteBox(box.getId(), result -> {
            final var message = result
                    ? "Pomyślnie usunięto pudełko"
                    : "Nie udało się usunąć pudełka";
            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            goBack();
        });
    }
}
