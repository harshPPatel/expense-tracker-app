package com.example.expensetracker.model.api;

public interface IIncomeAPI {
    void fetchIncomes(String token, IncomeAPIListener incomeAPIListener);
    void createIncome(String token, String title, int amount, String date, IncomeAPIListener incomeAPIListener);
    void updateIncome(String token, String id, String title, int amount, String date, IncomeAPIListener incomeAPIListener);
    void deleteIncome(String token, String id, IncomeAPIListener incomeAPIListener);
}
