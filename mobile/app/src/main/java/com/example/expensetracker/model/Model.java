package com.example.expensetracker.model;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.expensetracker.model.api.ExpenseAPI;
import com.example.expensetracker.model.api.ExpenseAPIListener;
import com.example.expensetracker.model.api.IAuthAPI;
import com.example.expensetracker.model.api.AuthAPIListener;
import com.example.expensetracker.model.api.AuthAPI;
import com.example.expensetracker.model.api.IExpenseAPI;
import com.example.expensetracker.model.api.IIncomeAPI;
import com.example.expensetracker.model.api.ISettingsAPI;
import com.example.expensetracker.model.api.IncomeAPI;
import com.example.expensetracker.model.api.IncomeAPIListener;
import com.example.expensetracker.model.api.SettingsAPI;
import com.example.expensetracker.model.api.SettingsAPIListener;

public class Model {
    private static Model model = null;
    private Application application;
    private IAuthAPI IAuthApi;
    private IExpenseAPI iExpenseAPI;
    private IIncomeAPI iIncomeAPI;
    private ISettingsAPI iSettingsAPI;

    private Model(Application app) {
        application = app;
        RequestQueue requestQueue = Volley.newRequestQueue(app);
        IAuthApi = new AuthAPI(app, requestQueue);
        iExpenseAPI = new ExpenseAPI(app, requestQueue);
        iIncomeAPI = new IncomeAPI(app, requestQueue);
        iSettingsAPI = new SettingsAPI(app, requestQueue);
    }

    public Application getApplication() {
        return application;
    }

    public static Model getInstance(Application app) {
        if (model == null) {
            model = new Model(app);
        }
        return model;
    }

    public void login(String username, String password, AuthAPIListener authApiListener) {
        IAuthApi.login(username, password, authApiListener);
    }

    public void signup(String username, String password, String confirmPassword, AuthAPIListener authApiListener) {
        IAuthApi.signup(username, password, confirmPassword, authApiListener);
    }

    public void validateToken(String token, AuthAPIListener authApiListener) {
        IAuthApi.validateToken(token, authApiListener);
    }

    public void logout(String token, AuthAPIListener authApiListener) {
        IAuthApi.logout(token, authApiListener);
    }

    public void fetchAllExpenses(String token, ExpenseAPIListener expenseAPIListener) {
        iExpenseAPI.fetchExpenses(token, expenseAPIListener);
    }

    public void createExpense(String token, String title, int amount, String date, ExpenseAPIListener expenseAPIListener) {
        iExpenseAPI.createExpense(token, title, amount, date, expenseAPIListener);
    }

    public void updateExpense(String token, String id, String title, int amount, String date, ExpenseAPIListener expenseAPIListener) {
        iExpenseAPI.updateExpense(token, id, title, amount, date, expenseAPIListener);
    }

    public void deleteExpense(String token, String id, ExpenseAPIListener expenseAPIListener) {
        iExpenseAPI.deleteExpense(token, id, expenseAPIListener);
    }

    public void fetchAllIncomes(String token, IncomeAPIListener incomeAPIListener) {
        iIncomeAPI.fetchIncomes(token, incomeAPIListener);
    }

    public void createIncome(String token, String title, int amount, String date, IncomeAPIListener incomeAPIListener) {
        iIncomeAPI.createIncome(token, title, amount, date, incomeAPIListener);
    }

    public void updateIncome(String token, String id, String title, int amount, String date, IncomeAPIListener incomeAPIListener) {
        iIncomeAPI.updateIncome(token, id, title, amount, date, incomeAPIListener);
    }

    public void deleteIncome(String token, String id, IncomeAPIListener incomeAPIListener) {
        iIncomeAPI.deleteIncome(token, id, incomeAPIListener);
    }

    public void updateCurrency(String token, String currency, SettingsAPIListener settingsAPIListener) {
        iSettingsAPI.updateCurrency(token, currency, settingsAPIListener);
    }
}