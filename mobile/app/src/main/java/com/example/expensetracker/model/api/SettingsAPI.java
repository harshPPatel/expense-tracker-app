package com.example.expensetracker.model.api;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.expensetracker.model.IncomeResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsAPI implements ISettingsAPI {
    public static final String BASE_URL = "https://expense-tarcker-app-api.now.sh/api/v1";

    private RequestQueue requestQueue;
    private Application application;

    public SettingsAPI(Application app, RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        application = app;
    }

    public void updateCurrency(final String token, String currency, final SettingsAPIListener settingsAPIListener) {
        String uri = BASE_URL + "/user/currency/update";

        JSONObject body = new JSONObject();
        try {
            body.put("currency", currency);
        } catch (JSONException e) {
            Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
        }

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String updatedCurrency = response.getString("updatedCurrency");
                    settingsAPIListener.onCurrencyUpdated(updatedCurrency);
                } catch (JSONException e) {
                    Toast.makeText(application, "JSON Parse Exception", Toast.LENGTH_LONG).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String responseError = new String(networkResponse.data);
                    try {
                        JSONObject jsonError = new JSONObject(responseError);
                        settingsAPIListener.onRequestFailed(jsonError);
                    } catch (JSONException e) {
                        Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, uri, body, successListener, errorListener) {
            /**
             * Passing token as header
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };
        requestQueue.add(request);
    }

}
