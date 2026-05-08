package com.ganesh.nalam.service;

import com.ganesh.nalam.exception.MedicineException;
import com.ganesh.nalam.model.Medicine;
import com.ganesh.nalam.repository.MedicineRepository;

import java.util.List;

public class MedicineValidationService {
    private MedicineRepository medicineRepository;
    public MedicineValidationService(MedicineRepository medicineRepository){
        this.medicineRepository=medicineRepository;
    }


    public boolean validateMedicine(Medicine medicine) throws MedicineException {
        if(medicine.getName()==null||medicine.getName().isEmpty()){
            throw new MedicineException("Medicine Name Cannot Be Empty");
        }
        if(medicine.getPrice()<=0){
            throw new MedicineException("Price cannot be empty");
        }
        if(medicine.getQuantity()<=0){
         throw new MedicineException("Medicine Quantity cannot be empty");
        }
        long expiryDate=medicine.getExpDate();

        if(expiryDate<=0){
            throw new MedicineException("Expiry Date cannot be Empty");
        }
        long currentMillis=System.currentTimeMillis();
        if(expiryDate<=currentMillis){
            throw new MedicineException("Medicine Expired");
        }
        if(medicineAlreadyExist(medicine.getName(),medicine.getQuantity())){
            throw new MedicineException("Medicine Added Suucessfully");
        }
        return true;
    }

    public boolean validateMedicine(String medicineName) throws MedicineException {
        if(medicineName==null||medicineName.isEmpty()){
            throw new MedicineException("Medicine name Cannot be Empty");
        }
        return true;
    }
    public boolean validateMedicine(String medicineName,int updateQuantity)throws MedicineException{
        if(medicineName==null|| medicineName.isEmpty()){
            throw new MedicineException("Medicine Name cannot be empty");
        }
        if(updateQuantity<=0){
            throw new MedicineException("Cannot update zero or negative quantity");
        }
        return true;
    }
    private boolean medicineAlreadyExist(String name, long quantity) {
        return medicineRepository.checkMedicineAlreadyExist(name,quantity);
    }

    public boolean medicineRemove(String name){
        return medicineRepository.medicineRemove(name);
    }
    public boolean addMedicine(Medicine medicine,long pharmacistId) {
        return medicineRepository.saveMedicine(medicine,pharmacistId);
    }

    public List<Medicine> getAllMedicines(long pharmacistId) {
        return medicineRepository.allMedicines(pharmacistId);
    }

    public boolean medicineUpdateQuantity(String medicineName, int updateQuantity, long pharmacistId) {
        return medicineRepository.updateQuantity(medicineName,updateQuantity,pharmacistId);
    }

    public List<Medicine> searchMedicines(String medicineName) {
            return medicineRepository.searchMedicines(medicineName);
    }
}
