package com.example.expensetracker.model.api;

import com.example.expensetracker.model.AuthResponse;

import org.json.JSONObject;

public class AbstractAPIListener implements APIListener {
    @Override
    public void onLogin(AuthResponse authResponse) { }

    @Override
    public void onRequestFailed(JSONObject jsonError) { }

    @Override
    public void onValidToken(boolean result) { }

}
