package com.nalam;

import com.nalam.controller.*;
import com.nalam.model.Pharmacist;
import com.nalam.repository.*;
import com.nalam.service.*;
import com.nalam.util.ConsoleFormatter;
import com.nalam.view.*;

import java.util.Optional;
import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════════════════════╗
 *  N A L A M  —  Pharmacy Management System
 *  Version     : 1.0.0
 *  Pattern     : MVC (Console Application)
 *  Storage     : In-Memory (No Database)
 *  Entry Point : NalamApp.java  ← COMPLETE FILE
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Package layout:
 *  com.nalam
 *  ├── model          — Entity classes (Medicine, Customer, Order, …)
 *  ├── dto            — Data Transfer Objects (MedicineDTO, OrderDTO, …)
 *  ├── repository     — In-memory CRUD stores (HashMap-backed)
 *  ├── service        — Business logic layer
 *  ├── controller     — MVC Controllers (delegate to services)
 *  ├── view           — Console UI (reads scanner, prints menus)
 *  ├── exception      — Custom runtime exceptions
 *  └── util           — IdGenerator, InputValidator, ConsoleFormatter
 *
 *  Default login → username: admin | password: admin123
 */
public class NalamApp {

    // ── Infrastructure ────────────────────────────────────────────────────────

    private final Scanner scanner;

    // ── Repositories ──────────────────────────────────────────────────────────

    private final MedicineRepository   medicineRepository;
    private final CustomerRepository   customerRepository;
    private final OrderRepository      orderRepository;
    private final PharmacistRepository pharmacistRepository;
    private final SupplierRepository   supplierRepository;

    // ── Services ──────────────────────────────────────────────────────────────

    private final MedicineService   medicineService;
    private final CustomerService   customerService;
    private final OrderService      orderService;
    private final PharmacistService pharmacistService;
    private final SupplierService   supplierService;
    private final ReportService     reportService;

    // ── Controllers ───────────────────────────────────────────────────────────

    private final MedicineController   medicineController;
    private final CustomerController   customerController;
    private final OrderController      orderController;
    private final PharmacistController pharmacistController;
    private final SupplierController   supplierController;
    private final ReportController     reportController;

    // ── Views ─────────────────────────────────────────────────────────────────

    private final LoginView    loginView;
    private final MedicineView medicineView;
    private final CustomerView customerView;
    private final OrderView    orderView;
    private final SupplierView supplierView;
    private final ReportView   reportView;

    // ── Session ───────────────────────────────────────────────────────────────

    private Pharmacist currentUser;

    // ─────────────────────────────────────────────────────────────────────────
    // Constructor — dependency wiring (Repositories → Services → Controllers → Views)
    // ─────────────────────────────────────────────────────────────────────────

    public NalamApp() {
        scanner = new Scanner(System.in);

        // Repositories
        medicineRepository   = new MedicineRepository();
        customerRepository   = new CustomerRepository();
        orderRepository      = new OrderRepository();
        pharmacistRepository = new PharmacistRepository();
        supplierRepository   = new SupplierRepository();

        // Services
        medicineService   = new MedicineService(medicineRepository);
        customerService   = new CustomerService(customerRepository);
        orderService      = new OrderService(orderRepository, medicineRepository,
                                             customerRepository, medicineService);
        pharmacistService = new PharmacistService(pharmacistRepository);
        supplierService   = new SupplierService(supplierRepository);
        reportService     = new ReportService(medicineRepository, orderRepository);

        // Controllers
        medicineController   = new MedicineController(medicineService);
        customerController   = new CustomerController(customerService);
        orderController      = new OrderController(orderService);
        pharmacistController = new PharmacistController(pharmacistService);
        supplierController   = new SupplierController(supplierService);
        reportController     = new ReportController(reportService);

        // Views
        loginView    = new LoginView(pharmacistController, scanner);
        medicineView = new MedicineView(medicineController, scanner);
        customerView = new CustomerView(customerController, scanner);
        orderView    = new OrderView(orderController, scanner);
        supplierView = new SupplierView(supplierController, scanner);
        reportView   = new ReportView(reportController, scanner);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Entry Point
    // ─────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        new NalamApp().run();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Application lifecycle
    // ─────────────────────────────────────────────────────────────────────────

    public void run() {
        printSplash();

        while (true) {
            Optional<Pharmacist> auth = loginView.promptLogin();
            if (auth.isEmpty()) { exit(); return; }

            currentUser = auth.get();
            orderView.setCurrentPharmacistId(currentUser.getPharmacistId());
            showMainMenu();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Splash Screen
    // ─────────────────────────────────────────────────────────────────────────

    private void printSplash() {
        System.out.println(ConsoleFormatter.CYAN + ConsoleFormatter.BOLD);
        System.out.println("  ███╗   ██╗ █████╗ ██╗      █████╗ ███╗   ███╗");
        System.out.println("  ████╗  ██║██╔══██╗██║     ██╔══██╗████╗ ████║");
        System.out.println("  ██╔██╗ ██║███████║██║     ███████║██╔████╔██║");
        System.out.println("  ██║╚██╗██║██╔══██║██║     ██╔══██║██║╚██╔╝██║");
        System.out.println("  ██║ ╚████║██║  ██║███████╗██║  ██║██║ ╚═╝ ██║");
        System.out.println("  ╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝");
        System.out.println();
        System.out.println("       நலம் — Pharmacy Management System v1.0");
        System.out.println("           Better Health, Every Day.");
        System.out.println(ConsoleFormatter.RESET);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Main Menu
    // ─────────────────────────────────────────────────────────────────────────

    private void showMainMenu() {
        boolean sessionActive = true;
        while (sessionActive) {
            ConsoleFormatter.printHeader("🏥  NALAM MAIN MENU  |  " + currentUser.getName()
                    + "  [" + currentUser.getRole() + "]");
            System.out.println("  1. 💊  Medicine Management");
            System.out.println("  2. 👤  Customer Management");
            System.out.println("  3. 🧾  Orders & Billing");
            System.out.println("  4. 🚚  Supplier Management");
            System.out.println("  5. 📊  Reports & Analytics");
            System.out.println("  6. 🔓  Logout");
            System.out.println("  0. 🚪  Exit Application");
            ConsoleFormatter.printSectionLine();
            System.out.print("  Choice: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> goToMedicineMenu();
                    case "2" -> goToCustomerMenu();
                    case "3" -> goToOrderMenu();
                    case "4" -> goToSupplierMenu();
                    case "5" -> goToReportsMenu();
                    case "6" -> { logout(); sessionActive = false; }
                    case "0" -> { exit(); System.exit(0); }
                    default  -> ConsoleFormatter.printWarning("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                ConsoleFormatter.printError(e.getMessage());
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Navigation helpers
    // ─────────────────────────────────────────────────────────────────────────

    private void goToMedicineMenu()  { medicineView.show(); }
    private void goToCustomerMenu()  { customerView.show(); }
    private void goToOrderMenu()     { orderView.show(); }
    private void goToSupplierMenu()  { supplierView.show(); }
    private void goToReportsMenu()   { reportView.show(); }

    private void logout() {
        ConsoleFormatter.printInfo("Logged out: " + currentUser.getName()
                + ". Returning to login screen...");
        currentUser = null;
    }

    private void exit() {
        ConsoleFormatter.printInfo("Shutting down Nalam Pharmacy System. நன்றி!");
        scanner.close();
    }
}
