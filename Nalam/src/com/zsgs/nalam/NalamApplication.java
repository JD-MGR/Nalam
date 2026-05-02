package com.zsgs.nalam;

import com.zsgs.nalam.controller.*;
import com.zsgs.nalam.exception.MedicineException;
import com.zsgs.nalam.exception.UserException;
import com.zsgs.nalam.model.Medicine;
import com.zsgs.nalam.repository.MedicineRepository;
import com.zsgs.nalam.repository.UserRepository;
import com.zsgs.nalam.service.MedicineValidationService;
import com.zsgs.nalam.service.OrderValidationService;
import com.zsgs.nalam.service.UserValidationService;
import com.zsgs.nalam.view.AppView;
import com.zsgs.nalam.view.MedicineView;
import com.zsgs.nalam.view.PharmacistView;
import com.zsgs.nalam.view.UserView;

public class NalamApplication {
    static void main() throws UserException {
        NalamApplication nalamApp=new NalamApplication();
        nalamApp.init();
    }
   public void init() throws UserException {
        AppView appView=new AppView();
        UserView userView=new UserView();
       PharmacistView pharmacistView=new PharmacistView();
       MedicineView medicineView=new MedicineView();

       UserRepository userRepository=new UserRepository();
       MedicineRepository medicineRepository=new MedicineRepository();

       MedicineValidationService medicineValidationService=new MedicineValidationService(medicineRepository);
       OrderValidationService orderValidationService=new OrderValidationService();
       UserValidationService userValidationService=new UserValidationService(userRepository);

       UserController userController=new UserController(userView,userValidationService);
       MedicineController medicineController=new MedicineController(medicineView,medicineValidationService);
       OrderController orderController=new OrderController();
       PharmacistController pharmacistController=new PharmacistController(pharmacistView,medicineController);
       AppController appController=new AppController(appView,userController,pharmacistController);

       appController.init();
    }
}
