package org.example.app;

import org.example.app.common.UserMenus;
import org.example.app.common.UserTypes;
import org.example.db.Database;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static UserService userService; // constructorları çalıştırmak için. kullanılmazsa verileri erişlmez sadece fonksiyonlara erişilir.
    private static ExpenseService expenseService;

    public static void main(String[] args) {

        Database database = new Database();
        userService = new UserService(database);
        expenseService = new ExpenseService(database);

        showMenu(null);

    }

    public static void showMenu(User user) {

        Scanner readScreen = new Scanner(System.in);

        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");

        Enum selectedMenu = null;

        if (user != null) {
            if (Objects.equals(user.getType(), UserTypes.ADMIN)) {
                selectedMenu = UserMenus.ADMIN_MENU;
            } else if (Objects.equals(user.getType(), UserTypes.CUSTOMER)) {
                selectedMenu = UserMenus.CUSTOMER_MENU;
            }
            System.out.println("\nHoşgeldiniz, " + user.getName());
        }
        else {
            selectedMenu = UserMenus.MAIN_MENU;
        }

        if (Objects.equals(selectedMenu, UserMenus.MAIN_MENU)) {

            menuHeader();
            System.out.println("1- Giriş Yap");
            System.out.println("2- Kaydol");
            menuFooter();

            switch (readScreen.nextInt()) {
                case 1:
                    user = userService.login();
                    showMenu(user);
                    break;
                case 2:
                    user = userService.register();
                    showMenu(user);
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
                    System.out.println("Silmek istediğiniz kullanıcı numarasını giriniz: ");
                    if (userService.deleteUser(readScreen.nextInt())){
                        System.out.println("Kullanıcı başarıyla silindi.");
                    }else {
                        System.out.println("Hata: Kullanıcı silinemedi.");
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

        System.out.println("Uygulama kapatılmıştır.");
    }

    public static void menuHeader() {

        System.out.println("");

    }

    public static void menuFooter() {

        System.out.println("0- Çıkış Yap\n");
        System.out.print("Lütfen bir menü numarası giriniz: ");

    }

    public static void logoutUser(User user) {

        if (userService.logout(user)){
            showMenu(null);
            System.out.println("\nOturum başarıyla kapatılmıştır.");
        }
        else {
            System.out.println("Hata: Oturum kapatılamadı.");
        }

    }

    public static void showAllUsers(){

        List<User> allUsersList = userService.getAllUsers();
        int i;
        for (i=0; i< allUsersList.size(); i++){
            System.out.println(i + "- " + allUsersList.get(i).getName() + ", " + allUsersList.get(i).getSurname() + ", " + allUsersList.get(i).getEmail());
        }

    }

}