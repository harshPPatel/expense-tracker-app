package com.example.expensetracker.model.api;

public interface IAuthAPI {
    void login(String username, String password, AuthAPIListener authApiListener);
    void logout(String token, AuthAPIListener authApiListener);
    void validateToken(String token, AuthAPIListener authApiListener);
    void signup(String username, String password, String confirmPassword, AuthAPIListener authApiListener);
}
