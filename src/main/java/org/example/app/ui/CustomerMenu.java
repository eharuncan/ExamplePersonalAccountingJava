package org.example.app.ui;

import org.example.app.domain.Expense;

import java.util.Objects;

import static org.example.app.App.userService;
import static org.example.app.App.expenseService;

import static org.example.app.utils.Date.dateFormatter;
import static org.example.app.utils.Screen.screenScanner;

public class CustomerMenu {
    private final Common common;

    public CustomerMenu(Common common) {
        this.common = common;
    }

    public void show() {
        try {
            System.out.println("\nHoşgeldiniz, " + userService.getCurrentUser().getName());
            System.out.println("\nBugünkü toplam harcamanız: " + expenseService.getSumOfExpensesOfDateByUserId(userService.getCurrentUser().getId(), new java.util.Date()) + " TL");

            while (true) {
                common.menuHeader();
                System.out.println("1- Harcamalarım");
                System.out.println("2- Harcama Ekle");
                System.out.println("3- Harcama Düzenle");
                System.out.println("4- Harcama Sil");
                System.out.println("5- Kategorilerim");
                System.out.println("6- Kategori Ekle");
                System.out.println("7- Kategori Sil");
                common.menuFooter();

                String input = screenScanner.nextLine();

                if (Objects.equals(input, "1")) {
                    common.showUserExpenses(userService.getCurrentUser().getId());
                    common.backwardMenu();
                } else if (Objects.equals(input, "2")) {
                    while (true) {
                        Expense newExpense = null;

                        newExpense = new Expense();

                        newExpense.setUserId(userService.getCurrentUser().getId());

                            System.out.println("\nHarcama Adını giriniz:");
                            newExpense.setName(screenScanner.nextLine());

                            System.out.println("\nHarcama Miktarını giriniz: Örneğin: " + 100.0);
                            newExpense.setAmount(Double.parseDouble(screenScanner.nextLine()));

                            String formattedDate = dateFormatter.format(new java.util.Date());
                            System.out.println("\nHarcama Tarihini giriniz: Örneğin: " + formattedDate);
                            newExpense.setDate(dateFormatter.parse(screenScanner.nextLine()));

                            System.out.println("\nHarcama Kategorisini seçiniz: (İsteğe bağlı)");
                            common.showUserExpenseCategories(userService.getCurrentUser().getId());
                            System.out.print("\nSeçiminiz: ");
                            int index = (Integer.parseInt(screenScanner.nextLine()) - 1);
                            newExpense.setCategory(userService.getExpenseCategoryByUserIdAndIndex(userService.getCurrentUser().getId(), index));

                        if (expenseService.addExpenseByUserId(userService.getCurrentUser().getId(), newExpense)) {
                            System.out.println("\nHarcama başarıyla kaydedildi.");
                            break;
                        } else {
                            System.out.println("\nHata: Harcama kaydı oluşturulamadı.");
                        }
                    }
                    common.backwardMenu();
                } else if (Objects.equals(input, "3")) {
                    while (true) {
                        System.out.println("\nTüm Harcamalarının Listesi:");
                        common.showUserExpenses(userService.getCurrentUser().getId());

                        System.out.println("\nDüzenlemek istediğiniz harcama ID yi giriniz:");
                        Expense selectedExpense = expenseService.getExpenseByUserIdAndExpenseId(userService.getCurrentUser().getId(), Integer.parseInt(screenScanner.nextLine()) - 1);

                        Expense editedExpense;

                        editedExpense = selectedExpense;

                            System.out.println("\nYeni Harcama Adını giriniz: (" + selectedExpense.getName() + ")");
                            editedExpense.setName(screenScanner.nextLine());

                            System.out.println("\nYeni Harcama Miktarını giriniz: (" + selectedExpense.getAmount() + ")");
                            editedExpense.setAmount(Double.parseDouble(screenScanner.nextLine()));

                            System.out.println("\nYeni Harcama Tarihini giriniz: (" + dateFormatter.format(selectedExpense.getDate()) + ")");
                            editedExpense.setDate(dateFormatter.parse(screenScanner.nextLine()));

                            System.out.println("\nYeni Harcama Kategorisi seçiniz: (" + selectedExpense.getCategory() + ")");
                            common.showUserExpenseCategories(userService.getCurrentUser().getId());
                            System.out.print("\nSeçiminiz: ");
                            int index = (Integer.parseInt(screenScanner.nextLine()) - 1);
                            editedExpense.setCategory(userService.getExpenseCategoryByUserIdAndIndex(userService.getCurrentUser().getId(), index));

                        if (expenseService.editExpenseByUserId(userService.getCurrentUser().getId(), selectedExpense.getId(), editedExpense)) {
                            System.out.println("\nHarcama başarıyla düzenlendi.");
                            break;
                        } else {
                            System.out.println("\nHata: Harcama düzenlenemedi.");
                        }
                    }
                    common.backwardMenu();
                } else if (Objects.equals(input, "4")) {
                    System.out.println("\nTüm Harcamalarının Listesi:");
                    common.showUserExpenses(userService.getCurrentUser().getId());
                    System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz:");
                    if (expenseService.deleteExpenseByUserId(userService.getCurrentUser().getId(), Integer.parseInt(screenScanner.nextLine()) - 1)) {
                        System.out.println("\nHarcama başarıyla silindi.");
                    } else {
                        System.out.println("\nHata: Harcama silinemedi.");
                    }
                    common.backwardMenu();
                } else if (Objects.equals(input, "5")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    common.showUserExpenseCategories(userService.getCurrentUser().getId());
                    common.backwardMenu();
                } else if (Objects.equals(input, "6")) {
                    System.out.println("\nHarcama Kategorisi Adını giriniz:");
                    String newExpenseCategory = screenScanner.nextLine();;
                    userService.getCurrentUser().getExpenseCategoryList().add(newExpenseCategory);
                    System.out.println("\nHarcama Kategorisi başarıyla kaydedildi.");
                    common.backwardMenu();
                } else if (Objects.equals(input, "7")) {
                    System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                    common.showUserExpenseCategories(userService.getCurrentUser().getId());
                    System.out.print("\nDeğiştirmek istediğiniz kategori sayısını seçiniz: ");
                    int index = (Integer.parseInt(screenScanner.nextLine()) - 1);
                    userService.getCurrentUser().getExpenseCategoryList().remove(userService.getExpenseCategoryByUserIdAndIndex(userService.getCurrentUser().getId(), index));
                    System.out.println("\nHarcama Kategorisi başarıyla silindi.");
                    common.backwardMenu();
                    break;
                } else if (Objects.equals(input, "g")) {
                    common.menuSelector();
                } else if (Objects.equals(input, "o")) {
                    common.logoutUser();
                    common.menuSelector();
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
