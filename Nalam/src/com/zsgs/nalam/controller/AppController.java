package com.zsgs.nalam.controller;

import com.zsgs.nalam.exception.MedicineException;
import com.zsgs.nalam.exception.UserException;
import com.zsgs.nalam.model.User;
import com.zsgs.nalam.view.AppView;

public class AppController {
    private AppView appView;
    private UserController userController;
    private PharmacistController pharmacistController;
    private MedicineController medicineController;

    public AppController(AppView appView, UserController userController, PharmacistController pharmacistController) {
        this.appView = appView;
        this.userController = userController;
        this.pharmacistController = pharmacistController;
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
                                userController.handleUserDashboard(userdata.getId());
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
