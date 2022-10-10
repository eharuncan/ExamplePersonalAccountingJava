package org.example.app.ui;

import static org.example.app.App.userService;

import java.util.Scanner;

public class AdminMenu {
    private final Common common;
    private static final Scanner scanner = new Scanner(System.in);

    public AdminMenu(Common common) {
        this.common = common;
    }

    public void show() {
        System.out.println("\nSistem yönetimine hoşgeldiniz, " + userService.getCurrentUser().getName());

        loops:
        while (true) {
            common.menuHeader();
            System.out.println("1- Kullanıcılar");
            System.out.println("2- Kullanıcı Sil");
            common.menuFooter();

            switch (scanner.nextLine()) {
                case "1":
                    common.showUsers();
                    common.backwardMenu();
                    break;
                case "2":
                    System.out.println("\nTüm Kullanıcıların Listesi:");
                    common.showUsers();

                    System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz:");
                    if (userService.deleteUserByIndex(Integer.parseInt(scanner.nextLine()))) {
                        System.out.println("\nKullanıcı başarıyla silindi");
                        common.backwardMenu();
                    } else {
                        System.out.println("\nHata: Kullanıcı silinemedi.");
                    }
                    break;
                case "o":
                    common.logoutUser();
                    common.menuSelector();
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
