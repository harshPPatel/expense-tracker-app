package com.example.expensetracker.ui.incomes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.expensetracker.R;
import com.example.expensetracker.model.IncomeResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IncomesAdapter extends ArrayAdapter<IncomeResponse> {
    int resourceId;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public IncomesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<IncomeResponse> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        IncomeResponse income = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_layout, null);
        }

        if (income != null) {
            TextView txtTitle = convertView.findViewById(R.id.txtExpenseTitle);
            TextView txtDate = convertView.findViewById(R.id.txtIncomeDate);
            TextView txtAmount = convertView.findViewById(R.id.txtIncomeAmount);
            txtTitle.setText(income.getTitle());
            txtDate.setText(income.getDate().toString());
            // TODO: Update it to use dynamic currency symbol
            txtAmount.setText("$ " + df2.format(income.getAmount()));
        }

        return convertView;
    }
}
