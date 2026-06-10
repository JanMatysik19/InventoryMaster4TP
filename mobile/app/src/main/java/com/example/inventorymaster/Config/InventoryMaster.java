package com.example.inventorymaster.Config;

import android.app.Application;

import com.example.inventorymaster.Modules.Boxes.BoxDAO;
import com.example.inventorymaster.Models.HttpClientModel;
import com.example.inventorymaster.Modules.Inventory.InventoryDAO;
import com.example.inventorymaster.Modules.ItemInstances.ItemInstanceDAO;
import com.example.inventorymaster.Modules.Items.ItemDAO;

public class InventoryMaster extends Application {
    private HttpClientModel httpClientModel;
    private BoxDAO boxDAO;
    private ItemDAO itemDAO;
    private ItemInstanceDAO itemInstanceDAO;
    private InventoryDAO  inventoryDAO;

    @Override
    public void onCreate() {
        super.onCreate();
        httpClientModel = new HttpClientModel();
        boxDAO = new BoxDAO(httpClientModel);
        itemDAO = new ItemDAO(httpClientModel);
        itemInstanceDAO = new ItemInstanceDAO(httpClientModel);
        inventoryDAO = new InventoryDAO(httpClientModel);
    }

    public ItemInstanceDAO getItemInstanceDAO() {
        return itemInstanceDAO;
    }

    public ItemDAO getItemModel() {
        return itemDAO;
    }

    public BoxDAO getBoxDAO() {
        return boxDAO;
    }

    public InventoryDAO getInventoryDAO() {
        return inventoryDAO;
    }
}
