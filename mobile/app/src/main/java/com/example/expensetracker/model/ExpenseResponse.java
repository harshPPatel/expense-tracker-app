package com.example.expensetracker.model;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseResponse {
    private String id;
    private String title;
    private double amount;
    private Date date;

    public ExpenseResponse(String id, String title, double amount, Date date) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public static ExpenseResponse getExpenseResponse(JSONObject jsonObject) throws JSONException, ParseException {
        String id = jsonObject.getString("_id");
        String title = jsonObject.getString("title");
        double amount = jsonObject.getDouble("amount");
        String stringDate = jsonObject.getString("date");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = format.parse(stringDate);

        return new ExpenseResponse(id, title, amount, date);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof ExpenseResponse) {
            ExpenseResponse that = (ExpenseResponse) obj;
            result = true;
        }

        return result;
    }
}
