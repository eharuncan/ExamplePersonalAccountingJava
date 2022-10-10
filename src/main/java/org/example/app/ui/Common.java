package org.example.app.ui;

import org.example.app.domain.Expense;
import org.example.app.domain.User;
import org.example.app.enums.UserTypes;
import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;
import org.example.app.utils.Dates;
import org.example.db.Database;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Common {
    private static UserService userService;
    private static ExpenseService expenseService;
    private static Admin admin;
    private static Customer customer;
    private static Guest guest;

    private static final Scanner scanner = new Scanner(System.in);

    public Common(Database database) {
        userService = new UserService(database);
        expenseService = new ExpenseService(database);
        admin = new Admin(database);
        customer = new Customer(database);
        guest = new Guest(database);
    }

    public static void menuSelector() {
        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");

        if (userService.getCurrentUser() != null) {
            if (Objects.equals(userService.getCurrentUser().getType(), UserTypes.ADMIN)) {
                admin.showMenu();
            } else if (Objects.equals(userService.getCurrentUser().getType(), UserTypes.CUSTOMER)) {
                customer.showMenu();
            }
        } else {
            guest.showMenu();
        }
    }

    public static void menuHeader() {
        System.out.print("\n");
    }

    public static void menuFooter() {
        System.out.print("\n");
        if (userService.getCurrentUser() != null) {
            System.out.println("o- Oturumu Kapat");
        }
        System.out.println("ç- Çıkış Yap");
        System.out.print("\nLütfen bir menü numarası giriniz: ");
    }

    public static void backwardMenu() {
        loops:
        while (true) {
            System.out.println("\ng- Geri Dön");
            menuFooter();
            switch (scanner.nextLine()) {
                case "g":
                    menuSelector();
                    break loops;
                case "o":
                    logoutUser();
                    menuSelector();
                case "ç":
                    break loops;
                default:
                    System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
                    break;
            }
        }
    }

    public static void logoutUser() {
        if (userService.logout()) {
            System.out.println("\nOturum başarıyla kapatıldı.");
        } else {
            System.out.println("\nHata: Oturum kapatılamadı.");
        }
    }

    public static void recordNotFound() {
        System.out.println("\nHerhangi bir kayıt bulunamadı.");
    }

    public static void showUsers() {
        List<User> userList = userService.getUsers();
        if (userList.size() == 0) {
            recordNotFound();
        } else {
            int i;
            for (i = 0; i < userList.size(); i++) {
                System.out.println("\nKullanıcı ID: " + userList.get(i).getId());
                System.out.println("Kullanıcı tipi: " + userList.get(i).getType());
                System.out.println("Kullanıcı adı: " + userList.get(i).getName());
                System.out.println("Kullanıcı soyadı: " + userList.get(i).getSurname());
                System.out.println("Kullanıcı eposta adresi: " + userList.get(i).getEmail());
            }
        }
    }

    public static void showUserExpenses(Integer userId) {
        List<Expense> expenseList = expenseService.getExpensesByUserId(userId);
        if (expenseList.size() == 0) {
            recordNotFound();
        } else {
            int i;
            for (i = 0; i < expenseList.size(); i++) {
                System.out.println("\nHarcama ID: " + (expenseList.get(i).getId() + 1));
                System.out.println("Harcama adı: " + expenseList.get(i).getName());
                System.out.println("Harcama miktarı: " + expenseList.get(i).getAmount());
                System.out.println("Harcama tarihi: " + Dates.formatter.format(expenseList.get(i).getDate()));
                System.out.println("Harcama kategorisi: " + expenseList.get(i).getCategory());
            }
        }
    }

    public static void showUserExpenseCategories(Integer userId) {
        System.out.print("\n");
        List<String> expenseCategoryList = userService.getUserById(userId).getExpenseCategoryList();
        if (expenseCategoryList.size() == 0) {
            recordNotFound();
        } else {
            int i;
            for (i = 0; i < expenseCategoryList.size(); i++) {
                System.out.println("" + (i + 1) + "- " + expenseCategoryList.get(i));
            }
        }
    }
}
