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
    private static final Scanner scanner = new Scanner(System.in);

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

    private static void setInitialValues(Database database){

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

    private static void menuSelector() throws ParseException {

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

    private static void showMainMenu() throws ParseException {

        menuHeader();
        System.out.println("1- Giriş Yap");
        System.out.println("2- Kaydol");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                String email;
                String password;

                while (true) {

                    System.out.println("\nEposta adresinizi giriniz:");
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

    private static void showAdminMenu() throws ParseException {

        System.out.println("\nSistem yönetimine hoşgeldiniz, " + userService.getCurrentUser().getName());

        menuHeader();
        System.out.println("1- Kullanıcılar");
        System.out.println("2- Kullanıcı Sil");
        System.out.println("3- Oturumu Kapat");
        menuFooter();

        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                showUsers();
                backwardMenu();
                break;
            case 2:
                showUsers();

                System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz: ");
                if (userService.deleteUser(Integer.parseInt(scanner.nextLine()))) {
                    System.out.println("\nKullanıcı başarıyla silindi");
                    backwardMenu();
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

    private static void showCustomerMenu() throws ParseException {

        System.out.println("\nHoşgeldiniz, " + userService.getCurrentUser().getName());

        System.out.println("\nBugünkü toplam harcamanız: " + expenseService.getSumOfExpensesOfDate(new Date()) + " TL");

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
                showExpenses();
                backwardMenu();
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

                    System.out.println("\nHarcama Kategorisini seçiniz: (İsteğe bağlı) ");
                    showUserExpenseCategories();
                    System.out.print("\nSeçiminiz: ");
                    int index = (Integer.parseInt(scanner.nextLine()) -1);
                    newExpense.setCategory(userService.getExpenseCategoryByIndex(index));

                    if (expenseService.addExpense(newExpense)) {
                        System.out.println("\nHarcama başarıyla kaydedildi.");
                        break;
                    } else {
                        System.out.println("\nHata Harcama kaydı oluşturulamadı.");
                    }

                }
                backwardMenu();
                break;
            case 3:

                while (true) {

                    showExpenses();

                    System.out.println("\nDüzenlemek istediğiniz harcama ID yi giriniz: ");
                    Expense selectedExpense = expenseService.getExpenseById(Integer.parseInt(scanner.nextLine())-1);

                    Expense editedExpense;

                    editedExpense  = selectedExpense;

                    System.out.println("\nYeni Harcama Adını giriniz: (" + selectedExpense.getName() + ")");
                    editedExpense.setName(scanner.nextLine());

                    System.out.println("\nYeni Harcama Miktarını giriniz: (" + selectedExpense.getAmount() + ")");
                    editedExpense.setAmount(Double.parseDouble(scanner.nextLine()));

                    System.out.println("\nYeni Harcama Tarihini giriniz: (" + Dates.formatter.format(selectedExpense.getDate()) + ")");
                    editedExpense.setDate(Dates.formatter.parse(scanner.nextLine()));

                    System.out.println("\nYeni Harcama Kategorisi giriniz: (" + selectedExpense.getCategory() + ")");
                    editedExpense.setCategory(scanner.nextLine());

                    if (expenseService.editExpense(selectedExpense.getId(), editedExpense)) {
                        System.out.println("\nHarcama başarıyla düzenlendi.");
                        break;
                    } else {
                        System.out.println("\nHata: Harcama düzenlenemedi.");
                    }
                }
                backwardMenu();
                break;
            case 4:
                showExpenses();
                System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz: ");
                if (expenseService.deleteExpense(Integer.parseInt(scanner.nextLine()) - 1)) {
                    System.out.println("\nHarcama başarıyla silindi.");
                } else {
                    System.out.println("\nHata: Harcama silinemedi.");
                }
                backwardMenu();
                break;
            case 6:
                logoutUser();
                break;
        }
    }

    private static void menuHeader() {

        System.out.println("");

    }

    private static void menuFooter() {

        System.out.println("0- Çıkış Yap");
        System.out.print("\nLütfen bir menü numarası giriniz: ");

    }

    private static void backwardMenu() throws ParseException {

        loops:
        while (true){
            menuHeader();
            System.out.println("1- Geri Dön");
            menuFooter();
            switch(Integer.parseInt(scanner.nextLine())){
                case 1:
                    menuSelector();
                    break loops;
                case 0:
                    break loops;
            }

        }

    }

    private static void logoutUser() throws ParseException {

        if (userService.logout()) {
            System.out.println("\nOturum başarıyla kapatılmıştır.");
            menuSelector();
        } else {
            System.out.println("\nHata: Oturum kapatılamadı.");
        }

    }

    private static void recordNotFound(){
        System.out.println("\nHerhangi bir kayıt bulunamadı");
    }

    private static void showUsers() {

        System.out.println("\nTüm Kullanıcıların Listesi:");

        List<User> userList = userService.getUsers();
        if (userList.size() == 0)
        {
            recordNotFound();
        }else {
            int i;
            for (i = 0; i < userList.size(); i++) {
                System.out.println("\nKullanıcı ID: " + userList.get(i).getId());
                System.out.println("Kullanıcı tipi: " + userList.get(i).getType());
                System.out.println("Kullanıcı adı: " + userList.get(i).getName());
                System.out.println("Kullanıcı soyadı: " + userList.get(i).getSurname());
                System.out.println("Kullanıcı eposta adresi: " + userList.get(i).getEmail());
            }
        }

    }

    private static void showExpenses() {

        System.out.println("\nTüm Harcamalarının Listesi:");

        List<Expense> expenseList = expenseService.getExpensesOfCurrentUser();
        if (expenseList.size() == 0)
        {
            recordNotFound();
        }else {
            int i;
            for (i = 0; i < expenseList.size(); i++) {
                System.out.println("\nHarcama ID: " + (expenseList.get(i).getId() + 1));
                System.out.println("Harcama adı: " + expenseList.get(i).getName());
                System.out.println("Harcama miktarı: " + expenseList.get(i).getAmount());
                System.out.println("Harcama tarihi: " + Dates.formatter.format(expenseList.get(i).getDate()));
                System.out.println("Harcama kategorisi: " + expenseList.get(i).getCategory());
            }
        }

    }

    private static void showUserExpenseCategories(){

        System.out.print("\n");
        List<String> expenseCategoryList = userService.getExpenseCategoryListByUser(userService.getCurrentUser());
        if (expenseCategoryList.size() == 0)
        {
            recordNotFound();
        }else {
            int i;
            for (i = 0; i < expenseCategoryList.size(); i++) {
                System.out.println("" + (i+1) + "- " + expenseCategoryList.get(i));
            }
        }

    }

}