package org.example.app.ui;

import org.example.app.domain.Expense;
import org.example.app.domain.User;
import org.example.app.enums.UserTypes;
import org.example.app.utils.Dates;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.example.app.App.expenseService;
import static org.example.app.App.userService;

public class Common {
    private final AdminMenu adminMenu;
    private final CustomerMenu customerMenu;
    private final GuestMenu guestMenu;

    private static final Scanner scanner = new Scanner(System.in);

    public Common() {
        this.adminMenu = new AdminMenu(this);
        this.customerMenu = new CustomerMenu(this);
        this.guestMenu = new GuestMenu(this);
    }

    public void menuSelector(){
        if (userService.getCurrentUser() != null) {
            if (Objects.equals(userService.getCurrentUser().getType(), UserTypes.ADMIN)) {
                adminMenu.show();
            } else if (Objects.equals(userService.getCurrentUser().getType(), UserTypes.CUSTOMER)) {
                customerMenu.show();
            }
        } else {
            guestMenu.show();
        }
    }

    public void menuHeader() {
        System.out.print("\n");
    }

    public void menuFooter() {
        System.out.print("\n");
        if (userService.getCurrentUser() != null) {
            System.out.println("o- Oturumu Kapat");
        }
        System.out.println("ç- Çıkış Yap");
        System.out.print("\nLütfen bir menü numarası giriniz: ");
    }

    public void backwardMenu() {
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

    public void logoutUser() {
        if (userService.logout()) {
            System.out.println("\nOturum başarıyla kapatıldı.");
        } else {
            System.out.println("\nHata: Oturum kapatılamadı.");
        }
    }

    public void recordNotFound() {
        System.out.println("\nHerhangi bir kayıt bulunamadı.");
    }

    public void showUsers() {
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

    public void showUserExpenses(Integer userId) {
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

    public void showUserExpenseCategories(Integer userId) {
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
