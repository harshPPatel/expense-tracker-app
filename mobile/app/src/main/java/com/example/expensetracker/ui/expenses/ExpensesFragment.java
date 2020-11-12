package com.example.expensetracker.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.expensetracker.R;
import com.example.expensetracker.model.ExpenseResponse;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment {

    private ExpensesViewModel expensesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        expensesViewModel =
                ViewModelProviders.of(this).get(ExpensesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_expenses, container, false);

        final ListView lsvExpenses = root.findViewById(R.id.lsvExpenses);
        expensesViewModel.getExpenses().observe(getViewLifecycleOwner(), new Observer<ArrayList<ExpenseResponse>>() {
            @Override
            public void onChanged(ArrayList<ExpenseResponse> expenseResponses) {
                ExpensesAdapter expensesAdapter = new ExpensesAdapter(getContext(), R.layout.expnese_layout, expenseResponses);
                lsvExpenses.setAdapter(expensesAdapter);
            }
        });
        return root;
    }
}