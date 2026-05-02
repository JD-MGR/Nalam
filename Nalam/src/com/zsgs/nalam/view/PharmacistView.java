package com.zsgs.nalam.view;

import com.zsgs.nalam.controller.PharmacistController;
import com.zsgs.nalam.util.ScannerHelper;

import java.util.Scanner;

public class PharmacistView {
    private Scanner scanner;
     public PharmacistView(){
        this.scanner= ScannerHelper.getScanner();
    }
    public int showDashBoard() {
        System.out.println("---------Welcome to Pharmacist Section--------");
        System.out.println();
        System.out.println("""
                1.Add Medicine
                2.Remove Medicine
                3.Update Stock
                4.View Stock
                5.View Orders
                6.View Users
                7.Generate Report""");
       while(!scanner.hasNextInt()){
           System.out.println("Invalid Choice");
       }
       return scanner.nextInt();
    }

    public void display(String s) {
        System.out.println(s);
    }
}
