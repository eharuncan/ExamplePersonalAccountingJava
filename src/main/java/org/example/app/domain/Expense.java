package org.example.app.domain;

import java.util.Date;

public class Expense {
    public Expense() {
    }

    public Expense(Integer userId, Integer id, String name, Double amount, Date date, Integer categoryId) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
    }

    //Getters, Setters, Attributes
    private Integer userId;
    private Integer id;
    private String name;
    private Double amount;
    private Date date;
    private Integer categoryId;

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

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}