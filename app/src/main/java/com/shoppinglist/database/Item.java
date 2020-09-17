package com.shoppinglist.database;

public class Item {
    private String name;
    private String quantity;
    private String otherInformation;

    public Item(String name) {
        this.name = name;
        this.quantity = "1";
        this.otherInformation = "";
    }

    public String getName() {
        return this.name;
    }
    public String getQuantity() {
        return this.quantity;
    }
    public String getOtherInformation() {
        return this.otherInformation;
    }

    public void setName(String newName) {
        this.name = newName;
    }
    public void setQuantity(String newQuantity) {
        this.quantity = newQuantity;
    }
    public void setOtherInformation(String newOtherInformation) {
        this.otherInformation = newOtherInformation;
    }

    @Override
    public String toString() {
        String string = name;

        if (!quantity.isEmpty()) {
            string = string + ": " + quantity;
        }

        if (!otherInformation.isEmpty()) {
            string = string + " (" + otherInformation + ")";
        }

        return string;
    }
}
