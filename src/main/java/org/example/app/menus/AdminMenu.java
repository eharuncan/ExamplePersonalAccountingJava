package org.example.app.menus;

import org.example.app.App;

import java.util.Objects;

import static org.example.app.App.*;

public class AdminMenu {
    private final CommonMenu commonMenu;

    public AdminMenu(CommonMenu commonMenu) {
        this.commonMenu = commonMenu;
    }

    public void show() {
        System.out.println("\nSistem yönetimine hoşgeldiniz, " + currentUser.getName());

        while (true) {
            commonMenu.showHeader();
            System.out.println("1- Kullanıcılar");
            System.out.println("2- Kullanıcı Sil");
            commonMenu.showFooter();

            String input = App.getInput(null);
            if (Objects.equals(input, "1")) {
                commonMenu.showUsers();
                commonMenu.showBackward();
                break;
            } else if (Objects.equals(input, "2")) {
                System.out.println("\nTüm Kullanıcıların Listesi:");
                commonMenu.showUsers();

                System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz:");
                userService.deleteUser(Long.parseLong(App.getInput(null)));
                System.out.println("\nKullanıcı başarıyla silindi");
                commonMenu.showBackward();
                break;
            } else if (Objects.equals(input, "o")) {
                App.logoutUser(currentUser);
                commonMenu.selector();
                break;
            } else if (Objects.equals(input, "ç")) {
                break;
            } else {
                System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
            }
        }
    }
}
