package com.example.expensetracker.ui.incomes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.R;
import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.IncomeResponse;
import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class IncomesViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<IncomeResponse>> incomes;
    private Model model;
    private SharedPreferences sharedPreferences;
    private String token;

    public IncomesViewModel(@NonNull final Application application) {
        super(application);

        model = Model.getInstance(getApplication());
        incomes = new MutableLiveData<>();
        sharedPreferences = application.getSharedPreferences(application.getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        token = sharedPreferences.getString("token", "");
        fetchIncomes();
    }

    private void fetchIncomes() {
        if (token.isEmpty()) return;
        model.fetchAllIncomes(token, new AbstractListener() {
            @Override
            public void onFetchAllIncomes(ArrayList<IncomeResponse> incomesList) {
                incomes.setValue(incomesList);
                Toast.makeText(getApplication(), "Fetched All Incomes Successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestFailed(JSONObject jsonError) {
                Toast.makeText(getApplication(), "ERROR while fetching incomes :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<IncomeResponse>> getIncomes() {
        return incomes;
    }

    public void refreshIncomes() {
        if (!token.isEmpty()) {
            fetchIncomes();
        }
    }
}