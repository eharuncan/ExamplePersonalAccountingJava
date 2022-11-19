package org.example.app.domain;

import java.util.Date;

public class Expense {

    private Long id;
    private Long userId;
    private String name;
    private Double amount;
    private Date date;
    private Long categoryId;

    public Expense(Long userId, String name, Double amount, Date date, Long categoryId) {
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
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

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}