package com.ganesh.nalam.controller;

import com.ganesh.nalam.view.PharmacistView;

public class PharmacistController {
    private PharmacistView pharmacistView;
    private MedicineController medicineController;
    private UserController userController;
    private OrderController orderController;
    public PharmacistController(PharmacistView pharmacistView,MedicineController medicineController,UserController userController,OrderController orderController){
        this.pharmacistView=pharmacistView;
        this.medicineController=medicineController;
        this.userController=userController;
        this.orderController=orderController;
    }
    public void init(long id) {
        boolean isLoggedIn=true;
        while(isLoggedIn){
            int choice=pharmacistView.showDashBoard();
            switch(choice){
                case 1->{
                    medicineController.handleAddMedicine(id);
                }
                case 2->{
                    medicineController.handleRemoveMedicine();
                }
                case 3->{
                    medicineController.handleUpdateMedicine(id);
                }
                case 4->{
                    medicineController.handleViewMedicine(id);
                }
                case 5 ->{
                    userController.handleUsersView();
                }
                case 6->{
                    orderController.handleOrdersView();
                }
                case 0 -> isLoggedIn = false;
                default -> pharmacistView.display("Invalid choice.");
            }
        }

    }
}
