package org.example.app;

import org.example.app.common.UserTypes;
import org.example.db.Database;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static UserService userService;
    private static ExpenseService expenseService;

    public static void main(String[] args) {

        Database database = new Database();
        userService = new UserService(database);
        expenseService = new ExpenseService(database);

        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");

        menuSelector(null);

        System.out.println("\nUygulama kapatılmıştır.");

    }

    public static void menuSelector(User user) {

        if (user != null) {
            if (Objects.equals(user.getType(), UserTypes.ADMIN)) {
                showAdminMenu(user);
            } else if (Objects.equals(user.getType(), UserTypes.CUSTOMER)) {
                showCustomerMenu(user);
            }
            System.out.println("\nHoşgeldiniz, " + user.getName());
        } else {
            showMainMenu(null);
        }

    }


    public static void showMainMenu(User user) {

        Scanner scanner = new Scanner(System.in);

        menuHeader();
        System.out.println("1- Giriş Yap");
        System.out.println("2- Kaydol");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                User currentUser = null;
                String email;
                String password;

                while (true) {

                    System.out.println("\nEpostanızı giriniz:");
                    email = scanner.nextLine();

                    System.out.println("\nŞifrenizi giriniz:");
                    password = scanner.nextLine();

                    currentUser = userService.login(email, password);

                    if (currentUser != null) {
                        System.out.println("\nBaşarıyla kullanıcı girişi yapıldı.");
                        break;
                    } else {
                        System.out.println("\nHata: Kullanıcı girişi yapılamadı.");
                    }

                }
                user = currentUser;
                break;
            case 2:

                User newUser = null;
                String secondPassword;

                while (true) {

                    newUser = new User();

                    newUser.setType(UserTypes.CUSTOMER);

                    System.out.println("\nAdınızı giriniz: ");
                    newUser.setName(scanner.nextLine());

                    System.out.println("\nSoyadınızı giriniz: ");
                    newUser.setSurname(scanner.nextLine());

                    System.out.println("\nEposta adresinizi giriniz: ");
                    newUser.setEmail(scanner.nextLine());

                    System.out.println("\nŞifrenizi giriniz:");
                    newUser.setPassword(scanner.nextLine());

                    System.out.println("\nŞifrenizi tekrar giriniz:");
                    secondPassword = scanner.nextLine();

                    if (userService.register(newUser, secondPassword)) {
                        System.out.println("\nKullanıcı kaydı başarıyla gerçekleşti.");
                        break;
                    } else {
                        System.out.println("\nHata kullanıcı kaydı oluşturulamadı.");
                    }

                }
                user = newUser;
                showMainMenu(user);
                break;
        }


    }

    public static void showAdminMenu(User user) {

        Scanner scanner = new Scanner(System.in);

        menuHeader();
        System.out.println("1- Kullanıcılar");
        System.out.println("2- Kullanıcı Sil");
        System.out.println("3- Oturumu Kapat");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                showAllUsers();
                break;
            case 2:
                showAllUsers();
                System.out.println("\nSilmek istediğiniz kullanıcı numarasını giriniz: ");
                if (userService.deleteUser(Integer.parseInt(scanner.nextLine()))) {
                    System.out.println("\nKullanıcı başarıyla silindi.");
                } else {
                    System.out.println("\nHata: Kullanıcı silinemedi.");
                }
                break;
            case 3:
                logoutUser(user);
                break;
        }

    }

    public static void showCustomerMenu(User user) {

        Scanner scanner = new Scanner(System.in);

        menuHeader();
        System.out.println("1- Harcamalarım");
        System.out.println("2- Harcama Ekle");
        System.out.println("3- Harcama Düzenle");
        System.out.println("4- Harcama Sil");
        System.out.println("5- Harcama Kategorileri");
        System.out.println("6- Oturumu Kapat");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                expenseService.showExpenses();
                break;
            case 2:
                expenseService.addExpense();
                break;
            case 3:
                expenseService.editExpense();
                break;
            case 4:
                expenseService.deleteExpense();
                break;
            case 6:
                logoutUser(user);
                user = null;
                showMainMenu(user);
                break;
        }
    }

    public static void menuHeader() {

        System.out.println("");

    }

    public static void menuFooter() {

        System.out.println("0- Çıkış Yap");
        System.out.print("\nLütfen bir menü numarası giriniz: ");

    }

    public static void logoutUser(User user) {

        if (userService.logout(user)) {
            System.out.println("\nOturum başarıyla kapatılmıştır.");
        } else {
            System.out.println("\nHata: Oturum kapatılamadı.");
        }

    }

    public static void showAllUsers() {

        System.out.println("\nTüm Kullanıcı Listesi:");

        List<User> allUsersList = userService.getAllUsers();
        int i;
        for (i = 0; i < allUsersList.size(); i++) {
            System.out.println(i + "- " + allUsersList.get(i).getName() + ", " + allUsersList.get(i).getSurname() + ", " + allUsersList.get(i).getEmail());
        }

    }

}