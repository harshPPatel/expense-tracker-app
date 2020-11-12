package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity {

    TextView txtExpenseTitle, txtExpenseDate, txtExpenseAmount;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Intent intent = getIntent();

        txtExpenseTitle = findViewById(R.id.txtExpenseTitle);
        txtExpenseDate = findViewById(R.id.txtExpenseDate);
        txtExpenseAmount = findViewById(R.id.txtExpenseAmount);

        Date date = new Date(intent.getStringExtra("date"));

        txtExpenseTitle.setText(intent.getStringExtra("title"));
        txtExpenseDate.setText(date.toString());
        // TODO: update currency icon dynamically
        txtExpenseAmount.setText("$ " + df2.format(intent.getDoubleExtra("amount", 0)));
    }
}