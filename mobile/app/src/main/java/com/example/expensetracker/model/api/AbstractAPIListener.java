package com.example.expensetracker.model.api;

import com.example.expensetracker.model.AuthResponse;

public class AbstractAPIListener implements APIListener {
    @Override
    public void onLogin(AuthResponse authResponse) { }
}
