package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity {

    TextView txtExpenseTitle, txtExpenseDate, txtExpenseAmount;
    String id;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Intent intent = getIntent();

        txtExpenseTitle = findViewById(R.id.txtExpenseTitle);
        txtExpenseDate = findViewById(R.id.txtExpenseDate);
        txtExpenseAmount = findViewById(R.id.txtExpenseAmount);

        id = intent.getStringExtra("id");

        Date date = new Date(intent.getStringExtra("date"));

        txtExpenseTitle.setText(intent.getStringExtra("title"));
        txtExpenseDate.setText(date.toString());
        // TODO: update currency icon dynamically
        txtExpenseAmount.setText("$ " + df2.format(intent.getDoubleExtra("amount", 0)));
    }

    public void onBtnExpenseDeleteClicked(View view) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if (!token.isEmpty()) {
            Model model = Model.getInstance(getApplication());
            model.deleteExpense(token, id, new AbstractListener() {
                @Override
                public void onRequestFailed(JSONObject jsonError) {
                    Toast.makeText(getApplicationContext(), "ERROR while deleting expense :(", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onExpenseDeleted(String id) {
                    Toast.makeText(getApplicationContext(), "Deleted Expense Successfully!", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("id", id);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            });
        }
    }
}