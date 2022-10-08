package org.example.app;

import org.example.app.common.UserMenus;
import org.example.app.common.UserTypes;
import org.example.db.Database;

import java.util.Arrays;
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

        showMenu(null);

    }

    public static void showMenu(User user) {

        Scanner readScreen = new Scanner(System.in);

        Enum selectedMenu = null;

        if (user != null) {
            if (Objects.equals(user.getType(), UserTypes.ADMIN)) {
                selectedMenu = UserMenus.ADMIN_MENU;
            } else if (Objects.equals(user.getType(), UserTypes.CUSTOMER)) {
                selectedMenu = UserMenus.CUSTOMER_MENU;
            }
            System.out.println("\nHoşgeldiniz, " + user.getName());
        } else {
            selectedMenu = UserMenus.MAIN_MENU;
        }

        if (Objects.equals(selectedMenu, UserMenus.MAIN_MENU)) {

            menuHeader();
            System.out.println("1- Giriş Yap");
            System.out.println("2- Kaydol");
            menuFooter();
            boolean check = false;

            switch (readScreen.nextInt()) {
                case 1:
                    User currentUser = null;
                    String email;
                    String password;

                    while (!check) {

                        System.out.println("Epostanızı giriniz:");
                        email = readScreen.nextLine();

                        System.out.println("Şifrenizi giriniz:");
                        password = readScreen.nextLine();

                        currentUser = userService.login(email, password);

                        if (currentUser != null) {
                            check = true;
                            System.out.println("\nBaşarıyla kullanıcı girişi yapıldı.");
                        } else {
                            System.out.println("\nHata: Kullanıcı girişi yapılamadı.");
                        }

                    }
                    showMenu(currentUser);
                    break;
                case 2:

                    User newUser = null;
                    String secondPassword;

                    while (!check) {

                        newUser = new User();

                        System.out.println("Adınızı giriniz:");
                        newUser.setName(readScreen.nextLine());

                        System.out.println("Soyadınızı giriniz:");
                        newUser.setSurname(readScreen.nextLine());

                        System.out.println("Eposta adresinizi giriniz:");
                        newUser.setEmail(readScreen.nextLine());

                        System.out.println("Şifrenizi giriniz:");
                        newUser.setPassword(readScreen.nextLine());

                        System.out.println("Şifrenizi tekrar giriniz:");
                        secondPassword = readScreen.nextLine();

                        if (userService.register(newUser, secondPassword)) {
                            System.out.println("\nKullanıcı kaydı başarıyla gerçekleşti.");
                            check = true;

                        } else {
                            System.out.println("\nHata kullanıcı kaydı oluşturulamadı.");
                        }

                    }
                    showMenu(newUser);
                    break;
            }

        } else if (Objects.equals(selectedMenu, UserMenus.ADMIN_MENU)) {

            menuHeader();
            System.out.println("1- Kullanıcılar");
            System.out.println("2- Kullanıcı Sil");
            System.out.println("3- Oturumu Kapat");
            menuFooter();

            switch (readScreen.nextInt()) {
                case 1:
                    showAllUsers();
                    showMenu(user);
                    break;
                case 2:
                    showAllUsers();
                    System.out.println("\nSilmek istediğiniz kullanıcı numarasını giriniz: ");
                    if (userService.deleteUser(readScreen.nextInt())) {
                        System.out.println("\nKullanıcı başarıyla silindi.");
                    } else {
                        System.out.println("\nHata: Kullanıcı silinemedi.");
                    }
                    showMenu(user);
                    break;
                case 3:
                    logoutUser(user);
                    break;
            }

        } else if (Objects.equals(selectedMenu, UserMenus.CUSTOMER_MENU)) {

            menuHeader();
            System.out.println("1- Harcamalarım");
            System.out.println("2- Harcama Ekle");
            System.out.println("3- Harcama Düzenle");
            System.out.println("4- Harcama Sil");
            System.out.println("5- Harcama Kategorileri");
            System.out.println("6- Oturumu Kapat");
            menuFooter();

            switch (readScreen.nextInt()) {
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
                    break;
            }

        }

        System.out.println("\nUygulama kapatılmıştır.");
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
            showMenu(null);
            System.out.println("\nOturum başarıyla kapatılmıştır.");
        } else {
            System.out.println("\nHata: Oturum kapatılamadı.");
        }

    }

    public static void showAllUsers() {

        List<User> allUsersList = userService.getAllUsers();
        int i;
        for (i = 0; i < allUsersList.size(); i++) {
            System.out.println(i + "- " + allUsersList.get(i).getName() + ", " + allUsersList.get(i).getSurname() + ", " + allUsersList.get(i).getEmail());
        }

    }

}