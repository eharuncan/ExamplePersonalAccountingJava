package org.example.app.menus;

import org.example.app.domain.Expense;
import org.example.app.domain.ExpenseCategory;
import org.example.app.domain.User;

import org.example.app.enums.UserTypes;

import java.util.List;
import java.util.Objects;

import static org.example.app.App.*;
import static org.example.app.utils.Utils.dateFormatter;

public class CommonMenu {
    private final AdminMenu adminMenu;
    private final CustomerMenu customerMenu;
    private final GuestMenu guestMenu;

    public CommonMenu() {
        this.adminMenu = new AdminMenu(this);
        this.customerMenu = new CustomerMenu(this);
        this.guestMenu = new GuestMenu(this);
    }

    public void selector() {
        if (currentUser != null) {
            if (Objects.equals(currentUser.getType(), UserTypes.ADMIN)) {
                adminMenu.show();
            } else if (Objects.equals(currentUser.getType(), UserTypes.CUSTOMER)) {
                customerMenu.show();
            }
        } else {
            guestMenu.show();
        }
    }

    public void showHeader() {
        System.out.print("\n");
    }

    public void showFooter() {
        System.out.print("\n");
        if (currentUser != null) {
            System.out.println("o- Oturumu Kapat");
        }
        System.out.println("ç- Çıkış Yap");
        System.out.print("\nLütfen bir menü numarası giriniz: ");
    }

    public void showBackward() {
        while (true) {
            System.out.println("\ng- Geri Dön");
            showFooter();

            String input = getInput(null);
            if (Objects.equals(input, "g")) {
                selector();
                break;
            } else if (Objects.equals(input, "o")) {
                logoutUser(currentUser);
                selector();
                break;
            } else if (Objects.equals(input, "ç")) {
                break;
            } else {
                System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
            }
        }
    }

    public void showRecordNotFound() {
        System.out.println("\nHerhangi bir kayıt bulunamadı.");
    }

    public void showUsers() {
        List<User> userList = userService.getUsers();
        if (userList.size() == 0) {
            showRecordNotFound();
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

    public void showUserProfile(User user) {
        System.out.println("\nAdınız: " + user.getName());
        System.out.println("Soyadınız: " + user.getSurname());
        System.out.println("Eposta adresiniz : " + user.getEmail());
    }

    public void showUserExpenses(User user) {
        List<Expense> expenseList = expenseService.getExpensesOfUser(user.getId());
        if (expenseList.size() == 0) {
            showRecordNotFound();
        } else {
            int i;
            for (i = 0; i < expenseList.size(); i++) {
                System.out.println("\nHarcama ID: " + (expenseList.get(i).getId()));
                System.out.println("Harcama adı: " + expenseList.get(i).getName());
                System.out.println("Harcama miktarı: " + expenseList.get(i).getAmount());
                System.out.println("Harcama tarihi: " + dateFormatter.format(expenseList.get(i).getDate()));
                System.out.println("Harcama kategorisi: " + expenseCategoryService.getExpenseCategoryOfUser(user.getId(), expenseList.get(i).getCategoryId()).getName());
            }
        }
    }

    public void showUserExpenseCategories(User user) {
        List<ExpenseCategory> expenseCategoryList = expenseCategoryService.getExpenseCategoriesOfUser(user.getId());
        if (expenseCategoryList.size() == 0) {
            showRecordNotFound();
        } else {
            int i;
            for (i = 0; i < expenseCategoryList.size(); i++) {
                System.out.println("\nKategori ID: " + (expenseCategoryList.get(i).getId()));
                System.out.println("Kategori adı: " + expenseCategoryList.get(i).getName());
            }
        }
    }
}
