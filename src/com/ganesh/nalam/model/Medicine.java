package com.ganesh.nalam.model;

public class Medicine {
    private long medicineId;
    private String name;
    private long expDate;
    private double price;
    private String description;
    private long quantity;

    public Medicine(){}

    public Medicine( String name, long expDate, double price, String description, long quantity) {
        this.name = name;
        this.expDate = expDate;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }


    public long getMedicineId() {
        return medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpDate() {
        return expDate;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
