package com.ganesh.nalam.view;

import com.ganesh.nalam.model.Medicine;
import com.ganesh.nalam.util.ScannerHelper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MedicineView {
    private Scanner scanner;
    public MedicineView(){
        this.scanner= ScannerHelper.getScanner();
    }
    public Medicine addMedicineForm() {
        Medicine medicine=new Medicine();
        System.out.println("----------Medicine Adding Form--------");
        System.out.println("Enter Medicine Name :");
        medicine.setName(scanner.next());
        scanner.nextLine();
        System.out.println("Enter Medicine Description :");
        medicine.setDescription(scanner.next());
        scanner.nextLine();
        System.out.println("Enter Medicine Price :");
        medicine.setPrice(scanner.nextDouble());
        scanner.nextLine();
        System.out.println("Enter Medicine Expiry Date");
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while(true){
            try {
                String expDateInput= scanner.nextLine();
                LocalDate date=LocalDate.parse(expDateInput,dateTimeFormatter);
                long expiryDate=date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                medicine.setExpDate(expiryDate);
                break;
            }
            catch (Exception e){
                System.out.println("Invalid format ,Please Enter date as dd-MM-yyyy");
            }
        }
        System.out.println("Enter Stock Quantity :");
        medicine.setQuantity(scanner.nextLong());
        return medicine;
    }
    public String deleteMedicineForm(){
        System.out.println("----------Medicine Deleteing Form---------");
        System.out.println("Enter Medicine name : ");
        return scanner.next();
    }
    public  boolean tryAgain() {
        System.out.print("Continue? (y/n) : ");
        return Objects.equals(scanner.next(), "y");
    }


    public void display(String s) {
        System.out.println(s);
    }

    public String getMedicineNameToUpdate() {
        System.out.println("---------Medicine Update Page-------");
        System.out.println("Enter Medicine Name :");
        return scanner.next();
    }

    public String getMedicineName() {
//        System.out.println("---------Medicine Search Page-------");
        System.out.print("Enter Medicine Name : ");
        return scanner.next();
    }

    public int getMedicineQuantity() {
        scanner.nextLine();
        System.out.println("Enter the Quantity :");
        int choice= scanner.nextInt();
        return choice;
    }

    public void showMedicines(List<Medicine> medicineList) {
        if (medicineList.isEmpty()) {
            System.out.println("No medicines found.");
            return;
        }
        for(Medicine medicine:medicineList){
            System.out.println("Medicine Name : "+medicine.getName());
            System.out.println("Medicine Description : "+medicine.getDescription());
            System.out.println("Medicine Price : "+medicine.getPrice());
            System.out.println("Medicine Expiry Date : " + String.format("%tF", medicine.getExpDate()));
            System.out.println("Medicine Quantity : "+medicine.getQuantity());
            System.out.println("================================================");
        }
    }

    public String showDashboard() {
        System.out.println("Enter Medicine Name to Search : ");
        return scanner.next();
    }
}
