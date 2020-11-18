package com.example.expensetracker.model.api;

import com.example.expensetracker.model.ExpenseResponse;

import java.util.ArrayList;

public interface ExpenseAPIListener extends BaseListener {
    void onFetchAllExpenses(ArrayList<ExpenseResponse> expenses);
    void onExpenseCreated(ExpenseResponse expenseResponse);
    void onExpenseDeleted(String id);
}
