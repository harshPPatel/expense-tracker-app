package com.example.expensetracker.ui.expenses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.expensetracker.ExpenseActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.UserDashboardActivity;
import com.example.expensetracker.model.ExpenseResponse;

import java.text.SimpleDateFormat;
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
                ExpensesAdapter expensesAdapter = new ExpensesAdapter(getContext(), R.layout.list_item_layout, expenseResponses);
                lsvExpenses.setAdapter(expensesAdapter);
                lsvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getContext(), ExpenseActivity.class);
                        intent.putExtra("id", expenseResponses.get(i).getId());
                        intent.putExtra("title", expenseResponses.get(i).getTitle());
                        intent.putExtra("amount", expenseResponses.get(i).getAmount());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        intent.putExtra("date", format.format(expenseResponses.get(i).getDate()));
                        startActivityForResult(intent, LAUNCH_EXPENSE_ACTIVITY);
                    }
                });
            }
        });

        // Used to detect when expense edited or created so that we can refresh expenses
        ((UserDashboardActivity)getActivity()).setFragmentRefreshListener(new UserDashboardActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                expensesViewModel.refreshExpenses();
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            expensesViewModel.refreshExpenses();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}