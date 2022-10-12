package org.example.app.ui;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;

import java.util.Objects;

import static org.example.app.App.userService;

public class GuestMenu {
    private final Common common;

    public GuestMenu(Common common) {
        this.common = common;
    }

    public void show() {
        System.out.println("\nHoşgeldiniz...");

        loops:
        while (true) {
            common.menuHeader();
            System.out.println("1- Giriş Yap");
            System.out.println("2- Kaydol");
            common.menuFooter();

            String input = common.getStringInput(null);

            if (Objects.equals(input, "1")) {
                String email;
                String password;

                while (true) {
                    System.out.println("\nEposta adresinizi giriniz:");
                    email = common.getStringInput(null);

                    System.out.println("\nŞifrenizi giriniz:");
                    password = common.getStringInput(null);

                    if (userService.login(email, password)) {
                        System.out.println("\nBaşarıyla kullanıcı girişi yapıldı.");
                        common.menuSelector();
                        break loops;
                    } else {
                        System.out.println("\nHata: Sistemde böyle bir kullanıcı bulunmuyor.");
                    }
                }
            } else if (Objects.equals(input, "2")) {
                User newUser;

                while (true) {
                    newUser = new User();

                    newUser.setType(UserTypes.CUSTOMER);

                    System.out.println("\nAdınızı giriniz:");
                    newUser.setName(common.getStringInput(null));

                    System.out.println("\nSoyadınızı giriniz:");
                    newUser.setSurname(common.getStringInput(null));

                    System.out.println("\nEposta adresinizi giriniz:");
                    newUser.setEmail(common.getStringInput(null));

                    newUser.setPassword(common.changePasswords(null));

                    if (userService.register(newUser)) {
                        System.out.println("\nKullanıcı kaydı başarıyla gerçekleşti.");
                        common.menuSelector();
                        break loops;
                    } else {
                        System.out.println("\nHata: kullanıcı kaydı oluşturulamadı.");
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
