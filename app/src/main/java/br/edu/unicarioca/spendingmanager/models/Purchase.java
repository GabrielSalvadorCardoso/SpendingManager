package br.edu.unicarioca.spendingmanager.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Purchase implements Serializable {
    private Long id;
    private Date purchaseDate;
    private String location;
    private List<Item> items = new ArrayList<Item>();

    public Purchase() {
    }

    public Purchase(Date purchaseDate, String location, List<Item> items) {
        this.purchaseDate = purchaseDate;
        this.location = location;
        this.items = items;
    }

    public Purchase(Date purchaseDate, String location) {
        this.purchaseDate = purchaseDate;
        this.location = location;
    }

    public Purchase(Long id, Date purchaseDate, String location, List<Item> items) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.location = location;
    }

    public Purchase(Long id, Date purchaseDate, String location) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public String getPurchaseDateString() {
        Date date = this.getPurchaseDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        return format.format( date );
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return this.location + " - " + this.getPurchaseDateString();
    }
}
