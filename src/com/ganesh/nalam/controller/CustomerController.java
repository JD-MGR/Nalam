package com.ganesh.nalam.controller;

import com.ganesh.nalam.view.CustomerView;
import com.ganesh.nalam.view.MedicineView;

public class CustomerController {
    private CustomerView customerView;
    private MedicineController medicineController;
    private MedicineView medicineView;
    private OrderController orderController;

    public CustomerController(CustomerView customerView,MedicineController medicineController,MedicineView medicineView,OrderController orderController){
        this.customerView=customerView;
        this.medicineController=medicineController;
        this.medicineView=medicineView;
        this.orderController=orderController;
    }
    public void init(long id) {
        boolean isLoggedIn=true;
        while (isLoggedIn){
            int choice=customerView.showDashBoard();
            switch (choice){
                case 1->{
                    medicineController.handleCustomerMedicineSearch();
                }
                case 2->{
                    String medicineName= customerView.getMedicineName();
                    int quantity= customerView.getQuantity();
                    long currentDate= customerView.getDate();
                    orderController.handleCustomerOrder(id,medicineName,quantity,currentDate);
                }
                case 3->{
                    orderController.handleCustomerOrderView(id);
                }
                case 0 -> isLoggedIn = false;
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}
