package com.example.expensetracker.model.api;

import com.example.expensetracker.model.AuthResponse;
import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.IncomeResponse;

import org.json.JSONObject;

import java.util.ArrayList;

public class AbstractListener implements AuthAPIListener, ExpenseAPIListener, IncomeAPIListener {
    @Override
    public void onLogin(AuthResponse authResponse) { }

    @Override
    public void onRequestFailed(JSONObject jsonError) { }

    @Override
    public void onValidToken(boolean result) { }

    @Override
    public void onLogout() { }

    @Override
    public void onSignUp(String username) { }

    @Override
    public void onFetchAllExpenses(ArrayList<ExpenseResponse> expenses) { }

    @Override
    public void onExpenseCreated(ExpenseResponse responseExpense) { }

    @Override
    public void onExpenseUpdated(ExpenseResponse responseExpense) { }

    @Override
    public void onExpenseDeleted(String id) { }

    @Override
    public void onFetchAllIncomes(ArrayList<IncomeResponse> incomes) {

    }

    @Override
    public void onIncomeCreated(IncomeResponse incomeResponse) {

    }

    @Override
    public void onIncomeUpdated(IncomeResponse incomeResponse) {

    }

    @Override
    public void onIncomeDeleted(String id) {

    }
}
