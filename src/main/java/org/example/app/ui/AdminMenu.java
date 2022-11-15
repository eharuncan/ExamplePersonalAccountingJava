package org.example.app.ui;

import java.util.Objects;

import static org.example.app.App.userService;

public class AdminMenu {
    private final Common common;

    public AdminMenu(Common common) {
        this.common = common;
    }

    public void show() {
        System.out.println("\nSistem yönetimine hoşgeldiniz, " + userService.getCurrentUser().getName());

        while (true) {
            common.menuHeader();
            System.out.println("1- Kullanıcılar");
            System.out.println("2- Kullanıcı Sil");
            common.menuFooter();

            String input = common.getInput(null);
            if (Objects.equals(input, "1")) {
                common.showUsers();
                common.backwardMenu();
                break;
            } else if (Objects.equals(input, "2")) {
                System.out.println("\nTüm Kullanıcıların Listesi:");
                common.showUsers();

                System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz:");
                if (userService.deleteUser(Long.parseLong(common.getInput(null)))) {
                    System.out.println("\nKullanıcı başarıyla silindi");
                    common.backwardMenu();
                    break;
                } else {
                    System.out.println("\nHata: Kullanıcı silinemedi.");
                }
            } else if (Objects.equals(input, "o")) {
                common.logoutUser();
                common.menuSelector();
                break;
            } else if (Objects.equals(input, "ç")) {
                break;
            } else {
                System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
            }
        }
    }
}
