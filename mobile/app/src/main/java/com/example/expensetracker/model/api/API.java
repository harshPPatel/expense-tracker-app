package com.example.expensetracker.model.api;

public interface API {
    void login(String username, String password, APIListener apiListener);
}
