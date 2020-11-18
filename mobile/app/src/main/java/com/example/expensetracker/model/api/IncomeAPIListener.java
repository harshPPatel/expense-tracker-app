package com.example.expensetracker.model.api;

import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.IncomeResponse;

import java.util.ArrayList;

public interface IncomeAPIListener extends BaseListener {
    void onFetchAllIncomes(ArrayList<IncomeResponse> incomes);
    void onIncomeCreated(IncomeResponse incomeResponse);
    void onIncomeUpdated(IncomeResponse incomeResponse);
    void onIncomeDeleted(String id);
}
