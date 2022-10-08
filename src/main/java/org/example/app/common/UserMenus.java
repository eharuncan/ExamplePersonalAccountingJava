package org.example.app.common;

public enum UserMenus {
    EXIT(0),
    MAIN_MENU(1),
    ADMIN_MENU(2),
    CUSTOMER_MENU(3);
    private int value;

    UserMenus(int value) {
        this.value = value;
    }
}
