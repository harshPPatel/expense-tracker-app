package com.example.expensetracker.ui.expenses;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ExpensesViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<ExpenseResponse>> expenses;
    private Model model;
    private SharedPreferences sharedPreferences;

    public ExpensesViewModel(@NonNull final Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        model = Model.getInstance(getApplication());
        expenses = new MutableLiveData<>();
        sharedPreferences = application.getSharedPreferences(application.getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            fetchExpenses(token);
        }
    }

    private void fetchExpenses(String token) {
        model.fetchAllExpenses(token, new AbstractListener() {
            @Override
            public void onFetchAllExpenses(ArrayList<ExpenseResponse> expensesList) {
                expenses.setValue(expensesList);
                Toast.makeText(getApplication(), "Fetched All Expenses Successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestFailed(JSONObject jsonError) {
                Toast.makeText(getApplication(), "ERROR while fetching expenses :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<ExpenseResponse>> getExpenses() {
        return expenses;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<ExpenseResponse>> removeExpense(final String id) {
        ArrayList<ExpenseResponse> expenseResponses = expenses.getValue();
        for (ExpenseResponse response : expenseResponses) {
            if (response.getId().equals(id)) {
                expenseResponses.remove(response);
                break;
            }
        }
        expenses.setValue(expenseResponses);
        return expenses;
    }

    public void addExpense(ExpenseResponse expense) {
        ArrayList<ExpenseResponse> tempExpenses = expenses.getValue();
        tempExpenses.add(expense);
        expenses.setValue(tempExpenses);
    }
}