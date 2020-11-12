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
import com.android.volley.toolbox.Volley;
import com.example.expensetracker.model.AuthResponse;
import com.example.expensetracker.model.ExpenseResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseAPI implements IExpenseAPI {
    public static final String BASE_URL = "https://expense-tracker-app-api.now.sh/api/v1";

    private RequestQueue requestQueue;
    private Application application;

    public ExpenseAPI(Application app) {
        requestQueue = Volley.newRequestQueue(app);
        application = app;
    }

    public void fetchExpenses(final String token, final ExpenseAPIListener expenseAPIListener) {
        String uri = BASE_URL + "/expense/";

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonExpenses = response.getJSONArray("expenses");
                    ArrayList<ExpenseResponse> expenses = new ArrayList<>(jsonExpenses.length());
                    for (int i = 0; i < jsonExpenses.length(); i++) {
                        ExpenseResponse expense = ExpenseResponse.getExpenseResponse(jsonExpenses.getJSONObject(i));
                        expenses.add(expense);
                    }
                    expenseAPIListener.onFetchAllExpenses(expenses);
                } catch (JSONException | ParseException e) {
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
                        expenseAPIListener.onRequestFailed(jsonError);
                    } catch (JSONException e) {
                        Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri, null, successListener, errorListener) {
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

    public void deleteExpense(final String token, String id, final ExpenseAPIListener expenseAPIListener) {
        String uri = BASE_URL + "/expense/delete";

        JSONObject body = new JSONObject();
        try {
            body.put("_id", id);
        } catch (JSONException e) {
            Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
        }

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getString("removedExpenseId");
                    expenseAPIListener.onExpenseDeleted(id);
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
                        expenseAPIListener.onRequestFailed(jsonError);
                    } catch (JSONException e) {
                        Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, uri, body, successListener, errorListener) {
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
