package com.ganesh.nalam.controller;

import com.ganesh.nalam.model.User;
import com.ganesh.nalam.view.AppView;

public class AppController {
    private AppView appView;
    private UserController userController;
    private PharmacistController pharmacistController;
    private MedicineController medicineController;
    private CustomerController customerController;

    public AppController(AppView appView, UserController userController, PharmacistController pharmacistController,CustomerController customerController) {
        this.appView = appView;
        this.userController = userController;
        this.pharmacistController = pharmacistController;
        this.customerController=customerController;
    }

    public void init() {
        while (true) {
            int choice = appView.showMenu();
            switch (choice) {
                case 1:
                    userController.handleRegistration();
                    break;
                case 2:
                    try {
                        User userdata = userController.handleLogin();
                        if (userdata != null) {
                            if (userdata.getRole().name().equals("PHARMACIST")) {
                                pharmacistController.init(userdata.getId());
                            } else {
                                customerController.init(userdata.getId());
                            }
                        }
                    } catch (Exception e) {
                        appView.displayMessage("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    appView.displayMessage("ThankYou For Using Nalam");
                    System.exit(0);
                default:
                    appView.displayMessage("Invalid Choice");
            }
        }
    }
}
