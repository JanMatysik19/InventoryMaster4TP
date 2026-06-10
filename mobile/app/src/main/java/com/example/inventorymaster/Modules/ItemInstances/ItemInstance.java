package com.example.inventorymaster.Modules.ItemInstances;

import java.io.Serializable;

public class ItemInstance implements Serializable {
    private int id;
    private int itemId;
    private int sequenceNumber;
    private int boxId;
    private String boxCode;

    public ItemInstance(int id, int itemId, int sequenceNumber, int boxId, String boxCode) {
        this.id = id;
        this.itemId = itemId;
        this.sequenceNumber = sequenceNumber;
        this.boxId = boxId;
        this.boxCode = boxCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }
}
