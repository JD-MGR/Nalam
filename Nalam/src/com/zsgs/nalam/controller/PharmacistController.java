package com.zsgs.nalam.controller;

import com.zsgs.nalam.exception.MedicineException;
import com.zsgs.nalam.view.PharmacistView;

public class PharmacistController {
    private PharmacistView pharmacistView;
    private MedicineController medicineController;
    public PharmacistController(PharmacistView pharmacistView,MedicineController medicineController){
        this.pharmacistView=pharmacistView;
        this.medicineController=medicineController;
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
                case 0 -> isLoggedIn = false;
                default -> pharmacistView.display("Invalid choice.");
            }
        }

    }
}
