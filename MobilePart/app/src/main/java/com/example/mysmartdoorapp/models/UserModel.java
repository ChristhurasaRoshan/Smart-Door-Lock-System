package com.example.mysmartdoorapp.models;

public class UserModel {
    String name;
    String email;
    String password;

    String currentStatus;

    public UserModel() {
    }

    public UserModel(String name, String mail, String password, String currentStatus) {
        this.name = name;
        this.email = mail;
        this.password = password;
        this.currentStatus = currentStatus;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
