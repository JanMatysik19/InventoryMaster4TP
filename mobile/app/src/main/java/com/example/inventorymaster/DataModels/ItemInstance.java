package com.example.inventorymaster.DataModels;

public class ItemInstance {
    private int id;
    private int itemId;
    private int sequenceNumber;
    private int boxId;

    public ItemInstance(int id, int itemId, int sequenceNumber, int boxId) {
        this.id = id;
        this.itemId = itemId;
        this.sequenceNumber = sequenceNumber;
        this.boxId = boxId;
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
}
