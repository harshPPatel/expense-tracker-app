package com.example.expensetracker.model.api;

import com.example.expensetracker.model.AuthResponse;

import org.json.JSONObject;

public interface APIListener {
    void onLogin(AuthResponse authResponse);
    void onValidToken(boolean result);

    void onRequestFailed(JSONObject jsonError);
}
