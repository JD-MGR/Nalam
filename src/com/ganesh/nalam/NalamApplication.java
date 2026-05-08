package com.ganesh.nalam;

import com.ganesh.nalam.controller.*;
import com.ganesh.nalam.view.*;
import com.zsgs.nalam.controller.*;
import com.ganesh.nalam.exception.UserException;
import com.ganesh.nalam.repository.MedicineRepository;
import com.ganesh.nalam.repository.OrderRepository;
import com.ganesh.nalam.repository.UserRepository;
import com.ganesh.nalam.service.MedicineValidationService;
import com.ganesh.nalam.service.OrderValidationService;
import com.ganesh.nalam.service.UserValidationService;
import com.zsgs.nalam.view.*;

public class NalamApplication {
    static void main() throws UserException {
        NalamApplication nalamApp=new NalamApplication();
        nalamApp.init();
    }
   public void init(){
        AppView appView=new AppView();
        UserView userView=new UserView();
       PharmacistView pharmacistView=new PharmacistView();
       MedicineView medicineView=new MedicineView();
       CustomerView customerView=new CustomerView();

       UserRepository userRepository=UserRepository.getInstance();
       MedicineRepository medicineRepository=MedicineRepository.getInstance();
       OrderRepository orderRepository=OrderRepository.getInstance();

       MedicineValidationService medicineValidationService=new MedicineValidationService(medicineRepository);
       OrderValidationService orderValidationService=new OrderValidationService(orderRepository);
       UserValidationService userValidationService=new UserValidationService(userRepository);

       UserController userController=new UserController(userView,userValidationService);
       MedicineController medicineController=new MedicineController(medicineView,medicineValidationService,orderValidationService);
       OrderController orderController=new OrderController(orderValidationService,customerView);
       PharmacistController pharmacistController=new PharmacistController(pharmacistView,medicineController,userController,orderController);
       CustomerController customerController=new CustomerController(customerView,medicineController,medicineView,orderController);
       AppController appController=new AppController(appView,userController,pharmacistController,customerController);

       appController.init();
    }
}
