package com.zsgs.nalam.view;

import com.zsgs.nalam.model.User;
import com.zsgs.nalam.util.ScannerHelper;

import java.util.Objects;
import java.util.Scanner;

public class UserView {
    Scanner scanner;
    public UserView(){
      this.scanner= ScannerHelper.getScanner();
    }
    public User showRegisterForm() {
        User user=new User();
        System.out.println("---------Registration Form--------");

        System.out.println("Enter Your Name : ");
        user.setName(scanner.next());

        System.out.println("Enter Your Email : ");
        user.setEmailId(scanner.next());

        System.out.println("Enter Your Password : ");
        user.setPassword(scanner.next());

        boolean isRole=true;
       while(isRole){
           System.out.println("""
                Your Role ? 
                1.Pharmacist
                2.Customer""");
           String input = scanner.next();
           if(input.equals("1")){
               user.setRole(User.Role.PHARMACIST);
               isRole=false;
           }

           else if(input.equals("2")){
               user.setRole(User.Role.CUSTOMER);
               isRole=false;
           }

           else{
               System.out.println("Invalid Input. Please enter 1 or 2.");
           }
       }
       return user;
    }
    public  void display(String message){
        System.out.println(message);
    }

    public  boolean shouldReturnToMenu() {
        System.out.print("Continue to main menu? (y/n) : ");
         return Objects.equals(scanner.next(), "y");
    }

    public boolean redirectLogin() {
        System.out.println("Wanna Login ? (y/n)");
        return Objects.equals(scanner.next(), "y");
    }

    public User loginForm() {
        User user=new User();

        System.out.println("Enter Your Email : ");
        user.setEmailId(scanner.next());

        System.out.println("Enter Your Password : ");
        user.setPassword(scanner.next());

        return user;
    }
}
