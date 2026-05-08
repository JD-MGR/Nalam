package com.ganesh.nalam.service;

import com.ganesh.nalam.exception.OrderException;
import com.ganesh.nalam.model.Order;
import com.ganesh.nalam.repository.OrderRepository;

import java.util.List;

public class OrderValidationService {
    private OrderRepository orderRepository;
    public OrderValidationService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }
    public boolean validateOrder(long id, String medicineName, int medicineQuantity, long currDate) throws OrderException {
       if(medicineName==null||medicineName.isEmpty()){
           throw new OrderException("Medicine Name can't be Empty.");
       }
       if(medicineQuantity<=0){
           throw new OrderException("Medicine Quantity must be greater than zero");
       }
       return orderRepository.addOrder(id,medicineName,medicineQuantity,currDate);
    }
    public List<Order> getOrder(long id) {
        return  orderRepository.viewOrder(id);
    }

    public List<Order> getOrders() {
        return orderRepository.viewOrders();
    }
}
