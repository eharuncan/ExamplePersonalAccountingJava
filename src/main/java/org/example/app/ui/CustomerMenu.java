package org.example.app.ui;

import org.example.app.domain.Expense;
import org.example.app.domain.ExpenseCategory;
import org.example.app.domain.User;

import java.util.Date;
import java.util.Objects;

import static org.example.app.App.*;
import static org.example.app.utils.Utils.dateFormatter;

public class CustomerMenu {
    private final Common common;

    public CustomerMenu(Common common) {
        this.common = common;
    }

    public void show() {
        try {
            System.out.println("\nHoşgeldiniz, " + currentUser.getName());
            System.out.println("\nBugün toplam harcamanız: " + expenseService.getSumOfUserExpensesOfDay(currentUser.getId(), new java.util.Date()) + " TL");
            System.out.println("Bu Ay toplam harcamanız: " + expenseService.getSumOfUserExpensesOfMonth(currentUser.getId(), new java.util.Date()) + " TL");
            System.out.println("Bu Yıl toplam harcamanız: " + expenseService.getSumOfUserExpensesOfYear(currentUser.getId(), new java.util.Date()) + " TL");
            loops:
            while (true) {
                common.menuHeader();
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
                common.menuFooter();

                String input = common.getInput(null);
                if (Objects.equals(input, "0")) {
                    common.showUserProfile(currentUser);
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "1")) {
                    while (true) {
                        System.out.println("\nProfil Bilgilerin:");
                        common.showUserProfile(currentUser);

                        System.out.println("\nYeni adınızı giriniz: (" + currentUser.getName() + ")");
                        String editedName = (common.getInput(currentUser.getName()));

                        System.out.println("\nYeni soyadınızı giriniz: (" + currentUser.getSurname() + ")");
                        String editedSurname = (common.getInput(currentUser.getSurname()));

                        System.out.println("\nYeni e-posta adresinizi giriniz: (" + currentUser.getEmail() + ")");
                        String editedEmail = (common.getInput(currentUser.getEmail()));

                        System.out.println("\nYeni şifrenizi giriniz:");
                        String editedPassword = (common.getInput(currentUser.getPassword()));

                        System.out.println("\nYeni şifrenizi tekrar giriniz:");
                        String retypedPassword = (common.getInput(currentUser.getPassword()));

                        User newUser = new User(editedName, editedSurname, editedEmail, editedPassword);

                        if (common.checkPasswords(editedPassword, retypedPassword)) {
                            if (userService.editUser(newUser, currentUser.getId()) != null) {
                                currentUser = newUser;
                                System.out.println("\nProfilin başarıyla güncellendi.");
                                common.backwardMenu();
                                break loops;
                            } else {
                                System.out.println("\nHata: Profilin güncellenemedi.");
                            }
                        }
                    }
                } else if (Objects.equals(input, "2")) {
                    common.showUserExpenses(currentUser);
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "3")) {
                    while (true) {

                        System.out.println("\nHarcama Adını giriniz:");
                        String name = (common.getInput(null));

                        System.out.println("\nHarcama Miktarını giriniz: Örneğin: " + 100.0);
                        Double amount = (Double.parseDouble(common.getInput(null)));

                        String formattedDate = dateFormatter.format(new java.util.Date());
                        System.out.println("\nHarcama Tarihini giriniz: Örneğin: " + formattedDate + " Bugünü seçmek için boş bırakınız.");
                        Date date = (dateFormatter.parse(common.getInput(formattedDate)));

                        System.out.println("\nHarcama Kategorisini seçiniz: (İsteğe bağlı)");
                        common.showUserExpenseCategories(currentUser);
                        System.out.print("\nSeçiminiz: ");
                        Long categoryId = ((Long.parseLong(common.getInput(null))));

                        Expense newExpense = new Expense(currentUser.getId(), name, amount, date, categoryId);

                        if (expenseService.addExpenseOfUser(newExpense) != null) {
                            System.out.println("\nHarcama başarıyla kaydedildi.");
                            common.backwardMenu();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama kaydı oluşturulamadı.");
                        }
                    }
                } else if (Objects.equals(input, "4")) {
                    while (true) {
                        System.out.println("\nTüm Harcamalarının Listesi:");
                        common.showUserExpenses(currentUser);

                        System.out.println("\nDüzenlemek istediğiniz harcama ID yi giriniz:");
                        Long selectedExpenseId = Long.parseLong(common.getInput(null));
                        Long currentUserId = currentUser.getId();

                        System.out.println("\nYeni Harcama Adını giriniz: (" + common.getInput(expenseService.getExpenseById(selectedExpenseId).getName()) + ")");
                        String editedName = (common.getInput(expenseService.getExpenseById(selectedExpenseId).getName()));

                        System.out.println("\nYeni Harcama Miktarını giriniz: (" + common.getInput(expenseService.getExpenseById(selectedExpenseId).getAmount()) + ")");
                        Double editedAmount = (Double.parseDouble(common.getInput(common.getInput(expenseService.getExpenseById(selectedExpenseId).getAmount()))));

                        System.out.println("\nYeni Harcama Tarihini giriniz: (" + dateFormatter.format(common.getInput(expenseService.getExpenseById(selectedExpenseId).getDate())) + ")");
                        Date editedDate = (dateFormatter.parse(common.getInput(common.getInput(expenseService.getExpenseById(selectedExpenseId).getDate()))));

                        System.out.println("\nYeni Harcama Kategorisi seçiniz: (" + common.getInput(expenseService.getExpenseById(selectedExpenseId).getCategoryId()) + ")");
                        common.showUserExpenseCategories(currentUser);
                        System.out.print("\nSeçiminiz: ");
                        Long editedCategoryId = (Long.parseLong(common.getInput(null)));

                        Expense newExpense = new Expense(currentUserId, editedName, editedAmount, editedDate, editedCategoryId);

                        if (expenseService.editExpenseOfUser(newExpense, selectedExpenseId) != null) {
                            System.out.println("\nHarcama başarıyla düzenlendi.");
                            common.backwardMenu();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama düzenlenemedi.");
                        }
                    }
                } else if (Objects.equals(input, "5")) {
                    System.out.println("\nTüm Harcamalarının Listesi:");
                    common.showUserExpenses(currentUser);
                    System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz:");
                    expenseService.deleteExpenseOfUser(Long.parseLong(common.getInput(null)));
                    System.out.println("\nHarcama başarıyla silindi.");
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "6")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    common.showUserExpenseCategories(currentUser);
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "7")) {
                    System.out.println("\nHarcama Kategorisi Adını giriniz:");
                    String name = common.getInput(null);
                    ExpenseCategory newExpenseCategory = new ExpenseCategory(currentUser.getId(), name);

                    if (expenseCategoryService.addExpenseCategory(newExpenseCategory) != null) {
                        System.out.println("\nHarcama Kategorisi başarıyla kaydedildi.");
                    } else {
                        System.out.println("\nHata: Harcama Kategorisi eklenemedi.");
                    }
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "8")) {
                    while (true) {
                        System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                        common.showUserExpenseCategories(currentUser);

                        System.out.println("\nDüzenlemek istediğiniz kategori ID yi giriniz:");
                        long selectedExpenseCategoryId = Long.parseLong(common.getInput(null));
                        long currentUserId = currentUser.getId();

                        System.out.println("\nYeni Kategori Adını giriniz: (" + expenseCategoryService.getExpenseCategory(selectedExpenseCategoryId).getName() + ")");
                        String editedName = (common.getInput(expenseCategoryService.getExpenseCategory(selectedExpenseCategoryId).getName()));

                        ExpenseCategory newExpenseCategory = new ExpenseCategory(currentUserId, editedName);

                        if (expenseCategoryService.editExpenseCategory(newExpenseCategory, selectedExpenseCategoryId) != null) {
                            System.out.println("\nHarcama başarıyla düzenlendi.");
                            common.backwardMenu();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama düzenlenemedi.");
                        }
                    }
                } else if (Objects.equals(input, "9")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    common.showUserExpenseCategories(currentUser);
                    System.out.print("\nSilmek istediğiniz Kategori ID yi giriniz: ");
                    expenseCategoryService.deleteExpenseCategory(Long.parseLong(common.getInput(null)));
                    System.out.println("\nHarcama başarıyla silindi.");
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "o")) {
                    common.logoutUser(currentUser);
                    common.menuSelector();
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
