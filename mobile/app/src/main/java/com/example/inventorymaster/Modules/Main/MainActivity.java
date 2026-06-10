package com.example.inventorymaster.Modules.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.inventorymaster.Config.InventoryMaster;
import com.example.inventorymaster.Modules.Inventory.BarcodeController;
import com.example.inventorymaster.Modules.Main.NavigationController.NavigationPage;
import com.example.inventorymaster.Modules.Items.ItemsPage;
import com.example.inventorymaster.Common.Page;
import com.example.inventorymaster.Modules.Boxes.StructurePage;
import com.example.inventorymaster.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private NavigationController navigationController;
    private BarcodeController barcodeController;
    private Map<NavigationPage, Page> pages;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) return;
                barcodeController.handleResult(result.getContents());
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final var app = (InventoryMaster) getApplication();
        final var boxDAO = app.getBoxDAO();
        final var itemDAO = app.getItemModel();
        final var itemInstanceDAO = app.getItemInstanceDAO();
        final var inventoryDAO = app.getInventoryDAO();

        final var inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final var menuView = (ConstraintLayout) findViewById(R.id.menuCl);
        final var frameView = (FrameLayout) findViewById(R.id.frameFl);

        pages = Map.of(
                NavigationPage.START, new StartPage(inflater, frameView, boxDAO, inventoryDAO),
                NavigationPage.STRUCTURE, new StructurePage(inflater, frameView, boxDAO, inventoryDAO),
                NavigationPage.ITEMS, new ItemsPage(inflater, frameView, itemDAO, inventoryDAO)
        );

        navigationController = new NavigationController(menuView, frameView, pages);

        barcodeController = new BarcodeController(this, itemDAO, itemInstanceDAO);
        navigationController.setOnScannerClickListener(v -> barcodeController.launch(barcodeLauncher));

        navigationController.navigateTo(NavigationController.NavigationPage.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(final var p : pages.values()) p.notifyOfActivityResume();
    }
}
