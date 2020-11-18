package com.example.expensetracker.model.api;

public interface ISettingsAPI {
    void updateCurrency(String token, String currency, SettingsAPIListener settingsAPIListener);
}
