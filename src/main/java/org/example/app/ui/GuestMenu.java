package org.example.app.ui;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;

import java.util.Objects;

import static org.example.app.App.userService;

import static org.example.app.utils.Screen.screenScanner;

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

            String input = screenScanner.nextLine();

            if (Objects.equals(input, "1")) {
                String email;
                String password;

                while (true) {
                    System.out.println("\nEposta adresinizi giriniz:");
                    email = screenScanner.nextLine();

                    System.out.println("\nŞifrenizi giriniz:");
                    password = screenScanner.nextLine();

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
                String typedPassword, retypedPassword;

                while (true) {
                    newUser = new User();

                    newUser.setType(UserTypes.CUSTOMER);

                    System.out.println("\nAdınızı giriniz:");
                    newUser.setName(screenScanner.nextLine());

                    System.out.println("\nSoyadınızı giriniz:");
                    newUser.setSurname(screenScanner.nextLine());

                    System.out.println("\nEposta adresinizi giriniz:");
                    newUser.setEmail(screenScanner.nextLine());

                    while (true) {
                        System.out.println("\nŞifrenizi giriniz:");
                        typedPassword = screenScanner.nextLine();

                        System.out.println("\nŞifrenizi tekrar giriniz:");
                        retypedPassword = screenScanner.nextLine();

                        if (common.checkPasswords(typedPassword, retypedPassword)) {
                            break;
                        } else {
                            System.out.println("\nHata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz.");
                        }
                    }
                    newUser.setPassword(typedPassword);

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
