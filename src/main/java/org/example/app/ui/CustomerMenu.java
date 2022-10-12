package org.example.app.ui;

import org.example.app.domain.Expense;
import org.example.app.domain.ExpenseCategory;
import org.example.app.domain.User;

import java.util.Objects;

import static org.example.app.App.*;
import static org.example.app.utils.Date.dateFormatter;

public class CustomerMenu {
    private final Common common;

    public CustomerMenu(Common common) {
        this.common = common;
    }

    public void show() {
        try {
            System.out.println("\nHoşgeldiniz, " + userService.getCurrentUser().getName());
            System.out.println("\nBugün toplam harcamanız: " + expenseService.getSumOfUserExpensesOfDay(userService.getCurrentUser().getId(), new java.util.Date()) + " TL");
            System.out.println("Bu Ay toplam harcamanız: " + expenseService.getSumOfUserExpensesOfMonth(userService.getCurrentUser().getId(), new java.util.Date()) + " TL");
            System.out.println("Bu Yıl toplam harcamanız: " + expenseService.getSumOfUserExpensesOfYear(userService.getCurrentUser().getId(), new java.util.Date()) + " TL");
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

                String input = common.getStringInput(null);
                if (Objects.equals(input, "0")) {
                    common.showUserProfile(userService.getCurrentUser());
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "1")) {
                    while (true) {
                        System.out.println("\nProfil Bilgilerin:");
                        common.showUserProfile(userService.getCurrentUser());

                        User selectedUser = userService.getCurrentUser();
                        User editedUser;
                        editedUser = selectedUser;

                        System.out.println("\nYeni Adınızı giriniz: (" + selectedUser.getName() + ")");
                        editedUser.setName(common.getStringInput(selectedUser.getName()));

                        System.out.println("\nYeni Soyadınızı giriniz: (" + selectedUser.getSurname() + ")");
                        editedUser.setSurname(common.getStringInput(selectedUser.getSurname()));

                        System.out.println("\nYeni E-Posta Adresinizi giriniz: (" + selectedUser.getEmail() + ")");
                        editedUser.setEmail(common.getStringInput(selectedUser.getEmail()));

                        editedUser.setPassword(common.changePasswords(selectedUser.getPassword()));

                        if (userService.editUser(userService.getCurrentUser(), editedUser)) {
                            System.out.println("\nProfilin başarıyla güncellendi.");
                            common.backwardMenu();
                            break loops;
                        } else {
                            System.out.println("\nHata: Profilin güncellenemedi.");
                        }
                    }
                } else if (Objects.equals(input, "2")) {
                    common.showUserExpenses(userService.getCurrentUser());
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "3")) {
                    while (true) {
                        Expense newExpense = null;

                        newExpense = new Expense();

                        newExpense.setUserId(userService.getCurrentUser().getId());

                        System.out.println("\nHarcama Adını giriniz:");
                        newExpense.setName(common.getStringInput(null));

                        System.out.println("\nHarcama Miktarını giriniz: Örneğin: " + 100.0);
                        newExpense.setAmount(Double.parseDouble(common.getStringInput(null)));

                        String formattedDate = dateFormatter.format(new java.util.Date());
                        System.out.println("\nHarcama Tarihini giriniz: Örneğin: " + formattedDate + " Bugünü seçmek için boş bırakınız.");
                        newExpense.setDate(dateFormatter.parse(common.getStringInput(formattedDate)));

                        System.out.println("\nHarcama Kategorisini seçiniz: (İsteğe bağlı)");
                        common.showUserExpenseCategories(userService.getCurrentUser());
                        System.out.print("\nSeçiminiz: ");
                        int selectedExpenseCategoryId = (Integer.parseInt(common.getStringInput(null)));
                        newExpense.setCategoryId(selectedExpenseCategoryId);

                        if (expenseService.addExpense(userService.getCurrentUser().getId(), newExpense)) {
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
                        common.showUserExpenses(userService.getCurrentUser());

                        System.out.println("\nDüzenlemek istediğiniz harcama ID yi giriniz:");
                        Expense selectedExpense = expenseService.getExpenseByUserIdAndExpenseId(userService.getCurrentUser().getId(), Integer.parseInt(common.getStringInput(null)));

                        Expense editedExpense;
                        editedExpense = selectedExpense;

                        System.out.println("\nYeni Harcama Adını giriniz: (" + selectedExpense.getName() + ")");
                        editedExpense.setName(common.getStringInput(selectedExpense.getName()));

                        System.out.println("\nYeni Harcama Miktarını giriniz: (" + selectedExpense.getAmount() + ")");
                        editedExpense.setAmount(Double.parseDouble(common.getDoubleInput(selectedExpense.getAmount())));

                        System.out.println("\nYeni Harcama Tarihini giriniz: (" + dateFormatter.format(selectedExpense.getDate()) + ")");
                        editedExpense.setDate(dateFormatter.parse(common.getDateInput(selectedExpense.getDate())));

                        System.out.println("\nYeni Harcama Kategorisi seçiniz: (" + selectedExpense.getCategoryId() + ")");
                        common.showUserExpenseCategories(userService.getCurrentUser());
                        System.out.print("\nSeçiminiz: ");
                        int selectedExpenseCategoryId = (Integer.parseInt(common.getStringInput(null)));
                        editedExpense.setCategoryId(selectedExpenseCategoryId);

                        if (expenseService.editExpense(selectedExpense, editedExpense)) {
                            System.out.println("\nHarcama başarıyla düzenlendi.");
                            common.backwardMenu();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama düzenlenemedi.");
                        }
                    }
                } else if (Objects.equals(input, "5")) {
                    System.out.println("\nTüm Harcamalarının Listesi:");
                    common.showUserExpenses(userService.getCurrentUser());
                    System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz:");
                    if (expenseService.deleteExpense(userService.getCurrentUser().getId(), Integer.parseInt(common.getStringInput(null)))) {
                        System.out.println("\nHarcama başarıyla silindi.");
                        common.backwardMenu();
                        break;
                    } else {
                        System.out.println("\nHata: Harcama silinemedi.");
                    }
                } else if (Objects.equals(input, "6")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    common.showUserExpenseCategories(userService.getCurrentUser());
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "7")) {
                    System.out.println("\nHarcama Kategorisi Adını giriniz:");
                    String newExpenseCategory = common.getStringInput(null);

                    if (expenseCategoryService.addExpenseCategory(userService.getCurrentUser().getId(), newExpenseCategory)) {
                        System.out.println("\nHarcama Kategorisi başarıyla kaydedildi.");
                    } else {
                        System.out.println("\nHata: Harcama Kategorisi eklenemedi.");
                    }
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "8")) {
                    while (true) {
                        System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                        common.showUserExpenseCategories(userService.getCurrentUser());

                        System.out.println("\nDüzenlemek istediğiniz kategori ID yi giriniz:");
                        ExpenseCategory selectedExpenseCategory = expenseCategoryService.getExpenseCategoryByUserIdAndExpenseCategoryId(userService.getCurrentUser().getId(), Integer.parseInt(common.getStringInput(null)));

                        ExpenseCategory editedExpenseCategory;
                        editedExpenseCategory = selectedExpenseCategory;

                        System.out.println("\nYeni Kategori Adını giriniz: (" + selectedExpenseCategory.getName() + ")");
                        editedExpenseCategory.setName(common.getStringInput(selectedExpenseCategory.getName()));

                        if (expenseCategoryService.editExpenseCategory(selectedExpenseCategory, editedExpenseCategory)) {
                            System.out.println("\nHarcama başarıyla düzenlendi.");
                            common.backwardMenu();
                            break loops;
                        } else {
                            System.out.println("\nHata: Harcama düzenlenemedi.");
                        }
                    }
                } else if (Objects.equals(input, "9")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    common.showUserExpenseCategories(userService.getCurrentUser());
                    System.out.print("\nSilmek istediğiniz Kategori ID yi giriniz: ");
                    if (expenseCategoryService.deleteExpenseCategory(userService.getCurrentUser().getId(), Integer.parseInt(common.getStringInput(null)))) {
                        System.out.println("\nHarcama başarıyla silindi.");
                        common.backwardMenu();
                        break;
                    } else {
                        System.out.println("\nHata: Harcama silinemedi.");
                    }
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "o")) {
                    common.logoutUser();
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
