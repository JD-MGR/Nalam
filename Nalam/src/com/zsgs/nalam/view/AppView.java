package com.zsgs.nalam.view;

import com.zsgs.nalam.util.ScannerHelper;

import java.util.Scanner;

public class AppView {
    public Scanner scanner;

    public AppView(){
        this.scanner= ScannerHelper.getScanner();
    }

    public int showMenu() {
        System.out.println();
        System.out.println("============NALAM============");
        System.out.println();
        System.out.println("-------Welcome to Nalam Pharmacy-------");
        System.out.println("""
                1.Register
                2.Log in
                3.Exit""");
        while(!scanner.hasNextInt()){
            System.out.println("Invalid Choice Please Select form 1 to 3 ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void displayMessage(String displayMessage) {
        System.out.println(displayMessage);
    }
}
