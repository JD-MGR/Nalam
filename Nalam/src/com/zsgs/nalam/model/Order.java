package com.zsgs.nalam.model;

import java.util.List;

public class Order {
    private List<Medicine> medicines;
    private long orderId;
    private long orderDate;
    private long customerId;
    private double orderPrice;

    public Order(){}

    public Order(List<Medicine> medicines, long orderId, long orderDate, long customerId, double orderPrice) {
        this.medicines = medicines;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.orderPrice = orderPrice;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
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
}
