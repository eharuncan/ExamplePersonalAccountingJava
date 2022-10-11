package org.example.app.domain;

import java.util.Date;

public class ExpenseCategory {
    public ExpenseCategory() {
    }

    public ExpenseCategory(Integer userId, Integer id, String name) {
        this.userId = userId;
        this.id = id;
        this.name = name;
    }

    //Getters, Setters, Attributes
    private Integer userId;
    private Integer id;
    private String name;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}