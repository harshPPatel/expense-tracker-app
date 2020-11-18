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
import com.example.expensetracker.model.IncomeResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IncomeAPI implements IIncomeAPI {
    public static final String BASE_URL = "https://expense-tarcker-app-api.now.sh/api/v1";

    private RequestQueue requestQueue;
    private Application application;

    public IncomeAPI(Application app, RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        application = app;
    }

    public void fetchIncomes(final String token, final IncomeAPIListener incomeAPIListener) {
        String uri = BASE_URL + "/income/";

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonIncomes = response.getJSONArray("incomes");
                    ArrayList<IncomeResponse> incomes = new ArrayList<>(jsonIncomes.length());
                    for (int i = 0; i < jsonIncomes.length(); i++) {
                        IncomeResponse income = IncomeResponse.getIncomeResponse(jsonIncomes.getJSONObject(i));
                        incomes.add(income);
                    }
                    incomeAPIListener.onFetchAllIncomes(incomes);
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
                        incomeAPIListener.onRequestFailed(jsonError);
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

    public void createIncome(final String token, String title, int amount, String date, final IncomeAPIListener incomeAPIListener) {
        String uri = BASE_URL + "/income/create";
        JSONObject body = new JSONObject();
        try {
            body.put("title", title);
            body.put("amount", amount);
            body.put("date", date);
        } catch (JSONException e) {
            Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
        }
        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("income");
                    IncomeResponse incomeResponse = IncomeResponse.getIncomeResponse(res);
                    incomeAPIListener.onIncomeCreated(incomeResponse);
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
                        incomeAPIListener.onRequestFailed(jsonError);
                    } catch (JSONException e) {
                        Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, uri, body, successListener, errorListener) {
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

    public void updateIncome(final String token, String id, String title, int amount, String date, final IncomeAPIListener incomeAPIListener) {
        String uri = BASE_URL + "/income/update";
        JSONObject body = new JSONObject();
        try {
            body.put("_id", id);
            body.put("title", title);
            body.put("amount", amount);
            body.put("date", date);
        } catch (JSONException e) {
            Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
        }
        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("updatedIncome");
                    IncomeResponse incomeResponse = IncomeResponse.getIncomeResponse(res);
                    incomeAPIListener.onIncomeUpdated(incomeResponse);
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
                        incomeAPIListener.onRequestFailed(jsonError);
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

    public void deleteIncome(final String token, String id, final IncomeAPIListener incomeAPIListener) {
        String uri = BASE_URL + "/income/" + id;

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getString("removedIncomeId");
                    incomeAPIListener.onIncomeDeleted(id);
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
                        incomeAPIListener.onRequestFailed(jsonError);
                    } catch (JSONException e) {
                        Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, uri, null, successListener, errorListener) {
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
