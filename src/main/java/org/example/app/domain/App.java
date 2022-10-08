package org.example.app.domain;

import org.example.app.enums.UserTypes;
import org.example.app.utils.Dates;
import org.example.db.Database;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class App {

    private static UserService userService;
    private static ExpenseService expenseService;

    public static void main(String[] args) throws ParseException {

        Database database = new Database();
        setInitialValues(database);

        userService = new UserService(database);
        expenseService = new ExpenseService(database);

        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");

        userService.setCurrentUser(null);

        menuSelector();

        System.out.println("\nUygulama başarıyla kapatıldı.");

    }

    public static void setInitialValues(Database database){

        User adminUser = new User();
        adminUser.setId(0);
        adminUser.setType(UserTypes.ADMIN);
        adminUser.setName("admin");
        adminUser.setSurname("admin");
        adminUser.setEmail("admin@admin.com");
        adminUser.setPassword("admin");
        database.getUserList().add(adminUser);

        User customerUser = new User();
        customerUser.setId(1);
        customerUser.setType(UserTypes.CUSTOMER);
        customerUser.setName("customer1");
        customerUser.setSurname("customer");
        customerUser.setEmail("123");
        customerUser.setPassword("123");
        database.getUserList().add(customerUser);

    }

    public static void menuSelector() throws ParseException {

        if (userService.getCurrentUser() != null) {
            if (Objects.equals(userService.getCurrentUser().getType(), UserTypes.ADMIN)) {
                showAdminMenu();
            } else if (Objects.equals(userService.getCurrentUser().getType(), UserTypes.CUSTOMER)) {
                showCustomerMenu();
            }
        } else {
            showMainMenu();
        }

    }

    public static void showMainMenu() throws ParseException {

        Scanner scanner = new Scanner(System.in);

        menuHeader();
        System.out.println("1- Giriş Yap");
        System.out.println("2- Kaydol");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                String email;
                String password;

                while (true) {

                    System.out.println("\nEpostanızı giriniz:");
                    email = scanner.nextLine();

                    System.out.println("\nŞifrenizi giriniz:");
                    password = scanner.nextLine();

                    if (userService.login(email, password)) {
                        System.out.println("\nBaşarıyla kullanıcı girişi yapıldı.");
                        break;
                    } else {
                        System.out.println("\nHata: Sistemde böyle bir kullanıcı bulunmuyor.");
                    }

                }
                menuSelector();
                break;
            case 2:

                User newUser;
                String retypedPassword;

                while (true) {

                    newUser = new User();

                    newUser.setType(UserTypes.CUSTOMER);

                    System.out.println("\nAdınızı giriniz: ");
                    newUser.setName(scanner.nextLine());

                    System.out.println("\nSoyadınızı giriniz: ");
                    newUser.setSurname(scanner.nextLine());

                    System.out.println("\nEposta adresinizi giriniz: ");
                    newUser.setEmail(scanner.nextLine());

                    while (true) {

                        System.out.println("\nŞifrenizi giriniz:");
                        newUser.setPassword(scanner.nextLine());

                        System.out.println("\nŞifrenizi tekrar giriniz:");
                        retypedPassword = scanner.nextLine();

                        if (userService.checkPasswords(newUser.getPassword(), retypedPassword)) {
                            break;
                        } else {
                            System.out.println("\nHata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz.");
                        }

                    }

                    if (userService.register(newUser, retypedPassword)) {
                        System.out.println("\nKullanıcı kaydı başarıyla gerçekleşti.");
                        break;
                    } else {
                        System.out.println("\nHata kullanıcı kaydı oluşturulamadı.");
                    }

                }
                menuSelector();
                break;
        }

    }

    public static void showAdminMenu() throws ParseException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSistem yönetimine hoşgeldiniz, " + userService.getCurrentUser().getName());

        menuHeader();
        System.out.println("1- Kullanıcılar");
        System.out.println("2- Kullanıcı Sil");
        System.out.println("3- Oturumu Kapat");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                showAllUsers();
                break;
            case 2:
                showAllUsers();

                System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz: ");
                if (userService.deleteUser(Integer.parseInt(scanner.nextLine()))) {
                    System.out.println("\nKullanıcı başarıyla silindi.");
                    showAllUsers();
                } else {
                    System.out.println("\nHata: Kullanıcı silinemedi.");
                }
                break;
            case 3:
                logoutUser();
                System.out.println("\nOturum başarıyla kapatıldı.");
                break;
        }

    }

    public static void showCustomerMenu() throws ParseException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nHoşgeldiniz, " + userService.getCurrentUser().getName());

        menuHeader();
        System.out.println("1- Harcamalarım");
        System.out.println("2- Harcama Ekle");
        System.out.println("3- Harcama Düzenle");
        System.out.println("4- Harcama Sil");
        System.out.println("5- Harcama Kategorileri");
        System.out.println("6- Oturumu Kapat");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                showAllExpenses();
                break;
            case 2:

                while (true) {

                    Expense newExpense = null;

                    newExpense = new Expense();

                    newExpense.setUserId(userService.getCurrentUser().getId());

                    System.out.println("\nHarcama Adını giriniz: ");
                    newExpense.setName(scanner.nextLine());

                    System.out.println("\nHarcama Miktarını giriniz: Örneğin: " + 100.0);
                    newExpense.setAmount(Double.parseDouble(scanner.nextLine()));

                    String formattedDate = Dates.formatter.format(new Date());
                    System.out.println("\nHarcama Tarihini giriniz: Örneğin: " + formattedDate);
                    newExpense.setDate(Dates.formatter.parse(scanner.nextLine()));

                    System.out.println("\nHarcama Kategorisi giriniz: (İsteğe bağlı) ");
                    newExpense.setCategory(scanner.nextLine());

                    if (expenseService.addExpense(newExpense)) {
                        System.out.println("\nHarcama kaydı başarıyla gerçekleşti.");
                        break;
                    } else {
                        System.out.println("\nHata Harcama kaydı oluşturulamadı.");
                    }

                }
                menuSelector();
                break;
            case 3:

                while (true) {

                    showAllExpenses();

                    Expense editedExpense = new Expense();

                    System.out.println("\nDüzenlemek istediğiniz harcama ID yi giriniz: ");
                    Expense selectedExpense = expenseService.getExpenseById(Integer.parseInt(scanner.nextLine())-1);

                    System.out.println("\nYeni Harcama Adını giriniz: (" + selectedExpense.getName() + ")");
                    editedExpense.setName(scanner.nextLine());

                    System.out.println("\nYeni Harcama Miktarını giriniz: (" + selectedExpense.getAmount() + ")");
                    editedExpense.setAmount(Double.parseDouble(scanner.nextLine()));

                    System.out.println("\nYeni Harcama Tarihini giriniz: (" + Dates.formatter.format(selectedExpense.getDate()) + ")");
                    editedExpense.setDate(Dates.formatter.parse(scanner.nextLine()));

                    System.out.println("\nYeni Harcama Kategorisi giriniz: (" + selectedExpense.getCategory() + ")");
                    editedExpense.setCategory(scanner.nextLine());

                    if (expenseService.editExpense((Integer.parseInt(scanner.nextLine())-1), editedExpense)) {
                        System.out.println("\nHarcama başarıyla düzenlendi.");
                        showAllExpenses();
                        break;
                    } else {
                        System.out.println("\nHata: Harcama düzenlenemedi.");
                    }
                }
                break;
            case 4:
                showAllExpenses();
                System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz: ");
                if (expenseService.deleteExpense(Integer.parseInt(scanner.nextLine()) - 1)) {
                    System.out.println("\nHarcama başarıyla silindi.");
                    showAllExpenses();
                } else {
                    System.out.println("\nHata: Harcama silinemedi.");
                }
                break;
            case 6:
                logoutUser();
                break;
        }
    }

    public static void menuHeader() {

        System.out.println("");

    }

    public static void menuFooter() {

        System.out.println("0- Çıkış Yap");
        System.out.print("\nLütfen bir menü numarası giriniz: ");

    }

    public static void logoutUser() throws ParseException {

        if (userService.logout()) {
            System.out.println("\nOturum başarıyla kapatılmıştır.");
            menuSelector();
        } else {
            System.out.println("\nHata: Oturum kapatılamadı.");
        }

    }

    public static void showAllUsers() {

        System.out.println("\nTüm Kullanıcı Listesi:");

        List<User> allUsersList = userService.getAllUsers();
        int i;
        for (i = 0; i < allUsersList.size(); i++) {
            System.out.println("Kullanıcı ID: " + allUsersList.get(i).getId());
            System.out.println("Kullanıcı tipi: " + allUsersList.get(i).getType());
            System.out.println("Kullanıcı adı: " + allUsersList.get(i).getName());
            System.out.println("Kullanıcı soyadı: " + allUsersList.get(i).getSurname());
            System.out.println("Kullanıcı email adresi: " + allUsersList.get(i).getEmail());
            System.out.println("");
        }

    }

    public static void showAllExpenses() {

        System.out.println("\nTüm Harcama Listesi:");

        List<Expense> allExpensesList = expenseService.getAllExpensesOfCurrentUser();
        int i;
        for (i = 0; i < allExpensesList.size(); i++) {
            System.out.println("Harcama ID: " + (allExpensesList.get(i).getId()+1));
            System.out.println("Harcama adı: " + allExpensesList.get(i).getName());
            System.out.println("Harcama miktarı: " + allExpensesList.get(i).getAmount());
            System.out.println("Harcama tarihi: " + Dates.formatter.format(allExpensesList.get(i).getDate()));
            System.out.println("Harcama kategorisi: " + allExpensesList.get(i).getCategory());
            System.out.println("");
        }

    }

}