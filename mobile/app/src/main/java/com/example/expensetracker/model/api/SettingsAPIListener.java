package com.example.expensetracker.model.api;

import com.example.expensetracker.model.IncomeResponse;

import java.util.ArrayList;

public interface SettingsAPIListener extends BaseListener {
    void onCurrencyUpdated(String currency);
}
