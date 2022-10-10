package org.example.app.ui;

import org.example.app.domain.Expense;
import org.example.app.services.ExpenseService;
import org.example.app.services.UserService;
import org.example.app.utils.Dates;

import java.util.Date;
import java.util.Scanner;

public class CustomerMenu {
    private final UserService userService;
    private final ExpenseService expenseService;
    private final Common common;

    private static final Scanner scanner = new Scanner(System.in);

    public CustomerMenu(Common common, UserService userService, ExpenseService expenseService) {
        this.common = common;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    public void show() {
        try {

            System.out.println("\nHoşgeldiniz, " + userService.getCurrentUser().getName());
            System.out.println("\nBugünkü toplam harcamanız: " + expenseService.getSumOfExpensesOfDate(userService.getCurrentUser().getId(), new Date()) + " TL");

            loops:
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

                switch (scanner.nextLine()) {
                    case "1":
                        common.showUserExpenses(userService.getCurrentUser().getId());
                        common.backwardMenu();
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
                            common.showUserExpenseCategories(userService.getCurrentUser().getId());
                            System.out.print("\nSeçiminiz: ");
                            int index = (Integer.parseInt(scanner.nextLine()) - 1);
                            newExpense.setCategory(userService.getExpenseCategoryByUserIdAndIndex(userService.getCurrentUser().getId(), index));

                            if (expenseService.addExpenseByUserId(userService.getCurrentUser().getId(), newExpense)) {
                                System.out.println("\nHarcama başarıyla kaydedildi.");
                                break;
                            } else {
                                System.out.println("\nHata: Harcama kaydı oluşturulamadı.");
                            }

                        }
                        common.backwardMenu();
                        break;
                    case "3":
                        while (true) {
                            System.out.println("\nTüm Harcamalarının Listesi:");
                            common.showUserExpenses(userService.getCurrentUser().getId());

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
                            common.showUserExpenseCategories(userService.getCurrentUser().getId());
                            System.out.print("\nSeçiminiz: ");
                            int index = (Integer.parseInt(scanner.nextLine()) - 1);
                            editedExpense.setCategory(userService.getExpenseCategoryByUserIdAndIndex(userService.getCurrentUser().getId(), index));

                            if (expenseService.editExpense(userService.getCurrentUser().getId(), selectedExpense.getId(), editedExpense)) {
                                System.out.println("\nHarcama başarıyla düzenlendi.");
                                break;
                            } else {
                                System.out.println("\nHata: Harcama düzenlenemedi.");
                            }
                        }
                        common.backwardMenu();
                        break;
                    case "4":
                        System.out.println("\nTüm Harcamalarının Listesi:");
                        common.showUserExpenses(userService.getCurrentUser().getId());
                        System.out.println("\nSilmek istediğiniz Harcama ID yi giriniz:");
                        if (expenseService.deleteExpenseByUserId(userService.getCurrentUser().getId(), Integer.parseInt(scanner.nextLine()) - 1)) {
                            System.out.println("\nHarcama başarıyla silindi.");
                        } else {
                            System.out.println("\nHata: Harcama silinemedi.");
                        }
                        common.backwardMenu();
                        break;
                    case "5":
                        System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                        common.showUserExpenseCategories(userService.getCurrentUser().getId());
                        common.backwardMenu();
                        break;
                    case "6":
                        System.out.println("\nHarcama Kategorisi Adını giriniz:");
                        String newExpenseCategory = scanner.nextLine();
                        userService.getCurrentUser().getExpenseCategoryList().add(newExpenseCategory);
                        System.out.println("\nHarcama Kategorisi başarıyla kaydedildi.");
                        common.backwardMenu();
                        break;
                    case "7":
                        System.out.println("\nTüm Harcama Kategorilerinin Listesi:");
                        common.showUserExpenseCategories(userService.getCurrentUser().getId());
                        System.out.print("\nDeğiştirmek istediğiniz kategori sayısını seçiniz: ");
                        int index = (Integer.parseInt(scanner.nextLine()) - 1);
                        userService.getCurrentUser().getExpenseCategoryList().remove(userService.getExpenseCategoryByUserIdAndIndex(userService.getCurrentUser().getId(), index));
                        System.out.println("\nHarcama Kategorisi başarıyla silindi.");
                        common.backwardMenu();
                        break;
                    case "g":
                        common.menuSelector();
                        break;
                    case "o":
                        common.logoutUser();
                        common.menuSelector();
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
}
