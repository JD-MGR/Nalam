package com.ganesh.nalam.controller;

import com.ganesh.nalam.exception.OrderException;
import com.ganesh.nalam.model.Order;
import com.ganesh.nalam.service.OrderValidationService;
import com.ganesh.nalam.view.CustomerView;

import java.util.List;

public class OrderController {
    private OrderValidationService orderValidationService;
    private CustomerView orderView;
    public OrderController(OrderValidationService orderValidationService,CustomerView orderView){
        this.orderValidationService=orderValidationService;
        this.orderView=orderView;
    }

    public void handleCustomerOrder(long id,String medicineName,int quantity,long currDate)
    {
        try{
            if(orderValidationService.validateOrder(id,medicineName,quantity,currDate)){
                orderView.display("Medicine Added Successfully");
            }

        }catch(OrderException e){
            orderView.display(e.getMessage());
        }
            }

    public void handleCustomerOrderView(long id) {
        List<Order> orderList= orderValidationService.getOrder(id);
        orderView.orderDisplay(orderList);
    }

    public void handleOrdersView() {
        List<Order> orderList=orderValidationService.getOrders();
        orderView.orderDisplay(orderList);
    }
}
