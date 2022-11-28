package org.example.app.ui;

import org.example.app.App;
import org.example.app.domain.Expense;
import org.example.app.domain.ExpenseCategory;
import org.example.app.domain.User;

import java.util.Date;
import java.util.Objects;

import static org.example.app.App.*;
import static org.example.app.utils.Utils.DATE_FORMATTER;

public class CustomerMenu {

    public static void show() {
        try {
            System.out.println("\nHoşgeldiniz, " + currentUser.getName());
            System.out.println("\nBugün toplam harcamanız: " + expenseService.getSumOfUserExpensesOfDay(currentUser.getId(), new java.util.Date()) + " TL");
            System.out.println("Bu Ay toplam harcamanız: " + expenseService.getSumOfUserExpensesOfMonth(currentUser.getId(), new java.util.Date()) + " TL");
            System.out.println("Bu Yıl toplam harcamanız: " + expenseService.getSumOfUserExpensesOfYear(currentUser.getId(), new java.util.Date()) + " TL");
            loops:
            while (true) {
                Ui.showHeader();
                System.out.println("0- Profilim");
                System.out.println("1- Profilimi Düzenle");
                System.out.println("2- Harcamalarım");
                System.out.println("3- Harcama Ekle");
                System.out.println("4- Harcama Düzenle");
                System.out.println("5- Harcama Sil");
                System.out.println("6- Kategorilerim");
                System.out.println("7- Kategori Ekle");
                System.out.println("8- Kategori Düzenle");
                System.out.println("9- Kategori Sil");
                Ui.showFooter();

                String input = Ui.getInput(null);
                if (Objects.equals(input, "0")) {
                    Ui.showUserProfile(currentUser);
                    Ui.showBackward();
                    break;
                } else if (Objects.equals(input, "1")) {
                    while (true) {
                        System.out.println("\nProfil Bilgilerin:");
                        Ui.showUserProfile(currentUser);

                        System.out.println("\nYeni adınızı giriniz: (" + currentUser.getName() + ")");
                        String editedName = (Ui.getInput(currentUser.getName()));

                        System.out.println("\nYeni soyadınızı giriniz: (" + currentUser.getSurname() + ")");
                        String editedSurname = (Ui.getInput(currentUser.getSurname()));

                        System.out.println("\nYeni e-posta adresinizi giriniz: (" + currentUser.getEmail() + ")");
                        String editedEmail = (Ui.getInput(currentUser.getEmail()));

                        System.out.println("\nYeni şifrenizi giriniz:");
                        String editedPassword = (Ui.getInput(currentUser.getPassword()));

                        System.out.println("\nYeni şifrenizi tekrar giriniz:");
                        String retypedPassword = (Ui.getInput(currentUser.getPassword()));

                        User newUser = new User(editedName, editedSurname, editedEmail, editedPassword);

                        if (App.checkPasswords(editedPassword, retypedPassword)) {
                            if (userService.editUser(newUser, currentUser.getId()) != null) {
                                currentUser = newUser;
                                System.out.println("\nProfilin başarıyla güncellendi.");
                                Ui.showBackward();
                                break loops;
                            } else {
                                System.out.println("\nHata: Profilin güncellenemedi.");
                            }
                        }
                    }
                } else if (Objects.equals(input, "2")) {
                    Ui.showUserExpenses(currentUser);
                    Ui.showBackward();
                    break;
                } else if (Objects.equals(input, "3")) {
                    while (true) {

                        System.out.println("\nHarcama Adını giriniz:");
                        String name = (Ui.getInput(null));

                        System.out.println("\nHarcama Miktarını giriniz: Örneğin: " + 100.0);
                        Double amount = (Double.parseDouble(Ui.getInput(null)));

                        String formattedDate = DATE_FORMATTER.format(new java.util.Date());
                        System.out.println("\nHarcama Tarihini giriniz: Örneğin: " + formattedDate + " Bugünü seçmek için boş bırakınız.");
                        Date date = (DATE_FORMATTER.parse(Ui.getInput(formattedDate)));

                        System.out.println("\nHarcama Kategorisini seçiniz: (İsteğe bağlı)");
                        Ui.showUserExpenseCategories(currentUser);
                        System.out.print("\nSeçiminiz: ");
                        Long categoryId = ((Long.parseLong(Ui.getInput(null))));

                        Expense newExpense = new Expense(currentUser.getId(), name, amount, date, categoryId);

                        if (expenseService.addExpenseOfUser(newExpense) != null) {
                            System.out.println("\nHarcama başarıyla kaydedildi.");
                            Ui.showBackward();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama kaydı oluşturulamadı.");
                        }
                    }
                } else if (Objects.equals(input, "4")) {
                    while (true) {
                        System.out.println("\nTüm Harcamalarının Listesi:");
                        Ui.showUserExpenses(currentUser);

                        System.out.println("\nDüzenlemek istediğiniz harcama ID yi giriniz:");
                        Long selectedExpenseId = Long.parseLong(Ui.getInput(null));
                        Long currentUserId = currentUser.getId();

                        System.out.println("\nYeni Harcama Adını giriniz: (" + Ui.getInput(expenseService.getExpenseById(selectedExpenseId).getName()) + ")");
                        String editedName = (Ui.getInput(expenseService.getExpenseById(selectedExpenseId).getName()));

                        System.out.println("\nYeni Harcama Miktarını giriniz: (" + Ui.getInput(expenseService.getExpenseById(selectedExpenseId).getAmount()) + ")");
                        Double editedAmount = (Double.parseDouble(Ui.getInput(Ui.getInput(expenseService.getExpenseById(selectedExpenseId).getAmount()))));

                        System.out.println("\nYeni Harcama Tarihini giriniz: (" + DATE_FORMATTER.format(Ui.getInput(expenseService.getExpenseById(selectedExpenseId).getDate())) + ")");
                        Date editedDate = (DATE_FORMATTER.parse(Ui.getInput(Ui.getInput(expenseService.getExpenseById(selectedExpenseId).getDate()))));

                        System.out.println("\nYeni Harcama Kategorisi seçiniz: (" + Ui.getInput(expenseService.getExpenseById(selectedExpenseId).getCategoryId()) + ")");
                        Ui.showUserExpenseCategories(currentUser);
                        System.out.print("\nSeçiminiz: ");
                        Long editedCategoryId = (Long.parseLong(Ui.getInput(null)));

                        Expense newExpense = new Expense(currentUserId, editedName, editedAmount, editedDate, editedCategoryId);

                        if (expenseService.editExpenseOfUser(newExpense, selectedExpenseId) != null) {
                            System.out.println("\nHarcama başarıyla düzenlendi.");
                            Ui.showBackward();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama düzenlenemedi.");
                        }
                    }
                } else if (Objects.equals(input, "5")) {
                    System.out.println("\nTüm Harcamalarının Listesi:");
                    Ui.showUserExpenses(currentUser);
                    System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz:");
                    expenseService.deleteExpenseOfUser(Long.parseLong(Ui.getInput(null)));
                    System.out.println("\nHarcama başarıyla silindi.");
                    Ui.showBackward();
                    break;
                } else if (Objects.equals(input, "6")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    Ui.showUserExpenseCategories(currentUser);
                    Ui.showBackward();
                    break;
                } else if (Objects.equals(input, "7")) {
                    System.out.println("\nHarcama Kategorisi Adını giriniz:");
                    String name = Ui.getInput(null);
                    ExpenseCategory newExpenseCategory = new ExpenseCategory(currentUser.getId(), name);

                    if (expenseCategoryService.addExpenseCategory(newExpenseCategory) != null) {
                        System.out.println("\nHarcama Kategorisi başarıyla kaydedildi.");
                    } else {
                        System.out.println("\nHata: Harcama Kategorisi eklenemedi.");
                    }
                    Ui.showBackward();
                    break;
                } else if (Objects.equals(input, "8")) {
                    while (true) {
                        System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                        Ui.showUserExpenseCategories(currentUser);

                        System.out.println("\nDüzenlemek istediğiniz kategori ID yi giriniz:");
                        long selectedExpenseCategoryId = Long.parseLong(Ui.getInput(null));
                        long currentUserId = currentUser.getId();

                        System.out.println("\nYeni Kategori Adını giriniz: (" + expenseCategoryService.getExpenseCategory(selectedExpenseCategoryId).getName() + ")");
                        String editedName = (Ui.getInput(expenseCategoryService.getExpenseCategory(selectedExpenseCategoryId).getName()));

                        ExpenseCategory newExpenseCategory = new ExpenseCategory(currentUserId, editedName);

                        if (expenseCategoryService.editExpenseCategory(newExpenseCategory, selectedExpenseCategoryId) != null) {
                            System.out.println("\nHarcama başarıyla düzenlendi.");
                            Ui.showBackward();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama düzenlenemedi.");
                        }
                    }
                } else if (Objects.equals(input, "9")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    Ui.showUserExpenseCategories(currentUser);
                    System.out.print("\nSilmek istediğiniz Kategori ID yi giriniz: ");
                    expenseCategoryService.deleteExpenseCategory(Long.parseLong(Ui.getInput(null)));
                    System.out.println("\nHarcama başarıyla silindi.");
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
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
