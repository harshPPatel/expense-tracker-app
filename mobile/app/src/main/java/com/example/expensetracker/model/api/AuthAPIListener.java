package com.example.expensetracker.model.api;

import com.example.expensetracker.model.AuthResponse;

public interface AuthAPIListener extends BaseListener {
    void onLogin(AuthResponse authResponse);
    void onValidToken(boolean result);
    void onLogout();
    void onSignUp(String username);
}
