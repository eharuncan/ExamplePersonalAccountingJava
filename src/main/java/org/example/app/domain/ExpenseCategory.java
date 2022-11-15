package org.example.app.domain;

public class ExpenseCategory {
    public ExpenseCategory() {
    }

    public ExpenseCategory(Long userId, Long id, String name) {
        this.userId = userId;
        this.id = id;
        this.name = name;
    }

    //Getters, Setters, Attributes
    private Long userId;
    private Long id;
    private String name;

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