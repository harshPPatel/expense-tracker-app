package com.example.expensetracker.model;

import android.app.Application;

import com.example.expensetracker.model.api.ExpenseAPI;
import com.example.expensetracker.model.api.ExpenseAPIListener;
import com.example.expensetracker.model.api.IAuthAPI;
import com.example.expensetracker.model.api.AuthAPIListener;
import com.example.expensetracker.model.api.AuthAPI;
import com.example.expensetracker.model.api.IExpenseAPI;

public class Model {
    private static Model model = null;
    private Application application;
    private IAuthAPI IAuthApi;
    private IExpenseAPI iExpenseAPI;

    private Model(Application app) {
        application = app;
        IAuthApi = new AuthAPI(app);
        iExpenseAPI = new ExpenseAPI(app);
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

    public void deleteExpense(String token, String id, ExpenseAPIListener expenseAPIListener) {
        iExpenseAPI.deleteExpense(token, id, expenseAPIListener);
    }

}