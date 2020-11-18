package com.example.expensetracker.ui.expenses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.expensetracker.ExpenseActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.UserDashboardActivity;
import com.example.expensetracker.model.ExpenseResponse;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment {

    private ExpensesViewModel expensesViewModel;
    private final int LAUNCH_EXPENSE_ACTIVITY = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        expensesViewModel =
                ViewModelProviders.of(this).get(ExpensesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_expenses, container, false);

        final ListView lsvExpenses = root.findViewById(R.id.lsvExpenses);
        expensesViewModel.getExpenses().observe(getViewLifecycleOwner(), new Observer<ArrayList<ExpenseResponse>>() {
            @Override
            public void onChanged(final ArrayList<ExpenseResponse> expenseResponses) {
                ExpensesAdapter expensesAdapter = new ExpensesAdapter(getContext(), R.layout.expnese_layout, expenseResponses);
                lsvExpenses.setAdapter(expensesAdapter);
                lsvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getContext(), ExpenseActivity.class);
                        intent.putExtra("id", expenseResponses.get(i).getId());
                        intent.putExtra("title", expenseResponses.get(i).getTitle());
                        intent.putExtra("amount", expenseResponses.get(i).getAmount());
                        intent.putExtra("date", expenseResponses.get(i).getDate().toString());
                        startActivityForResult(intent, LAUNCH_EXPENSE_ACTIVITY);
                    }
                });
            }
        });

        ((UserDashboardActivity)getActivity()).setFragmentRefreshListener(new UserDashboardActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh(Object o) {
                if (o instanceof ExpenseResponse) {
                    expensesViewModel.addExpense((ExpenseResponse) o);
                }
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String id = data.getStringExtra("id");
            expensesViewModel.removeExpense(id);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}