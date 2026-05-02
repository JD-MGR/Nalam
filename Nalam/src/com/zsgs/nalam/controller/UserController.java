package com.zsgs.nalam.controller;

import com.zsgs.nalam.exception.UserException;
import com.zsgs.nalam.model.User;
import com.zsgs.nalam.service.UserValidationService;
import com.zsgs.nalam.view.AppView;
import com.zsgs.nalam.view.UserView;

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
                    if (isAdded) {
                        userView.display("User added succesfully");
                        break;
                    } else {
                        userView.display("User not Added ");
                        break;
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

    public void handleUserDashboard(long id) {
    }
}
