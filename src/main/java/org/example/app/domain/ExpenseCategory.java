package org.example.app.domain;

public class ExpenseCategory {

    private Long id;
    private Long userId;
    private String name;

    public ExpenseCategory(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    //Getters, Setters, Attributes

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}