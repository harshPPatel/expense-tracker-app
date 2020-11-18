package com.example.expensetracker.model;

import androidx.annotation.Nullable;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthResponse {
    private  String username, token, currency;

    public AuthResponse(String username, String token, String currency) {
        this.username = username;
        this.token = token;
        this.currency = currency;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getCurrency() {
        return currency;
    }

    public static AuthResponse getAuthResponse(JSONObject jsonObject) throws JSONException {
        String username = jsonObject.getString("username");
        String token = jsonObject.getString("token");
        JSONObject settingsJson = jsonObject.getJSONObject("settings");
        String currency = settingsJson.getString("currency");

        AuthResponse authResponse = new AuthResponse(username, token, currency);
        return authResponse;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof AuthResponse) {
            AuthResponse that = (AuthResponse) obj;
            if (this.username.equalsIgnoreCase(that.username)) {
                result = true;
            }
        }

        return result;
    }
}
