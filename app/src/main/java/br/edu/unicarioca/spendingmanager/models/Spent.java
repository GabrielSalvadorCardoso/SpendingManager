package br.edu.unicarioca.spendingmanager.models;

import java.io.Serializable;
import java.util.Date;

public class Spent implements Serializable {
    private Long id;
    private Date spentDate;
    private String location; //todo: Change to geografic location
    private String item; //todo: Change to a Item object
    private int quantity;
    private Double unitaryVal;

    public Spent() {
    }

    public Spent(Date spentDate, String location, String item, int quantity, Double unitaryVal) {
        this.spentDate = spentDate;
        this.location = location;
        this.item = item;
        this.quantity = quantity;
        this.unitaryVal = unitaryVal;
    }

    public Spent(Long id, Date spentDate, String location, String item, int quantity, Double unitaryVal) {
        this.id = id;
        this.spentDate = spentDate;
        this.location = location;
        this.item = item;
        this.quantity = quantity;
        this.unitaryVal = unitaryVal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(Date spentDate) {
        this.spentDate = spentDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    @Override
    public String toString() {
        return this.item + ": " + this.unitaryVal.toString() + " x" + String.valueOf(this.quantity);
    }
}
