package org.example.app.ui;

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

                String input = common.getInput(null);
                if (Objects.equals(input, "0")) {
                    common.showUserProfile(userService.getCurrentUser());
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "1")) {
                    while (true) {
                        System.out.println("\nProfil Bilgilerin:");
                        common.showUserProfile(userService.getCurrentUser());

                        System.out.println("\nYeni Adınızı giriniz: (" + userService.getCurrentUser().getName() + ")");
                        String editedName = (common.getInput(userService.getCurrentUser().getName()));

                        System.out.println("\nYeni Soyadınızı giriniz: (" + userService.getCurrentUser().getSurname() + ")");
                        String editedSurname = (common.getInput(userService.getCurrentUser().getSurname()));

                        System.out.println("\nYeni E-Posta Adresinizi giriniz: (" + userService.getCurrentUser().getEmail() + ")");
                        String editedEmail = (common.getInput(userService.getCurrentUser().getEmail()));

                        String editedPassword = (common.getInput(userService.getCurrentUser().getPassword()));

                        String retypedPassword = (common.getInput(userService.getCurrentUser().getPassword()));

                        if (userService.editUser(userService.getCurrentUser().getId(), editedName, editedSurname, editedEmail, editedPassword, retypedPassword)) {
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

                        System.out.println("\nHarcama Adını giriniz:");
                        String name = (common.getInput(null));

                        System.out.println("\nHarcama Miktarını giriniz: Örneğin: " + 100.0);
                        Double amount = (Double.parseDouble(common.getInput(null)));

                        String formattedDate = dateFormatter.format(new java.util.Date());
                        System.out.println("\nHarcama Tarihini giriniz: Örneğin: " + formattedDate + " Bugünü seçmek için boş bırakınız.");
                        Date date = (dateFormatter.parse(common.getInput(formattedDate)));

                        System.out.println("\nHarcama Kategorisini seçiniz: (İsteğe bağlı)");
                        common.showUserExpenseCategories(userService.getCurrentUser());
                        System.out.print("\nSeçiminiz: ");
                        Long categoryId = ((Long.parseLong(common.getInput(null))));

                        if (expenseService.addExpense(userService.getCurrentUser().getId(), name, amount, date, categoryId)) {
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
                        Long selectedExpenseId = Long.parseLong(common.getInput(null));
                        Long currentUserId = userService.getCurrentUser().getId();

                        System.out.println("\nYeni Harcama Adını giriniz: (" + common.getInput(expenseService.getExpenseByUserIdAndExpenseId(currentUserId, selectedExpenseId).getName()) + ")");
                        String editedName = (common.getInput(expenseService.getExpenseByUserIdAndExpenseId(currentUserId, selectedExpenseId).getName()));

                        System.out.println("\nYeni Harcama Miktarını giriniz: (" + common.getInput(expenseService.getExpenseByUserIdAndExpenseId(currentUserId, selectedExpenseId).getAmount()) + ")");
                        Double editedAmount = (Double.parseDouble(common.getInput(common.getInput(expenseService.getExpenseByUserIdAndExpenseId(currentUserId, selectedExpenseId).getAmount()))));

                        System.out.println("\nYeni Harcama Tarihini giriniz: (" + dateFormatter.format(common.getInput(expenseService.getExpenseByUserIdAndExpenseId(currentUserId, selectedExpenseId).getDate())) + ")");
                        Date editedDate = (dateFormatter.parse(common.getInput(common.getInput(expenseService.getExpenseByUserIdAndExpenseId(currentUserId, selectedExpenseId).getDate()))));

                        System.out.println("\nYeni Harcama Kategorisi seçiniz: (" + common.getInput(expenseService.getExpenseByUserIdAndExpenseId(currentUserId, selectedExpenseId).getCategoryId()) + ")");
                        common.showUserExpenseCategories(userService.getCurrentUser());
                        System.out.print("\nSeçiminiz: ");
                        Long editedCategoryId = (Long.parseLong(common.getInput(null)));

                        if (expenseService.editExpense(currentUserId, selectedExpenseId, editedName, editedAmount, editedDate, editedCategoryId)) {
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
                    if (expenseService.deleteExpense(userService.getCurrentUser().getId(), Long.parseLong(common.getInput(null)))) {
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
                    String newExpenseCategory = common.getInput(null);

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
                        long selectedExpenseCategoryId = Long.parseLong(common.getInput(null));
                        long currentUserId = userService.getCurrentUser().getId();

                        System.out.println("\nYeni Kategori Adını giriniz: (" + expenseCategoryService.getExpenseCategoryByUserIdAndExpenseCategoryId(currentUserId, selectedExpenseCategoryId).getName() + ")");
                        String editedName = (common.getInput(expenseCategoryService.getExpenseCategoryByUserIdAndExpenseCategoryId(currentUserId, selectedExpenseCategoryId).getName()));

                        if (expenseCategoryService.editExpenseCategory(currentUserId, selectedExpenseCategoryId, editedName)) {
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
                    if (expenseCategoryService.deleteExpenseCategory(userService.getCurrentUser().getId(), Long.parseLong(common.getInput(null)))) {
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
