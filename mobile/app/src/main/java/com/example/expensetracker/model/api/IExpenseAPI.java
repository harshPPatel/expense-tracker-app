package com.example.expensetracker.model.api;

public interface IExpenseAPI {
    void fetchExpenses(String token, ExpenseAPIListener expenseAPIListener);
    void createExpense(String token, String title, int amount, String date, ExpenseAPIListener expenseAPIListener);
    void deleteExpense(String token, String id, ExpenseAPIListener expenseAPIListener);
}
