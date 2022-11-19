package org.example.app.ui;

import org.example.app.App;

import java.util.Objects;

import static org.example.app.App.*;

public class AdminMenu {

    public static void show() {
        System.out.println("\nSistem yönetimine hoşgeldiniz, " + currentUser.getName());

        while (true) {
            Ui.showHeader();
            System.out.println("1- Kullanıcılar");
            System.out.println("2- Kullanıcı Sil");
            Ui.showFooter();

            String input = Ui.getInput(null);
            if (Objects.equals(input, "1")) {
                Ui.showUsers();
                Ui.showBackward();
                break;
            } else if (Objects.equals(input, "2")) {
                System.out.println("\nTüm Kullanıcıların Listesi:");
                Ui.showUsers();

                System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz:");
                userService.deleteUser(Long.parseLong(Ui.getInput(null)));
                System.out.println("\nKullanıcı başarıyla silindi");
                Ui.showBackward();
                break;
            } else if (Objects.equals(input, "o")) {
                App.logoutUser(currentUser);
                Ui.showMenu();
                break;
            } else if (Objects.equals(input, "ç")) {
                break;
            } else {
                System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
            }
        }
    }
}
