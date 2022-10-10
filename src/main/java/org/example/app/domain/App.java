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

        userService = new UserService(database);
        expenseService = new ExpenseService(database);

        System.out.println("\nŞAHSİ MUHASEBEM - HARCAMALARINIZI TAKİP EDİN !");

        userService.setCurrentUser(null);

        setInitialValues(database);

        menuSelector();

        System.out.println("\nUygulama başarıyla kapatıldı.");
    }

    private static void setInitialValues(Database database) {
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
        customerUser.setExpenseCategoryList(userService.getDefaultExpenseCategoryList());
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

        loops:
        while (true) {
            menuHeader();
            System.out.println("1- Giriş Yap");
            System.out.println("2- Kaydol");
            menuFooter();

            switch (scanner.nextLine()) {
                case "1":
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
                case "2":
                    User newUser;
                    String retypedPassword;

                    while (true) {
                        newUser = new User();

                        newUser.setType(UserTypes.CUSTOMER);

                        System.out.println("\nAdınızı giriniz:");
                        newUser.setName(scanner.nextLine());

                        System.out.println("\nSoyadınızı giriniz:");
                        newUser.setSurname(scanner.nextLine());

                        System.out.println("\nEposta adresinizi giriniz:");
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
                            System.out.println("\nHata: kullanıcı kaydı oluşturulamadı.");
                        }
                    }
                    menuSelector();
                    break;
                case "ç":
                    break loops;
                default:
                    System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
                    break;
            }
        }
    }

    private static void showAdminMenu() throws ParseException {
        System.out.println("\nSistem yönetimine hoşgeldiniz, " + userService.getCurrentUser().getName());

        loops:
        while (true) {
            menuHeader();
            System.out.println("1- Kullanıcılar");
            System.out.println("2- Kullanıcı Sil");
            menuFooter();

            switch (scanner.nextLine()) {
                case "1":
                    showUsers();
                    backwardMenu();
                    break;
                case "2":
                    System.out.println("\nTüm Kullanıcıların Listesi:");
                    showUsers();

                    System.out.println("\nSilmek istediğiniz Kullanıcı ID yi giriniz:");
                    if (userService.deleteUser(Integer.parseInt(scanner.nextLine()))) {
                        System.out.println("\nKullanıcı başarıyla silindi");
                        backwardMenu();
                    } else {
                        System.out.println("\nHata: Kullanıcı silinemedi.");
                    }
                    break;
                case "o":
                    logoutUser();
                    menuSelector();
                    break;
                case "ç":
                    break loops;
                default:
                    System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
                    break;
            }
        }
    }

    private static void showCustomerMenu() throws ParseException {
        try {

            System.out.println("\nHoşgeldiniz, " + userService.getCurrentUser().getName());
            System.out.println("\nBugünkü toplam harcamanız: " + expenseService.getSumOfExpensesOfDate(userService.getCurrentUser().getId(), new Date()) + " TL");

            loops:
            while (true) {
                menuHeader();
                System.out.println("1- Harcamalarım");
                System.out.println("2- Harcama Ekle");
                System.out.println("3- Harcama Düzenle");
                System.out.println("4- Harcama Sil");
                System.out.println("5- Harcama Kategorilerim");
                menuFooter();

                switch (scanner.nextLine()) {
                    case "1":
                        showUserExpenses(userService.getCurrentUser().getId());
                        backwardMenu();
                        break;
                    case "2":
                        while (true) {
                            Expense newExpense = null;

                            newExpense = new Expense();

                            newExpense.setUserId(userService.getCurrentUser().getId());

                            System.out.println("\nHarcama Adını giriniz:");
                            newExpense.setName(scanner.nextLine());

                            System.out.println("\nHarcama Miktarını giriniz: Örneğin: " + 100.0);
                            newExpense.setAmount(Double.parseDouble(scanner.nextLine()));

                            String formattedDate = Dates.formatter.format(new Date());
                            System.out.println("\nHarcama Tarihini giriniz: Örneğin: " + formattedDate);
                            newExpense.setDate(Dates.formatter.parse(scanner.nextLine()));

                            System.out.println("\nHarcama Kategorisini seçiniz: (İsteğe bağlı)");
                            showUserExpenseCategories(userService.getCurrentUser().getId());
                            System.out.print("\nSeçiminiz: ");
                            int index = (Integer.parseInt(scanner.nextLine()) - 1);
                            newExpense.setCategory(userService.getExpenseCategoryByIndex(index));

                            if (expenseService.addExpenseByUserId(userService.getCurrentUser().getId(), newExpense)) {
                                System.out.println("\nHarcama başarıyla kaydedildi.");
                                break;
                            } else {
                                System.out.println("\nHata: Harcama kaydı oluşturulamadı.");
                            }

                        }
                        backwardMenu();
                        break;
                    case "3":
                        while (true) {
                            System.out.println("\nTüm Harcamalarının Listesi:");
                            showUserExpenses(userService.getCurrentUser().getId());

                            System.out.println("\nDüzenlemek istediğiniz harcama ID yi giriniz:");
                            Expense selectedExpense = expenseService.getExpenseByUserIdAndExpenseId(userService.getCurrentUser().getId(), Integer.parseInt(scanner.nextLine()) - 1);

                            Expense editedExpense;

                            editedExpense = selectedExpense;

                            System.out.println("\nYeni Harcama Adını giriniz: (" + selectedExpense.getName() + ")");
                            editedExpense.setName(scanner.nextLine());

                            System.out.println("\nYeni Harcama Miktarını giriniz: (" + selectedExpense.getAmount() + ")");
                            editedExpense.setAmount(Double.parseDouble(scanner.nextLine()));

                            System.out.println("\nYeni Harcama Tarihini giriniz: (" + Dates.formatter.format(selectedExpense.getDate()) + ")");
                            editedExpense.setDate(Dates.formatter.parse(scanner.nextLine()));

                            System.out.println("\nYeni Harcama Kategorisi seçiniz: (" + selectedExpense.getCategory() + ")");
                            showUserExpenseCategories(userService.getCurrentUser().getId());
                            System.out.print("\nSeçiminiz: ");
                            int index = (Integer.parseInt(scanner.nextLine()) - 1);
                            editedExpense.setCategory(userService.getExpenseCategoryByIndex(index));

                            if (expenseService.editExpense(userService.getCurrentUser().getId(), selectedExpense.getId(), editedExpense)) {
                                System.out.println("\nHarcama başarıyla düzenlendi.");
                                break;
                            } else {
                                System.out.println("\nHata: Harcama düzenlenemedi.");
                            }
                        }
                        backwardMenu();
                        break;
                    case "4":
                        System.out.println("\nTüm Harcamalarının Listesi:");
                        showUserExpenses(userService.getCurrentUser().getId());
                        System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz:");
                        if (expenseService.deleteExpenseByUserId(userService.getCurrentUser().getId(), Integer.parseInt(scanner.nextLine()) - 1)) {
                            System.out.println("\nHarcama başarıyla silindi.");
                        } else {
                            System.out.println("\nHata: Harcama silinemedi.");
                        }
                        backwardMenu();
                        break;
                    case "5":
                        menuHeader();
                        System.out.println("1- Kategorilerim");
                        System.out.println("2- Kategori Ekle");
                        System.out.println("3- Kategori Sil");
                        menuFooter();

                        switch (scanner.nextLine()) {
                            case "1":
                                System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                                showUserExpenseCategories(userService.getCurrentUser().getId());
                                backwardMenu();
                                break;
                            case "2":
                                System.out.println("\nHarcama Kategorisi Adını giriniz:");
                                String newExpenseCategory = scanner.nextLine();
                                userService.getCurrentUser().getExpenseCategoryList().add(newExpenseCategory);
                                System.out.println("\nHarcama Kategorisi başarıyla kaydedildi.");
                                backwardMenu();
                                break;
                            case "3":
                                break;
                            case "g":
                                menuSelector();
                                break;
                            case "o":
                                logoutUser();
                                menuSelector();
                            case "ç":
                                break loops;
                        }
                    case "g":
                        menuSelector();
                        break;
                    case "o":
                        logoutUser();
                        menuSelector();
                    case "ç":
                        break loops;
                    default:
                        System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void menuHeader() {
        System.out.print("\n");
    }

    private static void menuFooter() {
        System.out.print("\n");
        if (userService.getCurrentUser() != null) {
            System.out.println("o- Oturumu Kapat");
        }
        System.out.println("ç- Çıkış Yap");
        System.out.print("\nLütfen bir menü numarası giriniz: ");
    }

    private static void backwardMenu() throws ParseException {
        loops:
        while (true) {
            System.out.println("\ng- Geri Dön");
            menuFooter();
            switch (scanner.nextLine()) {
                case "g":
                    menuSelector();
                    break loops;
                case "o":
                    logoutUser();
                    menuSelector();
                case "ç":
                    break loops;
                default:
                    System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
                    break;
            }
        }
    }

    private static void logoutUser() throws ParseException {
        if (userService.logout()) {
            System.out.println("\nOturum başarıyla kapatıldı.");
        } else {
            System.out.println("\nHata: Oturum kapatılamadı.");
        }
    }

    private static void recordNotFound() {
        System.out.println("\nHerhangi bir kayıt bulunamadı.");
    }

    private static void showUsers() {
        List<User> userList = userService.getUsers();
        if (userList.size() == 0) {
            recordNotFound();
        } else {
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

    private static void showUserExpenses(Integer userId) {
        List<Expense> expenseList = expenseService.getExpensesByUserId(userId);
        if (expenseList.size() == 0) {
            recordNotFound();
        } else {
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

    private static void showUserExpenseCategories(Integer userId) {
        System.out.print("\n");
        List<String> expenseCategoryList = userService.getUserById(userId).getExpenseCategoryList();
        if (expenseCategoryList.size() == 0) {
            recordNotFound();
        } else {
            int i;
            for (i = 0; i < expenseCategoryList.size(); i++) {
                System.out.println("" + (i + 1) + "- " + expenseCategoryList.get(i));
            }
        }
    }
}