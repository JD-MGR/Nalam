package com.zsgs.nalam.service;

import com.zsgs.nalam.exception.UserException;
import com.zsgs.nalam.model.User;
import com.zsgs.nalam.repository.UserRepository;

public class UserValidationService {
    public UserRepository userRepository;
    public UserValidationService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public boolean isValidUser(User user) throws UserException {
        if (user == null) {
            throw new UserException("User is Null");
        }
        if (user.getName() == null) {
            throw new UserException("Name cannot be null");
        }
        if (user.getEmailId() == null || user.getEmailId().isEmpty() || !(user.getEmailId().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
            throw new UserException("Email is Not Valid");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserException("Password cannot be null");
        }
        if( user.getPassword().length() < 8){
        throw new UserException("Password must be greater then 8 characters");
        }
        if(userAlreadyExist(user.getEmailId())){
            throw new UserException("User with this email Already Exists");
        }
        return true;
    }

    public boolean add(User user) {
        return userRepository.saved(user);
    }
    public boolean userAlreadyExist(String email){
        return userRepository.isUserAlreadyExist(email);
    }

    public boolean validCredentials(User user) throws UserException {
        if(user==null){
            throw new UserException("User cannot be null");
        } else if (user.getEmailId()==null||user.getEmailId().isEmpty()) {
            throw new UserException("Email Cannot be null");
        } else if (!userAlreadyExist(user.getEmailId())) {
            throw new UserException("Email not Registered");
        }
        else if(!checkValidPassword(user.getEmailId(),user.getPassword())){
            throw new UserException("Incorrect email or password");
        }
        return true;
    }

    private boolean checkValidPassword(String emailId, String password) {
        return userRepository.isValidPassword(emailId,password);
    }

    public User getUserDetails(String emailId) {
        return userRepository.getUserDetails(emailId);
    }
}