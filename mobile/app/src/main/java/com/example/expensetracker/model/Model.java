package com.example.expensetracker.model;

import android.app.Application;

import com.example.expensetracker.model.api.API;
import com.example.expensetracker.model.api.APIListener;
import com.example.expensetracker.model.api.WebAPI;

public class Model {
    private static Model model = null;
    private Application application;
    private API api;

    private Model(Application app) {
        application = app;
        api = new WebAPI(app);
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
}