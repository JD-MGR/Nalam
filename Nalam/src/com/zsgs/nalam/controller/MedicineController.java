package com.zsgs.nalam.controller;

import com.zsgs.nalam.exception.MedicineException;
import com.zsgs.nalam.exception.UserException;
import com.zsgs.nalam.model.Medicine;
import com.zsgs.nalam.service.MedicineValidationService;
import com.zsgs.nalam.view.MedicineView;

import java.util.List;
import java.util.logging.LogRecord;

public class MedicineController {
    private MedicineView medicineView;
    private MedicineValidationService medicineValidationService;

    public MedicineController(MedicineView medicineView, MedicineValidationService medicineValidationService) {
        this.medicineView = medicineView;
        this.medicineValidationService = medicineValidationService;
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
            String medicineName=medicineView.getMedicineNameToUpdate();
            int updateQuantity=medicineView.getMedicineQuantityToUpdate();
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
}

