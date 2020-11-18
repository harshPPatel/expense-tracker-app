package com.example.expensetracker.ui.expenses;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.expensetracker.R;
import com.example.expensetracker.model.ExpenseResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpensesAdapter extends ArrayAdapter<ExpenseResponse> {
    int resourceId;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public ExpensesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ExpenseResponse> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ExpenseResponse expense = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_layout, null);
        }

        if (expense != null) {
            TextView txtTitle = convertView.findViewById(R.id.txtExpenseTitle);
            TextView txtDate = convertView.findViewById(R.id.txtIncomeDate);
            TextView txtAmount = convertView.findViewById(R.id.txtIncomeAmount);
            txtTitle.setText(expense.getTitle());
            txtDate.setText(expense.getDate().toString());
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
            String currency = sp.getString("currency", "$");
            txtAmount.setText(currency + " " + df2.format(expense.getAmount()));
        }

        return convertView;
    }
}
