package br.edu.unicarioca.spendingmanager.models;

import java.io.Serializable;

public class Item implements Serializable {
    private Long id;
    private String name;
    private int quantity;
    private Double unitaryVal;
    private Purchase purchase;

    public Item() {
    }

    public Item(String name, int quantity, Double unitaryVal, Purchase purchase) {
        this.name = name;
        this.quantity = quantity;
        this.unitaryVal = unitaryVal;
        this.purchase = purchase;
    }

    public Item(Long id, String name, int quantity, Double unitaryVal, Purchase purchase) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitaryVal = unitaryVal;
        this.purchase = purchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getUnitaryVal() {
        return unitaryVal;
    }

    public void setUnitaryVal(Double unitaryVal) {
        this.unitaryVal = unitaryVal;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return this.name + ": " + this.unitaryVal.toString() + " x" + String.valueOf(this.quantity);
    }
}
