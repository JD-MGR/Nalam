package com.ganesh.nalam.model;

public class Order {
    private long medicineId;
    private long orderId;
    private long orderDate;
    private long customerId;
    private double orderPrice;
    private long quantity;

    public Order(){}

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Order(long medicineId, long orderId, long orderDate, long quantity, double orderPrice) {
        this.medicineId = medicineId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.quantity=quantity;
        this.orderPrice = orderPrice;
    }
    public Order(long medicineId, long orderId, long orderDate, long quantity, double orderPrice,long customerId) {
        this.medicineId = medicineId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.quantity=quantity;
        this.orderPrice = orderPrice;
        this.customerId=customerId;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(long medicines) {
        this.medicineId = medicineId;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId(){return customerId;}

    public long getOrderId() {
        return this.orderId;
    }
}
