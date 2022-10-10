package org.example.app.ui;

import org.example.app.services.UserService;
import org.example.db.Database;

import java.util.Scanner;

public class Admin {
    private static UserService userService;
    private static final Scanner scanner = new Scanner(System.in);

    public Admin(Database database) {
        userService = new UserService(database);
    }

    public static void showMenu() {
        System.out.println("\nSistem yönetimine hoşgeldiniz, " + userService.getCurrentUser().getName());

        loops:
        while (true) {
            Common.menuHeader();
            System.out.println("1- Kullanıcılar");
            System.out.println("2- Kullanıcı Sil");
            Common.menuFooter();

            switch (scanner.nextLine()) {
                case "1":
                    Common.showUsers();
                    Common.backwardMenu();
                    break;
                case "2":
                    System.out.println("\nTüm Kullanıcıların Listesi:");
                    Common.showUsers();

                    System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz:");
                    if (userService.deleteUserByIndex(Integer.parseInt(scanner.nextLine()))) {
                        System.out.println("\nKullanıcı başarıyla silindi");
                        Common.backwardMenu();
                    } else {
                        System.out.println("\nHata: Kullanıcı silinemedi.");
                    }
                    break;
                case "o":
                    Common.logoutUser();
                    Common.menuSelector();
                    break;
                case "ç":
                    break loops;
                default:
                    System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
                    break;
            }
        }
    }

}
