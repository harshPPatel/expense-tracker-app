package com.example.expensetracker.model.api;

import org.json.JSONObject;

public interface BaseListener {
    void onRequestFailed(JSONObject jsonError);
}
