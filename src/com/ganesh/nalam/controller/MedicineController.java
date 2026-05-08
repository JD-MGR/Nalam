package com.ganesh.nalam.controller;

import com.ganesh.nalam.exception.MedicineException;
import com.ganesh.nalam.model.Medicine;
import com.ganesh.nalam.service.MedicineValidationService;
import com.ganesh.nalam.service.OrderValidationService;
import com.ganesh.nalam.view.MedicineView;

import java.util.List;

public class MedicineController {
    private MedicineView medicineView;
    private MedicineValidationService medicineValidationService;
    private OrderValidationService orderValidationService;

    public MedicineController(MedicineView medicineView, MedicineValidationService medicineValidationService,OrderValidationService orderValidationService) {
        this.medicineView = medicineView;
        this.medicineValidationService = medicineValidationService;
        this.orderValidationService=orderValidationService;
    }

    public void handleAddMedicine(long pharmacistId) {
        boolean isMedicineAdded = false;
        while (!isMedicineAdded) {
            Medicine medicine = medicineView.addMedicineForm();
            try {
                if (medicineValidationService.validateMedicine(medicine)) {
                    boolean isAdded = medicineValidationService.addMedicine(medicine, pharmacistId);
                    isMedicineAdded = true;
                } else {
                    medicineView.display("Medicine Addition failed ! ");
                }
            } catch (MedicineException e) {
                medicineView.display("Error: " + e.getMessage());
                if (!medicineView.tryAgain()) {
                    break;
                }
            }
        }
    }

    public void handleRemoveMedicine() {
        boolean isRemoved = false;
        while (!isRemoved) {
            try {
                String medicineName = medicineView.deleteMedicineForm();
                if (medicineValidationService.validateMedicine(medicineName)) {
                    boolean isSuccessful = medicineValidationService.medicineRemove(medicineName);
                    if (isSuccessful) {
                        medicineView.display("Medicine Removed Successfully");
                        isRemoved = true;
                    } else {
                        medicineView.display("Medicine Removal failed (Medicine not found)");
                        if (!medicineView.tryAgain()) {
                            break;
                        }
                    }
                }
            } catch (MedicineException e) {
                medicineView.display("Error: " + e.getMessage());
                if (!medicineView.tryAgain()) {
                    break;
                }
            }
        }
    }

    public void handleViewMedicine(long pharmacistId) {
        List<Medicine> medicineList=medicineValidationService.getAllMedicines(pharmacistId);
        medicineView.showMedicines(medicineList);
    }

    public void handleUpdateMedicine(long pharmacistId) {
        try {
            String medicineName=medicineView.getMedicineName();
            int updateQuantity=medicineView.getMedicineQuantity();
            if (medicineValidationService.validateMedicine(medicineName,updateQuantity)) {
                boolean isSuccessful = medicineValidationService.medicineUpdateQuantity(medicineName,updateQuantity,pharmacistId);
                if(isSuccessful){
                    medicineView.display("Medicine Quantity Increased");
                }
            }
            }
        catch (MedicineException e){
            medicineView.display("Error: " + e.getMessage());
        }
        }

    public void handleCustomerMedicineSearch(){
        String medicineName = medicineView.getMedicineName();
        List<Medicine> medicineList = medicineValidationService.searchMedicines(medicineName);
        medicineView.showMedicines(medicineList);
    }

    public void handleCustomerOrder() {
        String medicineName=medicineView.getMedicineName();
        int medicineQuantity= medicineView.getMedicineQuantity();
    }
}

