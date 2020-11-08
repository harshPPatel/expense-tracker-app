package com.example.expensetracker.model;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthResponse {
    private  String username, token;

    public AuthResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public static AuthResponse getAuthResponse(JSONObject jsonObject) throws JSONException {
        String username = jsonObject.getString("username");
        String token = jsonObject.getString("token");

        AuthResponse authResponse = new AuthResponse(username, token);
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
