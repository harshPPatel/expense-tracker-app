package com.example.expensetracker.model.api;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.expensetracker.model.AuthResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class WebAPI implements API {
    public static final String BASE_URL = "https://expense-tracker-app-api.now.sh/api/v1";

    private RequestQueue requestQueue;
    private Application application;

    public WebAPI(Application app) {
        requestQueue = Volley.newRequestQueue(app);
        application = app;
    }

    public void login(String username, String password, final APIListener apiListener) {
        String uri = BASE_URL + "/auth/login";
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
        }

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    AuthResponse authResponse = AuthResponse.getAuthResponse(response);
                    apiListener.onLogin(authResponse);
                } catch (JSONException e) {
                    Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(application, "Internal Server Exception", Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, uri, body, successListener, errorListener);
        requestQueue.add(request);
    }
}
