package com.ganesh.nalam.controller;

import com.ganesh.nalam.exception.UserException;
import com.ganesh.nalam.model.User;
import com.ganesh.nalam.service.UserValidationService;
import com.ganesh.nalam.view.UserView;

import java.util.List;

public class UserController {
    private UserView userView;
    private UserValidationService userValidationService;

    public UserController(UserView userView, UserValidationService userValidationService) {
        this.userView = userView;
        this.userValidationService = userValidationService;
    }

    public void handleRegistration() {
            while (true) {
                try{
                    User user = userView.showRegisterForm();
                    boolean isValid = userValidationService.isValidUser(user);
                    boolean isAdded = userValidationService.add(user);
                   if(isValid){
                    if (isAdded) {
                        userView.display("User added succesfully");
                        break;
                    } else {
                        userView.display("User not Added ");
                        break;
                    }
                }
                   else {
                       userView.display("User is Not valid");
                   }
                }
                catch (Exception e){
                    userView.display(e.getMessage());
                    if (userView.shouldReturnToMenu()) {
                        return;
                    }
                }
            }
    }

    public User handleLogin() throws UserException {
       User user=userView.loginForm();
       try{
           if(userValidationService.validCredentials(user)){
               userView.display("Login Successful");
               return userValidationService.getUserDetails(user.getEmailId());
           };
       } catch (UserException e) {
           userView.display("Invalid Credentials "+e.getMessage());
       }
       return null;
    }

//    public void handleUserDashboard(long id) {
//    }

    public void handleUsersView() {
        List<User> userList=userValidationService.handleUsersDisplay();
        userView.displayAllUsers(userList);
    }
}
