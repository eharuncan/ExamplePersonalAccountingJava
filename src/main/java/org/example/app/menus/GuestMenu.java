package org.example.app.menus;

import org.example.app.App;
import org.example.app.domain.User;

import java.util.Objects;

import static org.example.app.App.*;

public class GuestMenu {
    private final CommonMenu commonMenu;

    public GuestMenu(CommonMenu commonMenu) {
        this.commonMenu = commonMenu;
    }

    public void show() {
        System.out.println("\nHoşgeldiniz...");

        loops:
        while (true) {
            commonMenu.showHeader();
            System.out.println("1- Giriş Yap");
            System.out.println("2- Kaydol");
            commonMenu.showFooter();

            String input = App.getInput(null);
            if (Objects.equals(input, "1")) {
                String email;
                String password;
                while (true) {
                    System.out.println("\nEposta adresinizi giriniz:");
                    email = App.getInput(null);

                    System.out.println("\nŞifrenizi giriniz:");
                    password = App.getInput(null);

                    User newUser = new User("","", email, password);

                    if (App.loginUser(newUser) != null){
                        System.out.println("\nBaşarıyla kullanıcı girişi yapıldı.");
                        commonMenu.selector();
                        break loops;
                    } else {
                        System.out.println("\nHata: Kullanıcı girişi yapılamadı.");
                    }

                }
            } else if (Objects.equals(input, "2")) {
                while (true) {
                    System.out.println("\nAdınızı giriniz:");
                    String name = (App.getInput(null));

                    System.out.println("\nSoyadınızı giriniz:");
                    String surname = (App.getInput(null));

                    System.out.println("\nEposta adresinizi giriniz:");
                    String email = (App.getInput(null));

                    System.out.println("\nŞifrenizi giriniz:");
                    String password = (App.getInput(null));

                    System.out.println("\nŞifrenizi tekrar giriniz:");
                    String retypedPassword = (App.getInput(null));

                    User newUser = new User(name, surname, email, password );

                    if (App.checkPasswords(password, retypedPassword)) {
                        User registeredUser = userService.register(newUser);
                        if (registeredUser != null) {
                            currentUser = registeredUser;
                            System.out.println("\nKullanıcı kaydı başarıyla gerçekleşti.");
                            commonMenu.selector();
                            break loops;
                        } else {
                            System.out.println("\nHata: Kullanıcı kaydı oluşturulamadı.");
                        }
                    }
                }
            } else if (Objects.equals(input, "ç")) {
                break;
            } else {
                System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
            }
        }
    }
}
