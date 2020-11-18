package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity {

    TextView txtExpenseTitle, txtExpenseDate, txtExpenseAmount;
    String id;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private Model model;
    private SharedPreferences sharedPreferences;
    private String token;
    private int EDIT_ACTIVITY_LAUNCH_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Intent intent = getIntent();

        txtExpenseTitle = findViewById(R.id.txtExpenseTitle);
        txtExpenseDate = findViewById(R.id.txtIncomeDate);
        txtExpenseAmount = findViewById(R.id.txtIncomeAmount);

        id = intent.getStringExtra("id");

        txtExpenseTitle.setText(intent.getStringExtra("title"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        try {
            date = format.parse(intent.getStringExtra("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtExpenseDate.setText(date.toString());

        model = Model.getInstance(getApplication());

        sharedPreferences = getApplication().getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String currency = sp.getString("currency", "$");
        txtExpenseAmount.setText(currency + " " + df2.format(intent.getDoubleExtra("amount", 0)));
    }

    public void onBtnExpenseDeleteClicked(View view) {
        sharedPreferences = getApplication().getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        if (!token.isEmpty()) {
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

    public void onBtnExpensesUpdateClicked(View view) {
        Intent intent = new Intent(ExpenseActivity.this, ExpenseFormActivity.class);
        Intent parentIntent = getIntent();
        intent.putExtra("isEdit", true);
        intent.putExtra("id", parentIntent.getStringExtra("id"));
        intent.putExtra("title", parentIntent.getStringExtra("title"));
        intent.putExtra("amount", parentIntent.getDoubleExtra("amount", 0));
        intent.putExtra("date", parentIntent.getStringExtra("date"));
        startActivityForResult(intent, EDIT_ACTIVITY_LAUNCH_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ACTIVITY_LAUNCH_CODE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }
}