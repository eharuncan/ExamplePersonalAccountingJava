package org.example.app;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;
import org.example.app.services.UserService;
import org.example.app.ui.Common;
import org.example.db.Database;

public class App {

    private static UserService userService;
    private static Common common;

    public static void main(String[] args) {
        Database database = new Database();

        userService = new UserService(database);
        userService.setCurrentUser(null);
        common = new Common(database);

        setInitialValues(database);

        common.menuSelector();
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
}