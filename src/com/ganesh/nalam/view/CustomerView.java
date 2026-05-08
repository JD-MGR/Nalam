package com.ganesh.nalam.view;

import com.ganesh.nalam.model.Order;
import com.ganesh.nalam.util.ScannerHelper;

import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private Scanner scanner;
    public CustomerView(){
        this.scanner= ScannerHelper.getScanner();
    }


    public int showDashBoard() {
        System.out.println("-----------Welcome To Customer Dashboard");
        System.out.println("""
                1.Search Medicine
                2.Add Medicine to Order
                3.View Order
                0.Log Out""");
        return scanner.nextInt();
    }
    public String getMedicineName(){
        System.out.println("Enter Medicine Name : ");
        return scanner.next();
    }
    public int getQuantity(){
        System.out.println("Enter the Quantity of Medicines : ");
        return scanner.nextInt();
    }
    public long getDate(){
        return System.currentTimeMillis();
    }
    public void display(String message){
        System.out.println(message);
    }
    public void orderDisplay(List<Order> orders) {
        for (Order order : orders) {
            System.out.println("----------Your Order---------");
            System.out.println("Order Id : " + order.getOrderId());
            String result = String.format("%tF %<tT", order.getOrderDate());
            System.out.println("Order date : " + result);
            System.out.println("Medicine Id : " + order.getMedicineId());
            System.out.println("User Id : "+order.getCustomerId());
            System.out.println("Quantity : " + order.getQuantity());
            System.out.println("Total Amount : " + order.getOrderPrice());
            System.out.println("-----------------------------");
        }
    }
}
