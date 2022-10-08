package org.example.app.enums;

public enum ExpenseCategories {
    SAGLIK("Sağlık"),
    GUVENLIK("Güvenlik"),
    COCUK("Çocuk"),
    BILGISAYAR("Bilgisayar");
    private String value;

    ExpenseCategories(String value) {
        this.value = value;
    }
}
