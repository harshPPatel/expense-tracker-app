package com.example.expensetracker.model.api;

import com.example.expensetracker.model.AuthResponse;

public interface APIListener {
    void onLogin(AuthResponse authResponse);
}
