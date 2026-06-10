package com.example.inventorymaster.Modules.Inventory;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.example.inventorymaster.Modules.ItemInstances.ItemInstanceDAO;
import com.example.inventorymaster.Modules.ItemInstances.ItemInstanceManageActivity;
import com.example.inventorymaster.Modules.Items.ItemDAO;
import com.journeyapps.barcodescanner.ScanOptions;

public class BarcodeController {
    private final Context context;
    private final ItemDAO itemDAO;
    private final ItemInstanceDAO itemInstanceDAO;

    public BarcodeController(Context context, ItemDAO itemDAO, ItemInstanceDAO itemInstanceDAO) {
        this.context = context;
        this.itemDAO = itemDAO;
        this.itemInstanceDAO = itemInstanceDAO;
    }

    public void launch(ActivityResultLauncher<ScanOptions> launcher) {
        final var options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.CODE_128);
        options.setPrompt("Zeskanuj kod artykułu");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        launcher.launch(options);
    }

    public void handleResult(String contents) {
        final var lastHyphen = contents.lastIndexOf('-');
        if (lastHyphen == -1) {
            Toast.makeText(context, "Nieobsługiwany format kodu", Toast.LENGTH_SHORT).show();
            return;
        }

        final var featuresCode = contents.substring(0, lastHyphen).trim();
        final var sequenceNumberStr = contents.substring(lastHyphen + 1).trim();

        int sequenceNumber;
        try {
            sequenceNumber = Integer.parseInt(sequenceNumberStr);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Nieobsługiwany format kodu", Toast.LENGTH_SHORT).show();
            return;
        }

        itemDAO.getItems(1, featuresCode, items -> {
            if (items.isEmpty()) {
                Toast.makeText(context, "Nie znaleziono artykułu o tym kodzie", Toast.LENGTH_SHORT).show();
                return;
            }

            final var item = items.get(0);
            if (!item.getFeaturesCode().equalsIgnoreCase(featuresCode)) {
                Toast.makeText(context, "Nie znaleziono artykułu o tym kodzie", Toast.LENGTH_SHORT).show();
                return;
            }

            itemInstanceDAO.getItemInstances(1, item.getId(), sequenceNumber, instances -> {
                if (instances.isEmpty()) {
                    Toast.makeText(context, "Nie znaleziono konkretnej instancji (numer: " + sequenceNumberStr + ")", Toast.LENGTH_SHORT).show();
                    return;
                }

                final var instance = instances.get(0);
                Toast.makeText(context, "Pomyślnie zidentyfikowano artykuł: " + contents, Toast.LENGTH_SHORT).show();

                final var intent = new Intent(context, ItemInstanceManageActivity.class);
                intent.putExtra("item", item);
                intent.putExtra("instance", instance);
                context.startActivity(intent);
            });
        });
    }
}
