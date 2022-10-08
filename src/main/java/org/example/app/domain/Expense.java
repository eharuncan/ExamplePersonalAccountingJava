package org.example.app.domain;

import java.util.Date;

public class Expense {

    public Expense() {

    }

    //Getters, Setters, Attributes
    private Integer userId;
    private Integer id;
    private String name;
    private Double amount;
    private Date date;
    private String category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Expense(String name, Double amount, Date date, String category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

}