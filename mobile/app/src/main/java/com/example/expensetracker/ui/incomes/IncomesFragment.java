package com.example.expensetracker.ui.incomes;

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
import com.example.expensetracker.IncomeActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.UserDashboardActivity;
import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.IncomeResponse;
import com.example.expensetracker.ui.expenses.ExpensesAdapter;
import com.example.expensetracker.ui.expenses.ExpensesViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class IncomesFragment extends Fragment {

    private IncomesViewModel incomesViewModel;
    private final int LAUNCH_INCOME_ACTIVITY = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        incomesViewModel =
                ViewModelProviders.of(this).get(IncomesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_incomes, container, false);

        final ListView lsvIncomes = root.findViewById(R.id.lsvIncomes);

        incomesViewModel.getIncomes().observe(getViewLifecycleOwner(), new Observer<ArrayList<IncomeResponse>>() {
            @Override
            public void onChanged(final ArrayList<IncomeResponse> incomeResponses) {
                IncomesAdapter incomesAdapter = new IncomesAdapter(getContext(), R.layout.list_item_layout, incomeResponses);
                lsvIncomes.setAdapter(incomesAdapter);
                lsvIncomes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getContext(), IncomeActivity.class);
                        intent.putExtra("id", incomeResponses.get(i).getId());
                        intent.putExtra("title", incomeResponses.get(i).getTitle());
                        intent.putExtra("amount", incomeResponses.get(i).getAmount());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        intent.putExtra("date", format.format(incomeResponses.get(i).getDate()));
                        startActivityForResult(intent, LAUNCH_INCOME_ACTIVITY);
                    }
                });
            }
        });

        // Used to detect when expense edited or created so that we can refresh expenses
        ((UserDashboardActivity)getActivity()).setFragmentRefreshListener(new UserDashboardActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                incomesViewModel.refreshIncomes();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            incomesViewModel.refreshIncomes();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}