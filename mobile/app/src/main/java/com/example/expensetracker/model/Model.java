package com.example.expensetracker.model;

import android.app.Application;

import com.example.expensetracker.model.api.API;
import com.example.expensetracker.model.api.APIListener;
import com.example.expensetracker.model.api.AuthAPI;

public class Model {
    private static Model model = null;
    private Application application;
    private API api;

    private Model(Application app) {
        application = app;
        api = new AuthAPI(app);
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

    public void login(String username, String password, APIListener apiListener) {
        api.login(username, password, apiListener);
    }

    public void validateToken(String token, APIListener apiListener) {
        api.validateToken(token, apiListener);
    }
}