package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpenseFormActivity extends AppCompatActivity {

    boolean isValidAmount = false, isValidTitle = false, isEdit = false;
    Button btnSaveExpense;
    private TextInputEditText ietExpenseAmount, ietExpenseTitle, ietExpenseDate;
    private TextInputLayout tilExpenseDate;
    Model model;
    SharedPreferences sharedPreferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_form);

        btnSaveExpense = (Button) findViewById(R.id.btnSaveIncome);
        btnSaveExpense.setEnabled(false);

        ietExpenseAmount = findViewById(R.id.ietIncomeAmount);
        ietExpenseTitle = findViewById(R.id.ietIncomeTitle);
        ietExpenseDate = findViewById(R.id.ietIncomeDate);
        tilExpenseDate = findViewById(R.id.tilIncomeDate);

        ietExpenseTitle.addTextChangedListener(new TitleValidator());
        ietExpenseAmount.addTextChangedListener(new AmountValidator());

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);

        model = Model.getInstance(getApplication());
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        if (isEdit) {
            String title = intent.getStringExtra("title");
            String date = intent.getStringExtra("date");
            double amount = intent.getDoubleExtra("amount", 0);
            id = intent.getStringExtra("id");
            ietExpenseTitle.setText(title);
            ietExpenseDate.setText(date);
            ietExpenseAmount.setText(String.valueOf(amount));
            btnSaveExpense.setEnabled(true);
            btnSaveExpense.setText("Update");
        }

        ietExpenseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int date = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ExpenseFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, final int year, final int month, final int date) {
                        int hour = calendar.get(Calendar.HOUR);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(ExpenseFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                Date tempDate = new Date();
                                tempDate.setHours(hour);
                                tempDate.setMinutes(minute);
                                tempDate.setSeconds(0);
                                tempDate.setDate(date);
                                tempDate.setMonth(month);
                                tempDate.setYear(year);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                ietExpenseDate.setText(format.format(tempDate));
                            }
                        }, hour, minute, false);
                        timePickerDialog.show();
                    }
                }, year, month, date);

                if (b) {
                    datePickerDialog.show();
                } else {
                    datePickerDialog.hide();
                }
            }
        });
    }


    public void onBtnSaveClicked(View view) {
        String title = ietExpenseTitle.getText().toString();
        String amountString = ietExpenseAmount.getText().toString();
        int amount = Integer.parseInt(amountString);
        String date = ietExpenseDate.getText().toString();
        String token = sharedPreferences.getString("token", "");
        if (isEdit) {
            if (!token.isEmpty() && id != null) {
                model.updateExpense(token, id, title, amount, date, new AbstractListener() {
                    @Override
                    public void onExpenseUpdated(ExpenseResponse expenseResponse) {
                        Toast.makeText(getApplicationContext(), "Updated Expense Successfully!", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override
                    public void onRequestFailed(JSONObject jsonError) {
                        Toast.makeText(getApplicationContext(), "ERROR while updating expense :(", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            if (!token.isEmpty()) {
                model.createExpense(token, title, amount, date, new AbstractListener() {
                    @Override
                    public void onExpenseCreated(ExpenseResponse expenseResponse) {
                        Toast.makeText(getApplicationContext(), "Created Expense Successfully!", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override
                    public void onRequestFailed(JSONObject jsonError) {
                        Toast.makeText(getApplicationContext(), "ERROR while creating expense :(", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public class TitleValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = charSequence.toString();
            isValidTitle = false;
            btnSaveExpense.setEnabled(false);
            if (title.isEmpty()) {
                ietExpenseTitle.setError("Title is required!");
            } else if (title.length() < 3) {
                ietExpenseTitle.setError("Title must have at least three characters.");
            } else {
                isValidTitle = true;
                ietExpenseTitle.setError(null);
                if(isValidAmount) {
                    btnSaveExpense.setEnabled(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // ignore
        }
    }

    public class AmountValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = charSequence.toString();
            isValidAmount = false;
            btnSaveExpense.setEnabled(false);
            if (amount.isEmpty()) {
                ietExpenseAmount.setError("Amount is required!");
            } else {

                try {
                    int parsedAmount = Integer.parseInt(amount);
                    if (parsedAmount < 0) {
                        ietExpenseAmount.setError("Please enter value greater than 0.");
                    } else {
                        isValidAmount = true;
                        ietExpenseAmount.setError(null);
                        if(isValidTitle) {
                            btnSaveExpense.setEnabled(true);
                        }
                    }
                } catch (Exception e) {
                    ietExpenseAmount.setError("Please enter valid amount");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // ignore
        }
    }
}