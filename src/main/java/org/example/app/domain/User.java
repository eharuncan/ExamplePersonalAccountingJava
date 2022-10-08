package org.example.app.domain;

import org.example.app.enums.UserTypes;

import java.util.List;

public class User {

    public User() {

    }

    //Getters, Setters, Attributes
    private Integer id;
    private UserTypes type;
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<String> expenseCategoryList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getExpenseCategoryList() {
        return expenseCategoryList;
    }

    public void setExpenseCategoryList(List<String> expenseCategoryList) {
        this.expenseCategoryList = expenseCategoryList;
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

}
