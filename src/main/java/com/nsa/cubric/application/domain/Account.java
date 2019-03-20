package com.nsa.cubric.application.domain;

public class Account {
    Long id;
    String email;
    String password;
    String role;
    Boolean accountDisabled;

    public Account(){}

    public Account(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Account(Long id, String email, String password, String role, boolean accountDisabled) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountDisabled = accountDisabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getAccountDisabled() {
        return accountDisabled;
    }

    public void setAccountDisabled(Boolean accountDisabled) {
        this.accountDisabled = accountDisabled;
    }
}
